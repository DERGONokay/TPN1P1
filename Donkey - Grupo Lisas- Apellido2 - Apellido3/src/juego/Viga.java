package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Viga 
{
	private int x;
	private int y;
	private int ancho;
	private int alto;
	private Image textura;
	
	Viga(int x, int y, int an, int al)
	{
		this.x=x;
		this.y=y;
		this.alto=al;
		this.ancho=an;
		this.textura = Herramientas.cargarImagen("viga.png");
	}
	public int getX()
	{
		return this.x;
	}
	public void setX(int x)
	{
		this.x=x;
	}
	
	public int getY()
	{
		return this.y;
	}
	public void setY(int y)
	{
		this.y=y;
	}
	
	public int getAncho()
	{
		return this.ancho;
	}
	public void setAncho(int an)
	{
		this.ancho=an;
	}
	
	public int getAlto()
	{
		return this.alto;
	}
	public void setAlto(int al)
	{
		this.alto=al;
	}

	
	public void dibujarse(Entorno entorno) 
	{
//		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, -Math.PI/2, Color.red);
		/*Si es una viga que empieza a la derecha corro su textura 100 a la izquierda*/
		if(this.x == 0)
		{
			entorno.dibujarImagen(this.textura, this.x-100, this.y, 0);
		}
		/*Si es una viga que empieza a la izquierda corro su textura 100 a la derecha*/
		else
		{
			entorno.dibujarImagen(this.textura, this.x+100, this.y, 0);
		}
	}
	
	
	public boolean laToca(Agente agente)
	{
		int baseAgente = agente.getY();
		
		if(baseAgente <= this.y+(this.ancho/2) && baseAgente >= this.y-(this.ancho/2) &&     /*base del agente menor o igual a el centro de la viga +- la mitad de su altura*/
		   agente.getX() <= this.x+this.alto/2 && agente.getX() >= this.x-this.alto/2 )
		{
//			System.out.println("Toca viga");
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean seSuperpone(Agente agente)
	{
		int baseAgente = agente.getY();
		
		if(baseAgente <= this.y+(this.ancho/2) && baseAgente > this.y-(this.ancho/2) &&     /*base del agente menor o igual a el centro de la viga +- la mitad de su altura*/
				   agente.getX() < this.x+this.alto/2 && agente.getX() > this.x-this.alto/2)
				{
//					System.out.println("Toca viga");
					return true;
				}
				else
				{
					return false;
				}
	}

}
