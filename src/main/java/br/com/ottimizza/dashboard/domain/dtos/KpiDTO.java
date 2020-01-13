package br.com.ottimizza.dashboard.domain.dtos;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.ottimizza.dashboard.models.Kpi;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KpiDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String cnpj;
	private Integer kind;
	
	private BigInteger id;
	private String title;
	private String kpiAlias;
	private List<String> labelArray;
	private List<KpiDetailDTO> kpiDetailDTO;
	private String chartType; 
	private String chartOptions;
	
	
//	public static KpiDTO fromEntity(Kpi kpi) {
//	    // @formatter:off
//		KpiDTO dto = KpiDTO.builder()
//						   .id(kpi.getId())
//						   .cnpj(null)
//						   .kind(null)
//						   .title(kpi.getTitle())
//						   .kpiAlias(kpi.getKpiAlias())
//						   .chartType(kpi.getChartType())
//						   .chartOptions(kpi.getChartOptions())
//						   .labelArray(kpi.getLabelArray())
//						   .kpiDetailDTO(KpiDetailDTO.fromEntity(kpi.getKpiDetail()))
//   						   .build();
//	    // @formatter:on		
//	    return dto;
//	}
	
	public static List<KpiDTO> fromEntity(List<Kpi> kpiDTO){
		return kpiDTO.stream().map(KpiDTO::fromEntity).collect(Collectors.toList());
	}
	
	public static KpiDTO fromEntity(Kpi kpi) {
		KpiDTO dto = new KpiDTO();
		dto.setId(kpi.getId());
		dto.setTitle(kpi.getTitle());
		dto.setKpiAlias(kpi.getKpiAlias());
		dto.setLabelArray((kpi.getLabelArray() != null) ? kpi.getLabelArray() : new ArrayList<String>());
		if(kpi.getKpiDetail() != null)  dto.setKpiDetailDTO(KpiDetailDTO.fromEntity(kpi.getKpiDetail()));
		dto.setChartType(kpi.getChartType());
		dto.setChartType(kpi.getChartOptions());
		
	    System.out.println("*** D "+dto.toString());
	    return dto;
	}

}
