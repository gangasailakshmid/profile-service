package com.oms.profile_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ProfileRequest(
		@NotBlank String customerCode,
		@NotBlank String fullName,
		@NotBlank @Email String email,
		String phone,
		String password
) {
}
