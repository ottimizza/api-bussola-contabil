package br.com.ottimizza.dashboard.controllers;

import br.com.ottimizza.dashboard.models.Company;
import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.KpiShort;
import br.com.ottimizza.dashboard.services.KpiService;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.inject.Inject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/oauth")
public class OAuthClientController {

    @Value("${oauth2-config.oauth2-server-url}")
    private String OAUTH2_SERVER_URL;

    @Value("${oauth2-config.oauth2-client-id}")
    private String OAUTH2_CLIENT_ID;

    @Value("${oauth2-config.oauth2-client-secret}")
    private String OAUTH2_CLIENT_SECRET;

    @PostMapping("/callback")
    public ResponseEntity<String> oauthCallback(@RequestParam("code") String code,
                                                @RequestParam("redirect_uri") String redirectUri) throws IOException {
        System.out.println("AUTHORIZATION CODE EXCHANGE");
        System.out.println("code ..........: " + code);
        System.out.println("redirect_uri ..: " + redirectUri);

        ResponseEntity<String> response = null;
        RestTemplate restTemplate = new RestTemplate();

        String credentials = OAUTH2_CLIENT_ID + ":" + OAUTH2_CLIENT_ID;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        // HttpHeaders headers = new HttpHeaders();
        // headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        // headers.add("Authorization", "Basic " + encodedCredentials);

        // HttpEntity<String> request = new HttpEntity<String>(headers);

        String access_token_url = OAUTH2_SERVER_URL + "/oauth/token";
        access_token_url += "?code=" + code;
        access_token_url += "&grant_type=authorization_code";
        access_token_url += "&redirect_uri=" + redirectUri;

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(access_token_url);

        httpPost.setHeader("Authorization", "Basic " + encodedCredentials);

        HttpResponse httpResponse = httpClient.execute(httpPost);
        // int statusCode = httpResponse.getStatusLine().getStatusCode();
        HttpEntity responseEntity = httpResponse.getEntity();
        String responseBody = EntityUtils.toString(responseEntity, "UTF-8");

        return ResponseEntity.ok(responseBody); 
        //restTemplate.exchange(access_token_url, HttpMethod.POST, request, String.class);
        // response = restTemplate.exchange(access_token_url, HttpMethod.POST, request,
        // String.class);

        // System.out.println("Access Token Response ---------" + response.getBody());

        // // Get the Access Token From the recieved JSON response
        // ObjectMapper mapper = new ObjectMapper();
        // JsonNode node = mapper.readTree(response.getBody());
        // String token = node.path("access_token").asText();

        // return ResponseEntity.ok("");
    }

}
