package br.com.ottimizza.dashboard.repositories.kpi;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ottimizza.dashboard.domain.dtos.KpiDTO;
import br.com.ottimizza.dashboard.domain.dtos.KpiTitleAndValueDTO;
import br.com.ottimizza.dashboard.models.Kpi;

public interface KpiRepositoryCustom { // KpiRepositoryImpl
	
    List<Kpi> findKpisByCNPJ(List<String> cnpj);
    
    KpiTitleAndValueDTO findKpiDTOByCompanyId(BigInteger companyId);
    
    Page<KpiDTO> findAll(KpiDTO filter, Pageable pageable) throws Exception;
}
