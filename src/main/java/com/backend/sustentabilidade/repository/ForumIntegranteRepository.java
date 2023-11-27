package com.backend.sustentabilidade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.sustentabilidade.model.Forum;
import com.backend.sustentabilidade.model.ForumIntegrante;
import com.backend.sustentabilidade.model.Usuario;

@Repository
public interface ForumIntegranteRepository extends PagingAndSortingRepository<ForumIntegrante, Long>{
	
	@Query("SELECT fi FROM ForumIntegrante fi WHERE forum = :forum AND notificacao = true")
	public List<ForumIntegrante> buscaAtivos(@Param("forum") Forum forum);
	
	// select f.forum_id from forum_integrantes as f inner join usuario as u on f.usuario_id = u.id where f.usuario_id = 30
	@Query("SELECT fi.forum FROM ForumIntegrante fi WHERE fi.participante = :user")
	public List<Forum> buscaPorParticipante(@Param("user") Usuario usuario);
	
	//delete from sustentabilidade.forum_integrante as fi where forum_id = 14 and participante_id = 30
	@Query("DELETE FROM ForumIntegrante fi WHERE fi.forum = :forum AND fi.participante.id = :idUser")
	public void deletaFI(@Param("forum") Forum forum, @Param("idUser") Long id);
	
	public void deleteByForumAndParticipante(Forum forum, Usuario integrante);
}
