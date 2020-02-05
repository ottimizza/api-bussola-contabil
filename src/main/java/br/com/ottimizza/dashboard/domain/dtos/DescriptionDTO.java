package br.com.ottimizza.dashboard.domain.dtos;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ottimizza.dashboard.models.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DescriptionDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private BigInteger id;
	private BigInteger accountingId;	//organizationId;
	private String kpiAlias;
    private String cnpj;
	private String description;
	private BigInteger scriptId; 	   //scriptType; 
	private String title;
	private Integer graphOrder;
	private String chartType;
	private Boolean visible;
	private List<DescriptionDTO> descriptions;
	
	private String scriptDescription;


    public static DescriptionDTO descriptionToDto(Description description) {
    	DescriptionDTO dto = new DescriptionDTO();
    	
		if(description.getId() != null)				dto.setId(description.getId());
		if(description.getCnpj() != null)			dto.setCnpj(description.getCnpj());
    	if(description.getKpiAlias() != null)		dto.setKpiAlias(description.getKpiAlias());
    	if(description.getAccountingId() != null)	dto.setAccountingId(description.getAccountingId());
    	if(description.getDescription() != null)	dto.setDescription(description.getDescription());
    	if(description.getScriptId() != null) 		dto.setScriptId(description.getScriptId());
		if(description.getTitle() != null) 			dto.setTitle(description.getTitle());
		if(description.getGraphOrder() != null) 	dto.setGraphOrder(description.getGraphOrder());
		if(description.getChartType() != null) 		dto.setChartType(description.getChartType());
		if(description.getVisible() != null) 		dto.setVisible(description.getVisible());
		
		return dto;
    }
    public static List<DescriptionDTO> descriptionToDto(List<Description> description){
		return description.stream().map(DescriptionDTO::descriptionToDto).collect(Collectors.toList());
	}
    

    public static Description dtoToDescription(DescriptionDTO dto) {
    	Description description = new Description();
    	
    	if(dto.getId() != null)				description.setId(dto.getId());
		if(dto.getCnpj() != null)			description.setCnpj(dto.getCnpj());
    	if(dto.getKpiAlias() != null)		description.setKpiAlias(dto.getKpiAlias());
    	if(dto.getAccountingId() != null)	description.setAccountingId(dto.getAccountingId());
    	if(dto.getDescription() != null)	description.setDescription(dto.getDescription());
		if(dto.getScriptId() != null) 		description.setScriptId(dto.getScriptId());
		if(dto.getTitle() != null) 			description.setTitle(dto.getTitle());
		if(dto.getGraphOrder() != null) 	description.setGraphOrder(dto.getGraphOrder());
		if(dto.getChartType() != null) 		description.setChartType(dto.getChartType());
		if(dto.getVisible() != null) 		description.setVisible(dto.getVisible());
		
    	return description;
    }
    
    public static List<Description> dtoToDescription(List<DescriptionDTO> dtos){
		return dtos.stream().map(DescriptionDTO::dtoToDescription).collect(Collectors.toList());
	}
    
    public Description patch(Description description) {
		if (this.accountingId != null)	description.setAccountingId(this.accountingId);
		if (this.cnpj != null)			description.setCnpj(this.cnpj);
    	if (this.kpiAlias != null)		description.setKpiAlias(this.kpiAlias);
		if (this.description != null)	description.setDescription(this.description);
		if (this.scriptId != null)		description.setScriptId(this.scriptId);
		if (this.title != null)			description.setTitle(this.title);
		if (this.graphOrder != null)	description.setGraphOrder(this.graphOrder);
		if (this.chartType != null)		description.setChartType(this.chartType);
		if (this.visible != null)		description.setVisible(this.visible);
    	
    	return description;
    }
}