package br.com.ottimizza.dashboard.graphql.mutations;

import javax.persistence.EntityManager;

import br.com.ottimizza.dashboard.models.QKpiDetail;
import br.com.ottimizza.dashboard.repositories.kpi_detail.KpiDetailRepository;

public class KpiDetailMutation {

	private EntityManager em;
	private QKpiDetail kpiDetail = QKpiDetail.kpiDetail;
	private KpiDetailRepository kpiDetailRepository;

	public KpiDetailMutation(EntityManager em, KpiDetailRepository kpidetailRepository) {
		this.em = em;
		this.kpiDetailRepository = kpiDetailRepository;
	}
	
	/*
	 * @GraphQLMutation(name = "editKpiDetail")
		public KpiDetail editKpiDetail(BigInteger id, String title) {
		KpiDetail kpiDetail = kpiDetailRepository.findById(id);
		kpi.setTitle(title);
		
		return kpiDetailRepository.save(kpiDetail);
	}* 
	 * */

}
