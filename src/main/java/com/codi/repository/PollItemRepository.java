package com.codi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codi.dto.PollResponse;
import com.codi.entity.PollItem;

@Repository
@EnableJpaRepositories
public interface PollItemRepository extends JpaRepository<PollItem, Long>{
	
	//***interface projection 대신 dto projection 사용을 위해 new com.codi.dto.PollResponse으로 경로 지정
	@Query("SELECT new com.codi.dto.PollResponse(b.id as itemId, b.itemName, COUNT(b.itemName) as itemCount, FORMAT(c.createdAt, 'yyyy-MM-dd') as pollDate) "
			+ "FROM PollItem a LEFT OUTER JOIN a.item b LEFT OUTER JOIN a.poll c "
			+ "WHERE c.city = :city "
			+ "AND c.district = :district "
			+ "AND FORMATDATETIME(c.createdAt, 'yyyy-MM-dd') = CURRENT_DATE "
			+ "GROUP BY b.itemName")
	List<PollResponse> getPollResult( @Param("city") String city,
								@Param("district") String district);

}
