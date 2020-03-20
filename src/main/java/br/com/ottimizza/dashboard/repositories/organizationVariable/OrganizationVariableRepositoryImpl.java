package br.com.ottimizza.dashboard.repositories.organizationVariable;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.domain.dtos.UserDTO;
import br.com.ottimizza.dashboard.domain.dtos.VariableDTO;
import br.com.ottimizza.dashboard.models.OrganizationVariable;
import br.com.ottimizza.dashboard.models.QCompany;
import br.com.ottimizza.dashboard.models.QOrganizationVariable;
import br.com.ottimizza.dashboard.models.QVariable;
import br.com.ottimizza.dashboard.utils.StringUtil;

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
						variable.id.eq(organizationVariable.variableId));//.and(variable.accountingId.eq(userInfo.getOrganization().getId())))
		
		if(filter.getCompanyId() != null) query.where(organizationVariable.organizationId.eq(filter.getCompanyId()));
		else if(filter.getCnpj() != null) query.innerJoin(company).on(company.cnpj.eq(filter.getCnpj()));

		query.select(Projections.constructor(VariableDTO.class, 
				organizationVariable.id, organizationVariable.organizationId, variable.variableCode, variable.name, 
				variable.id, variable.scriptId, organizationVariable.originValue, organizationVariable.absoluteValue, organizationVariable.organizationId, 
				organizationVariable.accountingCode, variable.kpiAlias, variable.description));

		return query.fetch();
	}

	@Override
	public List<VariableDTO> findMissingByCompanyId(VariableDTO filter, UserDTO userInfo) {
		System.out.println(">>> OO "+filter.toString());

		JPAQuery<VariableDTO> query = new JPAQuery<VariableDTO>(em).from(variable);
		query.innerJoin(company).on(company.accountingId.eq(variable.accountingId)
			 .and(company.scriptId.eq(variable.scriptId))
		 	 .and(company.scriptId.eq(filter.getScriptId())));

		if(filter.getCompanyId() != null) {
			query.leftJoin(organizationVariable).on(organizationVariable.variableId.eq(variable.id)
				 .and(organizationVariable.organizationId.eq(filter.getCompanyId()))
				 .and(organizationVariable.scriptId.eq(variable.scriptId)));
		}
		if(filter.getCnpj() != null) {
			System.out.println(">>> PP "+filter.toString());
			query.leftJoin(organizationVariable).on(organizationVariable.variableId.eq(variable.id)
				 .and(company.cnpj.eq(StringUtil.formatCnpj(filter.getCnpj())))
				 .and(organizationVariable.scriptId.eq(variable.scriptId)));
		}

		query.where(organizationVariable.id.isNull());

		query.select(Projections.constructor(VariableDTO.class, 
				organizationVariable.id, organizationVariable.organizationId, variable.variableCode, variable.name, 
				variable.id, company.scriptId, variable.originValue, variable.absoluteValue, variable.accountingId, 
				variable.accountingCode, variable.kpiAlias, variable.description));

		return query.fetch();
	}

	@Override
	public List<OrganizationVariable> findOrganizationVariable(VariableDTO filter, UserDTO userInfo) {

		JPAQuery<OrganizationVariable> query = new JPAQuery<OrganizationVariable>(em).from(organizationVariable);
		if(filter.getCompanyId() != null)		query.where(organizationVariable.organizationId.eq(filter.getCompanyId()));
		if(filter.getScriptId() != null)		query.where(organizationVariable.scriptId.eq(filter.getScriptId()));
		if(filter.getVariableCode() != null)	query.where(organizationVariable.variableCode.eq(filter.getVariableCode()));
		
		return query.fetch();
	}

}
