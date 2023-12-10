package com.codi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

//@Entity @Getter
public class Style {
	
	@Id @GeneratedValue
	@Column(name = "style_id")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private StyleCategory styleCategory;
	
}
