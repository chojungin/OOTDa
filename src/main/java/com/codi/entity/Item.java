package com.codi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Item {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private Long id;
	
	private String itemCd;
	
	private String itemName;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outfit_id")
    private Outfit outfit;
	
	@Builder
	public Item(
		Long id, 
		String itemCd, 
		String itemName, 
		Outfit outfit
	) {
		this.id = id;
		this.itemCd = itemCd;
		this.itemName = itemName;
		this.outfit = outfit;
	}
	
}
