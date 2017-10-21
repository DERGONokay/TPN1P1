package juego;

import java.awt.*;
import entorno.*;

public class Donkey 
{
	private Point posicion;
	private int altura;
	private int ancho;
	private Image textura;
	
	public Donkey() // Constructor
	{
		this.posicion = new Point (110, 50);
		this.altura = 80;
		this.ancho = 55;
		this.textura = Herramientas.cargarImagen("Donkey.png");
	}
	
	public void mostrar(Entorno entorno) //Dibujador
	{
		//entorno.dibujarRectangulo( posicion.x, posicion.y, 0, Color.pink);
		entorno.dibujarImagen(textura, posicion.x, posicion.y, 0);
	}

	//Getters & Setters INICIO
	public Point getPosicion() {
		return posicion;
	}

	public void setPosicion(Point posicion) {
		this.posicion = posicion;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}
	//Getters & Setters FIN
	
	
}
