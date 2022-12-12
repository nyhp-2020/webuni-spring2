package hu.webuni.webshop.order.model;

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

//import hu.webuni.webshop.order.model.WsOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@RequiredArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebshopUser {
	
	@Id
	@GeneratedValue
	@ToString.Include
	@EqualsAndHashCode.Include
	private long id;
	@ToString.Include
	@EqualsAndHashCode.Include()
	private String username;
	private String password;
	private String email;
	private String facebookId;
	
//	@OneToMany(mappedBy = "webshopUser")
//	Set<WsOrder> wsorders;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> roles;

}
