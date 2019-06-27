package br.com.ottimizza.dashboard.repositories.kpi;

import br.com.ottimizza.dashboard.models.Kpi;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KpiRepository extends JpaRepository<Kpi, Long>, KpiRepositoryCustom{

	Kpi findById(BigInteger idKpi);
    
}
