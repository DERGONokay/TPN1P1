package juego;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;

public class Fuga 
{
	private int x;
	private int y;
	private int ancho;	/*longitud en eje y*/
	private int alto;    /*longitud en eje x*/
	private int duracion;
	Image textura;
	
	Fuga()
	{
		this.x = 0;
		this.y = 300;
		this.ancho = 20;
		this.alto = 10;
		this.duracion = 30;
		
	}
	Fuga(int x, int y, int ancho, int alto, int duracion)
	{
		this.setX(x);
		this.setY(y);
		this.setAncho(ancho);
		this.setAlto(alto);
		this.setDuracion(duracion);
		this.textura = Herramientas.cargarImagen("Fuga.png");
	}
	
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
	public int getDuracion()
	{
		return this.duracion;
	}
	public void setX(int x)
	{
		this.x = x;
		if(this.x > 800 || this.x < 0)
		{
			throw new RuntimeException("Posicion X inválida " + this.x);
		}
	}
	public void setY(int y)
	{
		this.y = y;
		if(this.y < 0 || this.y > 600)
		{
			throw new RuntimeException("Posición Y inválida " + this.y);
		}
	}
	public void setAncho(int ancho)
	{
		this.ancho = ancho;
		if(this.ancho > 400)
		{
			throw new RuntimeException("La fuga es demasiado ancha " + this.ancho);
		}
		else if(this.ancho <= 0)
		{
			throw new RuntimeException("La fuga es muy corta " + this.ancho);
		}
	}
	public void setAlto(int alto)
	{
		this.alto = alto;
		if(this.alto <= 0)
		{
			throw new RuntimeException("La fuga es muy chica " + this.alto);
		}
	}
	public void setDuracion(int duracion)
	{
		this.duracion = duracion;
		if(this.duracion <= 0)
		{
			throw new RuntimeException("La duración debe ser mayor a 0. " + duracion);
		}
	}
	
	public void dibujarse(Entorno entorno)
	{
		//entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.green);
		entorno.dibujarImagen(this.textura, this.x, this.y, 0);
//		entorno.dibujarCirculo(this.x,this.y,5,Color.black); /*centro*/
		cambiarPosicion(entorno);
	}
	
	private void cambiarPosicion(Entorno entorno) 
	{
		Random random = new Random();
		if(this.duracion <= 0)
		{
			/*defino si la siguiente fuga aparecera del lado derecho o izquierdo*/
			int x = random.nextInt(2);
			if(x == 0)
			{
				this.x = 0;
			}
			else
			{
				this.x = entorno.ancho();
			}
			
			/*ahora a que altura*/
			int y = random.nextInt(entorno.alto()-100);
			this. y = y;
			
			/*ahora cuando va a durar*/
			int d = random.nextInt(500);
			if(d > 100)
			{
				this.duracion = d;
			}
			else
			{
				this.duracion = 10;
			}
			
		}
		else
		{
			this.duracion--;
		}

	}
}
