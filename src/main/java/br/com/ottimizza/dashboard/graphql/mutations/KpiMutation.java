package br.com.ottimizza.dashboard.graphql.mutations;

import java.math.BigInteger;

import javax.persistence.EntityManager;

import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.QKpi;
import br.com.ottimizza.dashboard.repositories.kpi.KpiRepository;
import io.leangen.graphql.annotations.GraphQLMutation;

public class KpiMutation {

	private EntityManager em;
	private QKpi kpi = QKpi.kpi;
	private KpiRepository kpiRepository;

	public KpiMutation(EntityManager em, KpiRepository kpiRepository) {
		this.em = em;
		this.kpiRepository = kpiRepository;
	}
	
	@GraphQLMutation(name = "editKpi")
	public Kpi editKpi(BigInteger id, String title) {
		Kpi kpi = kpiRepository.findById(id);
		kpi.setTitle(title);
		
		return kpiRepository.save(kpi);
	}
}
