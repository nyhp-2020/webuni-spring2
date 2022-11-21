package hu.webuni.student.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

public class UserInfo extends User {

	@Getter
	private List<Long> courseIds;
	
	public UserInfo(String username, String password,
			Collection<? extends GrantedAuthority> authorities, List<Long> courseIds) {
		super(username, password, authorities);
		this.courseIds = courseIds;
	}
	

}
