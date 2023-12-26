package com.codi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codi.entity.Outfit;

@Repository
@EnableJpaRepositories
public interface OutfitRepository extends JpaRepository<Outfit, Long>{
	@Query("select o from Outfit o where o.outfitCode = :outfitCode")
	Outfit findIdByOutfitCode(@Param(value = "outfitCode") String outfitCode);
}
