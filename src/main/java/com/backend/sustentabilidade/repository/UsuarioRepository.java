package com.backend.sustentabilidade.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.sustentabilidade.model.Mensagem;
import com.backend.sustentabilidade.model.Usuario;

@Repository
public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>{

	//SELECT * FROM sustentabilidade.usuario as u where u.user_name like '%felipe%';
	@Query("SELECT u FROM Usuario u WHERE u.userName LIKE %:nome%")
	public List<Usuario> buscaPorUsername(@Param("nome") String nome);
	
	@Query("SELECT u FROM Usuario u WHERE u.email = :email")
	public Optional<Usuario> findByEmail(@Param("email") String email);	
	
}
