package com.oms.profile_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
		@NotBlank String customerCode,
		@NotBlank String fullName,
		@NotBlank @Email String email,
		String phone,
		@NotBlank @Size(min = 6, max = 64) String password
) {
}
