package br.com.ottimizza.dashboard.repositories.kpi_detail;

import java.util.List;

import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ottimizza.dashboard.domain.dtos.KpiDetailDTO;
import br.com.ottimizza.dashboard.models.KpiDetail;

public interface KpiDetailRepositoryCustom {	//KpiDetailRepositoryImpl
    
    List<KpiDetail> findKpiDetailsByCNPJ(List<String> cnpj);
    
    List<KpiDetail> findKpiDetailsByCNPJAndGraphType(List<String> cnpj, Short graphType, String kind);
    
    JSONObject deleteKpiDetail(KpiDetail kpiDetail);
    
    List<String> findKpiAlias(String cnpj);

    Page<KpiDetail> findAll(KpiDetailDTO filter, Pageable pageable);  

}
