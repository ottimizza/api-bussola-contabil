package br.com.ottimizza.dashboard.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import br.com.ottimizza.dashboard.dtos.UserDTO;
import br.com.ottimizza.dashboard.dtos.responses.GenericResponse;

@FeignClient(name = "OAuthClient", url = "${oauth2-config.oauth2-server-url}")
public interface OAuthClient {

	@GetMapping("/oauth/userinfo")
	HttpEntity<GenericResponse<UserDTO>> getUserInfo(@RequestHeader("Authorization") String authorization);
	
}
