package br.com.ottimizza.dashboard.repositoriesorganizationVariable;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;

import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.models.QOrganizationVariable;
import br.com.ottimizza.dashboard.models.QVariable;
import br.com.ottimizza.dashboard.models.Variable;

public class OrganizationVariableRepositoryImpl implements OrganizationVariableRepositoryCustom{

	EntityManager em;
	QVariable variable = QVariable.variable;
	QOrganizationVariable organizationVariable = QOrganizationVariable.organizationVariable;
	
	@Override
	public List<Variable> findVariablesByOrganizationId(BigInteger organizationId) {
		JPAQuery<Variable> query = new JPAQuery<Variable>(em).from(variable)
				.innerJoin(organizationVariable).on(organizationVariable.accountingCode.eq(variable.accountingCode))
				.where(organizationVariable.organizationId.eq(variable.id));
		return query.fetch();
	}
}
