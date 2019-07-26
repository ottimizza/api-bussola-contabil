package br.com.ottimizza.dashboard.graphql.queries;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.QKpi;
import br.com.ottimizza.dashboard.repositories.kpi.KpiRepository;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;

public class KpiResolver{

	private QKpi kpi = QKpi.kpi;
	private EntityManager em;
	private KpiRepository kpiRepository;
	
	public KpiResolver(EntityManager em, KpiRepository kpiRepository) {
		this.em = em;
		this.kpiRepository = kpiRepository;
	}

	@GraphQLQuery
	public List<Kpi> findKpi(BigInteger id, BigInteger companyId, String kpiAlias, String title, String subtitle, String description, 
							Short graphType, String columnX0Label, String label, String label2, String label3, String label4, Boolean visible) {
		JPAQuery<Kpi> query = new JPAQuery<Kpi>(em).from(kpi);
	
		if(companyId != null)	query.where(kpi.company.id.in(companyId));
		if(id != null)			query.where(kpi.id.in(id));
		if(kpiAlias != null)	query.where(kpi.kpiAlias.in(kpiAlias));
		if(title != null)		query.where(kpi.title .in(title ));
		if(subtitle != null)	query.where(kpi.subtitle .in(subtitle ));
		
		if(description != null)	query.where(kpi.description .in(description ));
		if(graphType != null)	query.where(kpi.graphType.in(graphType));
		if(columnX0Label != null) query.where(kpi.columnX0Label .in(columnX0Label ));
		if(visible != null)		query.where(kpi.visible.in(visible));
		
		if(label != null)		query.where(kpi.label .in(label ));
		if(label2 != null)		query.where(kpi.label2 .in(label2 ));
		if(label3 != null)		query.where(kpi.label3 .in(label3 ));
		if(label4 != null)		query.where(kpi.label4 .in(label4 ));
		
		return query.fetch();
	}

}










