package com.backend.sustentabilidade.model;

import java.time.LocalTime;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Banho {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Calendar data;
	
	@NotNull
	@OneToOne
	private Usuario usuario;
	
	@NotNull
	private LocalTime tempo;
	
	@NotNull
	private TipoChuveiro tipoChuv;
	
	private double vazaoChuv;
	
	private double consumo;
	
	private int pontos;
	
	public TipoChuveiro getTipoChuv() {
		return tipoChuv;
	}
	public void setTipoChuv(TipoChuveiro tipoChuv) {
		this.tipoChuv = tipoChuv;
	}
	public double getVazaoChuv() {
		return vazaoChuv;
	}
	public void setVazaoChuv(double vazaoChuv) {
		this.vazaoChuv = vazaoChuv;
	}
	public double getConsumo() {
		return consumo;
	}
	public void setConsumo(double consumo) {
		this.consumo = consumo;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Calendar getData() {
		return data;
	}
	public void setData(Calendar data) {
		this.data = data;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public LocalTime getTempo() {
		return tempo;
	}
	public void setTempo(LocalTime tempo) {
		this.tempo = tempo;
	}
	public int getPontos() {
		return pontos;
	}
	public void setPontos(int pontos) {
		this.pontos = pontos;
	}
	
	
}
