package br.com.ottimizza.dashboard.controllers;

import java.math.BigInteger;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ottimizza.dashboard.domain.dtos.KpiDTO;
import br.com.ottimizza.dashboard.domain.dtos.KpiTitleAndValueDTO;
import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.services.CompanyService;
import br.com.ottimizza.dashboard.services.KpiService;
import br.com.ottimizza.dashboard.utils.StringUtil;

@RestController
@RequestMapping("/kpi")
public class KpiController {

	@Inject
	KpiService kpiService;
	
	@Inject
	CompanyService companyService;

	@PostMapping("save")
	public ResponseEntity<Kpi> saveKpi(@RequestBody Kpi kpi) throws Exception {
		return ResponseEntity.ok(kpiService.save(kpi));
	}

	@GetMapping("find/{id}")
	public ResponseEntity<Optional<Kpi>> findKpiByID(@PathVariable("id") BigInteger idKpi) throws Exception {
		return ResponseEntity.ok(kpiService.findById(idKpi));
	}

//	@RequestMapping(value = "/find/cnpj", method = RequestMethod.POST, consumes = "application/json")
//	public ResponseEntity<List<KpiShort>> findKpiByCNPJ(@RequestBody Map<String, List<String>> body) throws Exception {
//		List<String> listaCNPJ = body.get("cnpj");
//		return ResponseEntity.ok(kpiService.findByListCNPJ(listaCNPJ));
//	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<String> removeKpi(@PathVariable("id") BigInteger idKpi) throws Exception {
		return ResponseEntity.ok(kpiService.delete(idKpi).toString());
	}

	@GetMapping("gain/{cnpj}")
	public ResponseEntity<KpiTitleAndValueDTO> findKpiValue(@PathVariable("cnpj") String cnpj) throws Exception {
		cnpj = StringUtil.formatCnpj(cnpj);
		BigInteger companyId = companyService.findByCnpj(cnpj).getId();
		return ResponseEntity.ok(kpiService.kpiValue(companyId));
	}
	
	@GetMapping
	public ResponseEntity<?> findAll(@Valid KpiDTO filter, 
									 @RequestParam(name = "page_index", defaultValue = "0") int pageIndex,
						             @RequestParam(name = "page_size", defaultValue = "10") int pageSize, 
						             @RequestHeader("Authorization") String authorization) throws Exception {
  		return ResponseEntity.ok(kpiService.findAll(filter, pageIndex, pageSize, authorization));
	}
	
	@GetMapping("toChart/{cnpj}")
	public ResponseEntity<?> findToChart(@PathVariable("cnpj") String cnpj) throws Exception {
		return ResponseEntity.ok(kpiService.findToChart(cnpj));
	}
	
	
}
