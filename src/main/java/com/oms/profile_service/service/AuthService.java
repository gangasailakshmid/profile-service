package com.oms.profile_service.service;

import com.oms.profile_service.dto.AuthResponse;
import com.oms.profile_service.dto.ProfileRequest;
import com.oms.profile_service.dto.SigninRequest;
import com.oms.profile_service.dto.SignupRequest;
import com.oms.profile_service.entity.Profile;
import com.oms.profile_service.exception.BadRequestException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final ProfileService profileService;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public AuthResponse signup(SignupRequest request) {
		ProfileRequest createRequest = new ProfileRequest(
				request.customerCode(),
				request.fullName(),
				request.email(),
				request.phone(),
				request.password()
		);
		var created = profileService.create(createRequest);
		String token = generateToken(created.id(), created.email());
		return new AuthResponse("Signup successful", token, created);
	}

	@Transactional(readOnly = true)
	public AuthResponse signin(SigninRequest request) {
		Profile profile = profileService.findByEmail(request.email());
		if (!passwordEncoder.matches(request.password(), profile.getPasswordHash())) {
			throw new BadRequestException("Invalid email or password");
		}
		String token = generateToken(profile.getId(), profile.getEmail());
		return new AuthResponse("Signin successful", token, profileService.map(profile));
	}

	private String generateToken(Long profileId, String email) {
		return "token-" + profileId + "-" + UUID.nameUUIDFromBytes(email.getBytes());
	}
}
