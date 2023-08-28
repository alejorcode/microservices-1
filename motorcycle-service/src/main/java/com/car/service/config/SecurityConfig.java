package com.car.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http

                .csrf(csrf -> csrf.disable())
                .securityMatcher("/**").authorizeHttpRequests().anyRequest().authenticated()
                .and()
                .oauth2ResourceServer(configure -> configure.jwt().jwtAuthenticationConverter(jwtAuthConverter()));
		
		return http.build();
		
	}

	private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthConverter() {
		JwtAuthenticationConverter converter= new JwtAuthenticationConverter();
		converter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
		return converter;
	}
	
	
}
