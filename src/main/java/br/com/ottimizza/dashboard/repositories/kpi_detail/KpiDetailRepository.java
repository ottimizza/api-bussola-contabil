package br.com.ottimizza.dashboard.repositories.kpi_detail;

import java.math.BigInteger;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.ottimizza.dashboard.models.KpiDetail;

public interface KpiDetailRepository extends JpaRepository<KpiDetail, BigInteger>, KpiDetailRepositoryCustom{

//	Optional <KpiDetail> findById(BigInteger idKpi);    
	@Modifying
	@Transactional
	@Query(" DELETE FROM kpis_details WHERE id = :id ")
	void deleteKpiDetailsById(@Param("id") BigInteger id);

}
