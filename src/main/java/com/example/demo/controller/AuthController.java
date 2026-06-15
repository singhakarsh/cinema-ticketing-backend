package com.example.demo.controller;

import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials, HttpSession session) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        Optional<Admin> adminOpt = adminRepository.findByUsername(username);

        if (adminOpt.isPresent() && adminOpt.get().getPassword().equals(password)) {
            // 🎫 SUCCESS: Store a security marker attribute directly inside the user's
            // server session token
            session.setAttribute("LOGGED_IN_ADMIN", username);
            return ResponseEntity.ok("Authentication successful!");
        }

        return ResponseEntity.status(401).body("❌ Invalid username or password.");
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkAuthentication(HttpSession session) {
        // Returns true if the session attribute exists, false otherwise
        return ResponseEntity.ok(session.getAttribute("LOGGED_IN_ADMIN") != null);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // Wipes out the active session cookie cache
        return ResponseEntity.ok("Logged out cleanly.");
    }
}