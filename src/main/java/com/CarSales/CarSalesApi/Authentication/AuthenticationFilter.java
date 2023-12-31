package com.CarSales.CarSalesApi.Authentication;

import java.io.IOException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter implements Filter {
    private static final String SECRET_KEY = "cars";

    @Override
    public void init(jakarta.servlet.FilterConfig filterConfig) throws jakarta.servlet.ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String authorizationHeader = httpRequest.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("{\"Error\": \"Unauthorized: Invalid token\"}");
            return;
        }

        String jwtToken = authorizationHeader.substring(7);
        try {
            DecodedJWT decodedJWT = AuthenticationUtilities.validateJWT(jwtToken, SECRET_KEY);

            Claim subjectClaim = decodedJWT.getClaim("sub");
            httpResponse.setHeader("X-User-Claim-Subject", subjectClaim.asString());

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("{\"Error\": \"Unauthorized: Invalid token\"}");
        }
    }

    @Override
    public void destroy() {}

}