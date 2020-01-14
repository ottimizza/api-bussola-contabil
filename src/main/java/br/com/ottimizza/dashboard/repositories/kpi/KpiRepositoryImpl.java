package br.com.ottimizza.dashboard.repositories.kpi;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.domain.dtos.KpiDTO;
import br.com.ottimizza.dashboard.domain.dtos.KpiTitleAndValueDTO;
import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.QCompany;
import br.com.ottimizza.dashboard.models.QKpi;
import br.com.ottimizza.dashboard.models.QKpiDetail;
import br.com.ottimizza.dashboard.utils.StringUtil;

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

	@Override
	public Page<Kpi> findAll(KpiDTO filter, Pageable pageable) {
		try {
			long totalElements = 0;
			JPAQuery<Kpi> query = new JPAQuery<Kpi>(em).from(kpi)
					.innerJoin(company).on(company.id.eq(kpi.company.id));
			
			if(filter.getCnpj() != null) {			
				query.where(company.cnpj.eq(StringUtil.formatCnpj(filter.getCnpj())));
			}
			
			if(filter.getKind() != null) {
				if(filter.getKind() == 1) query.where(kpi.kpiAlias.lt("60"));	//indicadores normais
				else if(filter.getKind() == 2) query.where(kpi.kpiAlias.goe("60"));	//comparativos
				else return null;
			}
			
			totalElements = query.fetchCount();
			query.limit(pageable.getPageSize());
			query.offset(pageable.getPageSize() * pageable.getPageNumber());
			
			return new PageImpl<Kpi>(query.fetch(), pageable, totalElements);
		}catch (Exception e) {
			return null;
		}
	}
}
