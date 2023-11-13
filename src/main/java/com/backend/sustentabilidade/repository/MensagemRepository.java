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
	
	//select u.id, u.user_name from usuario as u inner join mensagem as m on u.id = m.destinatario_id  where u.id = m.remetente_id and m.destinatario_id = 30 or u.id = m.destinatario_id and m.remetente_id = 30  group by u.id	
	@Query("SELECT m.destinatario FROM Usuario u INNER JOIN Mensagem m ON u.id = m.destinatario WHERE u.id = m.destinatario AND m.destinatario = :dest OR u.id = m.destinatario AND m.remetente = :dest GROUP BY u.id ")
	public List<Usuario> buscaHistorico(@Param("dest") Usuario remetente);
	
}