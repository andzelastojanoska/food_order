package com.seavus.foodorder.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "note")
	private String note;

	@Column(name = "date")
	@Type(type = "date")
	private Date date;

	@Column(name = "total")
	private double total;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@OneToMany(mappedBy = "order")
	private Set<OrderedFood> orderedFoods = new HashSet<>();
	
	public Order() {}

	public Order(Date date, Employee employee, double total) {
		this.date = date;
		this.employee = employee;
		this.total = total;
	}
	
	public Long getId() {
		return this.id;
	}

	public Set<OrderedFood> getOrderFoods() {
		return this.orderedFoods;
	}

	public void addOrderedFood(OrderedFood food) {
		this.orderedFoods.add(food);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Employee getEmployee() {
		return employee;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}
