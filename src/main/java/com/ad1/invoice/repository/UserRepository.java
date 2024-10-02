package com.ad1.invoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ad1.invoice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	Optional<User> findByUsernameOrEmail(String username, String email);

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	@Query(value = "select id, regional, location, name " + "from users u inner join user_roles ur "
			+ "on u.id =ur.user_id and ur.role_id =1", nativeQuery = true)
	List<Object[]> getUser();

	@Query(value = "select id, regional, location, name " + "from users u inner join user_roles ur "
			+ "on u.id =ur.user_id " + "and ur.role_id =1 "
			+ "and upper(name) like concat('%', "+"upper(:name)"+", '%')  ", nativeQuery = true)
	List<Object[]> getUserByName(String name);
	
	Optional<User> findById(Long id);
}
