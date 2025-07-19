package com.example.backend.config;

import com.example.backend.service.AuthService;
import com.example.backend.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class JwtFilter implements Filter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthService authService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();
        System.out.println("Authorization Header: " + path);

        // Allow auth endpoints
        if (path.startsWith("/auth")) {
            chain.doFilter(request, response);
            return;
        }

        // Check Authorization header
        String authHeader = req.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String userId = jwtUtil.validateTokenAndGetUserId(token);

            System.out.println("Extracted User ID: " + userId);

            if (userId != null) {
                UserDetails userDetails = authService.loadUserByUsername(userId);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                req.setAttribute("userId", userId);
                System.out.println("User ID set in request attribute: " + userId);
                chain.doFilter(request, response);
                return ;
            }
        }
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.getWriter().write("Unauthorized");

    }
}

