package juego;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.util.Random;

import entorno.Entorno;

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
		entorno.dibujarRectangulo(this.x, this.y-this.alto, this.ancho, this.alto, 0, Color.green);
		cambiarPosicion(entorno);
	}
	
	private void cambiarPosicion(Entorno entorno) 
	{
		Random random = new Random();
		if(this.duracion <= 0)
		{
			int x = random.nextInt(2);
			if(x == 0)
			{
				this.x = 0;
			}
			else
			{
				this.x = entorno.ancho();
			}
			
			int y = random.nextInt(entorno.alto()-100);
			this. y = y;
			
			int d = random.nextInt(200);
			this.duracion = d;
		}
		else
		{
			this.duracion--;
		}

	}
	/*Verifica si:
	 * El personaje toca la fuga o la fuga toca al personaje*/
	public boolean laToca(Agente agente)
	{
		return false;
//		/* flag */
//		boolean seTocan = false;
//		
//		/* esquinas para verificar si se tocan */
//		/*esquinas del agente */
//		Point agenteSupIzq = new Point(agente.getX()-agente.getAncho()/2,agente.getY()-agente.getAltura()/2);
//		Point agenteSupDer = new Point(agente.getX()+agente.getAncho()/2,agente.getY()-agente.getAltura()/2);
//		Point agenteInfIzq = new Point(agente.getX()-agente.getAncho()/2,agente.getY()+agente.getAltura()/2);
//		Point agenteInfDer = new Point(agente.getX()+agente.getAncho()/2,agente.getY()+agente.getAltura()/2);
//		/*esquinas de la fuga*/
//		Point fugaSupIzq = new Point(this.x-this.ancho/2,this.y-this.alto/2);
//		Point fugaSupDer = new Point(this.x+this.ancho/2,this.y-this.alto/2);
//		Point fugaInfIzq = new Point(this.x-this.ancho/2,this.y+this.alto/2);
//		Point fugaInfDer = new Point(this.x+this.ancho/2,this.y+this.alto/2);
//		
//		/* Primero verifico si el personaje toca a la fuga */
//		if(agenteSupIzq.y <= this.y+this.alto/2 && agenteSupIzq.y >= this.y-this.alto/2 &&
//				agenteSupIzq.x <= this.x+this.ancho/2 && agenteSupIzq.x >= this.x-this.ancho/2)
//		{
//			seTocan = true;
//		}
//		if(agenteSupDer.y <= this.y+this.alto/2 && agenteSupDer.y >= this.y-this.alto/2 &&
//				agenteSupDer.x <= this.x+this.ancho/2 && agenteSupDer.x >= this.x-this.ancho/2)
//		{
//			seTocan = true;
//		}
//		if(agenteInfIzq.y <= this.y+this.alto/2 && agenteInfIzq.y >= this.y-this.alto/2 &&
//				agenteInfIzq.x <= this.x+this.ancho/2 && agenteInfIzq.x >= this.x-this.ancho/2)
//		{
//			seTocan = true;
//		}
//		if(agenteInfDer.y <= this.y+this.alto/2 && agenteInfDer.y >= this.y-this.alto/2 &&
//				agenteInfDer.x <= this.x+this.ancho/2 && agenteInfDer.x >= this.x-this.ancho/2)
//		{
//			seTocan = true;
//		}
//		
//		/* Ahora verifico si la fuga toca al personaje si no se corroboró que el personaje toca a la fuga */	
//		if(fugaSupIzq.y <= agente.getY()+agente.getAltura()/2 && fugaSupIzq.y >= agente.getY()-agente.getAltura()/2 &&
//				fugaSupIzq.x <= agente.getX()+agente.getAncho()/2 && fugaSupIzq.x >= agente.getX()-agente.getAncho()/2)
//		{
//			seTocan = true;
//		}
//		if(fugaSupDer.y <= agente.getY()+agente.getAltura()/2 && fugaSupDer.y >= agente.getY()-agente.getAltura()/2 &&
//				fugaSupDer.x <= agente.getX()+agente.getAncho()/2 && fugaSupDer.x >= agente.getX()-agente.getAncho()/2)
//		{
//			seTocan = true;
//		}
//		if(fugaInfIzq.y <= agente.getY()+agente.getAltura()/2 && fugaInfIzq.y >= agente.getY()-agente.getAltura()/2 &&
//				fugaInfIzq.x <= agente.getX()+agente.getAncho()/2 && fugaInfIzq.x >= agente.getX()-agente.getAncho()/2)
//		{
//			seTocan = true;
//		}
//		if(fugaInfDer.y <= agente.getY()+agente.getAltura()/2 && fugaInfDer.y >= agente.getY()-agente.getAltura()/2 &&
//			fugaInfDer.x <= agente.getX()+agente.getAncho()/2 && fugaInfDer.x >= agente.getX()-agente.getAncho()/2)
//		{
//			seTocan = true;
//		}
//			
//		
//		
//		/* Devuelvo el valor encontrado */
//		return seTocan;
	}
}
