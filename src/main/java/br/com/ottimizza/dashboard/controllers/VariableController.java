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

import br.com.ottimizza.dashboard.models.Variable;
import br.com.ottimizza.dashboard.services.VariableService;

@RestController
@RequestMapping("/variables")
public class VariableController {

	@Inject
	VariableService service;

	@PostMapping
	public ResponseEntity<Variable> saveVariable(@RequestBody Variable variable) throws Exception {
		try {
			variable = service.save(variable);
			return ResponseEntity.ok(variable);
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
}
