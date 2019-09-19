package br.com.ottimizza.dashboard.services;

import javax.inject.Inject;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import br.com.ottimizza.dashboard.apis.SalesForceApi;
import br.com.ottimizza.dashboard.repositories.user.UserRepository;

@Service
public class SalesForceService {

    @Inject
    private UserRepository userRepository;

    //<editor-fold defaultstate="collapsed" desc="Busca lista CNPJ">
    public JSONObject searchCNPJ(String email) throws Exception {
        JSONObject response = new JSONObject();
        try {            
            SalesForceApi api = new SalesForceApi();
            response.put("status", "sucess");
            response.put("records", api.getCNPJbyEmail(email));
            response.put("urlLogotipo", api.getUrlLogotipoByEmail(email));
            
            System.out.println("SERVICE OK!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
    //</editor-fold>

}
