package com.backend.sustentabilidade.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class ForumIntegrante {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne
	private Usuario participante;
	
	@NotNull
	@ManyToOne
	private Forum forum;
	
	@NotNull
	private boolean notificacao;
	
	public ForumIntegrante() {
		this.forum = null;
		this.id = null;
		this.notificacao = false;
		this.participante = null;
	}
	
	public ForumIntegrante(Usuario user, Forum forum, boolean notificacao) {
		this.forum = forum;
		this.participante = user;
		this.notificacao = notificacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getParticipante() {
		return participante;
	}

	public void setParticipante(Usuario participante) {
		this.participante = participante;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public boolean isNotificacao() {
		return notificacao;
	}

	public void setNotificacao(boolean notificacao) {
		this.notificacao = notificacao;
	}
	
	
}
