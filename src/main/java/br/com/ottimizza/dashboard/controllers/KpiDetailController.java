package br.com.ottimizza.dashboard.controllers;

import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.KpiDetail;
import br.com.ottimizza.dashboard.services.KpiDetailService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.inject.Inject;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kpi/detail")
public class KpiDetailController {

	@Inject
	KpiDetailService kpiService;

	@PostMapping("save")
	public ResponseEntity<KpiDetail> saveKpi(@RequestBody KpiDetail kpi) throws Exception {
		return ResponseEntity.ok(kpiService.save(kpi));
	}

	@GetMapping("find/{id}")
	public ResponseEntity<Optional<KpiDetail>> findKpiByID(@PathVariable("id") BigInteger idKpi) throws Exception {
		return ResponseEntity.ok(kpiService.findById(idKpi));
	}

	@RequestMapping(value = "/find/cnpj", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<List<KpiDetail>> findKpiDetailByCNPJ(@RequestBody Map<String, List<String>> body)
			throws Exception {
		List<String> listaCNPJ = body.get("cnpj");
		return ResponseEntity.ok(kpiService.findByListCNPJ(listaCNPJ));
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<String> removeKpi(@PathVariable("id") BigInteger idKpi) throws Exception {
		return ResponseEntity.ok(kpiService.delete(idKpi).toString());
	}

	@PostMapping("createDetails")
//	public ResponseEntity<List<KpiDetail>> createDetails(@RequestBody Map<String, List<KpiDetail>> body) throws Exception {
	public ResponseEntity<List<KpiDetail>> createDetails(@RequestBody JSONObject objBody) throws Exception {

		System.out.println(">>> 0 ");
		List<KpiDetail> listaRet = new ArrayList<KpiDetail>();
		
		try {
			String kpiId = objBody.optString("kpi");
			System.out.println(">>> 1 "+kpiId);
			
			List<KpiDetail> listaKpis = (List<KpiDetail>) objBody.get("kpisDetail"); 
			
			
			for (KpiDetail kpiDetail : listaKpis) {
				KpiDetail detail = new KpiDetail();
				
				if(!kpiDetail.getColumnX().equals("") && kpiDetail.getValorKPI() != 0) {
					try {
						kpiService.save(kpiDetail);
						listaRet.add(kpiDetail);
					}catch (Exception e) { }
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return ResponseEntity.ok(listaRet);
	}

	
}
