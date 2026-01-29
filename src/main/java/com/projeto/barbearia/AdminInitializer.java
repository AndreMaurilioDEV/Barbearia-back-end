package com.projeto.barbearia;

import com.projeto.barbearia.service.AdminService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer {

    private final AdminService adminService;

    public AdminInitializer(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostConstruct
    public void init() {
        adminService.createAdmin();
    }
}
