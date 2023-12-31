package com.codi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.codi.entity.Temperature;

@Repository
@EnableJpaRepositories
public interface TemperatureRepository extends JpaRepository<Temperature, Long>{

}
