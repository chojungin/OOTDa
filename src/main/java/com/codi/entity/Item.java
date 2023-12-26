package com.codi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	
	private String itemCode;
	
	private String itemName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "outfit_id", referencedColumnName = "outfit_id", unique = false)
    private Outfit outfit;
	
	@Builder
	public Item(
		String itemCode, 
		String itemName, 
		Outfit outfit
	) {
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.outfit = outfit;
	}
	
}
