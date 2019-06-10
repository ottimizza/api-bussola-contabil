package br.com.ottimizza.dashboard.repositories.kpi.kpi_short;

import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.KpiShort;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KpiShortRepository extends JpaRepository<KpiShort, Long>, KpiShortRepositoryCustom {

}
