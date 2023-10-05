package com.backend.sustentabilidade.rest;

import java.util.ArrayList;
import java.util.List;

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
import com.backend.sustentabilidade.model.Sucesso;
import com.backend.sustentabilidade.model.Usuario;
import com.backend.sustentabilidade.repository.ForumRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/forum")
public class ForumRestController {
	
	@Autowired
	private ForumRepository repository;
	
	// Método que cria um novo Fórum
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criarForum(@RequestBody Forum forum){
		if(forum != null) {
			ArrayList<Usuario> participantes = new ArrayList<Usuario>();
			participantes.add(forum.getCriador());
			forum.setIntegrantes(participantes);
			repository.save(forum);
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
}
