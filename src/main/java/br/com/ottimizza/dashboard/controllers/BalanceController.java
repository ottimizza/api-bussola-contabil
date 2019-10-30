package br.com.ottimizza.dashboard.controllers;

import java.math.BigInteger;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ottimizza.dashboard.dtos.AnnotationDTO;
import br.com.ottimizza.dashboard.dtos.BalanceDTO;
import br.com.ottimizza.dashboard.models.Annotation;
import br.com.ottimizza.dashboard.models.Balance;
import br.com.ottimizza.dashboard.models.Company;
import br.com.ottimizza.dashboard.services.BalanceService;
import br.com.ottimizza.dashboard.services.CompanyService;

@RestController
@RequestMapping("balance")
public class BalanceController {
	
	@Inject
	BalanceService service;
	
	@Inject
	CompanyService companyService;
	
	@PostMapping
	public ResponseEntity<Balance> save(@RequestBody Balance balance) throws Exception {
		return ResponseEntity.ok(service.save(balance));
	}
	
//	@PatchMapping("{id}")
//    public ResponseEntity<Balance> patch(@PathVariable("id") BigInteger id, @RequestBody AnnotationDTO annotationDTO, Principal principal)
//            throws Exception {
//        return ResponseEntity.ok(balanceService.patch(id, annotationDTO, principal));
//    }
	
	@GetMapping("{id}")
	public ResponseEntity<Balance> findByID(@PathVariable("id") BigInteger balanceId) throws Exception {
		Balance balance = service.findById(balanceId);
		return (balance != null) ? ResponseEntity.ok(balance) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> remove(@PathVariable("id") BigInteger balanceId) throws Exception {
		return ResponseEntity.ok(service.delete(balanceId).toString());
	}
	

	@PostMapping("deleteAllBalances")
	public ResponseEntity<String> removeAllBalances(@RequestBody BalanceDTO body) throws Exception{
		return ResponseEntity.ok(service.deleteByCnpjAndDate(body.getCnpj(), body.getDateBalance()).toString());
	}
	
	@PostMapping("createBalances")
	public ResponseEntity<List<Balance>> createBalances(@RequestBody BalanceDTO body) throws Exception {
		
		List<Balance> listReturn = new ArrayList<Balance>();
		List<Balance> listBalances = new ArrayList<Balance>();
		BigInteger companyId = body.getCompanyId();
		Company company = new Company();
		
		try { company = companyService.findById(companyId).orElse(null); } 
		catch (Exception ee) {	}
		
		listBalances = body.getBalances();

		for (Balance balance : listBalances) {
			balance.setCompanyId(companyId);
			try {
				service.save(balance);
				listReturn.add(balance);
			} catch (Exception e) { }
		}
		return ResponseEntity.ok(listReturn);
	}
	
//	@PostMapping("createBalancesByCNPJ")
//	public ResponseEntity<List<Balance>> createBalancesCnpj(@RequestBody CreateBalanceDTO body) throws Exception {
//		
//		List<Balance> listReturn = new ArrayList<Balance>();
//		List<Balance> listBalances = new ArrayList<Balance>();
//		String cnpj = body.getCnpj();
//		Organization organization = new Organization();
//		
//		try { organization = organizationService.findByCnpj(cnpj); } 
//		catch (Exception ee) {	}
//		
//		listBalances = body.getBalances();
//
//		for (Balance balance : listBalances) {
//			balance.setOrganization(organization);
//			try {
//				balanceService.save(balance);
//				listReturn.add(balance);
//			} catch (Exception e) { }
//		}
//		return ResponseEntity.ok(listReturn);
//	}

}
