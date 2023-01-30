package com.example.demo.domain.entity;

import com.example.demo.common.domain.entity.BaseEntity;
import com.example.demo.common.domain.vo.Money;
import com.example.demo.common.domain.vo.OrderId;
import com.example.demo.domain.vo.OrderItemId;

public class OrderItem extends BaseEntity<OrderItemId> {

	private OrderId orderId;

	private final Product product;

	private final int quantity;

	private final Money price;

	private final Money subTotal;


	private OrderItem(Builder builder) {
		super.setId(builder.orderItemId);
		this.product = builder.product;
		this.quantity = builder.quantity;
		this.price = builder.price;
		this.subTotal = builder.subTotal;
	}

	public OrderId getOrderId() {
		return orderId;
	}

	public Product getProduct() {
		return product;
	}

	public int getQuantity() {
		return quantity;
	}

	public Money getPrice() {
		return price;
	}

	public Money getSubTotal() {
		return subTotal;
	}

	public OrderItem(OrderId orderId, Product product, int quantity, Money price, Money subTotal) {
		super.setId(getId());
		this.orderId = orderId;
		this.product = product;
		this.quantity = quantity;
		this.price = price;
		this.subTotal = subTotal;
	}


	public static Builder builder() {
		return new Builder();
	}


	public static final class Builder {
		private OrderItemId orderItemId;
		private Product product;
		private int quantity;
		private Money price;
		private Money subTotal;

		private Builder() {
		}

		public Builder orderItemId(OrderItemId orderItemId) {
			this.orderItemId = orderItemId;
			return this;
		}

		public Builder product(Product product) {
			this.product = product;
			return this;
		}

		public Builder quantity(int quantity) {
			this.quantity = quantity;
			return this;
		}

		public Builder price(Money price) {
			this.price = price;
			return this;
		}

		public Builder subTotal(Money subTotal) {
			this.subTotal = subTotal;
			return this;
		}

		public OrderItem build() {
			return new OrderItem(this);
		}
	}


	void initializeOrderItem(OrderId orderId, OrderItemId orderItemId) {
		this.orderId = orderId;
		super.setId(orderItemId);
		
	}
	
	boolean isPriceVaild() {
		return price.isGraterThanZero() &&
				price.equals(product.getPrice()) && 
				price.multiply(quantity).equals(subTotal);
	}

}
