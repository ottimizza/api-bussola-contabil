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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "variables")
public class Variable implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "variables_sequence", sequenceName = "variables_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "variables_sequence")
	private BigInteger id;
	
	@Column(name = "fk_organizations_id", nullable = true)
    private BigInteger accountingId;
	
	@Column(length = 20)	
	private String variableCode;	//era externalId
	
	@Column(length = 100)	
	private String name;

	private String  description;
	
	@Column(name = "fk_script_id", nullable = true)
	private BigInteger  scriptId;	//accountingCode

}
