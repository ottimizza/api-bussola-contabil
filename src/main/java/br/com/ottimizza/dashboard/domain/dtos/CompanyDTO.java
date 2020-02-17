package br.com.ottimizza.dashboard.domain.dtos;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import br.com.ottimizza.dashboard.models.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO implements Serializable{ //Company
	
	private static final long serialVersionUID = 1L;
	
	private BigInteger id;
	private String cnpj;
	private String name;
	private Integer sector;
//	private String organizationId;
	private String externalId;
	private BigInteger accountingId;
    private BigInteger scriptId;
	private String scriptDescription;
	
	
	public static CompanyDTO entityToDto(Company entity) {

		CompanyDTO dto = new CompanyDTO();
		if(entity.getId() != null)				dto.setId(entity.getId());
		if(entity.getCnpj() != null)			dto.setCnpj(entity.getCnpj());
		if(entity.getName() != null)			dto.setName(entity.getName());
		if(entity.getSector() != null)			dto.setSector(entity.getSector());
		if(entity.getAccountingId() != null)	dto.setAccountingId(entity.getAccountingId());
		if(entity.getExternalId() != null)		dto.setExternalId(entity.getExternalId());
		if(entity.getScriptId() != null)		dto.setScriptId(entity.getScriptId());
		// 'ScriptDescription' nao tem no entity
		
		return dto;
	}
	public static List<CompanyDTO> entityToDto(List<Company> entities){
		return entities.stream().map(CompanyDTO::entityToDto).collect(Collectors.toList());
	}
	
	public static Company dtoToEntity(CompanyDTO dto) {

		Company entity = new Company();
		if(dto.getId() != null)				entity.setId(dto.getId());
		if(dto.getCnpj() != null)			entity.setCnpj(dto.getCnpj());
		if(dto.getName() != null)			entity.setName(dto.getName());
		if(dto.getSector() != null)			entity.setSector(dto.getSector());
		if(dto.getAccountingId() != null)	entity.setAccountingId(dto.getAccountingId());
		if(dto.getExternalId() != null)		entity.setExternalId(dto.getExternalId());
		if(dto.getScriptId() != null)		entity.setScriptId(dto.getScriptId());
		// 'ScriptDescription' nao tem no entity
		
		return entity;
	}
	public static List<Company> dtoToEntity(List<CompanyDTO> dtos){
		return dtos.stream().map(CompanyDTO::dtoToEntity).collect(Collectors.toList());
	}
	
	public Company patch(Company company) {
		
		if (this.name != null && !this.name.equals(""))	company.setName(this.name);
		if (this.sector != null) company.setSector(this.sector);
		
		return company;
	}
}
