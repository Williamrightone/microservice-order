package com.example.demo.domain.entity;

import com.example.demo.common.domain.entity.BaseEntity;
import com.example.demo.common.domain.vo.Money;
import com.example.demo.common.domain.vo.ProductId;

public class Product extends BaseEntity<ProductId> {

	private String name;

	private Money price;

	public Product(ProductId productId, String name, Money price) {
		super.setId(productId);
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public Money getPrice() {
		return price;
	}
	

}
