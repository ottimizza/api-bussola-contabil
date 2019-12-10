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
	private BigInteger id;
	private String externalId;
	private String name;
	private Integer type;
	private String cnpj;
	private String codigoERP;
	private String email;
	private String avatar;
	private BigInteger organizationId;

}
