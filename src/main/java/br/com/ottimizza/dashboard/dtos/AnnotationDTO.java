package br.com.ottimizza.dashboard.dtos;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import br.com.ottimizza.dashboard.models.Annotation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnnotationDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private BigInteger organizationId;
	private LocalDateTime createAt;
	private String createdBy;
	private String kpiAlias;
	private String description;
	
	public Annotation patch(Annotation annotation) {
		if (this.organizationId != null)
            annotation.setOrganizationId(this.organizationId.toString());
		
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
