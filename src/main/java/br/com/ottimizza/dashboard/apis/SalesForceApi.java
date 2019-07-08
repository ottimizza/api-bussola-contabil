package br.com.ottimizza.dashboard.apis;

import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import java.util.ArrayList;

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
    public ArrayList<String> getCNPJbyEmail(String emailDono){
        ArrayList<String> cpfCnpj = new ArrayList<>();
        try {
            // Instancia conexão com Salesforce. 
            PartnerConnection sfConnection = new PartnerConnection(config());
            
            // Obtem o Roteiro /////////////////////////////////////////////////
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT CNPJ__c FROM Empresa__c WHERE Email_Dono_Empresa__c='").append(emailDono).append("'");

            //String cnpjResultado = "";
            QueryResult qres1 = sfConnection.query(sql.toString());
            if (qres1.getSize() > 0) {
                for (int xx=0;xx<qres1.getSize();xx++) {
                    SObject empresas = qres1.getRecords()[xx];
                    Object campoOb = empresas.getField("CNPJ__c");
                    if (campoOb != null) cpfCnpj.add(campoOb.toString());  // retornar todos os CNPJs para o Bussola
                }
            }
        } catch (ConnectionException e) {
            e.printStackTrace();
        }

        return cpfCnpj;
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
