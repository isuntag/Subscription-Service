package com.isaacsuntag.beltexam.services;

import org.springframework.stereotype.Service;

import com.isaacsuntag.beltexam.models.Role;
import com.isaacsuntag.beltexam.repositories.RoleRepository;

@Service
public class RoleService {
	private RoleRepository roleRepository;
	
	public RoleService(RoleRepository roleRepository)     {
        this.roleRepository = roleRepository;
    }
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}