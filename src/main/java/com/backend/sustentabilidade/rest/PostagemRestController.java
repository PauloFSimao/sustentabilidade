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
import com.backend.sustentabilidade.model.ForumIntegrante;
import com.backend.sustentabilidade.model.Postagem;
import com.backend.sustentabilidade.model.Sucesso;
import com.backend.sustentabilidade.model.Usuario;
import com.backend.sustentabilidade.repository.ForumIntegranteRepository;
import com.backend.sustentabilidade.repository.PostagemRepository;

@CrossOrigin
@RequestMapping("/api/postagem")
@RestController
public class PostagemRestController {

	@Autowired
	private PostagemRepository repository;
	
	@Autowired
	private SenderEmail senderEmail;
	
	@Autowired
	private ForumIntegranteRepository fiRepository;
	
	// Método que salva a postagem 
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> salvarPostagem(@RequestBody Postagem post){
		if(post != null) {
			repository.save(post);
			List<ForumIntegrante> ativos = fiRepository.buscaAtivos(post.getForum());
			for(int i = 0; i < ativos.size(); i++) {
				senderEmail.enviar(ativos.get(i).getParticipante().getEmail(), ativos.get(i).getForum().getNome(), ativos.get(i).getParticipante().getNome());
			}
			
			Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
			return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
		} else {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível fazer a postagem");
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Método que pega todas as postagens do banco de dados
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Postagem> buscarPostagem(){
		return repository.findAll();
	}
	
	// Método que deleta a postagem
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deletarPostagem(@PathVariable("id") Long id){
		if(repository.existsById(id)) {
			repository.deleteById(id);
			Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
			return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
		} else {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível deletar o seu post");
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Método que altera uma postagem
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> alterarPostagem(@PathVariable("id") Long id, @RequestBody Postagem post){
		if(id != post.getId()) {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível alterar o post");
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			repository.save(post);
			Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
			return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
		}
	}
	
	// Método que retorna postagens com uma palavra chave
	@RequestMapping(value = "/{palavra}", method = RequestMethod.GET)
	public List<Postagem> buscaPorPalavra(@PathVariable("palavra") String palavra){
		return repository.buscaPalavra(palavra);
	}
	
	
	
	
	
}
