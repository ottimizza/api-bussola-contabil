package br.com.ottimizza.dashboard.models;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class KpiCreate implements Serializable{

	private String nkpi;
	private List<KpiDetail> kpis;
	
}
