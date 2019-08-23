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
	public List<Kpi> findKpi(String cnpj, BigInteger id, BigInteger companyId, String kpiAlias, String title, String subtitle, String description, 
							Short graphType, String columnX0Label, String label, String label2, String label3, String label4, Boolean visible) {
		JPAQuery<Kpi> query = new JPAQuery<Kpi>(em).from(kpi);
	
//		if(cnpj != null)		query.where(kpi.company.cnpj.in(cnpj));
		
		if(companyId != null)	query.where(kpi.company.id.in(companyId));
		if(id != null)			query.where(kpi.id.in(id));
		if(kpiAlias != null)	query.where(kpi.kpiAlias.in(kpiAlias));
		if(title != null)		query.where(kpi.title.toUpperCase().in(title.toUpperCase()));
		if(subtitle != null)	query.where(kpi.subtitle.toUpperCase().in(subtitle.toUpperCase()));
		
		if(description != null)	query.where(kpi.description.toUpperCase().in(description.toUpperCase()));
		if(graphType != null)	query.where(kpi.graphType.in(graphType));
		if(columnX0Label != null) query.where(kpi.columnX0Label.toUpperCase().in(columnX0Label.toUpperCase()));
		if(visible != null)		query.where(kpi.visible.in(visible));
		
		if(label != null)		query.where(kpi.label.toUpperCase().in(label.toUpperCase()));
		if(label2 != null)		query.where(kpi.label2.toUpperCase().in(label2.toUpperCase()));
		if(label3 != null)		query.where(kpi.label3.toUpperCase().in(label3.toUpperCase()));
		if(label4 != null)		query.where(kpi.label4.toUpperCase().in(label4.toUpperCase()));
		
		return query.fetch();
	}

}










