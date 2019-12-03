package br.com.ottimizza.dashboard.controllers;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class OAuthClientController {

    @Value("${oauth2-config.oauth2-server-url}")
    private String OAUTH2_SERVER_URL;

    @Value("${oauth2-config.oauth2-client-id}")
    private String OAUTH2_CLIENT_ID;

    @Value("${oauth2-config.oauth2-client-secret}")
    private String OAUTH2_CLIENT_SECRET;

    
    @PostMapping(value = "/callback", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> oauthCallback(@RequestParam("code") String code,
            @RequestParam("redirect_uri") String redirectUri) throws IOException {
        
        String credentials = OAUTH2_CLIENT_ID + ":" + OAUTH2_CLIENT_SECRET;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            
            String uri = MessageFormat.format("{0}/oauth/token?grant_type={1}&code={2}&redirect_uri={3}", 
                    OAUTH2_SERVER_URL, "authorization_code", code, redirectUri
            );
            
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setHeader("Authorization", "Basic " + encodedCredentials);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity responseEntity = httpResponse.getEntity();
            
    		String response = EntityUtils.toString(responseEntity, "UTF-8");
    		HttpStatus status = HttpStatus.valueOf(httpResponse.getStatusLine().getStatusCode());
            
            System.out.println(" ***** ");
            System.out.println("Response ..: " + response);
            System.out.println("Status ....:   " + status.toString());
            
            return ResponseEntity.status(status).body(response);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(401).body("{}");
        }
    }
    
    @PostMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> oauthRefresh(@RequestParam("refresh_token") String refreshToken,
            @RequestParam("client_id") String clientId) throws IOException {
        
        String credentials = OAUTH2_CLIENT_ID + ":" + OAUTH2_CLIENT_SECRET;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        try {
            HttpClient httpClient = HttpClientBuilder.create().build();

            URIBuilder uriBuilder = new URIBuilder(OAUTH2_SERVER_URL + "/oauth/token");
            uriBuilder.addParameter("refresh_token", refreshToken);
            uriBuilder.addParameter("grant_type", "refresh_token");

            HttpPost httpPost = new HttpPost(uriBuilder.build());
            httpPost.setHeader("Authorization", "Basic " + encodedCredentials);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity responseEntity = httpResponse.getEntity();

            String response = EntityUtils.toString(responseEntity, "UTF-8");
            HttpStatus status = HttpStatus.valueOf(httpResponse.getStatusLine().getStatusCode());

            System.out.println(" ***** ");
            System.out.println("Response ..: " + response);
            System.out.println("Status ....:   " + status.toString());
            
            return ResponseEntity.status(status).body(response);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(401).body("{}");
        }
    }
}
