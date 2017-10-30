package juego;

import java.awt.Color;

import entorno.Entorno;

public class Hud 
{
	public static void dibujarse(Entorno entorno,int vidas, int tiempo, int puntos)
	{
		entorno.cambiarFont("asd", 15, Color.black);
		entorno.escribirTexto("VIDAS: " + vidas, 40, 30);
		entorno.escribirTexto("TIEMPO: " + tiempo,  entorno.ancho()/2-40, 30);
		entorno.escribirTexto("PUNTOS: " + puntos, entorno.ancho()-120, 30);
	}
	
	
}
