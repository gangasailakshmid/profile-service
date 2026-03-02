package com.oms.profile_service.dto;

public record AuthResponse(
		String message,
		String token,
		ProfileResponse profile
) {
}
