package br.com.ottimizza.dashboard.repositories.kpi_detail;

import java.util.List;

import org.json.JSONObject;

import br.com.ottimizza.dashboard.models.KpiDetail;

public interface KpiDetailRepositoryCustom {
    
    List<KpiDetail> findKpiDetailsByCNPJ(List<String> cnpj);
    
    List<KpiDetail> findKpiDetailsByCNPJAndGraphType(List<String> cnpj, Short graphType, String kind);
    
    JSONObject deleteKpiDetail(KpiDetail kpiDetail);

}
