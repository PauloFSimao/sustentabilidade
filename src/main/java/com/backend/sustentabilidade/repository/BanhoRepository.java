package com.backend.sustentabilidade.repository;

import java.time.Month;
import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.sustentabilidade.model.Banho;

@Repository
public interface BanhoRepository extends PagingAndSortingRepository<Banho, Long>{
	
	//select * from sustentabilidade.banho as b where b.data between "2004-03-01" and "2004-03-31";
	@Query("SELECT b FROM Banho b WHERE b.data >= :inicio AND b.data <= :fim AND b.usuario.id = :idUser")	
	public List<Banho> buscaBanhosMes(@Param("inicio") Calendar inicio, @Param("fim") Calendar fim, @Param("idUser") Long idUser);
	
	@Query("SELECT b FROM Banho b WHERE b.usuario.id = :idUser")
	public List<Banho> buscaBanhosUser(@Param("idUser") Long idUser);
	
	//select usuario_id, sum(pontos) from banho as b where b.data between "2004-04-01" and "2004-04-30" group by usuario_id order by sum(pontos) desc;
	//@Query("SELECT new Banho (b.usuario, sum(b.pontos) FROM Banho b WHERE b.data BETWEEN ':inicio' AND ':fim' GROUP BY b.usuario.id ORDER BY SUM(pontos)")
	//public List<Banho> buscaRanking(@Param("inicio") Calendar inicio, @Param("fim") Calendar fim);
	
}
