# Atrify Coding assignment

## Reference Documentation

 - Start server locally: `mvn spring-boot:run`
 - Build deployable image: `mvn package`

## Task description

Jim works in the production facility of a premium online donut retailer. He is responsible for
bringing donuts to the pickup counter once they’re done. But the manager is not satisfied
because either it takes too long before a delivery arrives, or only a few items arrive. Jim’s
manager wants to fix this and asks Jim to write a web service that accepts the orders and
provides a list of items to deliver to the pickup counter. Desperate Jim remembers his friend
who is working in a software company as a software engineer ... and your phone rings. Can
you help Jim?

### Problem Statement

Jim explains to you that he has a bunch of requirements for the priority queue and the web
service. The constraints he was given are:

 - The service should implement a RESTful interface
 - All orders will be placed in a single queue 
 - Each order is comprised of the ID of the client and the requested quantity of donuts
 - A client can only place one order and existing orders cannot be modified (only
canceled)
 - Client IDs are in the range of 1 to 20000 and customers with IDs less than 1000 are
premium customers
 - Orders are sorted by the number of seconds they are in the queue
 - Orders from premium customers always have a higher priority than orders from
regular customers

Jim is supposed to look at the queue every 5 minutes and bring as many orders to
the pickup counter as possible. His cart holds up to 50 donuts and he should put as
many orders into his cart as possible without skipping, changing, or splitting any
orders.

This leads to the following list of requirements for the endpoints:
 - An endpoint for adding items to the queue. This endpoint must take two parameters,
the ID of the client and the quantity
 - An endpoint for the client to check his queue position and approximate wait time.
Counting starts at 1.
 - An endpoint which allows his manager to see all entries in the queue with the
approximate wait time
 - An endpoint to retrieve his next delivery which should be placed in the cart
 - An endpoint to cancel an order. This endpoint should accept only the client ID

Endpoints should follow REST best practices. Parameters should be passed in the fashion
that most closely aligns with REST principles.

Please provide a Java program which accomplishes the above along with a build script.
