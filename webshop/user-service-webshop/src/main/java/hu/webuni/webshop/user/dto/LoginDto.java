package hu.webuni.webshop.user.dto;


import lombok.Data;

@Data
public class LoginDto {
	private String username;
	private String password;
	private String fbToken;
}
