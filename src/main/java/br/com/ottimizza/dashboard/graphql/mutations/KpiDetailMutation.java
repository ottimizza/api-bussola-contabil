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
	private KpiDetailRepository detailRepository;

	public KpiDetailMutation(EntityManager em, KpiDetailRepository kpiDetailRepository) {
		this.em = em;
		this.detailRepository = kpiDetailRepository;
	}
	
	//teste
	@GraphQLMutation(name = "editDetail")
	public KpiDetail editKpiDetail(BigInteger id, String columnXSeq) { 
		Optional<KpiDetail> detailOptional = detailRepository.findById(id);
		KpiDetail detail = new KpiDetail();
//		if(!detailOptional.isEmpty()) {
//			detail = detailOptional.get();
//			detail.setColumnXSeq(columnXSeq);
//		}
		return detailRepository.save(detail);
	}
	
	
	@GraphQLMutation(name = "deleteDetail")
	public KpiDetail deleteDetail(BigInteger id) {
		KpiDetail detail = new KpiDetail();
//		Optional<KpiDetail> detailOptional = kpiDetailRepository.findById(id);
//		KpiDetail detail = kpiDetailRepository.findById(id);
//		System.out.println(">> > "+detail.getValorKPI()); 
		detailRepository.deleteById(id);
		return detail;
	}
	

}
