package com.codi.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
public class Poll {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "poll_id")
	private Long id;
	
	private String city;
	
	private String district;
	
	private int sensTemp;
	
	private LocalDateTime createdAt;
	
	@OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    private Set<PollItem> pollItems = new HashSet<>();
	
	@Builder
	public Poll(
		String city, 
		String district, 
		int sensTemp,
		LocalDateTime createdAt
	) {
		this.city = city;
		this.district = district;
		this.sensTemp = sensTemp;
		this.createdAt = createdAt;
	}

}
