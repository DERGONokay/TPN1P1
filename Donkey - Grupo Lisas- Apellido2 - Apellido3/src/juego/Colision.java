package juego;

import java.awt.Color;
import java.awt.Rectangle;

import entorno.Entorno;

public class Colision 
{
	public static boolean hayColision(int x1, int y1, int ancho1, int alto1, int x2, int y2, int ancho2, int alto2)
	{
		/* flag */
		boolean hayColision = false;
		
		/*Creo los rectangulos para ver si se intersecan (x,y,ancho,alto)*/
		Rectangle r1 = new Rectangle(x1,y1,ancho1,alto1);
		Rectangle r2 = new Rectangle(x2,y2-alto2/2,ancho2,alto2);
		
//		entorno.dibujarRectangulo(x1, y1, ancho1, alto1, 0, Color.pink);
//		entorno.dibujarRectangulo(x2, y2-alto2/2, ancho2, alto2, 0, Color.blue);
			
		if(r1.intersects(r2))
		{
			hayColision = true;
		}
		
		/* Devuelvo el valor encontrado */
		return hayColision;
	}
}
