package com.example.demo.domain.exception;

import com.example.demo.common.domain.exception.DomainException;

public class OrderDomainException extends DomainException {

	public OrderDomainException(String message) {
		super(message);
	}

	public OrderDomainException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
