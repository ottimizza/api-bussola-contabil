package br.com.ottimizza.dashboard.graphql.mutations;

import java.math.BigInteger;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;

import br.com.ottimizza.dashboard.models.Company;
import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.KpiDetail;
import br.com.ottimizza.dashboard.models.QKpi;
import br.com.ottimizza.dashboard.repositories.company.CompanyRepository;
import br.com.ottimizza.dashboard.repositories.company.CompanyRepositoryCustom;
import br.com.ottimizza.dashboard.repositories.company.CompanyRepositoryImpl;
import br.com.ottimizza.dashboard.repositories.kpi.KpiRepository;
import br.com.ottimizza.dashboard.repositories.kpi_detail.KpiDetailRepository;
import br.com.ottimizza.dashboard.services.KpiDetailService;
import io.leangen.graphql.annotations.GraphQLMutation;

public class KpiMutation {

	@Inject
	KpiDetailMutation kpiDetailMut;

	
	private EntityManager em;
	private QKpi kpi = QKpi.kpi;
	private CompanyRepository companyRepository;
	private KpiRepository kpiRepository;
	private KpiDetailRepository kpiDetailRepository;

	public KpiMutation(EntityManager em, KpiRepository kpiRepository) {
		this.em = em;
		this.kpiRepository = kpiRepository;
	}
	
	@GraphQLMutation(name = "editKpi")
	public Kpi editKpi(Kpi newKpi) {
		Kpi kpi = new Kpi();
		try {
			kpi = kpiRepository.findById(newKpi.getId()).map(k -> k)
					.orElseThrow(() -> new NotFoundException());
		} catch (Exception e) { }
		
		kpi.setKpiAlias(newKpi.getKpiAlias());
		kpi.setTitle(newKpi.getTitle());
		kpi.setSubtitle(newKpi.getSubtitle());
		kpi.setDescription(newKpi.getDescription());
		kpi.setGraphType(newKpi.getGraphType());
		kpi.setColumnX0Label(newKpi.getColumnX0Label());
		kpi.setLabel(newKpi.getLabel());
		kpi.setLabel2(newKpi.getLabel2());
		kpi.setLabel3(newKpi.getLabel3());
		kpi.setLabel4(newKpi.getLabel4());
		kpi.setVisible(newKpi.getVisible());
		kpi.setKpiDetail(newKpi.getKpiDetail());
		
		return kpiRepository.save(kpi);
	}
	
	@GraphQLMutation(name = "createKpi")
	public Kpi createKpi(BigInteger companyID, String title, Short graphType, String columnX0Label, String label) {
		Kpi kpi = new Kpi();
		Optional<Company> optionalCompany = companyRepository.findById(companyID);
		Company c = new Company();
		try {
			c = optionalCompany.get();
		}catch (Exception e) {
			new NoSuchElementException();
		}
		kpi.setCompany(c);
		kpi.setTitle(title);
		kpi.setGraphType(graphType);
		kpi.setColumnX0Label(columnX0Label);
		kpi.setLabel(label);
		
		return kpiRepository.save(kpi);
		
	}
//	public Kpi createKpi(Kpi filter) {
//		return kpiRepository.save(filter);
//	}
	
	@GraphQLMutation(name = "deleteKpi")
	public Kpi deleteKpi(BigInteger id) {
		Kpi kpi = new Kpi();
		try {
			kpi = kpiRepository.findById(id).map(k -> k)
					.orElseThrow(() -> new NotFoundException());
		} catch (Exception e) { }
		
		if(kpi.getKpiDetail().isEmpty()) {
			kpiRepository.delete(kpi);
		} else {
			List<KpiDetail> kpiDetails = kpi.getKpiDetail();
			
			
			for (KpiDetail kpiDetail : kpiDetails) {
				System.out.println("for . "+kpiDetail.getId());
//				kpiDetailMut.deleteKpiDetail(kpiDetail.getId());
				
				System.out.println("for ..");
			}
//			deleteKpi(kpi.getId());
//			kpiRepository.delete(kpi);
			
		}

		return kpi;
	}
	
//	@GraphQLMutation(name = "deleteKpi")
//	public Kpi deleteKpi(Kpi newKpi) {
//		Kpi kpi = kpiRepository.findById(newKpi.getId());
//		kpiRepository.delete(kpi);
//
//		return kpi;
//	}
}
