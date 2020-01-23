package br.com.ottimizza.dashboard.repositories.organizationVariable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.domain.dtos.UserDTO;
import br.com.ottimizza.dashboard.domain.dtos.VariableDTO;
import br.com.ottimizza.dashboard.models.QCompany;
import br.com.ottimizza.dashboard.models.QOrganizationVariable;
import br.com.ottimizza.dashboard.models.QVariable;

@Repository
public class OrganizationVariableRepositoryImpl implements OrganizationVariableRepositoryCustom{

	@PersistenceContext
	EntityManager em;
	private QVariable variable = QVariable.variable;
	private QCompany company = QCompany.company;
	private QOrganizationVariable organizationVariable = QOrganizationVariable.organizationVariable;
	
	@Override
	public List<VariableDTO> findVariablesByCompanyId(VariableDTO filter, UserDTO userInfo) {
		JPAQuery<VariableDTO> query = new JPAQuery<VariableDTO>(em).from(organizationVariable)
				.innerJoin(variable).on(
						variable.id.eq(organizationVariable.variableId)/*.and(variable.accountingId.eq(userInfo.getOrganization().getId()))*/)
				.where(organizationVariable.organizationId.eq(filter.getCompanyId()));

		query.select(Projections.constructor(
				VariableDTO.class, organizationVariable.id, organizationVariable.organizationId, variable.variableCode, variable.name, variable.id, variable.scriptId, variable.originValue, variable.absoluteValue, 
								   organizationVariable.organizationId, organizationVariable.accountingCode));

		return query.fetch();
	}

//	@Override
//	public List<VariableDTO> findMissingByCompanyId(BigInteger companyId, UserDTO userInfo) {
//		JPAQuery<VariableDTO> query = new JPAQuery<VariableDTO>(em).from(variable)
//				.leftJoin(organizationVariable).on(
//						organizationVariable.variableId.eq(variable.id).and(organizationVariable.organizationId.eq(companyId)))
//				.where(organizationVariable.id.isNull().and(variable.accountingId.eq(userInfo.getOrganization().getId())));
//				
//		query.select(Projections.constructor(
//				VariableDTO.class, organizationVariable.id, variable.accountingId, variable.variableCode, variable.name, variable.id, variable.scriptId, variable.originValue, variable.absoluteValue, 
//								   organizationVariable.organizationId, variable.accountingCode));
//		return query.fetch();
//	}
	
	@Override
	public List<VariableDTO> findMissingByCompanyId(VariableDTO filter, UserDTO userInfo) {

		String cnpjTest = "07.586.955/0001-99"; //OK preciso remover isso
		JPAQuery<VariableDTO> query = new JPAQuery<VariableDTO>(em).from(variable);
		query.innerJoin(company).on(company.accountingId.eq(variable.accountingId)
				.and(company.cnpj.eq(cnpjTest))
				.and(company.scriptId.eq(variable.scriptId)));

		query.leftJoin(organizationVariable).on(organizationVariable.variableId.eq(variable.id)
				.and(organizationVariable.organizationId.eq(filter.getCompanyId()))
				.and(organizationVariable.scriptId.eq(variable.scriptId)));
					
		query.where(organizationVariable.id.isNull());
		
		query.select(Projections.constructor(VariableDTO.class, 
				organizationVariable.id, organizationVariable.organizationId, 
				variable.variableCode, variable.name, variable.id, variable.scriptId, variable.originValue, variable.absoluteValue, 
				organizationVariable.organizationId, variable.accountingCode));
		
		return query.fetch();
	}

}
