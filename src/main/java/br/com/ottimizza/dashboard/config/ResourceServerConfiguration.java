package br.com.ottimizza.dashboard.config;

import java.util.Arrays;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "bussola-contabil-resource-server";

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

}
