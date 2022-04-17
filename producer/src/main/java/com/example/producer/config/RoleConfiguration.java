package com.example.producer.config;

import domain.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RoleConfiguration {

    @Bean
    public List<Role> allRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.ADMIN);
        roles.add(Role.USER);
        return roles;
    }
}
