package com.pensionbackend.configurations;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.pensionbackend.enums.Role;
import org.springframework.security.authentication.AuthenticationProvider;



import com.pensionbackend.services.jwt.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
      private final UserService userService;

      @Bean
      public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
          return 
          http.csrf(AbstractHttpConfigurer::disable)
                  .authorizeHttpRequests(request -> request
                  .requestMatchers("/api/v1/auth/**").permitAll()
                  .requestMatchers("/v2/api-docs").permitAll()
                  .requestMatchers("/v3/api-docs/**" ).permitAll()
                  .requestMatchers("/configuration/ui").permitAll()
                  .requestMatchers("/swagger-resources/**").permitAll()
                  .requestMatchers( "/swagger-ui.html").permitAll()
                  .requestMatchers( "/swagger-ui/**").permitAll()
                  .requestMatchers("/webjars/**").permitAll()
                  .requestMatchers("/configuration/security").permitAll()
                
                  
                          .requestMatchers("/api/v1/admin/**").hasAuthority(Role.ADMIN.name())
                          .requestMatchers("/api/v1/user/**").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())
                          .anyRequest().authenticated()
                  )
                  .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                  .authenticationProvider(authenticationProvider())
                  .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                  .build();
      }
      @Bean
      public AuthenticationProvider authenticationProvider() {
          DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
          authenticationProvider.setUserDetailsService(userService.UserDetailsService());
          authenticationProvider.setPasswordEncoder(passwordEncoder());
          return authenticationProvider;
      }
      


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationmanager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    

}
