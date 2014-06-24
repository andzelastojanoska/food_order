package com.seavus.foodorder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "ordered_foods")
public class OrderedFood {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "quantity")
	private int quantity;
	

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@ManyToOne
	@JoinColumn(name = "food_id")
	private Food food;
	
	public OrderedFood() {}
	
	public OrderedFood(Order order, Food food) {
		this.order = order;
		this.food = food;
		this.quantity = 1;
	}
	
	public OrderedFood(Order order, Food food, int quantity) {
		this.order = order;
		this.food = food;
		this.quantity = quantity;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public Food getFood() {
		return this.food;
	}
}
