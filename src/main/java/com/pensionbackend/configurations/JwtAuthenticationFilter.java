package com.pensionbackend.configurations;

import com.pensionbackend.Util.JwtUtil;
import com.pensionbackend.services.jwt.UserService;
import org.springframework.security.core.context.SecurityContext;


import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtUtil jwtUtil;
    private final UserService userService;
    
    
    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
            jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
            throws jakarta.servlet.ServletException, IOException {


                final String authHeader = request.getHeader("Authorization");

        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWithIgnoreCase(authHeader, "Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String userEmail = jwtUtil.extractUsername(jwt);

        if(StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.UserDetailsService().loadUserByUsername(userEmail);
            if (jwtUtil.isTokenValid(jwt, userDetails)) {
    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities()
    );

    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    securityContext.setAuthentication(token);
    SecurityContextHolder.setContext(securityContext);
}

          
        }

  
      filterChain.doFilter(request, response);
      
     
    }

  
}
