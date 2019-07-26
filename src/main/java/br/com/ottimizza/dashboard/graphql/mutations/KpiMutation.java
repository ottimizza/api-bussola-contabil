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

	private EntityManager em;
	private QKpi kpi = QKpi.kpi;
	private CompanyRepository companyRepository;
	private KpiRepository kpiRepository;
	private KpiDetailRepository kpiDetailRepository;

	public KpiMutation(EntityManager em, KpiDetailRepository kpiDetailRepository, KpiRepository kpiRepository, CompanyRepository companyRepository) {
		this.em = em;
		this.kpiDetailRepository = kpiDetailRepository;
		this.kpiRepository = kpiRepository;
		this.companyRepository = companyRepository;
	}

	
//	public Kpi editKpi(Kpi newKpi) {
//		Kpi kpi = new Kpi();
//		try {
//			kpi = kpiRepository.findById(newKpi.getId()).map(k -> k).orElseThrow(() -> new NotFoundException());
//		} catch (Exception e) {
//		}
//
//		kpi.setKpiAlias(newKpi.getKpiAlias());
//		kpi.setTitle(newKpi.getTitle());
//		kpi.setSubtitle(newKpi.getSubtitle());
//		kpi.setDescription(newKpi.getDescription());
//		kpi.setGraphType(newKpi.getGraphType());
//		kpi.setColumnX0Label(newKpi.getColumnX0Label());
//		kpi.setLabel(newKpi.getLabel());
//		kpi.setLabel2(newKpi.getLabel2());
//		kpi.setLabel3(newKpi.getLabel3());
//		kpi.setLabel4(newKpi.getLabel4());
//		kpi.setVisible(newKpi.getVisible());
//		kpi.setKpiDetail(newKpi.getKpiDetail());
//
//		return kpiRepository.save(kpi);
//	}

	@GraphQLMutation(name = "createKpi")
	public Kpi createKpi(BigInteger companyId, String kpiAlias, String title, String subtitle, String description, Short graphType, 
						String columnX0Label, String label, String label2, String label3, String label4, Boolean visible) {

		Company company = new Company();
		Kpi kpi = new Kpi();

		if (companyId != null) {
			Optional<Company> optionalCompany = companyRepository.findById(companyId);
			try { company = optionalCompany.get(); } 
			catch (Exception e) { new NoSuchElementException(e.getStackTrace().toString()); }
		}
//		company.setName(name == null ? company.getName() : name);
		if (company != null) {
			kpi.setCompany(company);
			kpi.setKpiAlias(kpiAlias);
			kpi.setTitle(title);
			kpi.setSubtitle(subtitle != null ? subtitle : "");
			kpi.setDescription(description != null ? description : "");
			kpi.setGraphType(graphType);
			kpi.setColumnX0Label(columnX0Label);
			kpi.setLabel(label != null ? label : "");
			kpi.setLabel2(label2 != null ? label2 : "");
			kpi.setLabel3(label3 != null ? label3 : "");
			kpi.setLabel4(label4 != null ? label4 : "");
			kpi.setVisible(visible != null ? visible : true);
		}
		
		return kpiRepository.save(kpi);
	}
	
	@GraphQLMutation(name = "editKpi")
	public Kpi editKpi(BigInteger id, String kpiAlias, String title, String subtitle, String description, Short graphType, 
					String columnX0Label, String label, String label2, String label3, String label4, Boolean visible) {

		Kpi kpi = new Kpi();
//		kpi = kpiRepository.findById(id).map(k -> k).orElseThrow(() -> new NotFoundException());
		
		Optional<Kpi> kpiOptional = kpiRepository.findById(id);

		try {
			kpi = kpiOptional.get();
		}catch (Exception e) {}
		System.out.println(">> >1 "+kpi.getId());
		if (kpi != null) {
			System.out.println(">> >3 "+kpi.getTitle()+" - "+kpi.getLabel());
			
//			company.setName(name == null ? company.getName() : name);
			
			kpi.setKpiAlias(kpiAlias);
			kpi.setTitle(title);
			kpi.setSubtitle(subtitle);
			kpi.setDescription(description);
			kpi.setGraphType(graphType);
			kpi.setColumnX0Label(columnX0Label);
			kpi.setLabel(label);
			kpi.setLabel2(label2);
			kpi.setLabel3(label3);
			kpi.setLabel4(label4);
			kpi.setVisible(visible);
		}
		System.out.println(">> >4 "+kpi.getTitle()+" - "+kpi.getLabel());
		return kpiRepository.save(kpi);
	}
	
//	@GraphQLMutation(name = "deleteKpi")
//	public Kpi deleteKpi(BigInteger id) {
//		Kpi kpi = new Kpi();
//		try {
//			kpi = kpiRepository.findById(id).map(k -> k).orElseThrow(() -> new NotFoundException());
//		} catch (Exception e) {
//		}
//
//		if (kpi.getKpiDetail().isEmpty()) {
//			kpiRepository.delete(kpi);
//		} else {
//			List<KpiDetail> kpiDetails = kpi.getKpiDetail();
//
//			for (KpiDetail kpiDetail : kpiDetails) {
//				System.out.println("for . " + kpiDetail.getId());
////				kpiDetailMut.deleteKpiDetail(kpiDetail.getId());
//
//				System.out.println("for ..");
//			}
////			deleteKpi(kpi.getId());
////			kpiRepository.delete(kpi);
//
//		}
//
//		return kpi;
//	}

//	@GraphQLMutation(name = "deleteKpi")
//	public Kpi deleteKpi(Kpi newKpi) {
//		Kpi kpi = kpiRepository.findById(newKpi.getId());
//		kpiRepository.delete(kpi);
//
//		return kpi;
//	}
}
