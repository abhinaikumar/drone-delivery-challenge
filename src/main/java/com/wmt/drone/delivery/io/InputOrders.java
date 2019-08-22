package com.wmt.drone.delivery.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputOrders {
	
	public static List<String> getOrdersFromFile(String filePath){
		
		try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
			//1. filter line 3
			//2. convert all content to upper case
			//3. convert it into a List
			return stream
					.filter(line -> line.startsWith("WM"))
					.map(String::toUpperCase)
					.collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
