package com.bylski.spidersystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        // bez tego nie działa konsola h2 --------------
        http
                .authorizeRequests().antMatchers("/h2-console/**").permitAll();
        // this will ignore only h2-console csrf
        http.csrf().ignoringAntMatchers("/h2-console/**");
        //this will allow frames with same origin
        http.headers().frameOptions().sameOrigin();

        //------------------------------------------------

        // do czasu implementacji security
        http.authorizeRequests().anyRequest().permitAll();
        http.csrf().disable();


        return http.build();
    }


}
