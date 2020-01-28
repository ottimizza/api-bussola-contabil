package br.com.ottimizza.dashboard.controllers;

import java.math.BigInteger;

import javax.inject.Inject;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ottimizza.dashboard.domain.dtos.DescriptionDTO;
import br.com.ottimizza.dashboard.services.CompanyService;
import br.com.ottimizza.dashboard.services.DescriptionService;

@RestController
@RequestMapping("description")
public class DescriptionController {

	@Inject
	DescriptionService service;

	@Inject
	CompanyService companyService;
	
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

	/*@PostMapping("/new/descriptions")
	public ResponseEntity<List<Description>> createDescriptionsOrganizationId(@RequestBody DescriptionDTO body, @RequestHeader("Authorization") String authorization) throws Exception {
		System.out.println("Entrou no método");
		List<Description> listReturn = new ArrayList<Description>();
		List<Description> listDescriptions = new ArrayList<Description>();
		String cnpj = body.getCnpj();
		Company company = new Company();
		
		try { company = companyService.findByCnpj(cnpj); 
			System.out.println("Primeiro TRY");} 
		catch (Exception ee) {System.out.println("Entrou no primeiro catch");	}
		
		listDescriptions = body.getDescriptions();

		for (Description description : listDescriptions) {
			description.setOrganizationId(company.getOrganizationId());
			try {
				service.save(body);
				listReturn.add(description);
			} catch (Exception e) { }
		}
		System.out.println("Pré retorno");
		return ResponseEntity.ok(listReturn);
	}*/

	@PostMapping("/addDescriptions")
	public ResponseEntity<?> saveDescriptionList(@RequestBody DescriptionDTO descriptionDTO) throws Exception {
		return ResponseEntity.ok(service.saveDescriptionList(descriptionDTO));
	}

	@GetMapping("/descriptions")
	public ResponseEntity<?> returnDescriptionList(@Valid DescriptionDTO filter, 
									 			@RequestParam(name = "page_index", defaultValue = "0") int pageIndex, 
								 				@RequestParam(name = "page_size", defaultValue = "10") int pageSize, 
								 				@RequestHeader("Authorization") String authorization) throws Exception {
	return ResponseEntity.ok(service.returnDescriptionList(filter, pageIndex, pageSize, authorization
	));
	}

	@PutMapping("/updateDescription")
	public ResponseEntity<?> updateByOrganizationIdScriptType(@RequestBody DescriptionDTO descriptionDTO,
															  @RequestHeader("Authorization") String authorization) throws Exception{
		return ResponseEntity.ok(service.updateByOrganizationIdScriptType(descriptionDTO));
	}
	
}
