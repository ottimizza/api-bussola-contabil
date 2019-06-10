package br.com.ottimizza.dashboard.controllers;

import br.com.ottimizza.dashboard.services.SalesForceService;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("salesforce")
public class SalesForceController {
    
    @RequestMapping(value = "/cnpj", method = RequestMethod.POST, consumes = "application/json")
    //<editor-fold defaultstate="collapsed" desc="Busca de lista CNPJ - Zap Contábil">
    public ResponseEntity<String> searchCNPJ(@RequestBody Map<String,String> email){
        try {
            SalesForceService zapContabil = new SalesForceService();
            JSONObject response = zapContabil.searchCNPJ(email.get("email"));
            return ResponseEntity.ok(response.toString());
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
    //</editor-fold>

    
}
