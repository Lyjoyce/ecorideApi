package com.example.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.LoginRequest;
import com.example.api.entities.Actor;
import com.example.api.entities.Role;
import com.example.api.entities.Voiture;
import com.example.api.repositories.ActorRepository;
import com.example.api.repositories.AvisRepository;
import com.example.api.security.JwtUtil;
import com.example.api.services.ActorServiceImplementation;

import java.util.Objects; // ✅ Correct


@RestController
@RequestMapping("/api/v1/actor")
@CrossOrigin(origins = "*") // ou "http://127.0.0.1:3000"


public class ActorControllers {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ActorRepository actorRepository;
	@Autowired
	private ActorServiceImplementation actorServiceImplementation;
	@Autowired 
	private AvisRepository avisRepository; // TODO: utiliser pour gérer les avis
	@Autowired
	private com.example.api.repositories.VoitureRepository voitureRepository;
	@Autowired
	private com.example.api.repositories.RoleRepository roleRepository;



	@GetMapping("/test")
public String testCallActor() {
	return "ActorController ok";
}
	@PostMapping("/addNewActor")
	public ResponseEntity <?>createActor(@RequestBody Actor actor) {
		String hashedPassword = passwordEncoder.encode(actor.getPassword());
	    actor.setPassword(hashedPassword);
		try {
		Actor savedActor = actorServiceImplementation.createActor(actor);
		return ResponseEntity.ok(savedActor);
		
		 }catch(IllegalArgumentException e) {
			 return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());}
		 }
	
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
	    Optional<Actor> optionalActor = actorRepository.findByEmail(request.getEmail());

	    if (optionalActor.isPresent()) {
	        Actor actor = optionalActor.get();

	        if (passwordEncoder.matches(request.getPassword(), actor.getPassword())) {
	            String token = jwtUtil.generateToken(actor.getEmail());

	            
	            Map<String, Object> response = new HashMap<>();
	            response.put("token", token);
	            response.put("id", actor.getId());
	            response.put("firstname", actor.getFirstname());
	            response.put("lastname", actor.getLastname());
	            response.put("email", actor.getEmail());

	            return ResponseEntity.ok(response);
	        }
	    }

	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                         .body("Email ou mot de passe incorrect");
	}


	
	@GetMapping("/findAll")
	public List<Actor> getAllActors(){
		return actorRepository.findAll();
	}
	@GetMapping("/findAllActif")
	public List<Actor> getAllActiveActors(){
		return actorRepository.findByActiveTrue();
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<Actor> getActorById(@PathVariable Long id){
		return actorRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Actor> updateActor(@PathVariable Long id, @RequestBody Actor actorDetails){
		return actorRepository.findById(id)
				.map(actor->{
					actor.setLastname(actorDetails.getLastname());
					return ResponseEntity.ok(actorRepository.save(actor));
				})
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/becomeDriver/{actorId}")
	public ResponseEntity<?> becomeDriver(@PathVariable Long actorId, @RequestParam Long voitureId) {
	    Optional<Actor> optionalActor = actorRepository.findById(actorId);
	    Optional<Voiture> optionalVoiture = voitureRepository.findById(voitureId);
	    
	    if (optionalActor.isEmpty() || optionalVoiture.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Actor ou Voiture non trouvée.");
        }

	    if (optionalActor.isEmpty() || optionalVoiture.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }

	    Actor actor = optionalActor.get();
	    Voiture voiture = optionalVoiture.get();
	    if (!Objects.equals(voiture.getConducteurActor().getId(), actorId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cette voiture ne vous appartient pas.");
        }

        Role conducteurRole = roleRepository.findByName("ROLE_CONDUCTEUR");
        if (conducteurRole != null) {
            actor.getRoles().add(conducteurRole);
        }

        actorRepository.save(actor);
        return ResponseEntity.ok("L'acteur est maintenant conducteur.");
    }
	    
	    
	//soft Delete
	@PutMapping("/delete/{id}")
	public ResponseEntity<Actor> softDeleteActor(@PathVariable Long id){
		return actorRepository.findById(id)
				.map(actor->{
					actor.setActive(false);
					return ResponseEntity.ok(actorRepository.save(actor));
				})
				.orElse(ResponseEntity.notFound().build());
	}
	
}

