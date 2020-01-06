package br.com.ottimizza.dashboard.models;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
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
@Table(name = "balances", indexes = {@Index(name = "balance_index", columnList = "id", unique = true)})

public class Balance implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(nullable = false)
	@SequenceGenerator(name = "balance_sequence", sequenceName = "balance_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "balance_sequence")
	private BigInteger id;

	@Column(length = 40, nullable = false)
	private String syntheticId;
	
	@Column(length = 40, nullable = false)
	private String analyticId;
	
	private String description;
	
	@Column(nullable = false)
	private Double initialValue;
	
	@Column(nullable = false)
	private Double finalValue;
	
	private Double debitValue;
	
	private Double creditValue;
	
	private LocalDate dateBalance;

	@Column(name = "fk_company_id")
	private BigInteger companyId;
	
	private boolean active;
}
