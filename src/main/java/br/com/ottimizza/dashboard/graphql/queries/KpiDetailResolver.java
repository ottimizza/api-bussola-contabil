package br.com.ottimizza.dashboard.graphql.queries;

import java.math.BigInteger;
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
	public List<KpiDetail> findKpiDetail(BigInteger id, BigInteger kpiId, String columnX, String columnY, String columnZ, 
			Double valorKPI, Double valorKPI2, Double valorKPI3, Double valorKPI4, String columnXSeq, String xBinding) {
	
		JPAQuery<KpiDetail> query = new JPAQuery<KpiDetail>(em).from(kpiDetail);
		
		if(kpiId != null)		query.where(kpiDetail.kpiID.id.in(kpiId));
		if(id != null)			query.where(kpiDetail.id.in(id));
		if(columnX != null)		query.where(kpiDetail.columnX.toUpperCase().in(columnX.toUpperCase()));
		if(columnY != null)		query.where(kpiDetail.columnY.toUpperCase().in(columnY.toUpperCase()));
		if(columnZ != null)		query.where(kpiDetail.columnZ.toUpperCase().in(columnZ.toUpperCase()));

		if(valorKPI != null)	query.where(kpiDetail.valorKPI.in(valorKPI));
		if(valorKPI2 != null)	query.where(kpiDetail.valorKPI2.in(valorKPI2));
		if(valorKPI3 != null)	query.where(kpiDetail.valorKPI3.in(valorKPI3));
		if(valorKPI4 != null)	query.where(kpiDetail.valorKPI4.in(valorKPI4));
		
		if(columnXSeq != null)	query.where(kpiDetail.columnXSeq.toUpperCase().in(columnXSeq.toUpperCase()));
		if(xBinding != null)	query.where(kpiDetail.xBinding.toUpperCase().in(xBinding.toUpperCase()));
		
		return query.fetch();
		
	}
}
