package com.backend.sustentabilidade.rest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
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

import com.backend.sustentabilidade.model.Banho;
import com.backend.sustentabilidade.model.Erro;
import com.backend.sustentabilidade.model.Relatorio;
import com.backend.sustentabilidade.model.Sucesso;
import com.backend.sustentabilidade.model.TipoChuveiro;
import com.backend.sustentabilidade.repository.BanhoRepository;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;

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
			
			banho.getUsuario().setPontuacao(banho.getUsuario().getPontuacao() + banho.getPontos());
			
			Double time = (double) banho.getTempo().getMinute() + ((double) banho.getTempo().getSecond() / 100);
			
			if(banho.getVazaoChuv() == 0 && banho.getTipoChuv() == TipoChuveiro.CHUVEIRO) {
				banho.setVazaoChuv(5);
			}else if(banho.getVazaoChuv() == 0 && banho.getTipoChuv() == TipoChuveiro.DUCHA) {
				banho.setVazaoChuv(11);
			}
			
			banho.setConsumo(banho.getVazaoChuv() * time);
			
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
	
	// Método que pega todos os banhos do mês de um usuário e faz relatório
	@RequestMapping(value = "/{idUser}/{mes}/{ano}", method = RequestMethod.GET)
	public ResponseEntity<Object> buscaBanhos(@PathVariable("mes") String mesS, @PathVariable("ano") int ano, @PathVariable("idUser") Long idUser){
		
		int ultimo = 0;
		int mes = Integer.parseInt(mesS);
		if(mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
			ultimo = 31;
		}else if(mes == 2) {
			ultimo = 29;
		} else {
			ultimo = 30;
		}
		mes--;
		Calendar inicio = Calendar.getInstance();
		inicio.set(ano, mes, 1);
		Calendar fim = Calendar.getInstance();
		fim.set(ano, mes, ultimo);
		List<Banho> banhos = repository.buscaBanhosMes(inicio, fim, idUser);
		
		if(!banhos.isEmpty()) {
			Relatorio relatorio = new Relatorio();
			Object result[] = new Object[2];
			
			LocalTime time = LocalTime.of(0, 0);
			double consumo = 0;	
			int pontos = 0;
			double esperado = 0;
			int chuveiro = 0;
			int ducha = 0;
			
			for (Banho banho : banhos) {
				time = time.plusMinutes(banho.getTempo().getMinute()).plusSeconds(banho.getTempo().getSecond());
				consumo += banho.getConsumo();
				pontos += banho.getPontos();
				esperado += banho.getVazaoChuv() * 5;
				if(banho.getTipoChuv() == TipoChuveiro.CHUVEIRO) {
					chuveiro++;
				}else {
					ducha++;
				}
			}
			int minutos = time.getMinute() / banhos.size();
			int segundos = time.getSecond() / banhos.size();
			if(time.getMinute() % banhos.size() != 0) {
				segundos += 30;
			}
			
			List<Banho> todosBanhos = repository.buscaBanhosUser(idUser);
			double consumoTotal = 0;
			for (Banho banho : todosBanhos) {
				consumoTotal += banho.getConsumo();
			}

			LocalTime timeMedia = LocalTime.of(0, minutos, segundos);
			relatorio.setConsumo(consumo);
			relatorio.setPontos(pontos);
			relatorio.setQtdBanho(banhos.size());
			relatorio.setTempoTotal(time);
			relatorio.setTempoMedia(timeMedia);
			relatorio.setEsperado(esperado);
			relatorio.setBio(banhos.get(0).getUsuario().getBio());
			relatorio.setConsumoTotal(consumoTotal);
			relatorio.setQtdBanhoTotal(todosBanhos.size());
			relatorio.setPontuacaoTotal(banhos.get(0).getUsuario().getPontuacao());
			relatorio.setConsumoBanho(consumoTotal / todosBanhos.size());
			relatorio.setQtdChuveiro(chuveiro);
			relatorio.setQtdDucha(ducha);
			
			result[0] = relatorio;
			result[1] = banhos;
		
			return new ResponseEntity<Object>(result, HttpStatus.OK);
		}
		Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Não há banhos registrados!");
		return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
