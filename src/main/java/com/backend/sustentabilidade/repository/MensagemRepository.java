package com.backend.sustentabilidade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.sustentabilidade.model.Mensagem;
import com.backend.sustentabilidade.model.Usuario;

@Repository
public interface MensagemRepository extends PagingAndSortingRepository<Mensagem, Long>{
	
	@Query("SELECT m FROM Mensagem m WHERE m.destinatario = :dest and m.remetente = :rem OR m.destinatario = :rem AND m.remetente = :dest ORDER BY m.id ASC")
	public List<Mensagem> findEntreUser(@Param("dest") Usuario dest, @Param("rem") Usuario rem);
}
