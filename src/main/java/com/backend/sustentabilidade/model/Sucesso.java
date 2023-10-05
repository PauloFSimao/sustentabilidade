package com.backend.sustentabilidade.model;

import org.springframework.http.HttpStatus;

public class Sucesso {
	
	private HttpStatus status;
	private String mensagem;
	
	public Sucesso(HttpStatus st, String msg) {
		this.status = st;
		this.mensagem = msg;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	
}
