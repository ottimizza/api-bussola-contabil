package br.com.ottimizza.dashboard.repositories.kpi_detail;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.domain.dtos.KpiDetailDTO;
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
    
    @Override
    public List<KpiDetail> findKpiDetailsByCNPJAndGraphType(List<String> cnpj, Short graphType, String kind) {
        JPAQuery<KpiDetail> query = new JPAQuery<KpiDetail>(em).from(kpiDetail)
                .innerJoin(kpi).on(kpi.id.eq(kpiDetail.kpiID.id).and(kpi.graphType.eq(graphType)))
                .innerJoin(company).on(company.id.eq(kpi.company.id));
              	if	    (kind.equals("1")) query.where(company.cnpj.in(cnpj).and(kpi.kpiAlias.lt("60")));                
              	else if (kind.equals("2")) query.where(company.cnpj.in(cnpj).and(kpi.kpiAlias.goe("60")));
              	else					   query.where(company.cnpj.in(cnpj));
        							       query.orderBy(kpiDetail.columnXSeq.asc());
        return query.fetch();
    }

	@Override
	public JSONObject deleteKpiDetail(KpiDetail kpiDetail) {
		
		return null;
	}

	@Override
	public List<String> findKpiAlias(String cnpj) {
		JPAQuery<String> query = new JPAQuery<String>(em).from(kpi);
		query.innerJoin(company).on(kpi.company.id.eq(company.id));
		query.where(company.cnpj.eq(cnpj));
				
		query.select(kpi.kpiAlias);
		
		return query.orderBy(kpi.graphOrder.asc()).fetch();
	}

	@Override
	public Page<KpiDetail> findAll(KpiDetailDTO filter, Pageable pageable) {
		long totalElements = 0;
		JPAQuery<KpiDetail> query = new JPAQuery<KpiDetail>(em).from(kpiDetail);
		
		if (filter.getKpiId() != null) query.where(kpiDetail.kpiID.id.eq(filter.getKpiId()));
		
		totalElements = query.fetchCount();

		query.limit(pageable.getPageSize());
		query.offset(pageable.getPageSize() * pageable.getPageNumber());
		return new PageImpl<KpiDetail>(query.fetch(), pageable, totalElements);
	}

	@Override
	public List<KpiDetail> findByKpiId(BigInteger id) {
		JPAQuery<KpiDetail> query = new JPAQuery<KpiDetail>(em).from(kpiDetail)
                .innerJoin(kpi).on(kpi.id.eq(kpiDetail.kpiID.id));
		query.where(kpiDetail.kpiID.id.eq(id));
		return query.orderBy(kpiDetail.columnXSeq.asc()).fetch();
	}

	
}
