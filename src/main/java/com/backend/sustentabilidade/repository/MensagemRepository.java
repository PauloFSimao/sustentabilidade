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

	//select distinct m.destinatario_id, m.remetente_id from usuario as u inner join mensagem as m on u.id = m.destinatario_id where m.destinatario_id = 1 or m.remetente_id = 1;
	@Query("SELECT DISTINCT m.destinatario, m.remetente FROM Usuario u INNER JOIN Mensagem m ON u.id = m.destinatario WHERE m.destinatario = :dest OR m.remetente = :dest")
	public List<Usuario> buscaHistorico(@Param("dest") Usuario remetente);
	
	//select distinct m.* from mensagem as m where m.remetente_id = 1 group by m.teretente_id
	@Query("SELECT DISTINCT m.destinatario FROM Mensagem m WHERE m.remetente = :user GROUP BY m.remetente")
	public List<Usuario> buscaEnviados(@Param("user") Usuario remetente);
	
	//select distinct m.* from mensagem as m where m.destinatario_id = 1 group by m.remetente_id;
	@Query("SELECT DISTINCT m.remetente FROM Mensagem m WHERE m.destinatario = :user GROUP BY m.remetente")
	public List<Usuario> buscaRecebidos(@Param("user") Usuario user);
}