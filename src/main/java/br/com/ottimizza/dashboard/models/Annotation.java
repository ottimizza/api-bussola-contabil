package br.com.ottimizza.dashboard.models;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "annotations")
public class Annotation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "annotations_sequence", sequenceName = "annotations_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "annotations_sequence")
    private BigInteger id;
	
	@Column(name = "fk_organizations_id", nullable = false)
    private String organizationId;
	
   	private LocalDateTime createAt;
    
    @Column(name = "created_by", nullable = false)
    private String createdBy;
	
    @Column(name = "kpi_alias", nullable = false)
    private String kpiAlias;

    @Column(name = "description", nullable = true)
    private String description;

}
