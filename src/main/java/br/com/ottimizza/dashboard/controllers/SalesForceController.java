package br.com.ottimizza.dashboard.controllers;

import br.com.ottimizza.dashboard.models.KpiDetail;
import br.com.ottimizza.dashboard.models.KpiShort;
import br.com.ottimizza.dashboard.services.KpiDetailService;
import br.com.ottimizza.dashboard.services.SalesForceService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("salesforce")
public class SalesForceController {
	
	@Inject
	KpiDetailService kpiDetailService;
	
	
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
    
    
    @RequestMapping(value = "/cnpjWithKpi", method = RequestMethod.POST, consumes = "application/json")
    //<editor-fold defaultstate="collapsed" desc="Busca de lista CNPJ - Zap Contábil">
    public ResponseEntity<List<KpiDetail>> searchCNPJWithKpi(@RequestBody Map<String,String> email){

        List<KpiDetail> kpiList = new ArrayList<KpiDetail>();
        try {
            SalesForceService zapContabil = new SalesForceService();
            JSONObject response = zapContabil.searchCNPJ(email.get("email"));
            JSONArray cnpjList = response.optJSONArray("records");
            
            List<String> cnpj = new ArrayList<String>();
            for(int i = 0; i < cnpjList.length(); i++){
                cnpj.add(cnpjList.getString(i));
            }
            
            kpiList = kpiDetailService.findByListCNPJ(cnpj);
    	} catch (Exception e) {
	    	e.printStackTrace();
	    }
        
        return ResponseEntity.ok(kpiList);
        
    }
    //</editor-fold>

    
}
