package br.com.ottimizza.dashboard.repositories.kpi;

import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.KpiShort;
import br.com.ottimizza.dashboard.models.QCompany;
import br.com.ottimizza.dashboard.models.QKpi;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;

@Repository
public class KpiRepositoryImpl implements KpiRepositoryCustom {
    
    @PersistenceContext
    EntityManager em;
    
    private QCompany company = QCompany.company;
    private QKpi kpi = QKpi.kpi;
    
    @Override
    public List<Kpi> findKpisByCNPJ(List<String> cnpj) {
        JPAQuery<Kpi> query = new JPAQuery<Kpi>(em).from(kpi)
                .innerJoin(company).on(company.id.eq(kpi.company.id))
                .where(company.cnpj.in(cnpj));
        return query.orderBy(kpi.kpiAlias.asc()).fetch();
    }
    
}
