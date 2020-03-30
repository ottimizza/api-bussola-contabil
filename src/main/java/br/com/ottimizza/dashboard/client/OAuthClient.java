package br.com.ottimizza.dashboard.client;

import java.math.BigInteger;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.ottimizza.dashboard.domain.dtos.OrganizationDTO;
import br.com.ottimizza.dashboard.domain.dtos.UserDTO;
import br.com.ottimizza.dashboard.domain.responses.GenericResponse;

@FeignClient(name = "OAuthClient", url = "${oauth2-config.oauth2-server-url}")
public interface OAuthClient {

	@GetMapping("/oauth/userinfo")
	HttpEntity<GenericResponse<UserDTO>> getUserInfo(@RequestHeader("Authorization") String authorization);
	
	@GetMapping("/api/v1/organizations")
	HttpEntity<GenericResponse<OrganizationDTO>> getOrganizationInfo(@RequestHeader("Authorization") String authorization, @RequestParam String cnpj);	
	
	
	@GetMapping("/api/v1/organizations?ignoreAccountingFilter=true")
	HttpEntity<GenericResponse<OrganizationDTO>> getAccountingInfo(@RequestHeader("Authorization") String authorization, @RequestParam String cnpj);	
	
	@GetMapping("/api/v1/organizations")
	HttpEntity<GenericResponse<OrganizationDTO>> getOrganizationInfoById(@RequestHeader("Authorization") String authorization, @RequestParam BigInteger id);	
}