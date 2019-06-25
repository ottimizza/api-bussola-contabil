package br.com.ottimizza.dashboard.config;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.ottimizza.dashboard.graphQL.CompanyResolver;
import br.com.ottimizza.dashboard.graphQL.Query;
import br.com.ottimizza.dashboard.repositories.company.CompanyRepository;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;

@Configuration
public class GraphQLConfiguration {

	@PersistenceContext
	EntityManager em;

	@Inject
	CompanyRepository companyRepository;
	
	@Bean
	public GraphQLSchema schema() {
		Query query = new Query();
		CompanyResolver companyQuery = new CompanyResolver(em, companyRepository);
		return new GraphQLSchemaGenerator().
				withOperationsFromSingletons(
						query, 
						companyQuery
				).generate();
	}
	
}
