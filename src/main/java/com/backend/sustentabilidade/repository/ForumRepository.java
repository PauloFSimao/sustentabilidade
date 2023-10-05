package com.backend.sustentabilidade.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.backend.sustentabilidade.model.Forum;

@Repository
public interface ForumRepository extends PagingAndSortingRepository<Forum, Long>{
	
}
