package br.com.ottimizza.dashboard.repositories.kpi;

import java.math.BigInteger;
import java.util.List;

import br.com.ottimizza.dashboard.dtos.KpiDTO;
import br.com.ottimizza.dashboard.models.Kpi;

public interface KpiRepositoryCustom {
    
    List<Kpi> findKpisByCNPJ(List<String> cnpj);
    
    KpiDTO findKpiDTOByCompanyId(BigInteger companyId);
}
