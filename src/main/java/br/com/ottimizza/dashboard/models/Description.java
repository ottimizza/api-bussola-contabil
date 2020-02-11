package br.com.ottimizza.dashboard.models;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "description")
public class Description implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false)
    @SequenceGenerator(name = "descriptions_sequence", sequenceName = "descriptions_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "descriptions_sequence")
    private BigInteger id;
	
	@Column(name = "fk_accounting_id")
	private BigInteger accountingId;	//organizationId;
	
	@Column(nullable = false)
	private String kpiAlias;

	private String description;
	
	@Column(name = "fk_script_id")
	private BigInteger scriptId;		//scriptType;

	private String title;
	
	private Integer graphOrder;
	
	private String chartType;

	private String cnpj;
	
	@Column(columnDefinition = "boolean default true")
    private Boolean visible;
    
    
}
