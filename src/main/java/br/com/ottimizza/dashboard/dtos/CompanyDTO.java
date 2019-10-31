package br.com.ottimizza.dashboard.dtos;

import java.io.Serializable;

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
	
	private String cnpj;
	private String name;
	private Integer sector;

	
	public Company patch(Company company) {
		
		if (this.name != null && !this.name.equals(""))	company.setName(this.name);
		if (this.sector != null) company.setSector(this.sector);
		
		return company;
	}
}
