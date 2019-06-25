package br.com.ottimizza.dashboard.graphQL;

import javax.persistence.EntityManager;

import br.com.ottimizza.dashboard.models.QKpiDetail;
import br.com.ottimizza.dashboard.repositories.kpi_detail.KpiDetailRepository;
import io.leangen.graphql.annotations.GraphQLQuery;

public class KpiDetailResolver {

	private QKpiDetail kpiDetail = QKpiDetail.kpiDetail;
	private EntityManager em;
	private KpiDetailRepository kpiDetailRepository;
	
	public KpiDetailResolver(EntityManager em, KpiDetailRepository kpiDetailRepository) {
		this.em = em;
		this.kpiDetailRepository = kpiDetailRepository;
	}
	
//	@GraphQLQuery
//	public List<KpiDetail> findKpi(Kpi filter) {
//		JPAQuery<Kpi> query = new JPAQuery<Kpi>(em).from(kpi);
//		
//		if(filter.getId() != null) {
//			query.where(kpi.id.in(filter.getId()));
//		}
//		
//		return query.fetch();
//		
//	}
}
