package br.com.ottimizza.dashboard.controllers;

import java.math.BigInteger;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ottimizza.dashboard.dtos.ScriptTypeDTO;
import br.com.ottimizza.dashboard.services.ScriptTypeService;

@RestController
@RequestMapping("script_type")
public class ScriptTypeController {

	@Inject
	ScriptTypeService service;
	
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody ScriptTypeDTO dto, @RequestHeader("Authorization") String authorization) throws Exception {
		return ResponseEntity.ok(service.save(dto));
	}
	
	@PutMapping
	public ResponseEntity<?> update(@RequestBody ScriptTypeDTO dto, @RequestHeader("Authorization") String authorization) throws Exception {
		return ResponseEntity.ok(service.save(dto));
	}
	
	@GetMapping
	public ResponseEntity<?> findAll(@ModelAttribute ScriptTypeDTO filter, @RequestHeader("Authorization") String authorization) throws Exception {
		return ResponseEntity.ok(service.findAll(filter));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> remove(@PathVariable("id") BigInteger descriptionId, @RequestHeader("Authorization") String authorization) throws Exception {
		return ResponseEntity.ok(service.delete(descriptionId).toString());
	}
	
	@PatchMapping("{id}")
	public ResponseEntity<?> patch(@PathVariable("id") BigInteger id, @RequestBody ScriptTypeDTO dto, @RequestHeader("Authorization") String authorization) throws Exception {
        return ResponseEntity.ok(service.patch(id, dto));
    }
	
}
