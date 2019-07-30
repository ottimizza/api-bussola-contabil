package br.com.ottimizza.dashboard.controllers;

import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.KpiDetail;
import br.com.ottimizza.dashboard.models.KpiDetailCreate;
import br.com.ottimizza.dashboard.repositories.kpi.KpiRepository;
import br.com.ottimizza.dashboard.services.KpiDetailService;
import br.com.ottimizza.dashboard.services.KpiService;
import javassist.expr.NewArray;

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
	KpiDetailService detailService;

	@Inject
	KpiService kpiService;

	@PostMapping("save")
	public ResponseEntity<KpiDetail> saveKpi(@RequestBody KpiDetail kpi) throws Exception {
		return ResponseEntity.ok(detailService.save(kpi));
	}

	@GetMapping("find/{id}")
	public ResponseEntity<Optional<KpiDetail>> findKpiByID(@PathVariable("id") BigInteger idKpi) throws Exception {
		return ResponseEntity.ok(detailService.findById(idKpi));
	}

	@RequestMapping(value = "/find/cnpj", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<List<KpiDetail>> findKpiDetailByCNPJ(@RequestBody Map<String, List<String>> body)
			throws Exception {
		List<String> listaCNPJ = body.get("cnpj");
		return ResponseEntity.ok(detailService.findByListCNPJ(listaCNPJ));
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<String> removeKpi(@PathVariable("id") BigInteger idKpi) throws Exception {
		return ResponseEntity.ok(detailService.delete(idKpi).toString());
	}

	@PostMapping("createDetails")
	public ResponseEntity<List<KpiDetail>> createDetails(@RequestBody KpiDetailCreate body) throws Exception {
		List<KpiDetail> listReturn = new ArrayList<KpiDetail>();		
		List<KpiDetail> listDetails = new ArrayList<KpiDetail>();		
		BigInteger idKpi = body.getIdKpi();
		Kpi kpi = new Kpi();
		
		Optional<Kpi> optionalKpi = kpiService.findById(idKpi);
		try {kpi = optionalKpi.get();}
		catch(Exception ee) {}
		
		for (KpiDetail kpiDetail : listDetails) {
			KpiDetail detail = new KpiDetail();
			detail = kpiDetail;
			detail.setKpiID(kpi);
		}
		return ResponseEntity.ok(listReturn);
	}

	
}
