package com.oms.profile_service.dto;

public record ProfileResponse(
		Long id,
		String customerCode,
		String fullName,
		String email,
		String phone
) {
}
