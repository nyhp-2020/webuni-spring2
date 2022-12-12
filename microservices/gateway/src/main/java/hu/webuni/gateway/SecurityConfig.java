package hu.webuni.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

//E nélkül a gateway CSRF tokent követelne meg a kéréseknél,
//pl. már a login kérésnél is (mert az is POST).
//Látható, hogy filterChain beant kell itt is gyártani,
//viszont @EnableWebFluxSecurity annotáció kell a konfig osztályra,
//mert a Spring Cloud Gateway Webflux alapú.

@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		http.csrf(csrf -> csrf.disable());
		return http.build();
	}
}
