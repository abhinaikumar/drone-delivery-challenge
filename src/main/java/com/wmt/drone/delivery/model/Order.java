package com.wmt.drone.delivery.model;

import java.time.LocalTime;

public class Order {

	public Order(String orderID, String orderDirection, LocalTime orderTimeStamp) {
		this.orderID = orderID;
		this.orderDirection = orderDirection;
		this.orderTimeStamp = orderTimeStamp;
		setDistance(orderDirection);
	}

	public Order() {
	}

	private String orderID;

	private String orderDirection;

	private LocalTime orderTimeStamp;

	private int distance;

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
		setDistance(orderDirection);
	}

	public LocalTime getOrderTimeStamp() {
		return orderTimeStamp;
	}

	public void setOrderTimeStamp(LocalTime orderTimeStamp) {
		this.orderTimeStamp = orderTimeStamp;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(String orderDirection) {
		this.distance = parseDistance(orderDirection);
	}

	private int parseDistance(String orderDirection) {
		String[] tokens = orderDirection.split("\\D+");
		int distance = 0;
		for (String token : tokens) {
			try {
				distance += Integer.valueOf(token);
			} catch (Exception ignored) {
			}
		}
		return distance;
	}

}
