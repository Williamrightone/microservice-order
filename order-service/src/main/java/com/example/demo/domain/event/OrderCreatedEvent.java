package com.example.demo.domain.event;

import java.time.ZonedDateTime;

import com.example.demo.common.domain.event.DomainEvent;
import com.example.demo.domain.entity.Order;

public class OrderCreatedEvent implements DomainEvent<Order> {

	private final Order order;

	private final ZonedDateTime createdAt;

	public OrderCreatedEvent(Order order, ZonedDateTime createdAt) {
		this.order = order;
		this.createdAt = createdAt;
	}

	public Order getOrder() {
		return order;
	}

	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

}
