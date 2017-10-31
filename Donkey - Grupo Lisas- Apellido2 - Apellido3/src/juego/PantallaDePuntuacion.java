package juego;

import java.awt.Color;

import entorno.Entorno;

public class PantallaDePuntuacion 
{
	private int puntos;
	private int tiempo;
	private int vidas;
	
	PantallaDePuntuacion(int puntos, int tiempo, int vidas)
	{
		this.puntos = puntos;
		this.tiempo = tiempo;
		this.vidas = vidas;
	}
	
	//To do: getters y setters
	
	public void dibujarse(Entorno entorno, Boolean victoria, Mapa mapa)
	{
		mapa.dibujarse(entorno);
		if(victoria)
		{
			entorno.cambiarFont("arial", 20, Color.green);
			entorno.escribirTexto("¡SIMULACION FINALIZADA EXITOSAMENTE!", entorno.ancho()/2-120, 100);
		}
		else
		{
			entorno.cambiarFont("arial", 20, Color.red);
			entorno.escribirTexto("¡SIMULACION FALLIDA!", entorno.ancho()/2-120, 100);
		}
//		entorno.dibujarRectangulo(entorno.ancho()/2, entorno.alto()/2-55, 120, 30, 0, Color.white);
//		entorno.cambiarFont("arial", 12, Color.black);
//		entorno.escribirTexto("VOLVER AL MENU", entorno.ancho()/2-50, entorno.alto()/2-50);
//		
//		entorno.dibujarRectangulo(entorno.ancho()/2, entorno.alto()/2, 120, 30, 0, Color.white);
//		entorno.cambiarFont("arial", 12, Color.black);
//		entorno.escribirTexto("VOLVER A JUGAR", entorno.ancho()/2-50, entorno.alto()/2+5);
//		
		entorno.cambiarFont("arialblack", 20, Color.black);
		entorno.escribirTexto("Vidas: " + this.vidas, entorno.ancho()/4, entorno.alto()/4+30);
		
		entorno.cambiarFont("arialblack", 20, Color.black);
		entorno.escribirTexto("Tiempo: " + this.tiempo, entorno.ancho()/2-30, entorno.alto()/4+30);
		
		entorno.cambiarFont("arialblack", 20, Color.black);
		entorno.escribirTexto("Puntos: " + this.puntos, entorno.ancho()/2+160, entorno.alto()/4+30);
	}
}
