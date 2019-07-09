package br.com.ottimizza.dashboard.graphql.mutations;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.KpiDetail;
import br.com.ottimizza.dashboard.models.QKpi;
import br.com.ottimizza.dashboard.repositories.kpi.KpiRepository;
import br.com.ottimizza.dashboard.repositories.kpi_detail.KpiDetailRepository;
import io.leangen.graphql.annotations.GraphQLMutation;

public class KpiMutation {

	private EntityManager em;
	private QKpi kpi = QKpi.kpi;
	private KpiRepository kpiRepository;
	private KpiDetailRepository kpiDetailRepository;

	public KpiMutation(EntityManager em, KpiRepository kpiRepository) {
		this.em = em;
		this.kpiRepository = kpiRepository;
	}
	
//	@GraphQLMutation(name = "editKpi")
//	public Kpi editKpi(BigInteger id, String title) {
//		Kpi kpi = kpiRepository.findById(id);
//		kpi.setTitle(title);
//		
//		return kpiRepository.save(kpi);
//	}
	
	
//	@GraphQLMutation(name = "mutationKpi")
	public Kpi mutationKpi(Kpi kpi) {
		
		if(kpi.getId() != null) {
			return editKpi(kpi);
		} else {
			return createKpi(kpi);
		}
		
	}
	@GraphQLMutation(name = "editKpi")
	public Kpi editKpi(Kpi newKpi) {
		Kpi kpi = kpiRepository.findById(newKpi.getId());
		
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
	
	public Kpi createKpi(Kpi kpi) {
		return kpiRepository.save(kpi);
	}
	
	@GraphQLMutation(name = "deleteKpi")
	public Kpi deleteKpi(BigInteger id) {
		Kpi kpi = kpiRepository.findById(id);
		
		if(kpi.getKpiDetail().isEmpty()) {
			kpiRepository.delete(kpi);
		} else {
//			List<KpiDetail> kpiDetails = kpi.getKpiDetail();
//			for (KpiDetail kpiDetail : kpiDetails) {
//				kpiDetailRepository.delete(kpiDetail);
//			}
////			deleteKpi(kpi.getId());
//			kpiRepository.delete(kpi);
			
		}

		return kpi;
	}
	
	@GraphQLMutation(name = "deleteKpi")
	public Kpi deleteKpi(Kpi newKpi) {
		Kpi kpi = kpiRepository.findById(newKpi.getId());
		kpiRepository.delete(kpi);

		return kpi;
	}
}
