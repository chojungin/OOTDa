package com.codi.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Temperature {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "temp_id")
    private Long id;
	
	private String tempName;

    private int minTemp;
    
    private int maxTemp;
	
    @Builder
	public Temperature(
		String tempName,
		int minTemp,
		int maxTemp
	) {
    	this.tempName = tempName;
		this.minTemp = minTemp;
		this.maxTemp = maxTemp;
	}
    
}
