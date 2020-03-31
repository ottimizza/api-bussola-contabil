package br.com.ottimizza.dashboard.controllers;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ottimizza.dashboard.client.OAuthClient;
import br.com.ottimizza.dashboard.domain.dtos.UserDTO;
import br.com.ottimizza.dashboard.domain.dtos.VariableDTO;
import br.com.ottimizza.dashboard.models.OrganizationVariable;
import br.com.ottimizza.dashboard.services.OrganizationVariableService;

@RestController
@RequestMapping("/variables/organization")
public class OrganizationVariableController {

	@Inject
	OrganizationVariableService service;
	
	@Inject
	OAuthClient oauthClient;
	
	@PostMapping
	public ResponseEntity<VariableDTO> saveVariable(@RequestBody VariableDTO variableDto, @RequestHeader("Authorization") String authorization) throws Exception {
		UserDTO userInfo = oauthClient.getUserInfo(authorization).getBody().getRecord();
		if((variableDto.getAccountingCode() == null || variableDto.getAccountingCode().equals("")) && variableDto.getId() != null) {
			service.delete(variableDto.getId());
			return null;
		}
		try {
			variableDto = service.save(variableDto, userInfo);
			return ResponseEntity.ok(variableDto);
		} catch (Exception e) {  }
		
		return ResponseEntity.badRequest().build();		
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Optional<OrganizationVariable>> findByID(@PathVariable("id") BigInteger id) throws Exception {
		return (service.findById(id) != null) ? ResponseEntity.ok(service.findById(id)) : ResponseEntity.notFound().build();
	}
	
	@GetMapping
	public ResponseEntity<List<OrganizationVariable>> findAll() throws Exception {
		return ResponseEntity.ok(service.findAll());
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> removeOrgVariable(@PathVariable("id") BigInteger id) throws Exception {
		JSONObject response = service.delete(id);
		return (response.get("status") == "Success") ? ResponseEntity.ok(response.toString()) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping("byCompany")
	public ResponseEntity<List<VariableDTO>> findByCompanyId(@Valid VariableDTO filter, 
															 @RequestHeader("Authorization") String authorization) throws Exception {
//		UserDTO userInfo = oauthClient.getUserInfo(authorization).getBody().getRecord();
		UserDTO userInfo = new UserDTO();
		return ResponseEntity.ok(service.findVariableByCompanyId(filter, userInfo));
	}
	
	@GetMapping("missing")
	public ResponseEntity<List<VariableDTO>> findMissing(@Valid VariableDTO filter, 
														 @RequestHeader("Authorization") String authorization) throws Exception {
//		UserDTO userInfo = oauthClient.getUserInfo(authorization).getBody().getRecord();
		UserDTO userInfo = new UserDTO();
		//busca organization oauth CNPJ
		if(filter.getCnpj() == null) {
			try { filter.setCnpj(oauthClient.getOrganizationInfoById(authorization, filter.getCompanyId(), true).getBody().getRecords().get(0).getCnpj()); } 
			catch (Exception e) { e.printStackTrace(); }
		}
		return ResponseEntity.ok(service.findMissingByOrganizationId(filter, userInfo));
	}
	
}
