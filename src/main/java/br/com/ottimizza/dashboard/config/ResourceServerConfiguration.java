package br.com.ottimizza.dashboard.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import br.com.ottimizza.dashboard.config.security.SecurityProperties;

@Configuration
@EnableResourceServer
@EnableConfigurationProperties(SecurityProperties.class)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "bussola-contabil-resource-server";

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private TokenStore tokenStore;


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }

    @Override //@formatter:off
    public void configure(HttpSecurity http) throws Exception {
        String[] allowed = Arrays.asList(new String[] {
                "/oauth/callback*", 
        }).toArray(new String[] {});

        // http.authorizeRequests()
        //         .antMatchers(allowed).permitAll();

        // http
        //     .antMatcher("/**").authorizeRequests()
        //     .anyRequest().authenticated();

         // @formatter:off
        http
            .antMatcher("/**")
            .authorizeRequests()
                .antMatchers("/company**").authenticated()
                .antMatchers("/oauth/callback*").permitAll()
                .antMatchers("/oauth/refresh*").permitAll()
                .anyRequest().authenticated();
		// @formatter:on
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices(final TokenStore tokenStore) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);
        return tokenServices;
    }

    @Bean
    public TokenStore tokenStore() {
        if (tokenStore == null) {
            tokenStore = new JwtTokenStore(jwtAccessTokenConverter());
        }
        return tokenStore;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(getPublicKeyAsString());
        return converter;
    }

    private String getPublicKeyAsString() {
        try {
            // return IOUtils.toString(securityProperties.getJwt().getPublicKey().getInputStream(), "UTF-8");
            return securityProperties.getJwt().getPublicKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
