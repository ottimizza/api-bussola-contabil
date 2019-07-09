package br.com.ottimizza.dashboard.graphql.mutations;

import java.math.BigInteger;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.core.convert.ConversionException;

import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.KpiDetail;
import br.com.ottimizza.dashboard.models.QKpiDetail;
import br.com.ottimizza.dashboard.repositories.kpi_detail.KpiDetailRepository;
import io.leangen.graphql.annotations.GraphQLMutation;

public class KpiDetailMutation {

	private EntityManager em;
	private QKpiDetail kpiDetail = QKpiDetail.kpiDetail;
	private KpiDetailRepository kpiDetailRepository;

	public KpiDetailMutation(EntityManager em, KpiDetailRepository kpidetailRepository) {
		this.em = em;
		this.kpiDetailRepository = kpiDetailRepository;
	}
	
	/*
	  @GraphQLMutation(name = "editKpiDetail")
		public KpiDetail editKpiDetail(BigInteger id, String title) {
		KpiDetail kpiDetail = kpiDetailRepository.findById(id);
		kpi.setTitle(title);
		return kpiDetailRepository.save(kpiDetail);
	}
	*/
	
	@GraphQLMutation(name = "deleteKpiDetail")
	public KpiDetail deleteKpiDetail(BigInteger id) {
//		Optional<KpiDetail> kpiOptional = kpiDetailRepository.findById(id);
		KpiDetail kpiDetail = kpiDetailRepository.findById(id);
		System.out.println(">> > "+kpiDetail.getValorKPI()); 
		//kpiDetailRepository.deleteById(id);
		return kpiDetail;
	}
	

}
