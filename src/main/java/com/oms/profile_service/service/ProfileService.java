package com.oms.profile_service.service;

import com.oms.profile_service.dto.ChangePasswordRequest;
import com.oms.profile_service.dto.ProfileRequest;
import com.oms.profile_service.dto.ProfileResponse;
import com.oms.profile_service.entity.Profile;
import com.oms.profile_service.exception.BadRequestException;
import com.oms.profile_service.exception.ResourceNotFoundException;
import com.oms.profile_service.repository.ProfileRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

	private final ProfileRepository profileRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public List<ProfileResponse> getAll() {
		return profileRepository.findAll().stream().map(this::map).toList();
	}

	@Transactional(readOnly = true)
	public ProfileResponse getById(Long id) {
		return map(findEntity(id));
	}

	@Transactional
	public ProfileResponse create(ProfileRequest request) {
		validateUnique(request.customerCode(), request.email(), null);
		if (request.password() == null || request.password().isBlank()) {
			throw new BadRequestException("password is required while creating profile");
		}

		Profile profile = new Profile();
		apply(request, profile);
		profile.setPasswordHash(passwordEncoder.encode(request.password()));
		profile.setCreatedAt(LocalDateTime.now());
		return map(profileRepository.save(profile));
	}

	@Transactional
	public ProfileResponse update(Long id, ProfileRequest request) {
		Profile profile = findEntity(id);
		validateUnique(request.customerCode(), request.email(), id);
		apply(request, profile);
		return map(profileRepository.save(profile));
	}

	@Transactional
	public void changePassword(Long id, ChangePasswordRequest request) {
		Profile profile = findEntity(id);
		if (!passwordEncoder.matches(request.currentPassword(), profile.getPasswordHash())) {
			throw new BadRequestException("Current password is incorrect");
		}
		if (passwordEncoder.matches(request.newPassword(), profile.getPasswordHash())) {
			throw new BadRequestException("New password must be different from current password");
		}
		profile.setPasswordHash(passwordEncoder.encode(request.newPassword()));
		profileRepository.save(profile);
	}

	@Transactional
	public void delete(Long id) {
		profileRepository.delete(findEntity(id));
	}

	@Transactional(readOnly = true)
	public Profile findEntity(Long id) {
		return profileRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Profile not found: " + id));
	}

	@Transactional(readOnly = true)
	public Profile findByEmail(String email) {
		return profileRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("Profile not found: " + email));
	}

	private void validateUnique(String customerCode, String email, Long currentId) {
		profileRepository.findByCustomerCode(customerCode).ifPresent(existing -> {
			if (currentId == null || !existing.getId().equals(currentId)) {
				throw new BadRequestException("customerCode already exists: " + customerCode);
			}
		});
		profileRepository.findByEmail(email).ifPresent(existing -> {
			if (currentId == null || !existing.getId().equals(currentId)) {
				throw new BadRequestException("email already exists: " + email);
			}
		});
	}

	private void apply(ProfileRequest request, Profile profile) {
		profile.setCustomerCode(request.customerCode());
		profile.setFullName(request.fullName());
		profile.setEmail(request.email());
		profile.setPhone(request.phone());
	}

	public ProfileResponse map(Profile profile) {
		return new ProfileResponse(
				profile.getId(),
				profile.getCustomerCode(),
				profile.getFullName(),
				profile.getEmail(),
				profile.getPhone()
		);
	}
}
