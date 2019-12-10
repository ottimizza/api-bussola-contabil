package br.com.ottimizza.dashboard.dtos;

import java.io.Serializable;
import java.math.BigInteger;

import br.com.ottimizza.dashboard.models.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private BigInteger id;
	private String cnpj;
	private String name;
	private Integer sector;
	private BigInteger organizationId;
	private BigInteger ScriptType;
	
	
	public Company patch(Company company) {
		
		if (this.name != null && !this.name.equals(""))	company.setName(this.name);
		if (this.sector != null) company.setSector(this.sector);
		
		return company;
	}
}
