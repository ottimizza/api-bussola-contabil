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
	
//	@Query(value = " SELECT new br.com.ottimizza.dashboard.domain.dtos.KpiDTO(	"
//		+ "       c.cnpj,												"
//		+ "       0,													"
//		+ "       k.id,													"
//		+ "       d.title,												"
//		+ "       k.kpiAlias,											"
//		+ "       d.chartType,											"
//		+ "       k.chartOptions,										"
//		+ "       d.visible),											"
//		+ "     FROM kpis k												"
//		+ "     INNER JOIN companies c									"
//		+ "	        ON c.id = k.fk_companies_id							"
//		+ "     INNER JOIN description d								"
//		+ "         ON d.fk_script_id = c.fk_script_id					"
//		+ "         AND d.fk_accounting_id = c.fk_accounting_id			"
//		+ "         AND d.kpi_alias = k.kpi_alias						"
//		+ "         AND d.visible = true								"
//		+ "     WHERE k.visible = true									"
//		+ "     AND k.kpi_alias not like '07'							"
//		+ "     AND k.kpi_alias not like '12'							"
//		+ "     AND c.cnpj = :cnpj										"
//		+ "     CASE													"
//		+ "         WHEN :kind = 1 THEN k.kpi_alias < '60'				"
//		+ "         WHEN :kind = 2 THEN k.kpi_alias >= '60'				"
//		+ "     ORDER BY d.graph_order									", nativeQuery = true)
//	List<KpiDTO> findKpis(@Param("cnpj")String cnpj, @Param("kind")Integer kind);

}