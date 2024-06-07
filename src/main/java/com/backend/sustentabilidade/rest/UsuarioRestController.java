package com.backend.sustentabilidade.rest;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.backend.sustentabilidade.model.Erro;
import com.backend.sustentabilidade.model.Sucesso;
import com.backend.sustentabilidade.model.TokenJWT;
import com.backend.sustentabilidade.model.Usuario;
import com.backend.sustentabilidade.repository.UsuarioRepository;

@CrossOrigin
@RequestMapping("/api/usuario")
@RestController
public class UsuarioRestController {

	@Autowired
	private UsuarioRepository repository;
	
	private PasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public static final String SECRET = "sustentability";
	
	public static final String EMISSOR = "Futreen";
	
	
	
	// Método que cadastra o usuário
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> salvarUsuario(@RequestBody Usuario user){
		
		if(user != null) {
			if(!repository.findByUserName(user.getUserName()).isEmpty()) {
				Erro erro = new Erro(HttpStatus.UNAUTHORIZED, "Username já cadastrado!");
				return new ResponseEntity<Object>(erro,  HttpStatus.UNAUTHORIZED);
			}
			if(!repository.findByEmail(user.getEmail()).isEmpty()) {
				Erro erro = new Erro(HttpStatus.UNAUTHORIZED, "Email já cadastrado!");
				return new ResponseEntity<Object>(erro,  HttpStatus.UNAUTHORIZED);
			}
			
			
			// criptografando senha do usuário
			String crip = encoder.encode(user.getSenha());
			user.setSenha(crip); 
			
			repository.save(user);
			Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
			Object resp[] = new Object[4];
			resp[0] = sucesso;
			resp[1] = user.getId();
			resp[2] = user.getUserName();
			resp[3] = user.getFoto();
			ResponseEntity<Object> ok = new ResponseEntity<Object>(resp, HttpStatus.OK);
			
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
			Usuario userRep = repository.findById(user.getId()).get();
			
			if(user.getSenha() == "") {
				user.setSenha(userRep.getSenha());
			}else {
				String senhaCrip = encoder.encode(user.getSenha());
				user.setSenha(senhaCrip);
			}
			
			repository.save(user);
			Sucesso sucesso = new Sucesso(HttpStatus.OK, "Sucesso");
			return new ResponseEntity<Object>(sucesso, HttpStatus.OK);
			
		}
	}
	
	// Método que busca o usuário pelo id
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Optional<Usuario> buscaPorId(@PathVariable("id") Long id){
		return repository.findById(id);
	}
	
	//Método que busca usuários que se parecem com um nome
	@RequestMapping(value = "/nome/{nome}", method = RequestMethod.GET)
	public List<Usuario> buscaPorUsername(@PathVariable("nome") String nome){
		return repository.buscaPorUsername(nome);
	}
	
	// metodo feito para validar quando criamos o login
		// metodo feito para comparar se a senha bate com a do banco de dados
		public Boolean validarSenha(Usuario user, String senha) {
			// pegando a senha do banco de dados
			String senhaCrip = repository.findById(user.getId()).get().getSenha();
			// pegando a senha do banco e comparando com a atual
			Boolean valid = encoder.matches(senha, senhaCrip);
			return valid;
		}
	
	// Método de login
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> login(@RequestBody Usuario usuario){
		Optional<Usuario> user = repository.findByEmail(usuario.getEmail());
		
		System.out.println(user);
		
		if(!user.isEmpty()) {
			boolean validar = validarSenha(user.get(), usuario.getSenha());
			
			System.out.println(validar);
			
			if(validar == true) {
				Map<String, Object> payload = new HashMap<String, Object>();
				
				payload.put("id", user.get().getId());
				payload.put("username", user.get().getUserName());
				payload.put("email", user.get().getEmail());
				
				Calendar expiracao = Calendar.getInstance();
				
				expiracao.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH + 1);
				
				Algorithm algoritmo = Algorithm.HMAC512(SECRET);
				
				TokenJWT tokenJwt = new TokenJWT();
				
				tokenJwt.setToken(JWT.create().withPayload(payload).withIssuer(EMISSOR)
						.withExpiresAt(expiracao.getTime()).sign(algoritmo));
				
				Object resp[] = new Object[4];
				resp[0] = tokenJwt;
				resp[1] = user.get().getId();
				resp[2] = user.get().getUserName();
				resp[3] = user.get().getFoto();
				
				return ResponseEntity.ok(resp);
			
			} else {
				Erro erro = new Erro(HttpStatus.UNAUTHORIZED, "Email ou senha incorretos!");
				return new ResponseEntity<Object>(erro, HttpStatus.UNAUTHORIZED);
			}
		} else {
			Erro erro = new Erro(HttpStatus.UNAUTHORIZED, "Email ou senha incorretos!");
			return new ResponseEntity<Object>(erro, HttpStatus.UNAUTHORIZED);
		}
	}
}
