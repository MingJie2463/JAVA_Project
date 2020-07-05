package com.jayfan.store.domain;

import java.util.List;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

@Entity
@Table(name = "orders")
public class Orders {

	@Id
	//@GeneratedValue
	private String id;

	@Column(name = "order_date")
	private Long orderDate;

	// 1=待確認, 0=已確認
	@Column(name = "status")
	private int status = 1;

	// 訂單金額
	@Column(name = "total")
	private double total;

	/*
	 * 
	 * @OneToMany(mappedBy = "orders", cascade = {CascadeType.ALL}, fetch =
	 * FetchType.EAGER) private List<OrderLineItem> orderLineItems;
	 * 
	 * 
	 * 
	 * public void addLineItem(OrderLineItem orderLineItem) {
	 * orderLineItem.setOrders(this); orderLineItems.add(orderLineItem); }
	 * 
	 * 
	 * public List<OrderLineItem> getOrderLineItems() { return orderLineItems; }
	 * 
	 * 
	 * public void setOrderLineItems(List<OrderLineItem> orderLineItems) {
	 * this.orderLineItems = orderLineItems; }
	 * 
	 */

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Long orderDate) {
		this.orderDate = orderDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}
