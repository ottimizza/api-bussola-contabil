package br.com.ottimizza.dashboard.controllers;

import br.com.ottimizza.dashboard.dtos.KpiDTO;
import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.KpiShort;
import br.com.ottimizza.dashboard.services.KpiService;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.inject.Inject;
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
@RequestMapping("/kpi")
public class KpiController {

	@Inject
	KpiService kpiService;

	@PostMapping("save")
	public ResponseEntity<Kpi> saveKpi(@RequestBody Kpi kpi) throws Exception {
		return ResponseEntity.ok(kpiService.save(kpi));
	}

	@GetMapping("find/{id}")
	public ResponseEntity<Optional<Kpi>> findKpiByID(@PathVariable("id") BigInteger idKpi) throws Exception {
		return ResponseEntity.ok(kpiService.findById(idKpi));
	}

	@RequestMapping(value = "/find/cnpj", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<List<KpiShort>> findKpiByCNPJ(@RequestBody Map<String, List<String>> body) throws Exception {
		List<String> listaCNPJ = body.get("cnpj");
		return ResponseEntity.ok(kpiService.findByListCNPJ(listaCNPJ));
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<String> removeKpi(@PathVariable("id") BigInteger idKpi) throws Exception {
		return ResponseEntity.ok(kpiService.delete(idKpi).toString());
	}

	@GetMapping("gain/{companyId}")
	public ResponseEntity<KpiDTO> findKpiValue(@PathVariable("companyId") BigInteger companyId) throws Exception {		
		return ResponseEntity.ok(kpiService.kpiValue(companyId));
	}
	
	@PostMapping("gain")
	public ResponseEntity<KpiDTO> findKpiValueByCnpj(@RequestBody Map<String, String> body) throws Exception {		
		return ResponseEntity.ok(kpiService.kpiValueByCnpj(body.get("cnpj")));
	}
	
}
