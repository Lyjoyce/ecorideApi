package com.example.api.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.entities.Actor;
import com.example.api.repositories.ActorRepository;
import com.example.api.services.ActorServiceImplementation;

@RestController
@RequestMapping("/api/v1/actor")
@CrossOrigin
public class ActorController {
	@Autowired
	private ActorRepository actorRepository;
	@Autowired
	private ActorServiceImplementation actorServiceImplementation;

	@GetMapping("/test")
public String testCallActor() {
	return "ActorController ok";
}
	@PostMapping("/addNewActor")
	public ResponseEntity <?>createActor(@RequestBody Actor actor) {
		try {
		Actor savedActor = actorServiceImplementation.createActor(actor);
		return ResponseEntity.ok(savedActor);
		
		 }catch(IllegalArgumentException e) {
			 return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());}
		 }
		
	@GetMapping("/findAll")
	public List<Actor> getAllActors(){
		return actorRepository.findAll();
	}
	@GetMapping("/findAllActif")
	public Actor getAllActiveActors(){
		return actorRepository.findByIsActiveTrue();
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
	//soft Delete
	@PutMapping("/delete/{id}")
	public ResponseEntity<Actor> deleteSoftly(@PathVariable Long id){
		return actorRepository.findById(id)
				.map(actor->{
					actor.setActive(false);
					return ResponseEntity.ok(actorRepository.save(actor));
				})
				.orElse(ResponseEntity.notFound().build());
	}	
	
}
