package br.com.ottimizza.dashboard.repositories.kpi_detail;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.models.KpiDetail;
import br.com.ottimizza.dashboard.models.QCompany;
import br.com.ottimizza.dashboard.models.QKpi;
import br.com.ottimizza.dashboard.models.QKpiDetail;

@Repository
public class KpiDetailRepositoryImpl implements KpiDetailRepositoryCustom {
    
    @PersistenceContext
    EntityManager em;
    
    private QCompany company = QCompany.company;
    private QKpi kpi = QKpi.kpi;
    private QKpiDetail kpiDetail = QKpiDetail.kpiDetail;
    
    @Override
	public List<KpiDetail> findKpiDetailsByCNPJ(List<String> cnpj) {
        JPAQuery<KpiDetail> query = new JPAQuery<KpiDetail>(em).from(company)
                .innerJoin(kpi).on(kpi.company.id.eq(company.id))
                .innerJoin(kpiDetail).on(kpiDetail.kpiID.id.eq(kpi.id))
                .where(company.cnpj.in(cnpj));
        return query.orderBy(company.name.asc()).orderBy(kpi.kpiAlias.asc()).orderBy(kpiDetail.columnXSeq.asc()).fetch();
    }

	
}
