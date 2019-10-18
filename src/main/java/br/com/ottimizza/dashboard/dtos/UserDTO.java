package br.com.ottimizza.dashboard.dtos;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	BigInteger id;
	
	String username;
	String email;
	
	String avatar;
	
	String firstName;
	String lastName;
	
	Integer type;
	
//	BigInteger organizationId;
	
	OrganizationDTO organizationDTO;

}
