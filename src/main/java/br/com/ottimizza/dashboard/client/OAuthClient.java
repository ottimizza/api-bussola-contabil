package br.com.ottimizza.dashboard.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.ottimizza.dashboard.dtos.OrganizationDTO;
import br.com.ottimizza.dashboard.dtos.UserDTO;
import br.com.ottimizza.dashboard.dtos.responses.GenericResponse;

@FeignClient(name = "OAuthClient", url = "${oauth2-config.oauth2-server-url}")
public interface OAuthClient {

	@GetMapping("/oauth/userinfo")
	HttpEntity<GenericResponse<UserDTO>> getUserInfo(@RequestHeader("Authorization") String authorization);
	
	@GetMapping("/api/v1/organizations")
	HttpEntity<GenericResponse<String>> getOrganizationInfo(@RequestHeader("Authorization") String authorization, @RequestParam(name = "cnpj") String cnpj);
	
}
