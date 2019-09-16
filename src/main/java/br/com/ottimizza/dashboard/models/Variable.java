package br.com.ottimizza.dashboard.models;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Variable {

	@Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "variables_sequence", sequenceName = "variables_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "variables_sequence")
	private BigInteger id;
	
	@Column(name = "fk_organizations_id", nullable = false)
    private BigInteger companyId;
	
	private String externalId;
	
	private String  name;

	private String  description;
	
	private String  accountingCode;

}
