package br.com.ottimizza.dashboard.controllers;

import java.math.BigInteger;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.ottimizza.dashboard.client.OAuthClient;
import br.com.ottimizza.dashboard.domain.dtos.CompanyDTO;
import br.com.ottimizza.dashboard.domain.dtos.FilterOrganizationDTO;
import br.com.ottimizza.dashboard.domain.dtos.OauthOrganizationDTO;
import br.com.ottimizza.dashboard.domain.dtos.OrganizationDTO;
import br.com.ottimizza.dashboard.domain.dtos.UserDTO;
import br.com.ottimizza.dashboard.models.Company;
import br.com.ottimizza.dashboard.services.CompanyService;
import br.com.ottimizza.dashboard.services.SalesForceService;
import br.com.ottimizza.dashboard.services.ScriptTypeService;
import br.com.ottimizza.dashboard.services.UserService;
import br.com.ottimizza.dashboard.utils.StringUtil;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Inject
    CompanyService service;
    
    @Inject
    UserService userService;

    @Inject
    ScriptTypeService scriptTypeService;
    
	@Inject
	OAuthClient oauthClient;
	
    @PostMapping("save")
    public ResponseEntity<Company> saveCompany(@RequestBody CompanyDTO companyDto,  @RequestHeader String authorization) throws Exception {
    	
    	CompanyDTO filter = new CompanyDTO();
    	filter.setCnpj(companyDto.getCnpj());
    	List<CompanyDTO> companiesExisting = service.findCompanies(filter, authorization);
    	CompanyDTO newCompany = companyDto;
    	try{
    		if(companiesExisting.size() > 0) {	//existe company com o CNPJ enviado
	    		newCompany = companiesExisting.get(0);
	    		newCompany.setScriptDescription(companyDto.getScriptDescription());
	    		if(newCompany.getAccountingId() == null) {

	    			// busco contabilidade no account e seto accountingID no company
	    	    	OrganizationDTO filterOrg = new OrganizationDTO();
	    	    	filterOrg.setCnpj(StringUtils.leftPad(companyDto.getCnpjAccounting().replaceAll("\\D", ""), 14, "0"));
	    	    	filterOrg.setType(1);  // contabilidade
	    	    	List<OrganizationDTO> orgDtos = service.findOrganizationInfo(authorization, filterOrg);
	    	    	if(orgDtos.size() > 0) newCompany.setAccountingId(orgDtos.get(0).getId());
	    		}
	    		newCompany.setScriptId(scriptTypeService.criaScriptType(newCompany));
	    		try {
    	    		OauthOrganizationDTO newOrganization = new OauthOrganizationDTO();
    	    		newOrganization.setName(newCompany.getName());
    	    		newOrganization.setCnpj(StringUtil.cleanCpfCnpj(newCompany.getCnpj()));
    	    		newOrganization.setActive(true);
    	    		newOrganization.setType(2);
    	    		newOrganization.setOrganizationId(newCompany.getAccountingId());
    	    		
    	    		newOrganization = oauthClient.saveOrganization(authorization, newOrganization, true).getBody().getRecord();
    	    	}catch (Exception er) {
					System.out.println("ERROR creating company IN ACCOUNTS "+er.getMessage());
				}
	    	} else {	// NAO existe company com o CNPJ enviado

	    		// busco contabilidade no account e seto accountingID no company
    	    	OrganizationDTO filterOrg = new OrganizationDTO();
    	    	filterOrg.setCnpj(StringUtils.leftPad(companyDto.getCnpjAccounting().replaceAll("\\D", ""), 14, "0"));
    	    	filterOrg.setType(1);  // contabilidade
    	    	List<OrganizationDTO> orgDtos = service.findOrganizationInfo(authorization, filterOrg);
    	    	if(orgDtos.size() > 0) newCompany.setAccountingId(orgDtos.get(0).getId());
    	    	
    	    	newCompany.setScriptId(scriptTypeService.criaScriptType(newCompany));
    	    	
    	    	try {
    	    		OauthOrganizationDTO newOrganization = new OauthOrganizationDTO();
    	    		newOrganization.setName(newCompany.getName());
    	    		newOrganization.setCnpj(StringUtil.cleanCpfCnpj(newCompany.getCnpj()));
    	    		newOrganization.setCodigoERP("");
    	    		newOrganization.setActive(true);
    	    		newOrganization.setType(2);
    	    		newOrganization.setOrganizationId(newCompany.getAccountingId());
    	    		
    	    		newOrganization = oauthClient.saveOrganization(authorization, newOrganization, true).getBody().getRecord();
    	    	}catch (Exception er) {
					System.out.println("ERROR creating company IN ACCOUNTS "+er.getMessage());
				}
	    	}
	    	return ResponseEntity.ok(service.save(CompanyDTO.dtoToEntity(newCompany)));
    	} catch (Exception e) {
    		e.printStackTrace();
        	return ResponseEntity.badRequest().build();
    	}
    }
    
    @GetMapping("find/{id}")
    public ResponseEntity<Optional<Company>> findCompanyByID(Principal principal, @PathVariable("id") BigInteger idCompany)
            throws Exception {

        // Get Authorized User by Username.
//        User authorized = userService.findByUsername(principal.getName());

        return ResponseEntity.ok(service.findById(idCompany));
    }

    @RequestMapping(value = "/find/cnpj", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<List<Company>> findCompaniesByCNPJ(@RequestBody Map<String, List<String>> body)
            throws Exception {
        return ResponseEntity.ok(service.findByListCNPJ(StringUtil.formatCnpj(body.get("cnpj"))));
    }
    
    @RequestMapping(value = "/find/email", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<List<Company>>  searchCNPJ(@RequestBody Map<String,String> email){
    	List<Company> resposta = new ArrayList<Company>();
    	try {
    		SalesForceService sForce = new SalesForceService();
            JSONObject response = sForce.searchCNPJ(email.get("email"));
            JSONArray listaJson = response.optJSONArray("records");
            
            List<String> listaCNPJ = new ArrayList<String>();
            
            for (int i = 0; i < listaJson.length(); i++) {
            	listaCNPJ.add(listaJson.get(i).toString());
			}
            resposta = service.findByListCNPJ(listaCNPJ);
        } catch (Exception e) { }
        return ResponseEntity.ok(resposta);
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable("id") BigInteger idCompany, @RequestBody Company company)
            throws Exception {
        return ResponseEntity.ok(service.updateById(idCompany, company).toString());
    }

    @RequestMapping(value = "/deleteAllKpi", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> deleteAllInformationByCNPJ(@RequestBody Map<String,String> cnpj) throws Exception {
    	return ResponseEntity.ok(service.deleteAllInformationByCNPJ(cnpj.get("cnpj")).toString());
    }
    
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> removeCompany(@PathVariable("id") BigInteger idCompany) throws Exception {
        return ResponseEntity.ok(service.delete(idCompany).toString());
    }

	@PatchMapping
    public ResponseEntity<Company> patch(@RequestBody CompanyDTO companyDTO, @RequestHeader("Authorization") String authorization) throws Exception {
		UserDTO userInfo = oauthClient.getUserInfo(authorization).getBody().getRecord();
        return ResponseEntity.ok(service.patch(companyDTO, userInfo));
    }
	
	@GetMapping("")
	public ResponseEntity<?> findAll(@Valid CompanyDTO filter, @RequestHeader("Authorization") String authorization) throws Exception { 
		return ResponseEntity.ok(service.findCompanies(filter, authorization));
	
	}
	
	@GetMapping("organizationId")
	public BigInteger findOrganizationId(@Valid FilterOrganizationDTO filter, @RequestHeader("Authorization") String authorization) throws Exception { 
		return service.findOrganizationId(filter, authorization);
	}
	
//	public BigInteger criaScriptType(CompanyDTO companyDto) throws Exception {
//		ScriptTypeDTO filterScript = new ScriptTypeDTO();
//		filterScript.setAccounting(companyDto.getAccountingId());
//		List<ScriptTypeDTO> scripts = scriptTypeService.findAll(filterScript);
//		try {
//			if(companyDto.getScriptDescription() != null) {
//				if(scripts.size() == 0) return scriptTypeService.save(new ScriptTypeDTO(null, companyDto.getAccountingId(), companyDto.getScriptDescription())).getId();
//				else return scripts.get(0).getId();
//			}			
//			if(companyDto.getScriptDescription() == null) {
//				if(scripts.size() == 0) return scriptTypeService.save(new ScriptTypeDTO(null, companyDto.getAccountingId(), "PADRAO")).getId();
//				else if(scripts.size() == 1) return scripts.get(0).getId();
//				else if(scripts.size() > 1) return scripts.get(0).getId();
//			}
//		} catch (Exception e) { System.out.println(">>> XD ");}
//		return null;
//	}
}
