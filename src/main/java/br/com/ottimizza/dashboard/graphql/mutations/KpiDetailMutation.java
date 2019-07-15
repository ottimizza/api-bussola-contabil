package br.com.ottimizza.dashboard.graphql.mutations;

import java.math.BigInteger;
import java.util.NoSuchElementException;
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

	// teste
//	@GraphQLMutation(name = "editKpiDetail")
//	public KpiDetail editKpiDetail(BigInteger id, String columnXSeq, double valorKPI, String columnX) throws NoSuchElementException, Exception{ 
//		KpiDetail detail = new KpiDetail();
//		Optional<KpiDetail> detailOptional = detailRepository.findById(id);
//		try {	
//			detail = detailOptional.get();
//		}catch (Exception e) {	}
//		if(!columnXSeq.equals(""))		detail.setColumnXSeq(columnXSeq);
//		if(!columnX.equals(""))			detail.setColumnX(columnX);
//		if(valorKPI != 0) 				detail.setValorKPI(valorKPI);
//		return detailRepository.save(detail);
//	}
	@GraphQLMutation(name = "editKpiDetail")
	public KpiDetail editKpiDetail(KpiDetail filter) throws NoSuchElementException, Exception {
		KpiDetail detail = new KpiDetail();
		
		if(filter.getId() != null) {
			Optional<KpiDetail> detailOptional = detailRepository.findById(filter.getId());
			try { detail = detailOptional.get(); } 
			catch (Exception e) {	}
			
			if(filter.getColumnXSeq() != null) 	detail.setColumnXSeq(filter.getColumnXSeq());
			if(filter.getColumnX() != null) 	detail.setColumnX(filter.getColumnX());
			if(filter.getColumnY() != null) 	detail.setColumnY(filter.getColumnY());
			if(filter.getColumnZ() != null) 	detail.setColumnZ(filter.getColumnZ());
			if(filter.getColumnX() != null) 	detail.setColumnX(filter.getColumnX());
			if(filter.getValorKPI() != 0) 		detail.setValorKPI(filter.getValorKPI());
			if(filter.getValorKPI2() != 0) 		detail.setValorKPI2(filter.getValorKPI2());
			if(filter.getValorKPI3() != 0) 		detail.setValorKPI3(filter.getValorKPI3());
			if(filter.getValorKPI4() != 0) 		detail.setValorKPI4(filter.getValorKPI4());
			
		}

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
