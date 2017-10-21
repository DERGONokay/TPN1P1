package juego;

import java.awt.Rectangle;

public class Colision 
{
	public static boolean hayColision(int x1, int y1, int ancho1, int alto1, int x2, int y2, int ancho2, int alto2)
	{
		/* flag */
		boolean hayColision = false;
		
		/*Creo los rectangulos para ver si se intersecan (x,y,ancho,alto)*/
		Rectangle r1 = new Rectangle(x1-ancho1/2,y1+alto1/2,ancho1,alto1);
		Rectangle r2 = new Rectangle(x2-ancho2/2,y2+alto2/2,ancho2,alto2);
			
		if(r1.intersects(r2))
		{
			hayColision = true;
		}
		
		/* Devuelvo el valor encontrado */
		return hayColision;
	}
}
