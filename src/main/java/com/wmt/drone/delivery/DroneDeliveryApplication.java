package com.wmt.drone.delivery;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.wmt.drone.delivery.io.InputOrders;
import com.wmt.drone.delivery.io.OutputDeliveries;
import com.wmt.drone.delivery.model.Order;
import com.wmt.drone.delivery.scheduler.DroneDeliveryScheduler;

/**
 * Drone Delivery Challenge
 * 
 * @author abhinai
 *
 */
public class DroneDeliveryApplication {

	/**
	 * Default Input File Path
	 */
	public static String inputFilePath = "/Users/muthyalalaharini/eclipse-workspace/drone-delivery-challenge/input1.txt";
	/**
	 * Default Output File Path
	 */
	public static String output = "/Users/muthyalalaharini/eclipse-workspace/drone-delivery-challenge/output1.txt";

	/**
	 * Main method/Start Method for Drone Delivery Challenge
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> orderLines = null;
		String outputFilePath;
		if (args != null && args.length == 2) {
			orderLines = InputOrders.getOrdersFromFile(args[0]);
			outputFilePath = args[1];
		} else {
			orderLines = InputOrders.getOrdersFromFile(inputFilePath);
			outputFilePath = output;
//			System.out.println("Invalid Arguments");
//			return;
		}

		// Create tasks queue to store incoming orders
		Queue<Order> orders = new LinkedList<>();

		for (String cur : orderLines) {
			orders.offer(parseDroneOrder(cur)); // Read orders from file
		}
		//Instantiate Scheduler for processing the orders and deliver
		DroneDeliveryScheduler scheduler = new DroneDeliveryScheduler();
		scheduler.schedule(orders);
		//Output with orders in given output file along with Net Promoter Score
		OutputDeliveries.outputDeliveredOrders(outputFilePath, scheduler.getDeliveredOrders(), scheduler.getNPSScore());
	}

	/**
	 * Parse a String Order to a Order Object
	 * 
	 * @param line String Order
	 * @return Order
	 */
	
	private static Order parseDroneOrder(String line) {
		String[] orderLine = line.split(" ");
		Order order = new Order(orderLine[0], orderLine[1], LocalTime.parse(orderLine[2]));
		return order;
	}

}
