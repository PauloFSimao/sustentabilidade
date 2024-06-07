package com.backend.sustentabilidade.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.backend.sustentabilidade.model.Erro;
import com.backend.sustentabilidade.model.Forum;
import com.backend.sustentabilidade.model.ForumIntegrante;
import com.backend.sustentabilidade.model.Sucesso;
import com.backend.sustentabilidade.model.Usuario;
import com.backend.sustentabilidade.repository.ForumIntegranteRepository;
import com.backend.sustentabilidade.repository.ForumRepository;
import com.backend.sustentabilidade.repository.UsuarioRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/forum")
public class ForumRestController {
	
	@Autowired
	private ForumRepository repository;
	
	@Autowired
	private ForumIntegranteRepository fiRepository;
	
	@Autowired
	private UsuarioRepository userRepository;
	
	
	// Método que cria um novo Fórum
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criarForum(@RequestBody Forum forum){
		if(forum.getNome().isBlank()) {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Nome do Fórum é um campo obrigatório!");
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}else if(forum.getDescricao().isBlank()) {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Descrição do Fórum é um campo obrigatório!");
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(forum != null) {
			ArrayList<Usuario> participantes = new ArrayList<Usuario>();
			participantes.add(forum.getCriador());
			forum.setIntegrantes(participantes);
			repository.save(forum);
			ForumIntegrante fi = new ForumIntegrante(forum.getCriador(), forum, true);
			fiRepository.save(fi);
			Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
			return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
		} else {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível criar o fórum");
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	// Método que lista todos os fóruns
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Forum> findAll(){
		return repository.findAll();
	}
	
	// Método que deleta o Fórum
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deletarForum(@PathVariable("id") Long id, Usuario user){
		if(id == user.getId()) {
			repository.deleteById(id);
			Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
			return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
		} else {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível deletar o fórum");
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Método que altera o fórum
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> alterarForum(@RequestBody Forum forum, @PathVariable("id") Long id){
		if(id != forum.getId()) {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível alterar o Fórum");
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			repository.save(forum);
			Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
			return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
		}
	}
	
	// Método para aprticipar do Fórum
	@RequestMapping(value = "/{idForum}/{idUser}", method = RequestMethod.PUT)
	public ResponseEntity<Object> participarForum(@PathVariable("idForum") Long idForum, @PathVariable("idUser") Long idUser){
		Optional<Forum> forum = repository.findById(idForum);
		Optional<Usuario> user = userRepository.findById(idUser);
		forum.get().getIntegrantes().add(user.get());
		repository.save(forum.get());
		ForumIntegrante fi = new ForumIntegrante(user.get(), forum.get(), true);
		fiRepository.save(fi);
		Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
		return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
	}
	
	@Transactional
	//Método de sair do fórum
	@RequestMapping(value = "/sair/{idForum}/{idUser}", method = RequestMethod.PUT)
	public ResponseEntity<Object> sairForum(@PathVariable("idForum") Long idForum, @PathVariable("idUser") Long idUser){
		Forum forum = repository.findById(idForum).get();
		Usuario user = userRepository.findById(idUser).get();
		
		List<Usuario> participantes = forum.getIntegrantes();
		for(int i = 0; i < participantes.size(); i++) {
			if(participantes.get(i).getId() == idUser) {
				participantes.remove(i);
				break;
			}
		}
		
		forum.setIntegrantes(participantes);
		repository.save(forum);
		
		
		fiRepository.deleteByForumAndParticipante(forum, user);
		Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
		return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
		
	}
	
	//Método que pega todos os fóruns que um usuário participa
	@RequestMapping(value = "/{idUser}", method = RequestMethod.GET)
	public List<Forum> listaDoParticipante(@PathVariable("idUser") Long idUser){
		Optional<Usuario> user = userRepository.findById(idUser);
		return fiRepository.buscaPorParticipante(user.get());
	}
	
	// Método que pega o fórum pelo ID
	@RequestMapping(value = "/id/{id}")
	public Optional<Forum> findById(@PathVariable("id") Long id){
		return repository.findById(id);
	}
	
	//Método que busca por nome ou descrição
	@RequestMapping(value = "/palavra/{palavra}", method = RequestMethod.GET)
	public List<Forum> buscaPorDescNome(@PathVariable("palavra") String palavra){
		return repository.buscaPorDesNome(palavra);
	}
	
	
	
}
