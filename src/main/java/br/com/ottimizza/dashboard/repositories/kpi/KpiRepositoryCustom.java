package br.com.ottimizza.dashboard.repositories.kpi;

import java.math.BigInteger;
import java.util.List;

import br.com.ottimizza.dashboard.dtos.KpiTitleAndValueDTO;
import br.com.ottimizza.dashboard.models.Kpi;

public interface KpiRepositoryCustom {
	// KpiRepositoryImpl
    List<Kpi> findKpisByCNPJ(List<String> cnpj);
    
    KpiTitleAndValueDTO findKpiDTOByCompanyId(BigInteger companyId);
}
