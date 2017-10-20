package juego;

import entorno.Entorno;

public class Mapa 
{
	private Viga[] vigas;
	private Escalera[] escaleras;
	private Fuga fuga;

	
	Mapa()
	{
		vigas=new Viga[6];
		escaleras = new Escalera[9];
		
		/*Escaleras enteras*/
		escaleras[0] = new Escalera(710,600-65,100,30);
		escaleras[1] = new Escalera(135,500-65,100,30);
		escaleras[2] = new Escalera(710,400-65,100,30);
		escaleras[3] = new Escalera(135,300-65,100,30);
		escaleras[4] = new Escalera(710,200-65,100,30);
		/*Escaleras "rotas"*/
		escaleras[5] = new Escalera(350,300-35,40,30);
		escaleras[6] = new Escalera(350,300-98,35,30);
		escaleras[7] = new Escalera(550,200-95,40,30);
		escaleras[8] = new Escalera(400,500-35,40,30);
		
		/*Vigas*/
		vigas[0]=new Viga(0, 600, 30, 1650);
		vigas[1]=new Viga(0, 500, 30, 1450);
		vigas[2]=new Viga(850, 400, 30, 1450);
		vigas[3]=new Viga(0, 300, 30, 1450);
		vigas[4]=new Viga(850, 200, 30, 1450);
		vigas[5]=new Viga(0, 100, 30, 1450);
		
		/*Fuga*/
		fuga = new Fuga(0,460,100,10,30);
	}
	
	void dibujarse(Entorno entorno)
	{
		for (int i=0;i<vigas.length;i++)
		{
			vigas[i].dibujarse(entorno);
		}
		for(int j = 0 ; j < escaleras.length ; j++)
		{
			escaleras[j].dibujarse(entorno);
		}
		
		fuga.dibujarse(entorno);
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
