package com.wmt.drone.delivery.test;

import org.junit.Test;

import com.wmt.drone.delivery.DroneDeliveryApplication;

public class DroneDeliveryApplicationTest {

	private String inputFileName = "src/test/resources/testinput1.txt";
	private String outputFileName = "src/test/resources/outputtest1.txt";

	@Test
	public void mainTest() {
		DroneDeliveryApplication.main(new String[] { inputFileName, outputFileName });
	}

}
