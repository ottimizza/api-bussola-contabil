package br.com.ottimizza.dashboard.repositories.company;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;

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
    public List<Company> findAll(Integer pageSize, Integer pageIndex) {
        JPAQuery<Company> query = new JPAQuery<Company>(em).from(company);

        if (pageSize != null && pageSize > 0) {
            query.limit(pageSize);
            if (pageIndex != null && pageIndex > 0) {
                query.offset(pageSize * pageIndex);
            }
        }

        return query.orderBy(company.name.asc()).fetch();
    }
}
