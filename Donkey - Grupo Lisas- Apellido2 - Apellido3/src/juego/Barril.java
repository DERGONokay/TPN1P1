package juego;

import java.awt.Color;

import entorno.Entorno;

public class Barril 
{
	/* Variables de instancia */
	private int x;
	private int y;
	private int tam;
	private boolean avanzando;
	
	Barril()
	{
		this.x = 0;
		this.y = 50;
		this.tam = 20;
		this.avanzando = true;
	}
	Barril(int x, int y, int tam)
	{
		this.x=x;
		this.y=y;
		this.tam= tam;
		this.avanzando=true;
	}
	
	public int getX()
	{
		return this.x;
	}
	public int getY()
	{
		return this.y;
	}
	public int getTam()
	{
		return this.tam;
	}
	public boolean getAvanzando()
	{
		return this.avanzando;
	}
	
	void dibujarse(Entorno entorno)
	{
		entorno.dibujarCirculo(this.x, this.y , this.tam, Color.green);
	}
	
	public void moverse(int ancho) 
	{
		
		if (avanzando)
			this.x+=2;
		else
			this.x-=2;
		
		if (this.x > ancho)
			avanzando = false;
		
		if (this.x < 0)
			avanzando = true;
	}
	public boolean loToca(Viga viga)
	{
		int piso = viga.getY()-(viga.getAncho()/2);
		int baseBarril = this.y+(this.tam/2);
		
		if (baseBarril >= piso && baseBarril <= viga.getY()+viga.getAncho()/2 &&
				this.x>=(viga.getX()-(viga.getAlto()/2)) && this.x<=viga.getX()+(viga.getAlto()/2))
		{
			return true;
		}
		else
		{
			return false;
		}
			
	}
	
	void gravedad(Viga[] viga)
	{
		boolean tocaViga = false;
		
		for (int i=0 ; i<viga.length ; i++)
		{
			if(this.loToca(viga[i]))
			{
				tocaViga = true;
			}
		}
		
		if(!tocaViga)
		{
			this.y+=2;
		}
			
	}
	
	boolean tocaAgente(Agente agente)
	{
		if (Colision.hayColision(this.x, this.y, this.tam, this.tam, 
			agente.getX(), agente.getY(), agente.getAncho(), agente.getAltura()))
		{
			return true;
		}
		else
			return false;
	}
	

}
