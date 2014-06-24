package com.seavus.foodorder.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@Entity
@Table(name = "restaurants")
public class Restaurant {

	public void setFoods(Set<Food> foods) {
		this.foods = foods;
	}

	public Set<Food> getFoods() {
		return foods;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;
	
	@ManyToOne(cascade = javax.persistence.CascadeType.ALL)
	@JoinColumn(name = "NAME_ID")
	private LocalisedRestaurantNames nameStrings = new LocalisedRestaurantNames();

	@Column(name = "phone_number")
	private String phoneNumber;

	public LocalisedRestaurantNames getNameStrings() {
		return nameStrings;
	}

	public void setNameStrings(LocalisedRestaurantNames nameStrings) {
		this.nameStrings = nameStrings;
	}

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToMany(fetch= FetchType.EAGER, mappedBy = "restaurant")
	@Cascade({CascadeType.SAVE_UPDATE ,CascadeType.DELETE})
	private Set<Food> foods = new HashSet<>();
	
	@OneToMany(mappedBy = "restaurant")
	private Set<Rating> ratings = new HashSet<>();

	public Restaurant() {
	}

	public Restaurant(String name, String phone) {
		this.name = name;
		this.phoneNumber = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName(String locale) {
		return this.nameStrings.getString(locale);
	}

	public void setName(String locale, String name) {
		this.nameStrings.addString(locale, name);
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getId() {
		return id;
	}

}
