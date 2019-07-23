package br.com.ottimizza.dashboard.config;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.ottimizza.dashboard.graphql.mutations.CompanyMutation;
import br.com.ottimizza.dashboard.graphql.mutations.KpiDetailMutation;
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

//		CompanyMutation companyMutation = new CompanyMutation(em, companyRepository);
//		KpiMutation kpiMutation = new KpiMutation(em, kpiRepository, companyRepository);
		CompanyMutation companyMutation = new CompanyMutation(em, kpiDetailRepository, kpiRepository, companyRepository);
		KpiMutation kpiMutation = new KpiMutation(em, kpiDetailRepository, kpiRepository, companyRepository);
		KpiDetailMutation kpiDetailMutation = new KpiDetailMutation(em, kpiDetailRepository, kpiRepository, companyRepository);
		
		return new GraphQLSchemaGenerator().
				withOperationsFromSingletons(
						query, 
						companyQuery,
						kpiQuery,
						kpiDetailQuery,
						companyMutation,
						kpiDetailMutation,
						kpiMutation						
				).generate();
	}
	
}
