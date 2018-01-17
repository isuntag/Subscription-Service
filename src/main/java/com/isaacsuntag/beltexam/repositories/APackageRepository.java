package com.isaacsuntag.beltexam.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.isaacsuntag.beltexam.models.APackage;


@Repository
public interface APackageRepository extends CrudRepository<APackage, Long> {
    APackage findByName(String name);
}
