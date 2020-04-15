package br.com.ottimizza.dashboard.domain.dtos;

import java.io.Serializable;
import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilterOrganizationDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private BigInteger id;
	private String name;
	private Integer type;
	private String cnpj;
	private String cnpjAccounting;
	private BigInteger organizationId;
	private BigInteger scriptId; 
	
}
