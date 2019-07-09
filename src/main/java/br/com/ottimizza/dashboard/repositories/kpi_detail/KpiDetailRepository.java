package br.com.ottimizza.dashboard.repositories.kpi_detail;

import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.KpiDetail;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KpiDetailRepository extends JpaRepository<KpiDetail, Long>, KpiDetailRepositoryCustom{

	KpiDetail findById(BigInteger idKpi);

	void deleteById(BigInteger id);
    
//	Boolean deleteById(BigInteger idKpi);
	
}
