package br.com.ottimizza.dashboard.repositories.company;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.domain.dtos.CompanyDTO;
import br.com.ottimizza.dashboard.models.Company;
import br.com.ottimizza.dashboard.models.QCompany;
import br.com.ottimizza.dashboard.utils.StringUtil;

@Repository
public class CompanyRepositoryImpl implements CompanyRepositoryCustom {
    
    @PersistenceContext
    EntityManager em;
    
    private QCompany company = QCompany.company;
    
    @Override
    public List<Company> findCompaniesByCNPJ(List<String> cnpj) {
        JPAQuery<Company> query = new JPAQuery<Company>(em).from(company)
                .where(company.cnpj.in(cnpj));
//                .innerJoin(kpi).on(company.id.eq(kpi.company.id)) --Tentativa de trazer todas as informações de kpi e kpi Detail
//                .innerJoin(kpiDetail).on(kpi.id.eq(kpiDetail.kpiID.id))
        return query.orderBy(company.name.asc()).fetch();
    }
    
    @Override
    public List<Company> findAll(CompanyDTO filter) {
//      public List<Company> findAll(CompanyDTO filter, Integer pageSize, Integer pageIndex) {
        JPAQuery<Company> query = new JPAQuery<Company>(em).from(company);

        if(filter.getCnpj() != null) {	 	 
        	String cnpj = StringUtil.formatCnpj(filter.getCnpj());
        	query.where(company.cnpj.eq(cnpj));
        }
        
        if(filter.getId() != null)			 query.where(company.id.eq(filter.getId()));
        if(filter.getName() != null)		 query.where(company.name.eq(filter.getName()));
        if(filter.getSector() != null)		 query.where(company.sector.eq(filter.getSector()));
        if(filter.getExternalId() != null) 	 query.where(company.externalId.eq(filter.getExternalId()));
        if(filter.getScriptId() != null)	 query.where(company.scriptId.eq(filter.getScriptId()));
        if(filter.getAccountingId() != null) query.where(company.accountingId.eq(filter.getAccountingId()));
        
//        if (pageSize != null && pageSize > 0) {
//            query.limit(pageSize);
//            if (pageIndex != null && pageIndex > 0) {
//                query.offset(pageSize * pageIndex);
//            }
//        }

        return query.orderBy(company.name.asc()).fetch();
    }

	@Override
	public Company findByCnpj(String cnpj) {
		JPAQuery<Company> query = new JPAQuery<Company>(em).from(company);

		try { query.where(company.cnpj.eq(StringUtil.formatCnpj(cnpj))); } 
		catch (Exception ex) { ex.printStackTrace(); }
		
        return query.fetchOne();
	}

}
