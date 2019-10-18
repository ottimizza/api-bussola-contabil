package br.com.ottimizza.dashboard.repositoriesorganizationVariable;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.models.QOrganizationVariable;
import br.com.ottimizza.dashboard.models.QVariable;
import br.com.ottimizza.dashboard.models.Variable;

@Repository
public class OrganizationVariableRepositoryImpl implements OrganizationVariableRepositoryCustom{

	@PersistenceContext
	EntityManager em;
	QVariable variable = QVariable.variable;
	QOrganizationVariable organizationVariable = QOrganizationVariable.organizationVariable;
	
	@Override
	public List<Variable> findVariablesByOrganizationId(BigInteger organizationId) {
		System.out.println(">>> > "+organizationId);
		JPAQuery<Variable> query = new JPAQuery<Variable>(em).from(variable)
				.innerJoin(organizationVariable).on(organizationVariable.accountingCode.eq(variable.accountingCode))
				.where(organizationVariable.organizationId.eq(organizationId));
		return query.fetch();
	}
}
