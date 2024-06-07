package com.backend.sustentabilidade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.sustentabilidade.model.DicaSustentavel;

@Repository
public interface DicaSustentavelRepository extends PagingAndSortingRepository<DicaSustentavel, Long>{

	// SELECT * FROM sustentabilidade.dica_sustentavel as d where d.autor_id = 2;
	@Query("SELECT d FROM DicaSustentavel d WHERE d.autor.id = :id ORDER BY d.id DESC")
	public List<DicaSustentavel> LisbuscaPorAutor(@Param("id") Long id);
	
	@Query("SELECT d FROM DicaSustentavel d ORDER BY d.id DESC")
	public List<DicaSustentavel> listaDesc();
}
