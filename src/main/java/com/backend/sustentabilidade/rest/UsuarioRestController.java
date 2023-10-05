package com.backend.sustentabilidade.rest;

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
import com.backend.sustentabilidade.model.Sucesso;
import com.backend.sustentabilidade.model.Usuario;
import com.backend.sustentabilidade.repository.UsuarioRepository;

@CrossOrigin
@RequestMapping("/api/usuario")
@RestController
public class UsuarioRestController {

	@Autowired
	private UsuarioRepository repository;
	
	// Método que cadastra o usuário
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> salvarUsuario(@RequestBody Usuario user){
		if(user != null) {
			repository.save(user);
			Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
			ResponseEntity<Object> ok = new ResponseEntity<Object>(sucesso, HttpStatus.OK);
			
			return ok;
		} else {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível cadastrar o Usuário");
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Método que lista todos os usuários
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Usuario> listarUser(){
		return repository.findAll();
	}
	
	// Método que deleta o usuário
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deletaUser(@PathVariable("id") Long id, Usuario user){
		if(id == user.getId()) {
			repository.delete(user);
			Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
			return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
		} else {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível deletar o usuário");
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Método que altera o usuário
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> alterarUser(@PathVariable("id") Long id, @RequestBody Usuario user){
		if(id != user.getId()) {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível aterar o usuário");
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			repository.save(user);
			Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
			return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
			
		}
	}
}
