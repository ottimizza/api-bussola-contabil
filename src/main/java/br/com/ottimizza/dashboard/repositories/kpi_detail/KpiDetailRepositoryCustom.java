package br.com.ottimizza.dashboard.repositories.kpi_detail;

import br.com.ottimizza.dashboard.models.KpiDetail;

import java.math.BigInteger;
import java.util.List;

public interface KpiDetailRepositoryCustom {
    
    List<KpiDetail> findKpiDetailsByCNPJ(List<String> cnpj);
    
}
