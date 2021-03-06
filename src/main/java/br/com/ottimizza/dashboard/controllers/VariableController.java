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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ottimizza.dashboard.client.OAuthClient;
import br.com.ottimizza.dashboard.domain.dtos.UserDTO;
import br.com.ottimizza.dashboard.domain.dtos.VariableDTO;
import br.com.ottimizza.dashboard.models.Variable;
import br.com.ottimizza.dashboard.services.VariableService;

@RestController
@RequestMapping("/variables")
public class VariableController {

	@Inject
	VariableService service;

	@Inject
	OAuthClient oauthClient;
	
	
	@PostMapping
	public ResponseEntity<Variable> saveVariable(@RequestBody VariableDTO variableDto,
												 @RequestHeader("Authorization") String authorization) throws Exception {
		try {
			variableDto = service.save(variableDto, authorization);
			return ResponseEntity.ok(VariableDTO.variableDtoToVariable(variableDto));
		} catch (Exception e) { }
		return ResponseEntity.badRequest().build();		
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Optional<Variable>> findByID(@PathVariable("id") BigInteger id) throws Exception {
		return (service.findById(id) != null) ? ResponseEntity.ok(service.findById(id)) : ResponseEntity.notFound().build();
	}
	
	@GetMapping
	public ResponseEntity<List<Variable>> findAll() throws Exception {
		return ResponseEntity.ok(service.findAll());
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> removeVariable(@PathVariable("id") BigInteger id) throws Exception {
		
		JSONObject response = service.delete(id);
		return (response.get("status") == "Success") ? ResponseEntity.ok(response.toString()) : ResponseEntity.badRequest().build();
	}
	

	@GetMapping("byOrganization/{id}")	//deprecated
	public ResponseEntity<List<Variable>> findByOrganizationId(@PathVariable("id") BigInteger organizationId, @RequestHeader("Authorization") String authorization) throws Exception {
		UserDTO userInfo = oauthClient.getUserInfo(authorization).getBody().getRecord();
		
		return ResponseEntity.ok(service.findVariableByOrganizationId(organizationId, userInfo));
	}
	
	@GetMapping("byOrganization")
	public ResponseEntity<?> findByOrganization(@Valid VariableDTO filter, 
									 			@RequestParam(name = "page_index", defaultValue = "0") int pageIndex, 
								 				@RequestParam(name = "page_size", defaultValue = "10") int pageSize, 
								 				@RequestHeader("Authorization") String authorization) throws Exception { 
		UserDTO userInfo = oauthClient.getUserInfo(authorization).getBody().getRecord();

		return ResponseEntity.ok(service.findVariableByOrganization(filter, pageIndex, pageSize, userInfo));
	}
	
	@PatchMapping("{id}")
	public ResponseEntity<?> patch(@PathVariable("id") BigInteger id, 
								   @RequestBody VariableDTO dto, 
								   @RequestHeader("Authorization") String authorization) throws Exception {
		return ResponseEntity.ok(service.patch(id, dto));
	}
	
}
