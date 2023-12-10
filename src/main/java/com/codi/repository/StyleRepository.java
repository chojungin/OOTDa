package com.codi.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.codi.entity.Style;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class StyleRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	public void saveStyle(Style style) {
		em.persist(style);
	}
	
	public Style findStyleCategory(Long id) {
		return em.find(Style.class, id);
	}
	
	public List<Style> findAllStyleCategory() {
		return em.createQuery("select s.styleCategory from Stlye s", Style.class)
				.getResultList();
	}
}
