package br.com.ottimizza.dashboard.repositories.variable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.dtos.UserDTO;
import br.com.ottimizza.dashboard.models.QOrganizationVariable;
import br.com.ottimizza.dashboard.models.QVariable;
import br.com.ottimizza.dashboard.models.Variable;

@Repository
public class VariableRepositoryImpl implements VariableRepositoryCustom {
	
	@PersistenceContext
	EntityManager em;
	QVariable variable = QVariable.variable;
	QOrganizationVariable organizationVariable = QOrganizationVariable.organizationVariable;
	
	@Override
	public Variable findVariableByAccountingCode(String accountingCode, UserDTO userInfo) {
		JPAQuery<Variable> query = new JPAQuery<Variable>(em).from(variable)
				.where(variable.accountingCode.eq(accountingCode));
				
		return query.fetchFirst();
	}
	
	
	
}
