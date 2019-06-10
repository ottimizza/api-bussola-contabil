package br.com.ottimizza.dashboard.repositories.kpi.kpi_short;

import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.KpiShort;
import java.util.List;

public interface KpiShortRepositoryCustom {

    List<KpiShort> findKpisByCNPJ(List<String> cnpj);

}
