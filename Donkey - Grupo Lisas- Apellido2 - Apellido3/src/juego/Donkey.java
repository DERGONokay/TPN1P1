package juego;

import java.awt.*;
import java.util.Random;

import entorno.*;

public class Donkey 
{
	private Point posicion;
	private int altura;
	private int ancho;
	private Image textura;
	private Image textura2;
	private Image textura3;
	private boolean lanzando = false;
	private int chanceDeLanzar;
	
	public Donkey() // Constructor
	{
		this.posicion = new Point (110, 50);
		this.altura = 80;
		this.ancho = 55;
		this.chanceDeLanzar = 0;
		this.textura = Herramientas.cargarImagen("Donkey.png");
		this.textura2 = Herramientas.cargarImagen("Donkeylanzando.png");
		this.textura3 = Herramientas.cargarImagen("Donkeyagarrando.png");
	}
	
	public void mostrar(Entorno entorno) //Dibujador
	{
		//entorno.dibujarRectangulo( posicion.x, posicion.y, 0, Color.pink);
		if (lanzando)
			entorno.dibujarImagen(textura2, posicion.x, posicion.y, 0);
		else
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
	
	public void lanzarBarril(Barril barril)
	{
		
		Random random = new Random();
		int num1 = 0;
		int num2 = random.nextInt(2);
		this.lanzando = false;
		if(num1 == num2)
		{
			this.chanceDeLanzar++;
		}
		
		if(chanceDeLanzar >= 75)
		{
			barril.lanzar();
			this.chanceDeLanzar = 0;
			this.lanzando = true;
		}
	}
	
	
}
