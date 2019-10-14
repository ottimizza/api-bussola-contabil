package br.com.ottimizza.dashboard.repositories.kpi;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.dtos.KpiTitleAndValueDTO;
import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.QCompany;
import br.com.ottimizza.dashboard.models.QKpi;
import br.com.ottimizza.dashboard.models.QKpiDetail;

@Repository
public class KpiRepositoryImpl implements KpiRepositoryCustom {
    
    @PersistenceContext
    EntityManager em;
    
    private QCompany company = QCompany.company;
    private QKpi kpi = QKpi.kpi;
    private QKpiDetail kpiDetail = QKpiDetail.kpiDetail;
    
    @Override
    public List<Kpi> findKpisByCNPJ(List<String> cnpj) {
        JPAQuery<Kpi> query = new JPAQuery<Kpi>(em).from(kpi)
                .innerJoin(company).on(company.id.eq(kpi.company.id))
                .where(company.cnpj.in(cnpj));
        return query.orderBy(kpi.kpiAlias.asc()).fetch();
    }

	@Override
	public KpiTitleAndValueDTO findKpiDTOByCompanyId(BigInteger companyId) {
		
		JPAQuery<KpiTitleAndValueDTO> query = new JPAQuery<KpiTitleAndValueDTO>(em).from(kpi)
                .innerJoin(kpiDetail).on(kpiDetail.kpiID.id.eq(kpi.id))
                .where(kpi.company.id.eq(companyId)
                .and(kpi.graphType.in(7,12)));
        
        query.select(Projections.constructor(KpiTitleAndValueDTO.class, kpi.title, kpiDetail.valorKPI, kpi.kpiAlias));
		
        return query.fetchFirst();
	}

	   
}
