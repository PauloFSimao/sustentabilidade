package com.backend.sustentabilidade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.sustentabilidade.model.Forum;
import com.backend.sustentabilidade.model.ForumIntegrante;

@Repository
public interface ForumIntegranteRepository extends PagingAndSortingRepository<ForumIntegrante, Long>{
	
	@Query("SELECT fi FROM ForumIntegrante fi WHERE forum = :forum AND notificacao = true")
	public List<ForumIntegrante> buscaAtivos(@Param("forum") Forum forum);
}
