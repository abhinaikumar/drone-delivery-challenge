package com.wmt.drone.delivery.io;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;


public class InputOrdersTest {
	
	@Test
	public void getOrdersFromFileTest() {
		List<String> ordersFromFile = InputOrders.getOrdersFromFile("src/test/resources/testinput1.txt");
		assertFalse(ordersFromFile.isEmpty());
		
	}
	
	@Test
	public void getOrdersFromFileNoFile() {
		List<String> ordersFromFile = InputOrders.getOrdersFromFile("src/test/resources/testinput2.txt");
		
		assertTrue(ordersFromFile == null);
		
	}

}
