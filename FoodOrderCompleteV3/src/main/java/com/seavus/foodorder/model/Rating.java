package com.seavus.foodorder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ratings")
public class Rating {
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "rating")
	private int rating;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;
	
	public Rating(int rating, Employee employee, Restaurant restaurant) {
		this.employee = employee;
		this.restaurant = restaurant;
		this.rating = rating;
	}
	
	public Rating() {}
	
	public Employee getEmployee() {
		return employee;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}	
}
