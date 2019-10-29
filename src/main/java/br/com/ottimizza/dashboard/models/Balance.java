package br.com.ottimizza.dashboard.models;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;

import javax.persistence.Basic;
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
	@Column(scale = 0, nullable = false)
	@SequenceGenerator(name = "balance_sequence", sequenceName = "balance_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "balance_sequence")
	private BigInteger id;

	@Column(length = 40, nullable = false)
	private String syntheticId;
	
	@Column(length = 40, nullable = false)
	private String analyticId;
	
	@Column
	private String description;
	
	@Column(nullable = false)
	private Double initialValue;
	
	@Column(precision = 10, scale = 2)
	private Double debitValue;

	@Basic
	private Double creditValue;
	
	@Column(nullable = false)
	private Double finalValue;
	
	private LocalDate dateBalance;

	@Column(name = "fk_company_id")
	private BigInteger companyId;
}
