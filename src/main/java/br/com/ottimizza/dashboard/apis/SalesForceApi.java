package br.com.ottimizza.dashboard.apis;

import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

import javassist.expr.NewArray;

import java.util.ArrayList;

import org.json.JSONObject;

public class SalesForceApi {
    
    // Configurações para conexão com Salesforce.
    public ConnectorConfig config(){
        ConnectorConfig config = new ConnectorConfig();
        config.setUsername("adm@ottimizza.com.br");
        config.setPassword("oic@3333222YXZRHYLH8cxrchPu0rjyGH1j8");
        config.setAuthEndpoint("https://login.salesforce.com/services/Soap/u/45.0/");
        config.setTraceMessage(true);
        return config;
    }

    //<editor-fold defaultstate="collapsed" desc="Busca de lista CNPJ por email">
    public ArrayList<JSONObject> getCNPJbyEmail(String emailDono){
        ArrayList<JSONObject> empresas = new ArrayList<>();
        
        try {
            // Instancia conexão com Salesforce. 
            PartnerConnection sfConnection = new PartnerConnection(config());
            
            // Obtem o Roteiro /////////////////////////////////////////////////
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT CNPJ__c, name FROM Empresa__c WHERE Email_Dono_Empresa__c like '%").append(emailDono).append("%'");

            //String cnpjResultado = "";
            QueryResult qres1 = sfConnection.query(sql.toString());
            if (qres1.getSize() > 0) {
                for (int xx=0;xx<qres1.getSize();xx++) {

                    SObject resultados = qres1.getRecords()[xx];
                    JSONObject campoOb = new JSONObject();
                    campoOb.put("cnpj", resultados.getField("CNPJ__c"));
                    campoOb.put("name", resultados.getField("name"));
                    System.out.println(">> > "+resultados.getField("CNPJ__c")+" - "+resultados.getField("name"));
                    if (campoOb != null) empresas.add(campoOb);
                }
            }
        } catch (ConnectionException e) {
            e.printStackTrace();
        }

        return empresas;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Busca de UrlLogotipo por email">
    public String getUrlLogotipoByEmail(String emailDono){
        String urlLogotipo = "";
        try {
            // Instancia conexão com Salesforce. 
            PartnerConnection sfConnection = new PartnerConnection(config());
            
            // Obtem o Roteiro /////////////////////////////////////////////////
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT Logo_Contabilidade__c FROM Empresa__c WHERE Email_Dono_Empresa__c='").append(emailDono).append("' LIMIT 1 ");

            //String cnpjResultado = "";
            QueryResult qres1 = sfConnection.query(sql.toString());
            if (qres1.getSize() > 0) {
                SObject empresas = qres1.getRecords()[0];
                Object campoOb = empresas.getField("Logo_Contabilidade__c");
                if (campoOb != null) urlLogotipo = campoOb.toString();  // retornar a url Logotipo para o Bussola
            }
        } catch (ConnectionException e) {
            e.printStackTrace();
        }

        return urlLogotipo;
    }
    //</editor-fold>
    
}
