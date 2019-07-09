package br.com.ottimizza.dashboard.repositories.kpi_detail;

import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.KpiDetail;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KpiDetailRepository extends JpaRepository<KpiDetail, Long>, KpiDetailRepositoryCustom{

	Optional<KpiDetail> findById(BigInteger idKpi);
    
	Boolean deleteById(BigInteger idKpi);
	
}
