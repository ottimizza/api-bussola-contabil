package br.com.ottimizza.dashboard.controllers;

import java.math.BigInteger;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

import br.com.ottimizza.dashboard.models.OrganizationVariable;
import br.com.ottimizza.dashboard.services.OrganizationVariableService;

@RestController
@RequestMapping("/variables/organization")
public class OrganizationVariableController {

	@Inject
	OrganizationVariableService service;
	
	@PostMapping
	public ResponseEntity<OrganizationVariable> saveVariable(@RequestBody OrganizationVariable orgVariable) throws Exception {
		try {
			orgVariable = service.save(orgVariable);
			return ResponseEntity.ok(orgVariable);
		} catch (Exception e) { }
		
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

}
