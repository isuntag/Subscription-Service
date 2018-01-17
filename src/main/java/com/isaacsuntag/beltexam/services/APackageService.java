package com.isaacsuntag.beltexam.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.isaacsuntag.beltexam.models.APackage;
import com.isaacsuntag.beltexam.repositories.APackageRepository;

@Service
public class APackageService {
	private APackageRepository apackageRepo;
	
	public APackageService(APackageRepository apackageRepo) {
		this.apackageRepo = apackageRepo;
	}
	
	public APackage findByName(String name) {
        return apackageRepo.findByName(name);
    }
	
	public APackage getOne(Long id) {
		return apackageRepo.findOne(id);
	}
	
	public List<APackage> getAllPackages() {
		return (List<APackage>) apackageRepo.findAll();
}
	
	public void addPackage(APackage apackage) {
		apackageRepo.save(apackage);
	}
	
	public void changeActive(Long id) {
		APackage apackage = apackageRepo.findOne(id);
		apackage.setActive(!apackage.isActive());
		apackageRepo.save(apackage);
	}
	
	public void delete(Long id) {
		apackageRepo.delete(id);
	}
}
