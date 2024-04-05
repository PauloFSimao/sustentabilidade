package com.backend.sustentabilidade.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.backend.sustentabilidade.model.Banho;

@Repository
public interface BanhoRepository extends PagingAndSortingRepository<Banho, Long>{
	
}
