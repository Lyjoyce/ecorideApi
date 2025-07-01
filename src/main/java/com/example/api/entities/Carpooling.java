package com.example.api.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Carpooling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromCity;
    private String toCity;
    private String departureLocation;
    private String arrivalLocation;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;
    private boolean energy;
    private String preferences; // ex: "voyage écologique, autopilot"


    @JsonIgnore
    @ManyToMany
    private List<Actor> passagers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "conducteur_id")
    private Actor conducteur;

    private double price;
    private double note;
    private int duree;
    private List<String> jour = new ArrayList<>();

    @Column(nullable = false)
    private boolean annule = false;

    @OneToMany(mappedBy = "carpooling", cascade = CascadeType.ALL)
    private List<Avis> avisList = new ArrayList<>();

    @OneToMany(mappedBy = "carpooling", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatAvailable> reservations = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "voiture_id")
    private Voiture voiture;

	private Integer seatAvailable;

    // Constructeur vide obligatoire pour JPA
    public Carpooling() {}

    // Constructeur complet
    public Carpooling(String fromCity, String toCity, String departureLocation, String arrivalLocation,
                      LocalDate departureDate, LocalTime departureTime, LocalDate arrivalDate, LocalTime arrivalTime,
                      String option, boolean energy, Actor conducteur, double price, int note,
                      int duree, boolean annule, Voiture voiture) {
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.energy = energy;
        this.conducteur = conducteur;
        this.price = price;
        this.note = note;
        this.duree = duree;
        this.annule = annule;
        this.voiture = voiture;
    }

    // Exemples getters/setters
    public Long getId() {
        return id;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public void setArrivalLocation(String arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public boolean isEnergy() {
        return energy;
    }

    public void setEnergy(boolean energy) {
        this.energy = energy;
    }

    public List<Actor> getPassagers() {
        return passagers;
    }

    public void setPassagers(List<Actor> passagers) {
        this.passagers = passagers;
    }

    public Actor getConducteur() {
        return conducteur;
    }

    public void setConducteur(Actor conducteur) {
        this.conducteur = conducteur;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double  note) {
        this.note = note;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public boolean isAnnule() {
        return annule;
    }

    public void setAnnule(boolean annule) {
        this.annule = annule;
    }

    public List<Avis> getAvisList() {
        return avisList;
    }

    public void setAvisList(List<Avis> avisList) {
        this.avisList = avisList;
    }

    public List<SeatAvailable> getReservations() {
        return reservations;
    }

    public void setReservations(List<SeatAvailable> reservations) {
        this.reservations = reservations;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    // Calcul dynamique du nombre de places disponibles
    
    
    public Integer getSeatAvailable() {
        if (voiture == null) {
            return 0;
        }
        int nbPlacesTotales = voiture.getNbPlacesDisponibles();
        int nbReservations = (reservations != null) ? reservations.size() : 0;
        return nbPlacesTotales - nbReservations;
    }
    // Pas de setter pour seatAvailable (calculé automatiquement)
    
    public List<String> getJour() {
        return jour;
    }

    public void setJour(List<String> jour) {
        this.jour = jour;
    }

	public void setSeatAvailable(int nbPlacesDisponibles) {
		this.seatAvailable = nbPlacesDisponibles;
		
	}

	public String getPreferences() {
		return preferences;
	}

	public void setPreferences(String preferences2) {
		this.preferences= preferences;
		
	}

}
