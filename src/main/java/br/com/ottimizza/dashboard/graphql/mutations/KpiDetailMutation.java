package br.com.ottimizza.dashboard.graphql.mutations;

import java.math.BigInteger;

import javax.persistence.EntityManager;

import br.com.ottimizza.dashboard.models.KpiDetail;
import br.com.ottimizza.dashboard.models.QKpiDetail;
import br.com.ottimizza.dashboard.repositories.company.CompanyRepository;
import br.com.ottimizza.dashboard.repositories.kpi.KpiRepository;
import br.com.ottimizza.dashboard.repositories.kpi_detail.KpiDetailRepository;
import io.leangen.graphql.annotations.GraphQLMutation;

public class KpiDetailMutation {

	private EntityManager em;
	private QKpiDetail kpiDetail = QKpiDetail.kpiDetail;
	private KpiDetailRepository kpiDetailRepository;
	private KpiRepository kpiRepository;
	private CompanyRepository companyRepository;

	public KpiDetailMutation(EntityManager em, KpiDetailRepository kpiDetailRepository, KpiRepository kpiRepository, CompanyRepository companyRepository) {
		this.em = em;
		this.kpiDetailRepository = kpiDetailRepository;
		this.kpiRepository = kpiRepository;
		this.companyRepository = companyRepository;
		
	}
	
	@GraphQLMutation(name = "editKpiDetail")
	public KpiDetail editKpiDetail(BigInteger id, BigInteger kpiId, String columnX, String columnY, String columnZ, 
			Double valorKPI, Double valorKPI2, Double valorKPI3, Double valorKPI4, String columnXSeq, String xBinding) {
		
		KpiDetail detail = new KpiDetail();

//		if(id != null) {
//			Optional<KpiDetail> detailOptional = kpiDetailRepository.findById(id);
//			try { detail = detailOptional.get(); } 
//			catch (Exception e) {	}
//			
//			if(kpiId != null) {
//				Kpi kpi = new Kpi();
//				Optional<Kpi> kpiOptional = kpiRepository.findById(kpiId);
//				try { 
//					kpi = kpiOptional.get(); 
//					detail.setKpiID(kpi);		//validar
//				}catch(Exception ee) { }
//			}
//			
//			if(columnXSeq != null) 	detail.setColumnXSeq(columnXSeq);
//			if(columnX != null) 	detail.setColumnX(columnX);
//			if(columnY != null) 	detail.setColumnY(columnY);
//			if(columnZ != null) 	detail.setColumnZ(columnZ);
//			if(valorKPI != null) 	detail.setValorKPI(valorKPI);
//			if(valorKPI2 != null) 	detail.setValorKPI2(valorKPI2);
//			if(valorKPI3 != null) 	detail.setValorKPI3(valorKPI3);
//			if(valorKPI4 != null) 	detail.setValorKPI4(valorKPI4);
//			if(xBinding != null) 	detail.setXBinding(xBinding);
//			
//
//		}
		return kpiDetailRepository.save(detail);
	}
	
	@GraphQLMutation(name = "createKpiDetail")
	public KpiDetail createKpiDetail(BigInteger kpiId, String columnX, String columnY, String columnZ, 
				Double valorKPI, Double valorKPI2, Double valorKPI3, Double valorKPI4, String columnXSeq, String xBinding) {
		
		KpiDetail detail = new KpiDetail();

//		if(kpiId != null) {
//			Kpi kpi = new Kpi();
//			Optional<Kpi> kpiOptional = kpiRepository.findById(kpiId);
//			try { kpi = kpiOptional.get(); }
//			catch(Exception ee) { }
//		
//			detail.setKpiID(kpi);
//			detail.setColumnXSeq(columnXSeq != null ? columnXSeq : "");
//			detail.setColumnX(columnX != null ? columnX : "");
//			detail.setColumnY(columnY != null ? columnY : "");
//			detail.setColumnZ(columnZ != null ? columnZ : "");
//			detail.setValorKPI(valorKPI != null ? valorKPI : 0);
//			detail.setValorKPI2(valorKPI2 != null ? valorKPI2 : 0);
//			detail.setValorKPI3(valorKPI3 != null ? valorKPI3 : 0);
//			detail.setValorKPI4(valorKPI4 != null ? valorKPI4 : 0);
//			detail.setXBinding(xBinding != null ? xBinding : "");
//		}
		return kpiDetailRepository.save(detail);
	}
	

}
