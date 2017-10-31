package juego;


import javax.sound.sampled.*;
import java.awt.*;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;

	// Variables y métodos propios de cada grupo
	
	/*Juego*/
	Clip cortina;       /*Cancion de fondo del juego*/
	boolean play;	    /*Control del momento de reproducir la musica*/
	Mapa mapa;			/*Mapa de juego*/
	Donkey donkey;		/*Mono*/
	Agente agente;		/*Personaje del usuario*/
	Barril[] barriles;	/*Barriles que se lanzaran*/
	int tiempo;			/*Tiempo límite de juego*/
	int puntos;			/*Puntaje del usuario*/
	int tick;			/*Contador de ticks*/
	boolean jugando;	/*Define si el usuario está jugando*/
	boolean victoria;	/*Define si el usuario ganó o peridó*/
	
	/*Pantalla de puntuacion*/
	PantallaDePuntuacion pantallaDePuntuacion;	/*Pantalla final de puntuación*/
	
	
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Donkey - Grupo Apellido1 - Apellido2 -Apellido3 - V0.01", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		cortina = Herramientas.cargarSonido("cortina.aiff");
		cortina.loop(9);
		play = true;
		mapa = new Mapa();
		donkey = new Donkey();
		agente = new Agente();
		barriles = new Barril[20];
		inicializarBarriles();
		tiempo = 180;
		tick = 0;
		jugando = true; // cambiar cuando se haga la pantalla de inicio
		victoria = false; // determina si el jnugador ganó o perdió
		

		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{

		/*Cuento los ticks y los segundos de juego*/
		/*Inicio la pantalla inicial del juego*/
		//iniciarMenu();
		
		/*Inicio el juego*/
		if(jugando && tiempo >= 0 && agente.getVidas() > 0)
		{
			jugar();
		}
		/*Muestro la pantalla final*/
		else
		{
			mostratPuntuacionFinal(tiempo, puntos, agente.getVidas());	
		}
	}
	private void mostratPuntuacionFinal(int tiempo, int puntos, int vidas)
	{
		jugando = false;
		if(pantallaDePuntuacion == null)
		{
			pantallaDePuntuacion = new PantallaDePuntuacion(this.puntos, this.tiempo, agente.getVidas());
		}
		pantallaDePuntuacion.dibujarse(entorno, victoria, mapa);
	}
	private void jugar()
	{
		jugando = true;
		/*Cuento el tiempo y sumo puntos*/
		contarTiempo();
		sumarPuntos();
				
		/* Dibujo el mapa, el agente y el HUD */
		dibujarCosas();
		
		/* Lanzar barriles */
		lanzarBarril();
		
		/*chequeo si el ya ganó*/
		chequearVictoria();
		
		/* Chequeo si el agente toca algun barril o la fuga*/
		chequearColision();
		
		/* Movimiento de los barriles */
		moverBarriles();
		
		/* movimiento del personaje -----------------------------------------------------------------------*/
		/* Moverse a la derecha */
		if(entorno.estaPresionada(entorno.TECLA_DERECHA))
		{
			moverDerecha();
		}
		/* Moverse a la izquierda */
		if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA))
		{
			moverIzquierda();
		}
		/* Subir escalera */
		if(entorno.estaPresionada(entorno.TECLA_ARRIBA))
		{
			subir();
		}
		/* Bajar escalera */
		if(entorno.estaPresionada(entorno.TECLA_ABAJO))
		{
			bajar();
		}
		/*Salto: 
		 * Si está saltando no puede volver a saltar
		 * Si está tocando una escalera no puede satar*/
		if(entorno.sePresiono(entorno.TECLA_ESPACIO) && !agente.getSalto())
		{
			saltar();
		}
		/*--------------------------------------------------------------------------------------------------*/
		
		/* Le aplico gravedad al personaje*/
		aplicarGravedad();
		
		/*Chequeo si el barril llega al final del mapa*/
		eliminarBarril();
		}
	private void moverBarriles()
	{
		for(int i = 0 ; i < barriles.length ; i++)
		{
			if(barriles[i].lanzado())
			{
				barriles[i].moverse(mapa.getVigas());
			}
		}
	}
	private void eliminarBarril()
	{
		for(int i = 0 ; i < barriles.length ; i++)
		{
			if(barriles[i] != null)
			{
				if(barriles[i].getY() > 500 && barriles[i].getX() < 0)
				{
					barriles[i] = null;
					barriles[i] = new Barril();
				}
			}
		}
	}
	private void chequearVictoria()
	{
		if(agente.llegoAlFinal(donkey))
		{
			victoria = true;
			jugando = false;
		}
	}
	private void chequearColision()
	{
		for(int i = 0 ; i < barriles.length ; i++)
		{
			if(barriles[i] != null && barriles[i].lanzado())
			{
				if (agente.colisionaCon(barriles[i].getX(), barriles[i].getY(),
						barriles[i].getTam(), barriles[i].getTam()))
				{
					restarPuntos();
					agente.restarVida();
					agente.devolverAlInicio();
				}
			}
		}
		if(agente.colisionaCon(mapa.getFugas().getX(), mapa.getFugas().getY(), mapa.getFugas().getAncho(), mapa.getFugas().getAlto()))
		{
			restarPuntos();
			agente.restarVida();
			agente.devolverAlInicio();
		}
	}
	private void contarTiempo()
	{
		/* Cuento los ticks*/
		tick++;
		/* Cuento los segundos en funcion de los ticks*/
		if(tick % 100 == 0)
		{
			tiempo--;
		}
	}
	private void sumarPuntos()
	{
		/* +1 punto cada 50 ticks */
		if(tick % 50 == 0)
		{
			puntos++;
		}
		/* +10 puntos por cada tick que salta un barril */
		if(agente.saltoBarril(barriles))
		{
			puntos += 10;
		}
	}
	private void restarPuntos()
	{
		if(puntos >= 50)
			puntos -= 50;
		else
			puntos -= puntos;
	}
	private void dibujarBarriles()
	{
		for(int i = 0 ; i < barriles.length ; i++)
		{
//			System.out.println("Barril=" + i + " Lanzado=" + barriles[i].lanzado());
			if(barriles[i] != null && barriles[i].lanzado())
			{
//				System.out.println("Dibujando barril : " + i);
				barriles[i].dibujarse(entorno);
			}
		}
	}
	private void dibujarCosas()
	{
		mapa.dibujarse(entorno);
		dibujarBarriles();
		donkey.mostrar(entorno);
		if(agente != null)
		{
			agente.dibujarse(entorno, mapa.getEscaleras());
		}
		Hud.dibujarse(entorno,agente.getVidas(),tiempo, puntos);
	}
	private void moverDerecha()
	{
		agente.moverDerecha();
		if(!Agente.posValida(agente,entorno,mapa.getVigas(),mapa.getEscaleras()))
		{
			agente.moverIzquierda();
		}
	}
	private void moverIzquierda()
	{
		agente.moverIzquierda();
		if(!Agente.posValida(agente, entorno, mapa.getVigas(), mapa.getEscaleras()))
		{
			agente.moverDerecha();
		}
	}
	private void subir()
	{
		for(int i = 0 ; i < mapa.getEscaleras().length ; i++)//verifico que esté tocando alguna escalera
		{
			if(mapa.getEscaleras()[i].laToca(agente))
			{
				agente.subir();
			}
		}
		if(!Agente.posValida(agente, entorno, mapa.getVigas(), mapa.getEscaleras()))
		{
			agente.bajar();
		}
	}
	private void bajar()
	{
		agente.bajar();
		if(!Agente.posValida(agente, entorno, mapa.getVigas(), mapa.getEscaleras()))
		{
			agente.subir();
		}
	}
	private void saltar()
	{
		/* flag */
		boolean saltar = false;
		
		for(int i = 0 ; i < mapa.getVigas().length ; i++)//verifico que esté sobre una viga
		{
			if(mapa.getVigas()[i].laToca(agente))
			{
				saltar = true;
			}
		}
		for(int i = 0 ; i < mapa.getEscaleras().length ; i++)//Verifico que no este subiendo/bajando escaleras para que pueda saltar
		{
			if(mapa.getEscaleras()[i].seSuperpone(agente))
			{
				saltar = false;
			}
		}
		
		if(saltar)
		{
//			Herramientas.play(sonidoSalto);
			agente.saltar();
		}
	}
	private void aplicarGravedad()
	{
		agente.gravedad(mapa.getVigas(), mapa.getEscaleras());		
	}
	private void lanzarBarril()
	{
		for(int i = 0 ; i < barriles.length ; i++)
		{
			if(!barriles[i].lanzado())
			{
				donkey.lanzarBarril(barriles[i]);
				break;
			}
		}
	}
	private void inicializarBarriles()
	{
		for(int i = 0 ; i < barriles.length ; i++)
		{
			barriles[i] = new Barril();
		}
	}
	
	
	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
