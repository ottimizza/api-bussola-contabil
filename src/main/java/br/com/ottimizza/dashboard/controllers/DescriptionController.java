package br.com.ottimizza.dashboard.controllers;

import java.math.BigInteger;

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
import org.springframework.web.bind.annotation.RestController;

import br.com.ottimizza.dashboard.dtos.DescriptionDTO;
import br.com.ottimizza.dashboard.services.DescriptionService;

@RestController
@RequestMapping("description")
public class DescriptionController {

	@Inject
	DescriptionService service;
	
	@PostMapping
	public ResponseEntity<DescriptionDTO> save(@RequestBody DescriptionDTO descriptionDto, 
											   @RequestHeader("Authorization") String authorization) 
											   throws Exception {
		return ResponseEntity.ok(service.save(descriptionDto));
	}
	
	@GetMapping
	public ResponseEntity<?> findAll(@ModelAttribute DescriptionDTO filter, 
									 @RequestHeader("Authorization") String authorization) throws Exception {
		System.out.println("aaaa 01");
		return ResponseEntity.ok(service.findAll(filter, authorization));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> remove(@PathVariable("id") BigInteger descriptionId, 
										 @RequestHeader("Authorization") String authorization) throws Exception {
		return ResponseEntity.ok(service.delete(descriptionId).toString());
	}


}
