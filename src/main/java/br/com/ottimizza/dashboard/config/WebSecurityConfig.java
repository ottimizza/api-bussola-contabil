package br.com.ottimizza.dashboard.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableOAuth2Client
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${oauth2-config.oauth2-server-url}")
    private String OAUTH2_SERVER_URL;

    @Value("${oauth2-config.oauth2-client-id}")
    private String OAUTH2_CLIENT_ID;

    @Value("${oauth2-config.oauth2-client-secret}")
    private String OAUTH2_CLIENT_SECRET;

    @Override //@formatter:off
    public void configure(HttpSecurity http) throws Exception {
        String[] allowed = Arrays.asList(new String[] {
                "/oauth/callback*", "/", "/login", "/login**"
        }).toArray(new String[] {});

        http
            .authorizeRequests()
                .antMatchers(allowed).permitAll();

        http
            .authorizeRequests()
                .anyRequest().authenticated();

    }

    /**
     * The heart of our interaction with the resource; handles redirection for
     * authentication, access tokens, etc.
     *
     * @param oauth2ClientContext
     * @return
     */

    @Bean
    @Primary
    public ResourceServerTokenServices tokenServices() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();

        final String checkTokenEndpointUrl = OAUTH2_SERVER_URL + "/oauth/check_token";

        tokenServices.setClientId(OAUTH2_CLIENT_ID);
        tokenServices.setClientSecret(OAUTH2_CLIENT_SECRET);
        tokenServices.setCheckTokenEndpointUrl(checkTokenEndpointUrl);

        return tokenServices;
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
        authenticationManager.setTokenServices(tokenServices());
        return authenticationManager;
    }

}
