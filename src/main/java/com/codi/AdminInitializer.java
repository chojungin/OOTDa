package com.codi;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.codi.entity.Item;
import com.codi.entity.Member;
import com.codi.entity.Outfit;
import com.codi.entity.RoleType;
import com.codi.repository.ItemRepository;
import com.codi.repository.MemberRepository;
import com.codi.repository.OutfitRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class AdminInitializer implements ApplicationRunner {
    
	private final MemberRepository memberRepository;
	private final OutfitRepository outfitRepository;
	private final ItemRepository itemRepository;
	
	private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(ApplicationArguments args) {
    	Outfit savedOutfit = outfitRepository.save(
				Outfit.builder()
					.outfitCd("B")
					.outfitName("BOILING")
					.build()
			);
    	
    	itemRepository.save(
				Item.builder()
					.itemCd("SM_1")
					.itemName("민소매")
					.outfit(savedOutfit)
					.build()
			);
    	
    	memberRepository.save(
	        		Member.builder()
		                .account("admin")
		                .password(passwordEncoder.encode("1234"))
		                .userName("관리자")
		                .birthDate("19960116")
		                .roleType(RoleType.ROLE_ADMIN)
		                .useYn("Y")
		                .createdAt(LocalDateTime.now())
		                .build()
                );
    	memberRepository.save(
        		Member.builder()
	                .account("tester")
	                .password(passwordEncoder.encode("1234"))
	                .userName("홍길동")
	                .birthDate("19911111")
	                .roleType(RoleType.ROLE_USER)
	                .useYn("Y")
	                .createdAt(LocalDateTime.now())
	                .build()
            );
    }
}
