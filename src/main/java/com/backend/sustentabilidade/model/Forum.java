package com.backend.sustentabilidade.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Forum {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String nome;
	
	@NotNull
	private String descricao;
	
	@ManyToMany
	@JoinTable(name = "forum_integrantes", 
	joinColumns =  {@JoinColumn(name = "forum_id")},
	inverseJoinColumns = {@JoinColumn(name = "usuario_id")})
	private List<Usuario> integrantes;
	
	@ManyToOne
	@NotNull
	private Usuario criador;
	
	@Column(columnDefinition = "LONGTEXT")
	private String foto;

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Usuario> getIntegrantes() {
		return integrantes;
	}

	public void setIntegrantes(List<Usuario> integrante) {
		this.integrantes = integrante;
	}

	public Usuario getCriador() {
		return criador;
	}

	public void setCriador(Usuario criador) {
		this.criador = criador;
	}
}
