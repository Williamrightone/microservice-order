package com.example.demo.domain.vo;

import java.util.UUID;

import com.example.demo.common.domain.vo.BaseId;

public class TrackingId extends BaseId<UUID> {

	public TrackingId(UUID value) {
		super(value);
	}

}
