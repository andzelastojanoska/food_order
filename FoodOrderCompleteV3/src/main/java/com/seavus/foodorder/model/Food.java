package com.seavus.foodorder.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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

@Entity
@Table(name = "foods")
public class Food {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "NAME_ID")
	private LocalisedFoodNames nameStrings = new LocalisedFoodNames();

	@Column(name = "type")
	private String type;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "TYPE_ID")
	private LocalisedFoodTypes typeStrings = new LocalisedFoodTypes();

	@Column(name = "price")
	private double price;

	@OneToMany(mappedBy = "food")
	private Set<OrderedFood> orderedFoods = new HashSet<OrderedFood>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	public Food() {}

	public Food(String name, String type, double price, Restaurant restaurant) {
		this.name = name;
		this.type = type;
		this.price = price;
		this.restaurant = restaurant;
	}

	public Food(double price, Restaurant restaurant) {
		this.price = price;
		this.restaurant = restaurant;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getType(String locale) {
		return this.typeStrings.getString(locale);
	}

	public void setType(String locale, String type) {
		this.typeStrings.addString(locale, type);
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPrice(String locale) {
		if (locale.equals("MK")) {
			return getPrice() * 45.25;
		} else {
			return getPrice();
		}
	}

	public String getType() {
		return this.type;
	}

	public Long getId() {
		return this.id;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

}
