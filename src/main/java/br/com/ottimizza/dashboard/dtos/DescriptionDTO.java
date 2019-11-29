package br.com.ottimizza.dashboard.dtos;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import br.com.ottimizza.dashboard.models.Description;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DescriptionDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private BigInteger id;
	private String organizationId;
	private String kpiAlias;
    private String description;

    public static DescriptionDTO descriptionToDto(Description description) {
    	DescriptionDTO dto = new DescriptionDTO();
    	
		if(description.getId() != null)				dto.setId(description.getId());
    	if(description.getKpiAlias() != null)		dto.setKpiAlias(description.getKpiAlias());
    	if(description.getOrganizationId() != null)	dto.setOrganizationId(description.getOrganizationId());
    	if(description.getDescription() != null)	dto.setDescription(description.getDescription());
    	
    	return dto;
    }
    public static List<DescriptionDTO> descriptionToDto(List<Description> description){
		return description.stream().map(DescriptionDTO::descriptionToDto).collect(Collectors.toList());
	}
    

    public static Description dtoToDescription(DescriptionDTO dto) {
    	Description description = new Description();
    	
    	if(dto.getId() != null)				description.setId(dto.getId());
    	if(dto.getKpiAlias() != null)		description.setKpiAlias(dto.getKpiAlias());
    	if(dto.getOrganizationId() != null)	description.setOrganizationId(dto.getOrganizationId());
    	if(dto.getDescription() != null)	description.setDescription(dto.getDescription());
    	
    	return description;
    }
    
    public static List<Description> dtoToDescription(List<DescriptionDTO> dtos){
		return dtos.stream().map(DescriptionDTO::dtoToDescription).collect(Collectors.toList());
	}
    
    
    
    public Description patch(Description description) {
    	
    	//implementar
    	
    	return description;
    }
}