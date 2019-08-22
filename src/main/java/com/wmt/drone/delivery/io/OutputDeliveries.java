package com.wmt.drone.delivery.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.wmt.drone.delivery.model.OrderDelivery;

public class OutputDeliveries {
	
	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	public static void outputDeliveredOrders(String outputFilePath, List<OrderDelivery> deliveredOrders, double npsScore) {

		Path path = Paths.get(outputFilePath);

		// Use try-with-resource to get auto-closeable writer instance
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			deliveredOrders.stream().forEach(deliveredOrder -> {
				try {
					writer.write(String.format("%s%n",
							deliveredOrder.getOrderID() + " " + deliveredOrder.getOrderDepartureTime().format(formatter)));
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			writer.write(String.format("%s%n", "NPS " + npsScore));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
