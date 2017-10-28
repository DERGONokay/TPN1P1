package juego;

import java.awt.*;
import entorno.Entorno;
import entorno.Herramientas;

public class Mapa 
{
	private Viga[] vigas;
	private Escalera[] escaleras;
	private Fuga fuga;
	private Image fondo;

	
	Mapa()
	{
		fondo = Herramientas.cargarImagen("Fondo.png");
		
		vigas=new Viga[6];
		escaleras = new Escalera[9];
		
		/*Fuga*/
		fuga = new Fuga(0,460,100,20,30);
		
		/*Escaleras enteras */
		escaleras[0] = new Escalera(715,600-65,100,20,false);
		escaleras[1] = new Escalera(85,500-65,100,20,false);
		escaleras[2] = new Escalera(715,400-65,100,20,false);
		escaleras[3] = new Escalera(85,300-65,100,20,false);
		escaleras[4] = new Escalera(715,200-65,100,20,false);
		/*Escaleras "rotas"*/
		escaleras[5] = new Escalera(350,300-35,40,20,true);
		escaleras[5].setTextura("EscaleraRota2.png");
		
		escaleras[6] = new Escalera(350,300-98,35,20,true);
		escaleras[6].setTextura("EscaleraRota3.png");
		
		escaleras[7] = new Escalera(550,200-95,40,20,true);
		escaleras[7].setTextura("EscaleraRota1.png");
		
		escaleras[8] = new Escalera(400,500-35,40,20,true);
		escaleras[8].setTextura("EscaleraRota2.png");
		
		/*Vigas*/
		vigas[0]=new Viga(400, 600, 30, 1450);
		vigas[1]=new Viga(0, 500, 30, 1450);
		vigas[2]=new Viga(800, 400, 30, 1450);
		vigas[3]=new Viga(0, 300, 30, 1450);
		vigas[4]=new Viga(800, 200, 30, 1450);
		vigas[5]=new Viga(0, 100, 30, 1450);
		

	}
	
	void dibujarse(Entorno entorno)
	{
		entorno.dibujarImagen(fondo, entorno.ancho()/2, entorno.alto()/2, 0);//dibujo el fondo
		
		fuga.dibujarse(entorno);
		
		
		for (int i=0;i<vigas.length;i++)
		{
			vigas[i].dibujarse(entorno);
		}
		
		for(int j = 0 ; j < escaleras.length ; j++)
		{
			escaleras[j].dibujarse(entorno);
		}
	}
	
	Viga[] getVigas()
	{
		return this.vigas;
	}
	Escalera[] getEscaleras()
	{
		return this.escaleras;
	}
	Fuga getFugas()
	{
		return this.fuga;
	}

}
