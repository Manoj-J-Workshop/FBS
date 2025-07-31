package com.fbs.central_api.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthFilter {

    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            String token = bearerToken.substring(7);
            // We got the token now we need to validate that this token is a genuine token or not.
            boolean isValid = userService.validateToken(token);
            if(isValid == false){
                // I am not going to set any kind of authentication and i will return from here it self
                // before filtering if i am not setting any kind of authetication that
                // means i am rejecting the reuquest
                filterChain.doFilter(request, response);
                return;
            }
            String payload = authUtility.decryptJwtToken(token);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(payload, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response); // If you are not setting up username and password authenthication that means you are rejecting token
    }
}
