package br.com.ottimizza.dashboard.dtos;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDTO {
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
