package br.com.ottimizza.dashboard.domain.dtos;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import br.com.ottimizza.dashboard.models.KpiDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KpiDetailDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private BigInteger id;
	private String columnX;
	private String valorStringArray;
	
	public static KpiDetailDTO fromEntity(KpiDetail kpiDetail) {
	    // @formatter:off
		KpiDetailDTO dto = KpiDetailDTO.builder()
									   .id(kpiDetail.getId())
									   .columnX(kpiDetail.getColumnX())
									   .valorStringArray(kpiDetail.getValorStringArray())
									   .build();
	    // @formatter:on
	    return dto;
	}
	
	public static List<KpiDetailDTO> fromEntity(List<KpiDetail> kpiDetailDTO){
		return kpiDetailDTO.stream().map(KpiDetailDTO::fromEntity).collect(Collectors.toList());
	}

}
