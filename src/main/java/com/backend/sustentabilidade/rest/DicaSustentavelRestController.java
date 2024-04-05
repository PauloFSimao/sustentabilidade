package com.backend.sustentabilidade.rest;

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

import com.backend.sustentabilidade.model.DicaSustentavel;
import com.backend.sustentabilidade.model.Erro;
import com.backend.sustentabilidade.model.Sucesso;
import com.backend.sustentabilidade.repository.DicaSustentavelRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/dica")
public class DicaSustentavelRestController {

	@Autowired
	private DicaSustentavelRepository repository;
	
	//Método que salva a dica sustentável
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> salvarDicaSustentavel(@RequestBody DicaSustentavel dica){
		if(dica != null) {
			DicaSustentavel d = new DicaSustentavel();			
			repository.save(dica);
			
			Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
			return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
		}
		Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível cadastrar a dica sustentável");
		return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// Método que lista todos as dicas sustentáveis
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<DicaSustentavel> listarTodos(){
		return repository.findAll();
	}
	
	// Método que deleta a dica sustentável
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deletarDica(@PathVariable("id") Long id, DicaSustentavel dica){
		if(dica.getId() == id) {
			repository.deleteById(id);
			Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
			return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
		}
		Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível deletar a Dica Sustentável!");
		return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//Método que altera a dica sustentável
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> alterarDica(@PathVariable("id") Long id, @RequestBody DicaSustentavel dica){
		if(id == dica.getId()) {
			repository.save(dica);
			Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
			return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
		}
		Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível alterar a Dica Sustentável!");
		return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}