package com.backend.sustentabilidade.model;

import java.time.LocalTime;

public class Relatorio {
	private int qtdBanho;
	private LocalTime tempoTotal;
	private LocalTime tempoMedia;
	private double consumo;
	private int pontos;
	private double esperado;
	private int qtdBanhoTotal;
	private double consumoTotal;
	private String bio;
	private int pontuacaoTotal;
	private double consumoBanho;
	private int qtdChuveiro;
	private int qtdDucha;
	
	
	
	public int getQtdChuveiro() {
		return qtdChuveiro;
	}
	public void setQtdChuveiro(int qtdChuveiro) {
		this.qtdChuveiro = qtdChuveiro;
	}
	public int getQtdDucha() {
		return qtdDucha;
	}
	public void setQtdDucha(int qtdDucha) {
		this.qtdDucha = qtdDucha;
	}
	public double getConsumoBanho() {
		return consumoBanho;
	}
	public void setConsumoBanho(double consumoBanho) {
		this.consumoBanho = consumoBanho;
	}
	public int getQtdBanhoTotal() {
		return qtdBanhoTotal;
	}
	public void setQtdBanhoTotal(int qtdBanhoTotal) {
		this.qtdBanhoTotal = qtdBanhoTotal;
	}
	public double getConsumoTotal() {
		return consumoTotal;
	}
	public void setConsumoTotal(double consumoTotal) {
		this.consumoTotal = consumoTotal;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public int getPontuacaoTotal() {
		return pontuacaoTotal;
	}
	public void setPontuacaoTotal(int pontuacaoTotal) {
		this.pontuacaoTotal = pontuacaoTotal;
	}
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
