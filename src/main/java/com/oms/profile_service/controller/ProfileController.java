package com.oms.profile_service.controller;

import com.oms.profile_service.dto.ChangePasswordRequest;
import com.oms.profile_service.dto.ProfileRequest;
import com.oms.profile_service.dto.ProfileResponse;
import com.oms.profile_service.service.ProfileService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class ProfileController {

	private final ProfileService profileService;

	@GetMapping
	public List<ProfileResponse> getAll() {
		return profileService.getAll();
	}

	@GetMapping("/{id}")
	public ProfileResponse getById(@PathVariable Long id) {
		return profileService.getById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProfileResponse create(@Valid @RequestBody ProfileRequest request) {
		return profileService.create(request);
	}

	@PutMapping("/{id}")
	public ProfileResponse update(@PathVariable Long id, @Valid @RequestBody ProfileRequest request) {
		return profileService.update(id, request);
	}

	@PutMapping("/{id}/password")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changePassword(@PathVariable Long id, @Valid @RequestBody ChangePasswordRequest request) {
		profileService.changePassword(id, request);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		profileService.delete(id);
	}
}
