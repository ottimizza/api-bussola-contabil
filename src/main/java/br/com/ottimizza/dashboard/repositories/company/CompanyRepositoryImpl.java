package br.com.ottimizza.dashboard.repositories.company;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.domain.dtos.CompanyDTO;
import br.com.ottimizza.dashboard.models.Company;
import br.com.ottimizza.dashboard.models.QCompany;

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
    public List<Company> findAll(CompanyDTO filter, Integer pageSize, Integer pageIndex) {
        JPAQuery<Company> query = new JPAQuery<Company>(em).from(company);
        
        if(filter.getCnpj() != null)	query.where(company.cnpj.eq(filter.getCnpj()));
        if(filter.getId() != null)		query.where(company.id.eq(filter.getId()));
        if(filter.getName() != null)	query.where(company.name.eq(filter.getName()));
        if(filter.getSector() != null)	query.where(company.sector.eq(filter.getSector()));
        if(filter.getOrganizationId() != null)	query.where(company.organizationId.eq(filter.getOrganizationId()));
        if(filter.getScriptType() != null)		query.where(company.scriptType.eq(filter.getScriptType()));
        
        if (pageSize != null && pageSize > 0) {
            query.limit(pageSize);
            if (pageIndex != null && pageIndex > 0) {
                query.offset(pageSize * pageIndex);
            }
        }

        return query.orderBy(company.name.asc()).fetch();
    }

//	@Override
//	public Company findByCnpj(String cnpj) {
//		JPAQuery<Company> query = new JPAQuery<Company>(em).from(company).where(company.cnpj.eq(cnpj));
//		//select c from Company c where cnpj = :cnpj limit 1")
//
//		return null;
//	}
}
