package br.com.ottimizza.dashboard.repositories.kpi;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ottimizza.dashboard.models.Kpi;

@Repository
public interface KpiRepository extends JpaRepository<Kpi, BigInteger>, KpiRepositoryCustom{

//	@Modifying
//	@Transactional
//	@Query(" DELETE FROM kpis WHERE id = :id ")
//	void deleteKpiById(@Param("id") BigInteger id);

}
