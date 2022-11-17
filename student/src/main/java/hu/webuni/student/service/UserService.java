package hu.webuni.student.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import hu.webuni.student.model.StudentUser;
import hu.webuni.student.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final OAuth2AuthorizedClientService authClientService;
	private final UserRepository userRepository;

	public void registerNewUserIfNeeded(OAuth2AuthenticationToken authenticationToken) {
		
		String userId = authenticationToken.getName();
		System.out.println("FB id:" + userId);
		
		OAuth2User oauth2User = authenticationToken.getPrincipal();
		System.out.println("FB id from oauth2User:" + oauth2User.getName());
		Object email = oauth2User.getAttribute("email");
		System.out.println("email:" + email);
		System.out.println("full name:" + oauth2User.getAttribute("name"));
		
		String authorizedClientRegistrationId = authenticationToken.getAuthorizedClientRegistrationId(); //facebook,google,stb.
	
		OAuth2AuthorizedClient client = authClientService.loadAuthorizedClient(authorizedClientRegistrationId, userId);
		System.out.println("access token:" + client.getAccessToken().getTokenValue());
		
		Optional<StudentUser> optionalExsistingUser = userRepository.findByFacebookId(userId);
		if(optionalExsistingUser.isEmpty()) {
			StudentUser newUser = new StudentUser(email.toString(),"",Set.of("user"));
			newUser.setFacebookId(userId);
			userRepository.save(newUser);
		}
	}

}
