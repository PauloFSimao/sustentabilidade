package com.backend.sustentabilidade.model;

import org.springframework.http.HttpStatus;

public class Erro {
	
	private HttpStatus error;
	private String mensagem;
	
	public Erro(HttpStatus erro, String msg) {
		this.error = erro;
		this.mensagem = msg;
	}

	public HttpStatus getError() {
		return error;
	}

	public void setError(HttpStatus error) {
		this.error = error;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}


}
