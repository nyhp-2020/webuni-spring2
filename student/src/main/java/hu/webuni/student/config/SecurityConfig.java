package hu.webuni.student.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import hu.webuni.student.security.JwtAuthFilter;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig /*extends WebSecurityConfigurerAdapter */{
	
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtAuthFilter jwtAuthFilter;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
//			.httpBasic()
//			.and()
			.csrf().disable()
//			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //oauth2 höz ki kell kapcsolni
//			.and()
			.authorizeRequests()
			.antMatchers("/oauth2/**").permitAll()
			.antMatchers("/api/login/**").permitAll()
			.antMatchers("/api/stomp/**").permitAll()
			.antMatchers("/services/**").permitAll()
			.antMatchers(HttpMethod.POST, "/api/courses/**").hasAuthority("TEACHER")
			.antMatchers(HttpMethod.PUT, "/api/courses/**").hasAuthority("TEACHER")
			.anyRequest().authenticated()
			.and()
			.oauth2Login()
			;
		
		http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.authenticationProvider(authenticationProvider());
////		auth.inMemoryAuthentication()
////			.passwordEncoder(passwordEncoder())
////			.withUser("user").authorities("user").password(passwordEncoder().encode("pass"))
////			.and()
////			.withUser("admin").authorities("user", "admin").password(passwordEncoder().encode("pass"));
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
////			.httpBasic()
////			.and()
//			.csrf().disable()
////			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////			.and()
//			.authorizeRequests()
//			.antMatchers("/oauth2/**").permitAll()
//			.antMatchers("/fbLoginSuccess").permitAll()
//			.antMatchers("/api/login/**").permitAll()
//			.antMatchers("/api/stomp/**").permitAll()
//			.antMatchers(HttpMethod.POST, "/api/students/**").hasAuthority("admin")
//			.antMatchers(HttpMethod.PUT, "/api/students/**").hasAnyAuthority("user", "admin")
//			.anyRequest().authenticated()
//			.and()
//			.oauth2Login()
//			.defaultSuccessUrl("/fbLoginSuccess", true)
//			;
//		
//		http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		return daoAuthenticationProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authManagerBuilder.authenticationProvider(authenticationProvider());
		return authManagerBuilder.build();
	}

//	@Override
//	@Bean
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
	

}
