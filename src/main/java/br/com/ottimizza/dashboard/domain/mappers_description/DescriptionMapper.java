package br.com.ottimizza.dashboard.domain.mappers_description;

import java.util.List;
import java.util.stream.Collectors;

import br.com.ottimizza.dashboard.domain.dtos.DescriptionDTO;
import br.com.ottimizza.dashboard.models.Description;

public class DescriptionMapper {
    
    public static Description fromDto(DescriptionDTO description) {
        return Description.builder()
                .id(description.getId())
                .accountingId(description.getAccountingId())
                .kpiAlias(description.getKpiAlias())
                .description(description.getDescription())
                .scriptType(description.getScriptType())
                .title(description.getTitle())
                .graphOrder(description.getGraphOrder())
                .chartType(description.getChartType())
                .visible(description.getVisible())
                .cnpj(description.getCnpj())
            .build();
    } 

    public static DescriptionDTO fromEntity(Description description) {
        return DescriptionDTO.builder()
                .id(description.getId())
                .accountingId(description.getAccountingId())
                .kpiAlias(description.getKpiAlias())
                .description(description.getDescription())
                .scriptType(description.getScriptType())
                .title(description.getTitle())
                .graphOrder(description.getGraphOrder())
                .chartType(description.getChartType())
                .visible(description.getVisible())
                .cnpj(description.getCnpj())
            .build();
    }
    public static List<DescriptionDTO> fromEntities(List<Description> descriptions) {
        return descriptions.stream().map(user -> fromEntity(user)).collect(Collectors.toList());
    }

}