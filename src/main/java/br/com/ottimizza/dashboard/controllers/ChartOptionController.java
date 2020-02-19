package br.com.ottimizza.dashboard.controllers;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ottimizza.dashboard.domain.dtos.ChartOptionDTO;
import br.com.ottimizza.dashboard.services.ChartOptionService;

@RestController
@RequestMapping("option")
public class ChartOptionController {
	
	@Inject
	ChartOptionService service;

	@PostMapping
	public ResponseEntity<?> save(@RequestBody ChartOptionDTO optionDto, 
								  @RequestHeader("Authorization") String authorization) {
		return ResponseEntity.ok(service.save(optionDto, authorization));
	}
	
	@GetMapping
	public ResponseEntity<?> findAll(@ModelAttribute ChartOptionDTO filter,
			 						 @RequestHeader("Authorization") String authorization) {
		return ResponseEntity.ok(service.findAll(filter));
	}

}
