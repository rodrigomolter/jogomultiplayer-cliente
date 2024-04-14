package br.feevale.jogomultiplayer.cliente;

public class Personagem {

	private int id;
	private int vida;
	private int posX;
	private int posY;
	private int aparencia;
	private Action action;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public int getVida() {
		return vida;
	}
	public void setVida(int vida) {
		this.vida = vida;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public int getAparencia() {
		return aparencia;
	}
	public void setAparencia(int aparencia) {
		this.aparencia = aparencia;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
	
}
