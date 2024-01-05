package com.codi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codi.entity.Item;

@Repository
@EnableJpaRepositories
public interface ItemRepository extends JpaRepository<Item, Long>{
	
	@Query("SELECT i FROM Item i "
			+ "JOIN i.temperature t " 
		    + "WHERE :val BETWEEN t.minTemp AND t.maxTemp")
	List<Item> findItemByTemperature(@Param("val") int val);
	
}
