package com.backend.sustentabilidade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.sustentabilidade.model.Postagem;

@Repository
public interface PostagemRepository extends PagingAndSortingRepository<Postagem, Long>{

	@Query("SELECT p FROM Postagem as p WHERE p.conteudo like '%:palavra%'")
	public List<Postagem> buscaPalavra(@Param("palavra") String palavra);
}
