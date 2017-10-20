package juego;

import java.awt.Color;

import entorno.Entorno;

public class Escalera 
{
	//variables de instancia
	private int x;
	private int y;
	private int alto;
	private int ancho;
	private int bordeInferior; /*Posicion en Y de la base de la escalera*/
	private int bordeSuperior; /*Posicion en Y de la parte superior de la escalera*/
	
	Escalera(int x, int y, int alto, int ancho)
	{
		this.x = x;
		this.y = y;
		this.alto = alto;
		this.ancho = ancho;
		this.bordeInferior = this.y+alto/2;
		this.bordeSuperior = this.y-alto/2;
	}
	/* getters y setters */
	public int getX()
	{
		return this.x;
	}
	public int getY()
	{
		return this.y;
	}
	public int getAncho()
	{
		return this.ancho;
	}
	public int getAlto()
	{
		return this.alto;
	}
	public int getInferior()
	{
		return this.bordeInferior;
	}
	public int getSuperior()
	{
		return this.bordeSuperior;
	}
	
	 /*Dibuja una escalera en un entorno que se le pasa por parametro*/
	public void dibujarse(Entorno entorno)
	{
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.orange);
	}
	
	/*Devuelve verdadero en caso de que el personaje est� tocando la escalera*/
	public boolean laToca(Agente agente)
	{
		/* parametros de comparacion */
		int baseAgente = agente.getY();
		int ladoDerecho = agente.getX(); /*Limite derecho*/
		int ladoIzquierdo = agente.getX(); /*Limite izquierdo*/

		/* comparacion de los valores */
		if( baseAgente <= this.y+(this.alto/2) && baseAgente >= this.y-(this.alto/2) && 
				ladoDerecho >= this.x-(this.ancho/2) && ladoIzquierdo <= this.x+(this.ancho)/2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/* Basicamente lo mismo que la funcion "laToca" pero en este caso los valores deben ser estrictamente
	 * menores que los de la escalera para decidir si hay superposici�n o no*/
	public boolean seSuperpone(Agente agente)
	{
		/* parametros de comparaci�n */
		int baseAgente = agente.getY();
		int ladoDerecho = agente.getX() - agente.getAncho()/2;
		int ladoIzquierdo = agente.getX() + agente.getAncho()/2;

		/* comparo los parametros */
		if( baseAgente < this.y+(this.alto/2) && baseAgente > this.y-(this.alto/2) && 
				ladoDerecho > this.x-(this.ancho/2) && ladoIzquierdo < this.x+(this.ancho) || 
			(agente.getY() < this.y+(this.alto/2) && agente.getY() > this.y-(this.alto/2) &&
					agente.getX() > this.x-this.ancho/2 && agente.getX() < this.x+this.ancho/2))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
