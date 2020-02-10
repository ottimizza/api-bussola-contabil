package br.com.ottimizza.dashboard.domain.dtos;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ottimizza.dashboard.models.Kpi;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KpiDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	private String cnpj;
	@JsonIgnore
	private Integer kind;
	
	private BigInteger id;
	private String title;
	private String kpiAlias;
	
	
	private String labelArray;
//	private List<KpiDetailDTO> kpiDetail;
	private String chartType; 
	private String chartOptions;
	private boolean visible;
	
	
	
	public static List<KpiDTO> fromEntity(List<Kpi> kpiDTO){
		return kpiDTO.stream().map(KpiDTO::fromEntity).collect(Collectors.toList());
	}
	
	
	public List<String> getLabelArray() {
		return Arrays.asList(labelArray.split(";"));
	}
	
	public static KpiDTO fromEntity(Kpi kpi) {
		KpiDTO dto = new KpiDTO();
		dto.setId(kpi.getId());
		dto.setTitle(kpi.getTitle());
		dto.setKpiAlias(kpi.getKpiAlias());
		dto.setLabelArray(kpi.getLabelStringArray());
//		dto.setLabelArray(kpi.getLabelStringArray().equals("") ? new ArrayList<String>() : kpi.getLabelArray());
//		dto.setKpiDetail(kpi.getKpiDetail() == null ? new ArrayList<KpiDetailDTO>() : KpiDetailDTO.fromEntity(kpi.getKpiDetail()));
		dto.setChartType(kpi.getChartType());
		dto.setChartOptions(kpi.getChartOptions());
		dto.setVisible(kpi.getVisible());
	    return dto;
	}
	
}
