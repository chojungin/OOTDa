package com.codi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.codi.entity.Item;

@Repository
@EnableJpaRepositories
public interface ItemRepository extends JpaRepository<Item, Long>{

}
