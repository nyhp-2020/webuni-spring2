package hu.webuni.webshop.order.dto;

import java.util.ArrayList;

import lombok.Data;

@Data
public class WebshopUserDto {
	private long id;

	private String username;

	private String password;

	private String email;
	private String facebookId;
	private ArrayList<String> roles;
}
