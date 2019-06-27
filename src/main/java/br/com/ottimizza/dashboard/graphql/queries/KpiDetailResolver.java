package br.com.ottimizza.dashboard.graphql.queries;

import java.util.List;

import javax.persistence.EntityManager;

import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.models.KpiDetail;
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
	
	@GraphQLQuery
	public List<KpiDetail> findKpiDetail(KpiDetail filter) {
		JPAQuery<KpiDetail> query = new JPAQuery<KpiDetail>(em).from(kpiDetail);
		
		if(filter.getId() != null) {
			query.where(kpiDetail.id.in(filter.getId()));
		}
		
		return query.fetch();
		
	}
}
