package hu.webuni.webshop.user.service;

import java.util.HashSet;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.webshop.user.model.WebshopUser;
import hu.webuni.webshop.user.repository.UserRepository;
import hu.webuni.webshop.user.security.FacebookLoginService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	private final FacebookLoginService facebookLoginService;
	
	
	@Transactional
	public WebshopUser save(WebshopUser user) {
		return userRepository.save(user);
	}
	
	@Transactional
	public WebshopUser update(WebshopUser user) {
		if(userRepository.existsById(user.getId()))
				return userRepository.save(user);
		else
			throw new NoSuchElementException();
	}
	
	@Transactional
	public void delete(long id) {
		userRepository.deleteById(id);
	}
	
	@Transactional
	public WebshopUser createWebShopUserWithFacebookToken(String fbToken,String role) {
		WebshopUser user = facebookLoginService.createWebshopUserForToken(fbToken);
		HashSet<String> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		return userRepository.save(user);
	}

}
