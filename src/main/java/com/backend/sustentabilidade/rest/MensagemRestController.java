package com.backend.sustentabilidade.rest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

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
import com.backend.sustentabilidade.model.Mensagem;
import com.backend.sustentabilidade.model.Sucesso;
import com.backend.sustentabilidade.model.Usuario;
import com.backend.sustentabilidade.repository.MensagemRepository;
import com.backend.sustentabilidade.repository.UsuarioRepository;

@CrossOrigin
@RequestMapping("/api/mensagem")
@RestController
public class MensagemRestController {
	
	@Autowired
	private MensagemRepository repository;
	
	@Autowired
	private UsuarioRepository userRepository;
	
	// Método que registra uma mensagem
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> mandarMensagem(@RequestBody Mensagem msg){
		if(msg != null) {
			msg.setData(LocalDate.now());
			repository.save(msg);
			Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
			return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
		} else {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível enviar sua mensagem");
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Método que pega as mensagens entre dois usuários
	@RequestMapping(value = "/{idRem}/{idDest}", method = RequestMethod.GET)
	public List<Mensagem> buscarConversa(@PathVariable("idRem") Long idRem, @PathVariable("idDest") Long idDest){
		Usuario dest = userRepository.findById(idDest).get();
		Usuario rem = userRepository.findById(idRem).get();
		return repository.findEntreUser(dest, rem);
	}
	
	// Método que busca o histórico de conversa
	@RequestMapping(value = "/{idRem}", method = RequestMethod.GET)
	public List<Usuario> buscaHistorico(@PathVariable("idRem") Long id){
		Usuario rem = userRepository.findById(id).get();
		ArrayList<Usuario> users = (ArrayList<Usuario>) repository.buscaRecebidos(rem);
		
		List<Usuario> enviados = repository.buscaEnviados(rem);
		
		for(int i = 0; i < enviados.size(); i++) {
			boolean igual = false;
			for(int j = 0; j < users.size(); j++) {
				if(enviados.get(i) == users.get(j)) {
					igual = true;
				}
			}
			if(igual == false) {
				users.add(enviados.get(i));
			}
		}
		
		System.out.println(users);
		
		return users;
	}
}
