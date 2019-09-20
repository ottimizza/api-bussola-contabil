package br.com.ottimizza.dashboard.repositories.kpi_detail;

import java.util.List;

import br.com.ottimizza.dashboard.models.KpiDetail;

public interface KpiDetailRepositoryCustom {
    
    List<KpiDetail> findKpiDetailsByCNPJ(List<String> cnpj);
    
	

}
