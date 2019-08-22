package com.wmt.drone.delivery.scheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Before;
import org.junit.Test;

import com.wmt.drone.delivery.model.Order;



public class DroneDeliverySchedulerTest {
	
	private DroneDeliveryScheduler droneDeliveryScheduler;
	
	@Before
    public void setUp() throws Exception {
		droneDeliveryScheduler = new DroneDeliveryScheduler();
    }
	
	@Test
	public void scheduleSuccess() {
		Queue<Order> orders = new LinkedList<Order>();
		orders.add(new Order("WM0001", "N1E13", LocalTime.of(6, 01, 30)));
		droneDeliveryScheduler.schedule(orders);
		assertTrue(droneDeliveryScheduler.getDeliveredOrders().size()>0);
	}
	
	@Test
	public void scheduleDetractor() {
		Queue<Order> orders = new LinkedList<Order>();
		orders.add(new Order("WM0001", "N50E50", LocalTime.of(6, 01, 30)));
		orders.add(new Order("WM0002", "S50E50", LocalTime.of(6, 02, 30)));
		orders.add(new Order("WM0003", "N50W50", LocalTime.of(6, 03, 30)));
		orders.add(new Order("WM0004", "S50E50", LocalTime.of(6, 04, 30)));
		orders.add(new Order("WM0005", "N50E50", LocalTime.of(7, 01, 30)));
		orders.add(new Order("WM0006", "S50E50", LocalTime.of(7, 02, 30)));
		orders.add(new Order("WM0007", "N50W50", LocalTime.of(7, 03, 30)));
		orders.add(new Order("WM0008", "S50E50", LocalTime.of(7, 04, 30)));
		orders.add(new Order("WM0009", "N50E50", LocalTime.of(8, 01, 30)));
		orders.add(new Order("WM0010", "S50E50", LocalTime.of(8, 02, 30)));
		orders.add(new Order("WM0011", "N50W50", LocalTime.of(8, 03, 30)));
		orders.add(new Order("WM0012", "S50E50", LocalTime.of(8, 04, 30)));
		
		droneDeliveryScheduler.schedule(orders);
		assertTrue(droneDeliveryScheduler.getDeliveredOrders().size()>0);
	}
	
	@Test
	public void getNPSScore() {
		Queue<Order> orders = new LinkedList<Order>();
		orders.add(new Order("WM0001", "N5E50", LocalTime.of(6, 01, 30)));
		orders.add(new Order("WM0002", "S7E12", LocalTime.of(6, 02, 30)));
		orders.add(new Order("WM0003", "N12W30", LocalTime.of(6, 03, 30)));
		orders.add(new Order("WM0004", "S5E2", LocalTime.of(6, 04, 30)));
		orders.add(new Order("WM0005", "N0E5", LocalTime.of(7, 01, 30)));
		
		droneDeliveryScheduler.schedule(orders);
		assertEquals(40, droneDeliveryScheduler.getNPSScore(), 0);
	}
	

}
