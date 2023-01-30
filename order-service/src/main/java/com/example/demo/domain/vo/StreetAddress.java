package com.example.demo.domain.vo;

import java.util.Objects;
import java.util.UUID;

public class StreetAddress {

	private final UUID id;

	private final String street;

	private final String psotalCode;

	private final String city;

	public StreetAddress(UUID id, String street, String psotalCode, String city) {
		this.id = id;
		this.street = street;
		this.psotalCode = psotalCode;
		this.city = city;
	}

	protected UUID getId() {
		return id;
	}

	protected String getStreet() {
		return street;
	}

	protected String getPsotalCode() {
		return psotalCode;
	}

	protected String getCity() {
		return city;
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, psotalCode, street);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StreetAddress other = (StreetAddress) obj;
		return Objects.equals(city, other.city) && Objects.equals(psotalCode, other.psotalCode)
				&& Objects.equals(street, other.street);
	}

}
