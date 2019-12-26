package com.rvj.app.foodorder.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rvj.app.foodorder.entity.converters.OrderStatusConverter;
import com.rvj.app.foodorder.entity.enums.OrderStatus;

import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {
	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "ordered_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderedOn;
	
	@Column(name = "status")
	@Convert(converter = OrderStatusConverter.class)
	private OrderStatus status;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "res_id")
	private Restaurant restaurant;
	
	@OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<OrderItem> items = new ArrayList<OrderItem>();
	
	public void addItem(OrderItem item) {
		this.items.add(item);
		item.setOrder(this);
	}
	
	public void removeItem(OrderItem item) {
		this.items.remove(item);
		item.setOrder(null);
	}
	
}
