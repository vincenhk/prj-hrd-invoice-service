package com.ad1.invoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ad1.invoice.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(String name);
}
