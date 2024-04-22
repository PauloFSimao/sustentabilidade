package com.backend.sustentabilidade.model;

import java.time.LocalTime;

public class Relatorio {
	private int qtdBanho;
	private LocalTime tempoTotal;
	private LocalTime tempoMedia;
	private double consumo;
	private int pontos;
	private double esperado;
	public int getQtdBanho() {
		return qtdBanho;
	}
	public void setQtdBanho(int qtdBanho) {
		this.qtdBanho = qtdBanho;
	}
	public LocalTime getTempoTotal() {
		return tempoTotal;
	}
	public void setTempoTotal(LocalTime tempoBanho) {
		this.tempoTotal = tempoBanho;
	}
	public LocalTime getTempoMedia() {
		return tempoMedia;
	}
	public void setTempoMedia(LocalTime tempoMedia) {
		this.tempoMedia = tempoMedia;
	}
	public double getConsumo() {
		return consumo;
	}
	public void setConsumo(double consumo) {
		this.consumo = consumo;
	}
	public int getPontos() {
		return pontos;
	}
	public void setPontos(int pontos) {
		this.pontos = pontos;
	}
	public double getEsperado() {
		return esperado;
	}
	public void setEsperado(double esperado) {
		this.esperado = esperado;
	}
	
	
}
