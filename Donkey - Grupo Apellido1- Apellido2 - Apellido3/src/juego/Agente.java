package juego;

import entorno.*;
import java.awt.*;

public class Agente 
{
	private Point posicion;
	private int altura;
	private int ancho;
	private int velocidadMovimiento;
	private boolean saltando;
	private int potenciaSalto;
	private int alturaSalto;
	private int vidas;
	
	public Agente()  // Constructor de un PJ unico
	{
		posicion = new Point (50, 550);
		this.altura = 30;
		this.ancho = 15;
		this.velocidadMovimiento = 1;
		this.saltando = false;
		this.potenciaSalto = 2;
		this.alturaSalto = 0;
		this.vidas = 3;
	}
	
	public int getX()
	{
		return this.posicion.x;
	}
	
	public int getY()
	{
		return this.posicion.y;
	}
	
	public int getAltura()
	{
		return this.altura;
	}
	
	public int getAncho()
	{
		return this.ancho;
	}
	public boolean getSalto()
	{
		return this.saltando;
	}
	public int getVidas()
	{
		return this.vidas;
	}
	public void setVidas(int vida)
	{
		this.vidas += vidas;
	}
	
	public void dibujarse(Entorno entorno)  // Dibuja al PJ
	{
		entorno.dibujarRectangulo(posicion.x, posicion.y-(altura/2), this.ancho, this.altura, 0, Color.white);
	}
	
	public void moverDerecha()
	{
		this.posicion.x += this.velocidadMovimiento;
	}
	public void moverIzquierda()
	{
		this.posicion.x -= this.velocidadMovimiento;
	}
	public void subir()
	{
		this.posicion.y -= this.velocidadMovimiento;
	}
	public void bajar()
	{
		this.posicion.y += this.velocidadMovimiento;
	}
	
	
	/* gravedad del personaje / gravedad invertida para el salto */
	public void gravedad(Viga[] vigas, Escalera[] escaleras)
	{
		boolean tocaViga = false;
		boolean tocaEscalera = false;
		
		for(int i = 0 ; i < vigas.length ; i++)
		{
			if(vigas[i].laToca(this))
			{
				tocaViga = true;
				break;
			}
		}
		for(int k = 0 ; k < escaleras.length ; k++)
		{
			if(escaleras[k].laToca(this))
			{
				tocaEscalera = true;
				break;
			}
		}
		
		
		if(!tocaViga && !tocaEscalera)/* aplico gravedad en caso de que no este tocando escalera y no esté saltando*/
		{
			this.posicion.y++;
		}
		
		if(this.saltando && this.alturaSalto >= 0) /**/
		{
 			this.posicion.y -= this.potenciaSalto;
		}
		if(this.alturaSalto <= 0)
		{
			this.saltando = false;
			this.alturaSalto = 0;
		}
		this.alturaSalto--;
		
  	}
	
	public void velocidad(int vel)  // Seteo de velocidad posible para el movimiento del PJ
	{
		if (vel >= 1 && vel <=10)
			this.velocidadMovimiento = vel;
		else
			throw new RuntimeException(" La velocidad tiene que estar dentro del intevalo [1, 10]");
	}

	public void saltar() 
	{
		if(!saltando)
		{
			this.saltando = true;
			this.alturaSalto = 35; /* cantidad de ticks que va a saltar */
		}
	}
	
	
	public static boolean posValida(Agente agente, Entorno entorno, Viga[] vigas, Escalera[] escaleras)
	{
		/* flag */
		boolean posicion = true;
		
		/* chequeo que esté dentro de los limites del entorno*/
		if(agente.getX()+agente.getAncho()/2 > entorno.ancho() || agente.getX()-agente.getAncho()/2 < 0)
		{
			posicion = false;
		}
		if(agente.getY()+agente.getAltura()/2 > entorno.alto() || agente.getY()-agente.getAltura()/2 < 0)
		{
			posicion = false;
		}
		
		/* posicion relativa a las vigas */
		for(int i = 0 ; i < vigas.length ; i++)
		{
			if(vigas[i].seSuperpone(agente))
			{
				posicion = false;
			}
		}
		
		/* corrección para que pueda subir escaleras */
		for(int i = 0 ; i < escaleras.length ; i++)
		{
			if(escaleras[i].laToca(agente))
			{
				posicion = true;
			}
		}
		
		return posicion;
	}
}
