package com.oms.profile_service.controller;

import com.oms.profile_service.dto.AuthResponse;
import com.oms.profile_service.dto.SigninRequest;
import com.oms.profile_service.dto.SignupRequest;
import com.oms.profile_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	@ResponseStatus(HttpStatus.CREATED)
	public AuthResponse signup(@Valid @RequestBody SignupRequest request) {
		return authService.signup(request);
	}

	@PostMapping("/signin")
	public AuthResponse signin(@Valid @RequestBody SigninRequest request) {
		return authService.signin(request);
	}
}
