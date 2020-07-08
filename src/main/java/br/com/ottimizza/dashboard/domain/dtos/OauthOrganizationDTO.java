package br.com.ottimizza.dashboard.domain.dtos;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OauthOrganizationDTO  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String cnpj;
	private String codigoERP;
	private boolean active;
	
	private Integer type;
	private BigInteger organizationId;
}
