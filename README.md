# rewards-system
This project is a simple Spring boot REST API that calculates **reward points** earned by customers based on their monthly purchases.

# rewards logic
**2 points** for every dollar spent over $100
**1 point** for every dollar spent between $50 and $100.
no points for transaction below $50

# tech stack
- Java 17
- Springboot 3
- RESTFul APIs
- Lombok
- Junit5
- Maven

  # api endpoints
  POST /api/rewards
  - Accepts a list of transactions in JSON
  - - Returns reward points per customer for last 3 months.
