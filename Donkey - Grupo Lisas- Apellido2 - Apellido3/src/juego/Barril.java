package juego;

import java.awt.Color;
import java.awt.Point;

import entorno.Entorno;

public class Barril 
{
	/* Variables de instancia */
	private int x;
	private int y;
	private int diametro;
	private int velocidadDeMovimiento;
	private boolean avanzando;
	private boolean lanzado;
	private boolean cayendo;
	
	/*Constructores */
	Barril()
	{
		this.x = 0;
		this.y = 50;
		this.diametro = 20;
		this.avanzando = false;
		this.lanzado = false;
		this.velocidadDeMovimiento = 2;
	}
	Barril(int x, int y, int diametro)
	{
		this.x = x;
		this.y = y;
		this.diametro = diametro;
		this.avanzando = false;
		this.velocidadDeMovimiento = 2;
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
		return this.diametro;
	}
	public boolean getAvanzando()
	{
		return this.avanzando;
	}
	void dibujarse(Entorno entorno)
	{
//		entorno.dibujarRectangulo(this.x, this.y, this.diametro, this.diametro, 0, Color.red);
		entorno.dibujarCirculo(this.x, this.y , this.diametro, Color.green);
//		entorno.dibujarCirculo(this.x, this.y, 5, Color.black);
	}
	public void moverse(Viga[] vigas) 
	{
		/*Movimiento horizontal: */
		boolean tocaViga = false;
		/*Primero verifico si el barril está tocando alguna viga*/
		for (int i=0 ; i<vigas.length ; i++)
		{
			if(this.loToca(vigas[i]))
			{
				/*Si toca viga:*/
				tocaViga = true;/*activo la bandera*/
				break;
			}
		}
		if(!tocaViga)
		{
			/*Si no toca viga*/
			this.cayendo = true;/*empieza a caer*/
			this.y += velocidadDeMovimiento;
		}
		
		/*"Gravedad" del barril (movimiento vertical)*/
		if (this.avanzando && tocaViga)
		{
			this.x += velocidadDeMovimiento;
		}
		else if(tocaViga)
		{
			this.x -= velocidadDeMovimiento;
		}
		
		if (tocaViga && cayendo)
		{
			cayendo = !cayendo;
			avanzando = !avanzando;
		}
	}
	public boolean loToca(Viga viga)
	{
		Point[] verticesBarril = Agente.generarVertices(this.x, this.y, this.diametro, this.diametro);
		boolean tocaViga = false;
		
		for(int i = 0 ; i < verticesBarril.length ; i++)
		{
			if(Agente.estaDentro(verticesBarril[i], viga.getX(), viga.getY(), viga.getAlto(), viga.getAncho()))
			{
				tocaViga = true;
			}
		}
		
		return tocaViga;
	}
	public boolean lanzado() 
	{
		return this.lanzado;
	}
	public void lanzar() 
	{
		this.lanzado = true;
	}
	

}
