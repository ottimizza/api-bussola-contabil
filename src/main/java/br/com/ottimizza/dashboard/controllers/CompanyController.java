package br.com.ottimizza.dashboard.controllers;

import java.math.BigInteger;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

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
import br.com.ottimizza.dashboard.domain.dtos.OrganizationDTO;
import br.com.ottimizza.dashboard.domain.dtos.ScriptTypeDTO;
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
    	System.out.println("A >>> ");
    	OrganizationDTO filter = new OrganizationDTO();
    	
    	Company company = CompanyDTO.dtoToEntity(companyDto);
    	
    	System.out.println("B >>> "+company.getName()+" -- "+ company.getCnpj());
    	
    	filter.setCnpj(company.getCnpj());
    	List<OrganizationDTO> orgDtos = service.findOrganizationInfo(authorization, filter);
    	System.out.println("C >>> "+orgDtos.size());
    	if(orgDtos.size() > 0)	{
    		OrganizationDTO response = orgDtos.get(0);
    		System.out.println("D >>> "+response.getName()+" -- "+response.getOrganizationId());
			
    		BigInteger idContabilidade = response.getOrganizationId();
			company.setAccountingId(idContabilidade);

			List<ScriptTypeDTO> scripts = scriptTypeService.findAll(new ScriptTypeDTO(null, idContabilidade, null));

			if(companyDto.getScriptDescription() == null) {
	    		System.out.println("E >>> ");

				if(scripts.size() == 0) {
					System.out.println("E1 >>> ");

					company.setScriptType(scriptTypeService.save(new ScriptTypeDTO(null, idContabilidade, "padrao")).getId());
				} else if(scripts.size() == 1) {
					System.out.println("E2 >>> ");

					company.setScriptType(scripts.get(0).getId());
				} else if(scripts.size() > 1) {
					System.out.println("E3 >>> ");

					company.setScriptType(scripts.get(0).getId());
				}
			}
			if(companyDto.getScriptDescription() != null) {
				System.out.println("F >>> ");

				if(scripts.size() == 0) {
					System.out.println("F1 >>> ");

					company.setScriptType(scriptTypeService.save(new ScriptTypeDTO(null, idContabilidade, companyDto.getScriptDescription())).getId());
//				}else if(scripts.size() > 0) {
//					List<ScriptTypeDTO> scripts2 = scriptTypeService.findAll(new ScriptTypeDTO(null, null, companyDto.getScriptDescription()));
//					if (scripts2.size() == 0){
//						company.setScriptType(scriptTypeService.save(new ScriptTypeDTO(null, idContabilidade, companyDto.getScriptDescription())).getId());
				}else {
					System.out.println("F2 >>> ");

					List<ScriptTypeDTO> scripts2 = scriptTypeService.findAll(new ScriptTypeDTO(null, null, companyDto.getScriptDescription()));
					company.setScriptType(scripts2.get(0).getId());
				}
//				}
			}
			
			return ResponseEntity.ok(service.save(company));
    	}
    	return ResponseEntity.badRequest().build();
    	
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
            
            List<String> listaCNPJ = new ArrayList();
            
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
	
    
}
