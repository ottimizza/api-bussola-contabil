package br.com.ottimizza.dashboard.dtos;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	BigInteger id;
	String externalId;
	String name;
	String type;
	String cnpj;
	String codigoERP;
	String email;
	String avatar;
	String organizationId;

}
