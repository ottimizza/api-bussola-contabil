package br.com.ottimizza.dashboard.repositories.kpi;

import br.com.ottimizza.dashboard.models.Kpi;

import java.math.BigInteger;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface KpiRepository extends JpaRepository<Kpi, BigInteger>, KpiRepositoryCustom{

//	@Modifying
//	@Transactional
//	@Query(" DELETE FROM kpis WHERE id = :id ")
//	void deleteKpiById(@Param("id") BigInteger id);

}
