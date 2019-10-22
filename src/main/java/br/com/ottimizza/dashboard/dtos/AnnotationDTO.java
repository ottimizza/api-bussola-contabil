package br.com.ottimizza.dashboard.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.com.ottimizza.dashboard.models.Annotation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnnotationDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private String organizationId;
	private LocalDateTime createAt;
	private String createdBy;
	private String kpiAlias;
	private String description;
	
	
	public Annotation patch(Annotation annotation) {
		if (this.organizationId != null && !this.organizationId.equals(""))
            annotation.setOrganizationId(this.organizationId);
		
		if (this.createAt != null)
            annotation.setCreateAt(this.createAt);
		
		if (this.createdBy != null && !this.createdBy.equals(""))
            annotation.setCreatedBy(this.createdBy);
		
		if (this.kpiAlias != null && !this.kpiAlias.equals(""))
            annotation.setKpiAlias(this.kpiAlias);
		
		if (this.description != null && !this.description.equals(""))
            annotation.setDescription(this.description);
		
		return annotation;
	}	
	    
}
