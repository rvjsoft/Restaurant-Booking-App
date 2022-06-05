package com.rvj.app.foodorder.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NaturalId;

import lombok.Data;

@Data
@Entity
@Table(name = "customer")
@PrimaryKeyJoinColumn(name = "id")
public class Customer extends User implements Serializable {
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;
	
	@Column(name = "phone")
	private String phone;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Address> addresses = new ArrayList<Address>();
	
	@OneToMany(mappedBy = "customer", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private List<TableBooking> bookings = new ArrayList<TableBooking>();
	
	@OneToMany(mappedBy = "customer", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private List<Order> orders = new ArrayList<Order>();
	
	public void addAddress(Address address) {
		this.addresses.add(address);
		address.setCustomer(this);
	}
	
	public void removeAddress(Address address) {
		this.addresses.remove(address);
		address.setCustomer(null);
	}
	
	public void addBooking(TableBooking booking) {
		this.bookings.add(booking);
		booking.setCustomer(this);
	}
	
	public void removeBooking(TableBooking booking) {
		this.bookings.remove(booking);
		booking.setCustomer(null);
	}
	
	public void addOrder(Order order) {
		this.orders.add(order);
		order.setCustomer(this);
	}

	public void removeOrder(Order order) {
		this.orders.remove(order);
		order.setCustomer(null);
	}
	
	public String getFullName() {
		return this.getFirstName() + " " +this.getLastName();
	}
}
