package br.com.ottimizza.dashboard.controllers;

import br.com.ottimizza.dashboard.models.Company;
import br.com.ottimizza.dashboard.models.users.User;
import br.com.ottimizza.dashboard.services.CompanyService;
import br.com.ottimizza.dashboard.services.SalesForceService;
import br.com.ottimizza.dashboard.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.inject.Inject;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Inject
    CompanyService companyService;

    @Inject
    UserService userService;

    @PostMapping("save")
    // <editor-fold defaultstate="collapsed" desc="Save company">
    public ResponseEntity<Company> saveCompany(@RequestBody Company company) throws Exception {
        return ResponseEntity.ok(companyService.save(company));
    }
    // </editor-fold>

    @GetMapping("find/{id}")
    // <editor-fold defaultstate="collapsed" desc="Find company by ID">
    public ResponseEntity<Optional<Company>> findCompanyByID(Principal principal, @PathVariable("id") Long idCompany)
            throws Exception {

        // Get Authorized User by Username.
        User authorized = userService.findByUsername(principal.getName());

        return ResponseEntity.ok(companyService.findById(idCompany));
    }
    // </editor-fold>

    @RequestMapping(value = "/find/cnpj", method = RequestMethod.POST, consumes = "application/json")
    // <editor-fold defaultstate="collapsed" desc="Find company by ID">
    public ResponseEntity<List<Company>> findCompaniesByCNPJ(@RequestBody Map<String, List<String>> body)
            throws Exception {
        List<String> listaCNPJ = body.get("cnpj");
        return ResponseEntity.ok(companyService.findByListCNPJ(listaCNPJ));
    }
    // </editor-fold>
    
    @RequestMapping(value = "/find/email", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<List<Company>>  searchCNPJ(@RequestBody Map<String,String> email){
    	List<Company> resposta = new ArrayList<Company>();
    	try {

    		SalesForceService sForce = new SalesForceService();
            JSONObject response = sForce.searchCNPJ(email.get("email"));
            System.out.println(">> busca 1");
            
//            JSONObject js = new JSONObject();
//            js.put("email", response.get("records"));
            
            List<String> listaCNPJ = Arrays.asList(response.optString("records"));
            
            System.out.println(">> busca 2 "+ listaCNPJ);
            System.out.println(">> busca 3 "+ response.optString("records"));
            System.out.println(">> busca 4 "+ response.get("records"));

            resposta = companyService.findByListCNPJ(listaCNPJ);
            System.out.println(">> busca OK");
        } catch (Exception e) {            System.out.println(">> busca nOK");

        }
        return ResponseEntity.ok(resposta);

    }
    
    @PutMapping("update/{id}")
    // <editor-fold defaultstate="collapsed" desc="Update by ID">
    public ResponseEntity<String> updateCompany(@PathVariable("id") Long idCompany, @RequestBody Company company)
            throws Exception {
        return ResponseEntity.ok(companyService.updateById(idCompany, company).toString());
    }
    // </editor-fold>

    @RequestMapping(value = "/deleteAllKpi", method = RequestMethod.POST, consumes = "application/json")
    // <editor-fold defaultstate="collapsed" desc="DELETE all information related to CNPJ">
    public ResponseEntity<String> deleteAllInformationByCNPJ(@RequestBody Map<String,String> cnpj)
            throws Exception {
        return ResponseEntity.ok(companyService.deleteAllInformationByCNPJ(cnpj.get("cnpj")).toString());
    }
    // </editor-fold>
    
    @DeleteMapping("delete/{id}")
    // <editor-fold defaultstate="collapsed" desc="Delete company">
    public ResponseEntity<String> removeCompany(@PathVariable("id") Long idCompany) throws Exception {
        return ResponseEntity.ok(companyService.delete(idCompany).toString());
    }
    // </editor-fold>

}
