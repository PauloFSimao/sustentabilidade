package com.backend.sustentabilidade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.sustentabilidade.model.Forum;

@Repository
public interface ForumRepository extends PagingAndSortingRepository<Forum, Long>{
	
	//SELECT * FROM sustentabilidade.forum as f where f.descricao like '%paulo%' or f.nome like '%paulo%';
	@Query("SELECT f FROM Forum f WHERE f.descricao LIKE %:nome% OR f.nome LIKE %:nome%")
	public List<Forum> buscaPorDesNome(@Param("nome") String nome);
	
	@Query("SELECT f FROM Forum f ORDER BY f.id DESC")
	public Iterable<Forum> findAllOrderByIdDesc();
	
}
