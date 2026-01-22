package com.pw.edu.pl.master.thesis.user.configuration;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class AuthContext {

    private final HttpServletRequest request;

    /** Username from SecurityContext (Basic auth principal). */
    public String currentUsernameOrThrow() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName() == null) {
            throw new IllegalStateException("No authenticated user in SecurityContext");
        }
        return auth.getName();
    }




}
