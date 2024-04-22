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
	@Query("SELECT b FROM Banho b WHERE b.data >= :inicio AND b.data <= :fim")	
	public List<Banho> buscaBanhosMes(@Param("inicio") Calendar inicio, @Param("fim") Calendar fim);
	
}
