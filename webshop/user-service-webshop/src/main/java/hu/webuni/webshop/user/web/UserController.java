package hu.webuni.webshop.user.web;

import java.util.ArrayList;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.webshop.user.dto.LoginDto;
import hu.webuni.webshop.user.dto.WebshopUserDto;
import hu.webuni.webshop.user.mapper.UserMapper;
import hu.webuni.webshop.user.model.WebshopUser;
import hu.webuni.webshop.user.repository.UserRepository;
import hu.webuni.webshop.user.security.FacebookLoginService;
import hu.webuni.webshop.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
	private static final String DEFAULT_ROLE = "customer";

	private final UserRepository userRepository;

	private final UserMapper userMapper;

	private final UserService userService;

	private final PasswordEncoder passwordEncoder;

	private final FacebookLoginService facebookLoginService;

	@GetMapping("/{id}")
	public WebshopUserDto findById(@PathVariable("id") long id) {
		return userMapper.webshopUserToDto(
				userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
	}

	@PostMapping
	public WebshopUserDto createWebshopUser(@Valid @RequestBody WebshopUserDto userDto) {
//		String username = userDto.getUsername();
//		String password = userDto.getPassword();
//		String email = userDto.getEmail();
//		if( username == null || username.isEmpty() ||
//			password == null || password.isEmpty() ||
//			email == null || email.isEmpty()) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//		}
		ArrayList<String> roles = new ArrayList<String>();
		roles.add(DEFAULT_ROLE);
		userDto.setRoles(roles);
		String encodedPassword = passwordEncoder.encode(userDto.getPassword());
		userDto.setPassword(encodedPassword);
		WebshopUser user = userService.createNewWebshopUser(userMapper.dtoToWebshopUser(userDto));
//		WebshopUser user = userService.save(userMapper.dtoToWebshopUser(userDto));
		return userMapper.webshopUserToDto(user);
	}

	@PostMapping("/facebook")
	public WebshopUserDto createWebshopUserWithFacebookToken(@RequestBody LoginDto loginDto) {
		String fbToken = loginDto.getFbToken();
		if (ObjectUtils.isEmpty(fbToken)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		WebshopUser user = userService.createWebShopUserWithFacebookToken(fbToken, DEFAULT_ROLE);
		return userMapper.webshopUserToDto(user);
	}

}
