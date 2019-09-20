package br.com.ottimizza.dashboard.repositories.kpi.kpi_short;

import java.util.List;

import br.com.ottimizza.dashboard.models.KpiShort;

public interface KpiShortRepositoryCustom {

    List<KpiShort> findKpisByCNPJ(List<String> cnpj);

}
