package com.codi.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Outfit {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "outfit_id")
	private Long id;
	
	private String outfitCode;
	
	private String outfitName;
	
	private int temperature;
	
	@OneToMany(mappedBy = "outfit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;

	@Builder
	public Outfit(
		String outfitCode, 
		String outfitName,
		int temperature
	) {
		this.outfitCode = outfitCode;
		this.outfitName = outfitName;
		this.temperature = temperature;
	}
	
}
