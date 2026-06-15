package com.example.demo.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession(false);

        // If no session exists or the login marker is missing, stop the request flow
        if (session == null || session.getAttribute("LOGGED_IN_ADMIN") == null) {
            // Send an unauthorized status for API calls, or redirect web pages to the login
            // portal
            if (request.getRequestURI().startsWith("/api/")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied: Please log in.");
            } else {
                response.sendRedirect("/login.html");
            }
            return false; // Blocks execution chain link
        }

        return true; // Proceed normally
    }
}