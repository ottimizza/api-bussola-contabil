package br.com.ottimizza.dashboard.repositories.kpi;

import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.KpiShort;
import java.util.List;

public interface KpiRepositoryCustom {
    
    List<Kpi> findKpisByCNPJ(List<String> cnpj);
    
}
