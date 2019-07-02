package br.com.ottimizza.dashboard.config;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.ottimizza.dashboard.graphql.mutations.KpiMutation;
import br.com.ottimizza.dashboard.graphql.queries.CompanyResolver;
import br.com.ottimizza.dashboard.graphql.queries.KpiDetailResolver;
import br.com.ottimizza.dashboard.graphql.queries.KpiResolver;
import br.com.ottimizza.dashboard.graphql.queries.Query;
import br.com.ottimizza.dashboard.repositories.company.CompanyRepository;
import br.com.ottimizza.dashboard.repositories.kpi.KpiRepository;
import br.com.ottimizza.dashboard.repositories.kpi_detail.KpiDetailRepository;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;

@Configuration
public class GraphQLConfiguration {

	@PersistenceContext
	EntityManager em;

	@Inject
	CompanyRepository companyRepository;
	@Inject
	KpiRepository kpiRepository;
	@Inject
	KpiDetailRepository kpiDetailRepository;
	
	@Bean
	public GraphQLSchema schema() {
		Query query = new Query();
		CompanyResolver companyQuery = new CompanyResolver(em, companyRepository);
		KpiResolver kpiQuery = new KpiResolver(em, kpiRepository);
		KpiDetailResolver kpiDetailQuery = new KpiDetailResolver(em, kpiDetailRepository);
		
		KpiMutation kpiMutations = new KpiMutation(em, kpiRepository);
		
		return new GraphQLSchemaGenerator().
				withOperationsFromSingletons(
						query, 
						companyQuery,
						kpiQuery,
						kpiDetailQuery,
						kpiMutations
				).generate();
	}
	
}
