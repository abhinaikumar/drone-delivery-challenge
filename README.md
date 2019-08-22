### Drone Delivery Challenge

##### Overview

Implement an algorithm to schedule drone-carried deliveries to customers in a small town. The town is organized in a perfect grid, with a warehouse and drone-launch facility at the center. All deliveries originate at the warehouse and are carried by a drone to a customer location. A drone's "ground speed" is exactly one horizontal or vertical grid block per minute.
Your homework assignment is to design and write a drone launch scheduling program that maximizes the net promoter score (NPS) amongst drone-delivery customers. Net promoter score is defined as the percentage of promoters minus the percentage of detractors. See the diagram below to understand the relationship between delivery speed and customer feedback responses. The town owns one drone, which is allowed to operate from 6 a.m. until 10 p.m.

##### Assumptions

* Drone can carry only one order at a time and has to return to warehouse to pick-up another one. Downtime for load and drop is negligible.
* Orders received after 10PM are ignored, however if order is received before 10PM and couldn't deliver before 10PM is considered as detractor.
* Orders are predefined and available in input file before execution and orders cannot be inserted later. Didn't consider streaming live data as NPS score to be provided for demo purpose.
* Valid input data.

##### Build and Execute Project
Go to project folder and execute below commands

Package to Executable Jar

``mvn clean package``

Execute - Inside target folder

``java -jar drone-delivery-challenge-0.0.1-SNAPSHOT-jar-with-dependencies.jar /Users/muthyalalaharini/eclipse-workspace/drone-delivery-challenge/input1.txt /Users/muthyalalaharini/eclipse-workspace/drone-delivery-challenge/output1.txt``


