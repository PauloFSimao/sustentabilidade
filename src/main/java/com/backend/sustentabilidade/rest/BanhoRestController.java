package com.backend.sustentabilidade.rest;

import java.time.LocalTime;
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

import com.backend.sustentabilidade.model.Banho;
import com.backend.sustentabilidade.model.Erro;
import com.backend.sustentabilidade.model.Sucesso;
import com.backend.sustentabilidade.repository.BanhoRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/banho")
public class BanhoRestController {
	
	@Autowired
	private BanhoRepository repository;
	
	// Método que salva o banho tomado pelo usuário
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> salvarBanho(@RequestBody Banho banho){
		if(banho != null) {
			if(banho.getTempo().getMinute() < 5) {
				banho.setPontos(15);
			} else if(banho.getTempo().getMinute() > 6) {
				banho.setPontos(5);
			} else {
				banho.setPontos(10);
			}
			
			System.out.println(banho.getTempo().getHour());
			repository.save(banho);
			
			Sucesso sucesso = new Sucesso(HttpStatus.OK, banho.getPontos()+"");
			return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
		}
		Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível registrar o banho!");
		return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//Método que pega as informações de um banho
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Optional<Banho> buscaBanho(@PathVariable("id") Long id){
		return repository.findById(id);
	}
}
