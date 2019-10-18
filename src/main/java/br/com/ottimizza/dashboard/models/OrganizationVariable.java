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
@Table(name= "organizations_variables")
public class OrganizationVariable implements Serializable {

	@Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "org_variables_sequence", sequenceName = "org_variables_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "org_variables_sequence")
	private BigInteger id;
	
	@Column(name = "fk_variables_id", nullable = false)
	private BigInteger variableId;

	@Column(name = "fk_organizations_id", nullable = false)
	private BigInteger organizationId;
	
	private String  accountingCode;

}
