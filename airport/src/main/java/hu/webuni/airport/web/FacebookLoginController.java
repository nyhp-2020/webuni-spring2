package hu.webuni.airport.web;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import hu.webuni.airport.service.UserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FacebookLoginController {
	
	private final UserService userService;

	@RequestMapping("/fbLoginSuccess")
	public String onFacebookLoginSuccess(Map<String,Object> model,OAuth2AuthenticationToken authenticationToken,
			@AuthenticationPrincipal OAuth2User principal) {
		
		userService.registerNewUserIfNeeded(authenticationToken);
		String fullName = authenticationToken.getPrincipal().getAttribute("name");
		fullName = principal.getAttribute("name");
		model.put("fullName", fullName);
		return "home";
	}

}
