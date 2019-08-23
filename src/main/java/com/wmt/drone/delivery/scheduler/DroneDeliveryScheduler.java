package com.wmt.drone.delivery.scheduler;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import com.wmt.drone.delivery.model.Order;
import com.wmt.drone.delivery.model.OrderDelivery;

public class DroneDeliveryScheduler {

	private PriorityQueue<Order> queue;

	private int promoters;
	private int detractors;

	private LocalTime droneReturnTime;

	private List<OrderDelivery> deliveredOrders = new ArrayList<OrderDelivery>();

	public DroneDeliveryScheduler() {
		// Comparator let shorter distance order goes first
		queue = new PriorityQueue<Order>((a, b) -> a.getDistance() - b.getDistance()); 
		promoters = 0;
		detractors = 0;
	}

	private static LocalTime START_TIME = LocalTime.of(6, 0, 0);
	private static LocalTime END_TIME = LocalTime.of(22, 0, 0);

	public final static int PROMOTERS = 1;
	public final static int NEUTRAL = 3;
	public final static int DETRACTORS = 3;

	/**
	 * Schedule the order, store timestamps and calculate NPS
	 * @param orders
	 */
	public void schedule(Queue<Order> orders) {
		// Edge case: if there is no orders then return
		if (orders.isEmpty())
			return;
		// Add all orders to queue for processing
		queue.addAll(orders);

		while (!queue.isEmpty()) {
			//check if order is within time range and can be completed before end time
			if (queue.peek().getOrderTimeStamp().isAfter(END_TIME) || !canDeliverBeforeEndTime()) {
				queue.remove();
				continue;
			}
			// Process current order
			Order order = queue.poll();
			//Create delivery object for each order to record timestamps
			OrderDelivery orderDelivery = new OrderDelivery(order.getOrderID());
			orderDelivery.setOrderCreatedTime(order.getOrderTimeStamp());
			if (this.droneReturnTime != null) {
				//set the previous order return time as departure time of current order
				orderDelivery.setOrderDepartureTime(this.droneReturnTime);
			} else if (order.getOrderTimeStamp().isAfter(START_TIME)) {
				orderDelivery.setOrderDepartureTime(order.getOrderTimeStamp());
			} else {
				//orders arrived before start time
				orderDelivery.setOrderDepartureTime(START_TIME);
			}
			orderDelivery
					.setOrderDeliveredTime(orderDelivery.getOrderDepartureTime().plus(durationToReachOrder(order)));
			//return time would be drone reaching the warehouse after order delivered
			this.droneReturnTime = orderDelivery.getOrderDepartureTime().plus(durationToReachOrder(order))
					.plus(durationToReturnWareHouse(order));
			calculateNPS(orderDelivery);
			deliveredOrders.add(orderDelivery);

		}
	}

	/**
	 * Calculate Net Promoter Score
	 * @param orderDelivery
	 * @return
	 */
	private Rating calculateNPS(OrderDelivery orderDelivery) {
		Rating rating = getRating(Duration
				.between(orderDelivery.getOrderCreatedTime(), orderDelivery.getOrderDeliveredTime()).toHours());
		if (Rating.PROMOTERS.equals(rating))
			promoters++;
		else if (Rating.DETRACTORS.equals(rating))
			detractors++;
		return rating;
	}

	/**
	 * @return true/false - can be delivered before end time
	 */
	private boolean canDeliverBeforeEndTime() {

		Order order = queue.peek();
		LocalTime estimatedDeliveryTime;
		if (this.droneReturnTime != null) {
			estimatedDeliveryTime = this.droneReturnTime.plus(durationToFulfillOrder(order));
		} else {
			estimatedDeliveryTime = START_TIME.plus(durationToFulfillOrder(order));
		}
		if(estimatedDeliveryTime.isAfter(END_TIME)) {
			detractors++;
			return false;
		}
		return true;
	}

	/**
	 * @return duration in minutes drone takes to go from factory to order location
	 */
	private Duration durationToReachOrder(Order order) {
		return Duration.ofMinutes(order.getDistance());
	}

	/**
	 * @return duration in minutes drone reaches back warehouse after delivering order
	 */
	private Duration durationToReturnWareHouse(Order order) {
		return Duration.ofMinutes(order.getDistance());
	}

	/**
	 * @return duration minutes to fulfill an order, duration to reach order location plus return back to warehouse
	 */
	private Duration durationToFulfillOrder(Order order) {
		Duration durationToReachOrder = durationToReachOrder(order);
		return durationToReachOrder.plus(durationToReachOrder);
	}

	/**
	 * Categorize the ratings to the orders based on waitTime
	 * 
	 * @param waitTime in seconds
	 * @return rating result "Promoters", "Neutral", "Detractors".
	 */
	private static Rating getRating(long waitTime) {
		if (waitTime <= PROMOTERS) // shorter than 1 hour
			return Rating.PROMOTERS;
		else if (waitTime <= NEUTRAL) // shorter than 3 hours and more than 1 hour
			return Rating.NEUTRAL;
		else
			return Rating.DETRACTORS;
	}

	public List<OrderDelivery> getDeliveredOrders() {
		return deliveredOrders;
	}

	/**
	 * @return calculate NPS score based on number of orders delivered and ratings
	 */
	public double getNPSScore() {
		return getDeliveredOrders().size() > 0 ? ((promoters - detractors) / (double) getDeliveredOrders().size()) * 100
				: 0;
	}

}
