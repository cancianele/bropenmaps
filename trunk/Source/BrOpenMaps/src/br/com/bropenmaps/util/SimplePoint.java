package br.com.bropenmaps.util;

/**
 * Representa um ponto no plano cartesiano
 * @author Rafael Melo Salum
 *
 */
public class SimplePoint {

	private int x;
	private int y;
	
	/**
	 * Construtor da classe
	 * @param x
	 * @param y
	 */
	public SimplePoint(int x, int y) {
		
		this.x = x;
		this.y = y;
		
	}
	
	/**
	 * Retorna a coordenada x
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * Configura a variável x
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Retorna a coordenada y
	 * @return
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Configura a variável y
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	
}