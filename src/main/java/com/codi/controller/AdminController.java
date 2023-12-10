package com.codi.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController 
@RequestMapping("/api/admin")
public class AdminController {
	
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") 
    public String AdminSetting (@AuthenticationPrincipal User user) { 
        return "";
    }
}
