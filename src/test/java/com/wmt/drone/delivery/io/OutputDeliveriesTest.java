package com.wmt.drone.delivery.io;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.wmt.drone.delivery.model.OrderDelivery;

public class OutputDeliveriesTest {
	
	@Test
	public void outputDeliveredOrders() {
		OrderDelivery orderDelivery = new OrderDelivery("WM0001");
		orderDelivery.setOrderDepartureTime(LocalTime.of(07, 30, 50));
		List<OrderDelivery> deliveredOrders = new ArrayList<OrderDelivery>();
		deliveredOrders.add(orderDelivery);
		OutputDeliveries.outputDeliveredOrders("src/test/resources/outputtest1.txt", deliveredOrders, 50);
	}

}
