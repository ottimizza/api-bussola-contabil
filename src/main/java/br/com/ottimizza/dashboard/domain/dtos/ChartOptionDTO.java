package br.com.ottimizza.dashboard.domain.dtos;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import br.com.ottimizza.dashboard.models.ChartOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChartOptionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigInteger id;	
	private String chartType;
	private Short style;	//darkmode etc
	private String option;
	
	public static ChartOption fromDto(ChartOptionDTO dto) {
		ChartOption option = new ChartOption();
    	
		if(dto.getId() != null)				option.setId(dto.getId());
		if(dto.getChartType() != null)		option.setChartType(dto.getChartType());
		if(dto.getStyle() != null)			option.setStyle(dto.getStyle());
		if(dto.getOption() != null)			option.setOption(dto.getOption());
		
		return option;
    }
	
	public static List<ChartOption> fromDto(List<ChartOptionDTO> dtos){
		return dtos.stream().map(ChartOptionDTO::fromDto).collect(Collectors.toList());
	}
	
	public static ChartOptionDTO fromEntity(ChartOption option) {
		ChartOptionDTO dto = new ChartOptionDTO();
    	
		if(option.getId() != null)			dto.setId(option.getId());
		if(option.getChartType() != null)	dto.setChartType(option.getChartType());
		if(option.getStyle() != null)		dto.setStyle(option.getStyle());
		if(option.getOption() != null)		dto.setOption(option.getOption());
		
		return dto;
    }

	public static List<ChartOptionDTO> fromEntity(List<ChartOption> options){
		return options.stream().map(ChartOptionDTO::fromEntity).collect(Collectors.toList());
	}
	
	
}