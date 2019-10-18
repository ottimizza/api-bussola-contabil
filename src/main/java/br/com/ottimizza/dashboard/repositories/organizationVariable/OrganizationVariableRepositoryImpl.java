package br.com.ottimizza.dashboard.repositories.organizationVariable;

import java.math.BigInteger;
import java.security.Principal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.dtos.UserDTO;
import br.com.ottimizza.dashboard.dtos.VariableDTO;
import br.com.ottimizza.dashboard.models.QOrganizationVariable;
import br.com.ottimizza.dashboard.models.QVariable;

@Repository
public class OrganizationVariableRepositoryImpl implements OrganizationVariableRepositoryCustom{

	@PersistenceContext
	EntityManager em;
	QVariable variable = QVariable.variable;
	QOrganizationVariable organizationVariable = QOrganizationVariable.organizationVariable;
	
	@Override
	public List<VariableDTO> findVariablesByOrganizationId(BigInteger organizationId, UserDTO userInfo) {
		
		System.out.println(" --- As Queries do Diogo --- ");
		
		System.out.println(" UserInfo.organizationId : " + userInfo.getOrganization().getId());
		System.out.println("          organizationId : " + organizationId);
		
		JPAQuery<VariableDTO> query = new JPAQuery<VariableDTO>(em).from(organizationVariable)
				.innerJoin(variable).on(
						variable.id.eq(organizationVariable.variableId).and(organizationVariable.organizationId.eq(userInfo.getOrganization().getId())))
				.where(organizationVariable.organizationId.eq(organizationId));
		
		System.out.println(" count : " + query.fetchCount());
                
		query.select(Projections.constructor(
				VariableDTO.class, variable.companyId, variable.externalId, variable.name, variable.id, organizationVariable.organizationId, organizationVariable.accountingCode));
		
		System.out.println(" count : " + query.fetchCount());
		System.out.println(" --- Fim Das Queries do Diogo --- ");
		
		return query.fetch();
	}
}
