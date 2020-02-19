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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chart_options")
public class ChartOption implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(nullable = false)
	@SequenceGenerator(name = "options_sequence", sequenceName = "options_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "options_sequence")
	private BigInteger id;
	
	private String chartType;
	
	private Short style;	//darkmode etc
	
	@Column(length = 600)
	private String option;

}
