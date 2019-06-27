package br.com.ottimizza.dashboard.graphql;

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
	public List<Kpi> findKpi(Kpi filter) {
		JPAQuery<Kpi> query = new JPAQuery<Kpi>(em).from(kpi);
		
		if(filter.getId() != null) {
			query.where(kpi.id.in(filter.getId()));
		}
		
		return query.fetch();		
	}
	

	@GraphQLMutation
	public Kpi editKpi(BigInteger id, String title) {
		Kpi kpi = kpiRepository.findById(id);
		kpi.setTitle(title);
		
		return kpiRepository.save(kpi);
	}
//	public Kpi editKpi(BigInteger id, String title) {
//		
//		Link newLink = new Link(url, description, context.getUser().getId());
//	    linkRepository.saveLink(newLink);
//	    return newLink;
//	}
}










