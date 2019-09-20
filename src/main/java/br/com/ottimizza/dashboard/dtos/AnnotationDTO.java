package br.com.ottimizza.dashboard.dtos;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AnnotationDTO implements Serializable{
		
	private String organizationId;
	private LocalDateTime createAt;
	private String createdBy;
	private String kpiAlias;
	private String description;
	    
}
