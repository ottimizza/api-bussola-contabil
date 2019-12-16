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

import br.com.ottimizza.dashboard.domain.dtos.DescriptionDTO;
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
	
	@PutMapping
	public ResponseEntity<DescriptionDTO> update(@RequestBody DescriptionDTO descriptionDto, 
												@RequestHeader("Authorization") String authorization) 
								  				throws Exception {
		return ResponseEntity.ok(service.save(descriptionDto));
	}
	
	@PatchMapping("{id}")
	public ResponseEntity<DescriptionDTO> patch(@PathVariable("id") BigInteger id, 
												@RequestBody DescriptionDTO descriptionDTO,
												@RequestHeader("Authorization") String authorization) 
												throws Exception {
        return ResponseEntity.ok(service.patch(id, descriptionDTO));
    }
			
	
	@GetMapping
	public ResponseEntity<?> findAll(@ModelAttribute DescriptionDTO filter, 
									 @RequestHeader("Authorization") String authorization) throws Exception {
		return ResponseEntity.ok(service.findAll(filter));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> remove(@PathVariable("id") BigInteger descriptionId, 
										 @RequestHeader("Authorization") String authorization) throws Exception {
		return ResponseEntity.ok(service.delete(descriptionId).toString());
	}


}
