
# Notification Service

A microservice built with **Java Spring Boot**, **MongoDB**, and **RabbitMQ** that allows sending notifications (EMAIL, SMS, IN_APP) to users, with support for retrying failed deliveries.

---

## Features

- REST API to send and fetch notifications
- Asynchronous messaging using RabbitMQ
- Retry logic (via Spring Retry)
- MongoDB for persistent storage of notifications
- JSON-based message conversion

---

## Tech Stack

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Web + Spring Data MongoDB**
- **Spring AMQP (RabbitMQ)**
- **Spring Retry**
- **Lombok**
- **MongoDB**
- **RabbitMQ**

---

## Project Structure

```
notification-service/
├── controller/               # REST API layer
├── model/                    # Data models (Notification, NotificationRequest, etc.)
├── queue/                    # RabbitMQ producer and consumer
├── repository/               # MongoDB repository interface
├── service/                  # Notification delivery logic (with retry)
├── config/                   # RabbitMQ config
├── NotificationServiceApplication.java
└── application.properties
```

---

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/notification-service.git
cd notification-service
```

### 2. Start Dependencies

#### Start MongoDB

Make sure MongoDB is running at `mongodb://localhost:27017`

#### Start RabbitMQ

If using Docker:
```bash
docker run -d --hostname rabbit --name rabbitmq   -p 5672:5672 -p 15672:15672   rabbitmq:3-management
```
Access RabbitMQ UI at: [http://localhost:15672](http://localhost:15672) (Login: `guest / guest`)

### 3. Configure Application

In `src/main/resources/application.properties`:

```properties
spring.application.name=notification-service

# MongoDB
spring.data.mongodb.uri=mongodb://localhost:27017/notification_service

# RabbitMQ
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

### 4. Run the App

```bash
mvn clean install
mvn spring-boot:run
```

---

## 🔌 API Endpoints

### 🔸 Send Notification
`POST /notifications`

**Request Body:**
```json
{
  "userId": "123",
  "message": "Your order has shipped!",
  "type": "EMAIL"
}
```

### 🔸 Get User Notifications
`GET /notifications/users/{userId}/notifications`

**Response:**
```json
[
  {
    "id": "...",
    "userId": "123",
    "message": "Your order has shipped!",
    "type": "EMAIL",
    "status": "SENT",
    "timestamp": "2025-05-18T18:05:12.492"
  }
]
```

---

## Retry Logic

The service uses `@Retryable` on notification delivery. If a simulated failure occurs, it will retry up to 3 times with a 2-second delay between attempts.



If all retries fail, the notification status is set to `FAILED`.

---

## Testing with Postman

1. Start the app and RabbitMQ
2. Send a POST to `http://localhost:8080/notifications`
3. Confirm logs show delivery attempt or retry
4. Check MongoDB `notifications` collection

---

## Author

**Vedant Awasthi (22052432)**  
Built as part of a backend notification service assignment using Spring Boot.

---

## License

This project is free and open source.
