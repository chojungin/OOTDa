package com.codi.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
	
	private String outfitCd;
	
	private String outfitName;
	
	@OneToOne(mappedBy = "outfit", cascade = CascadeType.REMOVE)
    private Item item;

	@Builder
	public Outfit(
		Long id, 
		String outfitCd, 
		String outfitName
	) {
		this.id = id;
		this.outfitCd = outfitCd;
		this.outfitName = outfitName;
	}
	
}
