package br.com.ottimizza.dashboard.repositories.kpi_detail;

import br.com.ottimizza.dashboard.models.CompanyCustom;
import br.com.ottimizza.dashboard.models.KpiDetail;
import br.com.ottimizza.dashboard.models.KpiDetailCustom;
import br.com.ottimizza.dashboard.models.QCompany;
import br.com.ottimizza.dashboard.models.QCompanyCustom;
import br.com.ottimizza.dashboard.models.QKpi;
import br.com.ottimizza.dashboard.models.QKpiCustom;
import br.com.ottimizza.dashboard.models.QKpiDetail;
import br.com.ottimizza.dashboard.models.QKpiDetailCustom;

import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class KpiDetailRepositoryImpl implements KpiDetailRepositoryCustom {

	@PersistenceContext
	EntityManager em;

	private QCompany company = QCompany.company;
	private QKpi kpi = QKpi.kpi;
	private QKpiDetail kpiDetail = QKpiDetail.kpiDetail;
	

	private QCompanyCustom companyCustom = QCompanyCustom.companyCustom;
	private QKpiCustom kpiCustom = QKpiCustom.kpiCustom;
	private QKpiDetailCustom kpiDetailCustom = QKpiDetailCustom.kpiDetailCustom;
	
	@Override
	public List<KpiDetail> findKpiDetailsByCNPJ(List<String> cnpj) {
		JPAQuery<KpiDetail> query = new JPAQuery<KpiDetail>(em).from(kpiDetail).innerJoin(kpi)
				.on(kpi.id.eq(kpiDetail.kpiID.id)).innerJoin(company).on(company.id.eq(kpi.company.id))
				.where(company.cnpj.in(cnpj));
		return query.orderBy(kpiDetail.id.asc()).fetch();
	}

	public List<CompanyCustom> findKpiDetailsCustomByCNPJ(List<String> cnpj) {
		JPAQuery<CompanyCustom> query = new JPAQuery<CompanyCustom>(em).from(companyCustom).innerJoin(kpiCustom)
				.on(kpiCustom.CompanyCustom.id.eq(companyCustom.id)).innerJoin(kpiDetailCustom).on(kpiDetailCustom.kpiID.id.eq(kpiCustom.id))
				.where(companyCustom.cnpj.in(cnpj));
		return query.orderBy(companyCustom.name.asc()).orderBy(kpiCustom.kpiAlias.asc()).orderBy(kpiDetailCustom.columnXSeq.asc())
				.fetch();
	}

}
