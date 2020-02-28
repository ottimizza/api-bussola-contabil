package br.com.ottimizza.dashboard.domain.dtos;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ottimizza.dashboard.models.Kpi;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WebChartDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private Integer kind;
	
	private String cnpj;
	
	private BigInteger id;
	private String title;
	private String kpiAlias;
		
	private String labelArray;
	private String chartType; 
	private String chartOptions;
	private boolean visible;
	
	private String subtitle;
	private String companyName;
	
	public WebChartDTO() {
		super();
	}

	public WebChartDTO(Integer kind, String cnpj, BigInteger id, String title, String kpiAlias, String labelArray,
			String chartType, String chartOptions, boolean visible, String subtitle, String companyName) {
		super();
		this.kind = kind;
		this.cnpj = cnpj;
		this.id = id;
		this.title = title;
		this.kpiAlias = kpiAlias;
		this.labelArray = labelArray;
		this.chartType = chartType;
		this.chartOptions = chartOptions;
		this.visible = visible;
		this.subtitle = subtitle;
		this.companyName = companyName;
	}
	
	
}
