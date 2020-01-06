package br.com.ottimizza.dashboard.controllers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ottimizza.dashboard.client.OAuthClient;
import br.com.ottimizza.dashboard.domain.dtos.BalanceDTO;
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
	
	@Inject
	OAuthClient oauthClient;
	
	@PostMapping
	public ResponseEntity<Balance> save(@RequestBody Balance balance, @RequestHeader("Authorization") String authorization) throws Exception {
//		UserDTO userInfo = oauthClient.getUserInfo(authorization).getBody().getRecord();
		return ResponseEntity.ok(service.save(balance));
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Balance> findByID(@PathVariable("id") BigInteger balanceId, @RequestHeader("Authorization") String authorization) throws Exception {
		Balance balance = service.findById(balanceId);
		return (balance != null) ? ResponseEntity.ok(balance) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> remove(@PathVariable("id") BigInteger balanceId, @RequestHeader("Authorization") String authorization) throws Exception {
		return ResponseEntity.ok(service.delete(balanceId).toString());
	}

	@PostMapping("delete_all")
	public ResponseEntity<String> removeAllBalances(@RequestBody BalanceDTO body, @RequestHeader("Authorization") String authorization) throws Exception {
		return ResponseEntity.ok(service.deleteByCnpjAndDate(body.getCnpj(), body.getDateBalance()).toString());
	}
	
	@PostMapping("new")
	public ResponseEntity<List<Balance>> createBalance(@RequestBody BalanceDTO body, @RequestHeader("Authorization") String authorization) throws Exception {
		
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
	
	@PostMapping("new/balances")
	public ResponseEntity<List<Balance>> createBalancesCnpj(@RequestBody BalanceDTO body, @RequestHeader("Authorization") String authorization) throws Exception {
		
		List<Balance> listReturn = new ArrayList<Balance>();
		List<Balance> listBalances = new ArrayList<Balance>();
		String cnpj = body.getCnpj();
		Company company = new Company();
		
		try { company = companyService.findByCnpj(cnpj); } 
		catch (Exception ee) {	}
		
		listBalances = body.getBalances();

		for (Balance balance : listBalances) {
			balance.setCompanyId(company.getId());
			try {
				service.save(balance);
				listReturn.add(balance);
			} catch (Exception e) { }
		}
		return ResponseEntity.ok(listReturn);
	}
	
	@GetMapping
	public ResponseEntity<?> findAll(@ModelAttribute BalanceDTO filter, 
									 @RequestParam(name = "page_index", defaultValue = "0") int pageIndex,
						             @RequestParam(name = "page_size", defaultValue = "10") int pageSize, 
						             @RequestHeader("Authorization") String authorization) throws Exception {
		return ResponseEntity.ok(service.findAll(filter, pageIndex, pageSize, authorization));
	}
	
	@PostMapping("active")
	public ResponseEntity<?> setNotActive(@ModelAttribute BalanceDTO filter, @RequestHeader("Authorization") String authorization) throws Exception {

		return ResponseEntity.ok(service.notActive(filter, authorization));
	}
}
