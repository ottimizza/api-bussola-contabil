package br.com.ottimizza.dashboard.repositories.kpi_detail;

import br.com.ottimizza.dashboard.models.CompanyCustom;
import br.com.ottimizza.dashboard.models.KpiDetail;
import br.com.ottimizza.dashboard.models.KpiDetailCustom;

import java.util.List;

public interface KpiDetailRepositoryCustom {
    
    List<CompanyCustom> findKpiDetailsCustomByCNPJ(List<String> cnpj);
    
    List<KpiDetail> findKpiDetailsByCNPJ(List<String> cnpj);
}
