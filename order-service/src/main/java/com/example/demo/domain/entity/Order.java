package com.example.demo.domain.entity;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.example.demo.common.domain.entity.AggregateRoot;
import com.example.demo.common.domain.vo.CustomerId;
import com.example.demo.common.domain.vo.Money;
import com.example.demo.common.domain.vo.OrderId;
import com.example.demo.common.domain.vo.OrderStatus;
import com.example.demo.common.domain.vo.RestaurantId;
import com.example.demo.domain.exception.OrderDomainException;
import com.example.demo.domain.vo.OrderItemId;
import com.example.demo.domain.vo.StreetAddress;
import com.example.demo.domain.vo.TrackingId;

public class Order extends AggregateRoot<OrderId> {

	private final CustomerId customerId;

	private final RestaurantId restaurantId;

	private final StreetAddress deliveryAddress;

	private final Money price;

	private final List<OrderItem> items;

	private TrackingId trackingId;

	private OrderStatus orderStatus;

	private List<String> failureMessage;

	public void initializeOrder() {
		setId(new OrderId(UUID.randomUUID()));
		trackingId = new TrackingId(UUID.randomUUID());
		orderStatus = OrderStatus.PENDING;
		initializeOrderItems();
	}
	
	public void pay() {
		if(orderStatus != OrderStatus.PENDING) {
			throw new OrderDomainException("Order id not in correct state for pay operation"); 
		}
		
		orderStatus = OrderStatus.PAID;
	}
	
	public void approve() {
		if(orderStatus != OrderStatus.PAID) {
			throw new OrderDomainException("Order id not in correct state for approve operation"); 
		}
		orderStatus = OrderStatus.APPROVED;
	}
	
	public void initCancle() {
		
		if(orderStatus != OrderStatus.PAID) {
			throw new OrderDomainException("Order id not in correct state for initCancle operation"); 
		}
		orderStatus = OrderStatus.CANCELLING;
	}
	
	public void cancle() {
		if(!(orderStatus == OrderStatus.CANCELLING || orderStatus == OrderStatus.PENDING)) {
			throw new OrderDomainException("Order id not in correct state for Cancle operation"); 
		}
		
		orderStatus = OrderStatus.CANCELLED;
	}

	private void initializeOrderItems() {
		long itemId = 1;
		for (OrderItem orderItem : items) {
			orderItem.initializeOrderItem(super.getId(), new OrderItemId(itemId++));
		}
	}

	public void vaildateOrder() {
		vaildateInitialOrder();
		vaildateTotalPrice();
		vaildateItemPrice();
	}

	private void vaildateItemPrice() {
		Money orderItemsTotal = items.stream().map(orderItem -> {
			vaildateItemPrice(orderItem);
			return orderItem.getSubTotal();
		}).reduce(Money.ZERO, Money::add);

		if(! price.equals(orderItemsTotal)) {
			throw new OrderDomainException("Total Price is not equal to Oreder Item Price");
		}
		
	}

	private void vaildateItemPrice(OrderItem orderItem) {
		if(! orderItem.isPriceVaild()) {
			throw new OrderDomainException("Order item price is not Vaild to product");
		}

	}

	private void vaildateTotalPrice() {
		if (price == null || !price.isGraterThanZero()) {
			throw new OrderDomainException("Total Price must grater than zero");
		}

	}

	private void vaildateInitialOrder() {
		if (orderStatus != null || getId() != null) {
			throw new OrderDomainException("oreder is not correct state for initialization");
		}

	}

	private Order(Builder builder) {
		super.setId(builder.orderId);
		this.customerId = builder.customerId;
		this.restaurantId = builder.restaurantId;
		this.deliveryAddress = builder.deliveryAddress;
		this.price = builder.price;
		this.items = builder.items;
		this.trackingId = builder.trackingId;
		this.orderStatus = builder.orderStatus;
		this.failureMessage = builder.failureMessage;
	}

	public TrackingId getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(TrackingId trackingId) {
		this.trackingId = trackingId;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<String> getFailureMessage() {
		return failureMessage;
	}

	public void setFailureMessage(List<String> failureMessage) {
		this.failureMessage = failureMessage;
	}

	public CustomerId getCustomerId() {
		return customerId;
	}

	public RestaurantId getRestaurantId() {
		return restaurantId;
	}

	public StreetAddress getDeliveryAddress() {
		return deliveryAddress;
	}

	public Money getPrice() {
		return price;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private OrderId orderId;
		private CustomerId customerId;
		private RestaurantId restaurantId;
		private StreetAddress deliveryAddress;
		private Money price;
		private List<OrderItem> items = Collections.emptyList();
		private TrackingId trackingId;
		private OrderStatus orderStatus;
		private List<String> failureMessage = Collections.emptyList();

		private Builder() {
		}

		public Builder orderId(OrderId orderId) {
			this.orderId = orderId;
			return this;
		}

		public Builder customerId(CustomerId customerId) {
			this.customerId = customerId;
			return this;
		}

		public Builder restaurantId(RestaurantId restaurantId) {
			this.restaurantId = restaurantId;
			return this;
		}

		public Builder deliveryAddress(StreetAddress deliveryAddress) {
			this.deliveryAddress = deliveryAddress;
			return this;
		}

		public Builder price(Money price) {
			this.price = price;
			return this;
		}

		public Builder items(List<OrderItem> items) {
			this.items = items;
			return this;
		}

		public Builder trackingId(TrackingId trackingId) {
			this.trackingId = trackingId;
			return this;
		}

		public Builder orderStatus(OrderStatus orderStatus) {
			this.orderStatus = orderStatus;
			return this;
		}

		public Builder failureMessage(List<String> failureMessage) {
			this.failureMessage = failureMessage;
			return this;
		}

		public Order build() {
			return new Order(this);
		}
	}

}
