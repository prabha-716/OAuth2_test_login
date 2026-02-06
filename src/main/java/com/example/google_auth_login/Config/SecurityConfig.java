package com.example.google_auth_login.Config;


import com.example.google_auth_login.Service.CustomOAuth2Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private final CustomOAuth2Service customOAuth2Service ;

    public SecurityConfig(CustomOAuth2Service customOAuth2Service){
        this.customOAuth2Service = customOAuth2Service;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, SuccessHandler successHandler) throws Exception {

        http
        .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/login").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2Login( oauth -> oauth.successHandler(successHandler)
                        .userInfoEndpoint(userInfo-> userInfo.userService(customOAuth2Service)))

                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );


        return http.build();
    }
}
