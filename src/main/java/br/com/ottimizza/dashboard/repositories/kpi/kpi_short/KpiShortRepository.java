package br.com.ottimizza.dashboard.repositories.kpi.kpi_short;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ottimizza.dashboard.models.KpiShort;

public interface KpiShortRepository extends JpaRepository<KpiShort, BigInteger>, KpiShortRepositoryCustom {

}
