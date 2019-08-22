package com.wmt.drone.delivery.model;

import java.time.LocalTime;

public class OrderDelivery {
	
	public OrderDelivery(String orderID) {
		this.orderID = orderID;
	}
	
	private String orderID;
	
	private LocalTime orderDeliveredTime;
	
	private LocalTime orderDepartureTime;
	
	private LocalTime orderCreatedTime;

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public LocalTime getOrderDeliveredTime() {
		return orderDeliveredTime;
	}

	public void setOrderDeliveredTime(LocalTime orderDeliveredTime) {
		this.orderDeliveredTime = orderDeliveredTime;
	}

	public LocalTime getOrderDepartureTime() {
		return orderDepartureTime;
	}

	public void setOrderDepartureTime(LocalTime orderDepartureTime) {
		this.orderDepartureTime = orderDepartureTime;
	}

	public LocalTime getOrderCreatedTime() {
		return orderCreatedTime;
	}

	public void setOrderCreatedTime(LocalTime orderCreatedTime) {
		this.orderCreatedTime = orderCreatedTime;
	}

}
