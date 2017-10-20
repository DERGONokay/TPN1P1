package juego;

import entorno.Entorno;

public class Hud 
{
	private int vidas;
	private int tiempo;
	private int puntos;
	
	Hud(int vidas, int tiempo, int puntos)
	{
		this.setVidas(vidas);
		this.setTiempo(tiempo);
		this.setPuntos(puntos);
	}

	public int getPuntos() 
	{
		return this.puntos;
	}

	public void setPuntos(int puntos) 
	{
		this.puntos = puntos;
	}

	public int getTiempo() 
	{
		return this.tiempo;
	}

	public void setTiempo(int tiempo) 
	{
		this.tiempo = tiempo;
	}

	public int getVidas() 
	{
		return this.vidas;
	}

	public void setVidas(int vidas) 
	{
		this.vidas = vidas;
	}
	
	public void dibujarse(Entorno entorno,int vidas, int tiempo, int puntos)
	{
		entorno.escribirTexto("VIDAS: " + vidas, 40, 30);
		entorno.escribirTexto("TIEMPO: " + tiempo,  entorno.ancho()/2-40, 30);
		entorno.escribirTexto("PUNTOS: " + puntos, entorno.ancho()-120, 30);
	}
	
	
}
