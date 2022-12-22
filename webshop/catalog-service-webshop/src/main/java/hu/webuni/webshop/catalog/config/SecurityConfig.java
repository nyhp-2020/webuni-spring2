package hu.webuni.webshop.catalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import hu.webuni.tokenlib.JwtAuthFilter;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig /*extends WebSecurityConfigurerAdapter*/{
	
	@Autowired
	JwtAuthFilter jwtAuthFilter; //Tokenlib-ből
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //oauth2 höz ki kell kapcsolni
			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/api/categories/**").hasAuthority("admin")
			.antMatchers(HttpMethod.PUT, "/api/categories/**").hasAuthority("admin")
			.antMatchers(HttpMethod.DELETE, "/api/categories/**").hasAuthority("admin")
			.antMatchers(HttpMethod.POST, "/api/products/**").hasAuthority("admin")
			.antMatchers(HttpMethod.PUT, "/api/products/**").hasAuthority("admin")
			.antMatchers(HttpMethod.DELETE, "/api/products/**").hasAuthority("admin")
			.anyRequest().denyAll();
			//			.antMatchers("/oauth2/**").permitAll()
//			.antMatchers("/api/login/**").permitAll()
//			.antMatchers("/api/users").permitAll()
//			.antMatchers("/api/categories/**").permitAll()
////			.antMatchers("/api/stomp/**").permitAll()
////			.antMatchers("/services/**").permitAll()
////			.antMatchers(HttpMethod.POST, "/api/courses/**").hasAuthority("TEACHER")
////			.antMatchers(HttpMethod.PUT, "/api/courses/**").hasAuthority("TEACHER")
//			.anyRequest().authenticated()
////			.and()
////			.oauth2Login()
//			;
		
		http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}
