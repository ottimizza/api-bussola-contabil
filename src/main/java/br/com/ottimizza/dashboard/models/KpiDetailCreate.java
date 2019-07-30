package br.com.ottimizza.dashboard.models;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class KpiDetailCreate implements Serializable{

	private BigInteger idKpi;
	private List<KpiDetail> kpisDetail;
	
}
