package hu.webuni.webshop.user.dto;

import java.util.ArrayList;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import lombok.Data;

@Data
public class WebshopUserDto {
	private long id;
	@NotNull
	@NotEmpty
	private String username;
	@NotNull
	@NotEmpty
	private String password;
	@NotNull
	@NotEmpty
	@Email
	private String email;
	private String facebookId;
	private ArrayList<String> roles;
}
