package br.com.ottimizza.dashboard.models;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companies")
public class Company implements Serializable {
    
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigInteger id;
    
    @Column(name = "cnpj", unique = true, nullable = false)
    private String cnpj;

    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "sector", nullable = true)
    private Integer sector;

    // external_id que vem do accounts
    @Column(name = "fk_organization_id")
    private String organizationId;
    
    // organization_Id que vem do accounts
    @Column(name = "accounting_id")
    private BigInteger accountingId;
    
    @Column(name = "fk_Script_Type")
    private BigInteger scriptType;
    
}
