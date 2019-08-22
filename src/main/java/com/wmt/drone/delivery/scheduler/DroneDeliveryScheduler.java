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

	public void schedule(Queue<Order> orders) {
		// Edge case: if there is no orders then return
		if (orders.isEmpty())
			return;
		queue.addAll(orders);

		while (!queue.isEmpty()) {
			if (queue.peek().getOrderTimeStamp().isAfter(END_TIME) || !canDeliverBeforeEndTime()) {
				queue.remove();
				continue;
			}
			// Process current order
			Order order = queue.poll();
			System.out.println(order.getOrderID());
			OrderDelivery orderDelivery = new OrderDelivery(order.getOrderID());
			orderDelivery.setOrderCreatedTime(order.getOrderTimeStamp());
			if (this.droneReturnTime != null) {
				orderDelivery.setOrderDepartureTime(this.droneReturnTime);
			} else if (order.getOrderTimeStamp().isAfter(START_TIME)) {
				orderDelivery.setOrderDepartureTime(order.getOrderTimeStamp());
			} else {
				orderDelivery.setOrderDepartureTime(START_TIME);
			}
			orderDelivery
					.setOrderDeliveredTime(orderDelivery.getOrderDepartureTime().plus(durationToReachOrder(order)));
			this.droneReturnTime = orderDelivery.getOrderDepartureTime().plus(durationToReachOrder(order))
					.plus(durationToReturnWareHouse(order));
			calculateNPS(orderDelivery);
			deliveredOrders.add(orderDelivery);

		}
	}

	private Rating calculateNPS(OrderDelivery orderDelivery) {
		Rating rating = getRating(Duration
				.between(orderDelivery.getOrderCreatedTime(), orderDelivery.getOrderDeliveredTime()).toHours());
		if (Rating.PROMOTERS.equals(rating))
			promoters++;
		else if (Rating.DETRACTORS.equals(rating))
			detractors++;
		return rating;
	}

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
	 * @return minutes a drone will take to go from factory to order location
	 */
	private Duration durationToReachOrder(Order order) {
		return Duration.ofMinutes(order.getDistance());
	}

	/**
	 * @return minutes a drone will take to go from factory to order location
	 */
	private Duration durationToReturnWareHouse(Order order) {
		return Duration.ofMinutes(order.getDistance());
	}

	/**
	 * @return minutes to fulfill an order, which is defined as minutes required to:
	 *         go from factory (pick up order), deliver at order location, come back
	 *         to factory
	 */
	private Duration durationToFulfillOrder(Order order) {
		Duration durationToReachOrder = durationToReachOrder(order);
		return durationToReachOrder.plus(durationToReachOrder);
	}

	/**
	 * Calculate which category the watingTime belongs to. All the calculations are
	 * second based.
	 * 
	 * @param waitingTime in seconds
	 * @return category, including "Invalid", "Promoters", "Neutral", "Detractors".
	 */
	private static Rating getRating(long waitingTime) {
		if (waitingTime <= PROMOTERS) // shorter than 1 hour
			return Rating.PROMOTERS;
		else if (waitingTime <= NEUTRAL) // shorter than 3 hours
			return Rating.NEUTRAL;
		else
			return Rating.DETRACTORS;
	}

	public List<OrderDelivery> getDeliveredOrders() {
		return deliveredOrders;
	}

	public double getNPSScore() {
		return getDeliveredOrders().size() > 0 ? ((promoters - detractors) / (double) getDeliveredOrders().size()) * 100
				: 0;
	}

}
