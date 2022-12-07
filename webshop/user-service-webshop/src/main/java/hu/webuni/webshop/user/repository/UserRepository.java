package hu.webuni.webshop.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.webshop.user.model.WebshopUser;

public interface UserRepository extends JpaRepository<WebshopUser, Long>{
	boolean existsByUsername(String username);
	Optional<WebshopUser> findByUsername(String username);
	Optional<WebshopUser> findByFacebookId(String facebookId);
}
