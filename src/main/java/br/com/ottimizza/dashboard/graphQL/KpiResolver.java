package br.com.ottimizza.dashboard.graphQL;

import java.util.List;

import javax.persistence.EntityManager;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.QKpi;
import br.com.ottimizza.dashboard.repositories.kpi.KpiRepository;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;

public class KpiResolver implements GraphQLQueryResolver{

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
	public List<Kpi> editKpi(Kpi filter) {
		List<Kpi> query = findKpi(filter);
		if(query.isEmpty()) {
		throw new Error("dont find it");
		}
		query.get(0).setTitle(filter.getTitle());
		
		return query;
//		if(filter.getId() != null) {
//			Kpi k = findKpi(filter).get(0);
//			if(filter.getTitle() != null) {
//				k.setTitle(filter.getTitle());
//				query.where(kpi.id.in(filter.getId()));
//			}
//		}
//		return query.fetch();	
	}

}

//updateAuthor: (_, { authorId, firstName, lastName }) => { 
//	 const author = find(authors, { id: authorId }); 
//	 if (!author) {
//	   throw new Error(`Couldn’t find author with id ${authorId}`);
//	 }
//	 author.firstName = firstName; 
//	 author.lastName = lastName; 
//	 return author;
//	}











