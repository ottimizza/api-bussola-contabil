package br.com.ottimizza.dashboard.repositories.kpi_detail;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ottimizza.dashboard.models.KpiDetail;

public interface KpiDetailRepository extends JpaRepository<KpiDetail, BigInteger>, KpiDetailRepositoryCustom{

	Optional <KpiDetail> findById(BigInteger idKpi);  
	
}
