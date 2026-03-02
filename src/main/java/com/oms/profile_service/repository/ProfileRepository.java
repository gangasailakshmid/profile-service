package com.oms.profile_service.repository;

import com.oms.profile_service.entity.Profile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
	Optional<Profile> findByCustomerCode(String customerCode);

	Optional<Profile> findByEmail(String email);

	boolean existsByCustomerCode(String customerCode);

	boolean existsByEmail(String email);
}
