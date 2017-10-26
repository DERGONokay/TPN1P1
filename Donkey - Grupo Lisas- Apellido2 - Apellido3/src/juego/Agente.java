package juego;

import entorno.*;
import java.awt.*;

public class Agente 
{
	private Point posicion;			/*Posicion x e y del centro de la hit box del personaje*/
	private int alto;				/*Alto de la hit box del personaje*/
	private int ancho;				/*Ancho de la hit box del personje*/
	private char direccion;			/*Define la direccion a la que se está moviendo el personaje*/
	private boolean corriendo;		/*Define si el personaje esta corriendo*/
	private int velocidadMovimiento;/*Velocidad de movimiento del personaje*/
	private boolean saltando;		/*Define si el personaje está saltando*/
	private int potenciaSalto;		/*Cuántos pixeles sube el personaje por tick*/
	private int alturaSalto;		/*Durante cuantos ticks va a saltar el personaje*/
	private int vidas;				/*Cantidad de vidas del personaje*/
	private Image textura;			/*Textura del personaje*/
	private int intercalado;		/*Contador que se usa para modificar la variable "elegirTextura"*/
	private boolean elegirTextura;  /*Ayuda a intercalar las imagenes para crear "animaciones" */
	private boolean pasoIntermedio; /*define si el personaje debe usar la imagen de "paso intermedio"*/
	private boolean subiendo;       /*define si el personaje esta subiendo una escalera*/
	
	public Agente()  // Constructor de un PJ unico
	{
		this.direccion = 'd';
		corriendo = false;
		posicion = new Point (50, 550);
		this.alto = 30;
		this.ancho = 15;
		this.velocidadMovimiento = 1;
		this.saltando = false;
		this.potenciaSalto = 3;
		this.alturaSalto = 0;
		this.vidas = 3;
		this.textura = Herramientas.cargarImagen("Agente.png");
		this.intercalado = 10;
		this.elegirTextura = false;
		this.subiendo = false;
	}
	
	public int getX()
	{
		return this.posicion.x;
	}
	public int getY()
	{
		return this.posicion.y;
	}	
	public int getAlto()
	{
		return this.alto;
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
	public void dibujarse(Entorno entorno, Escalera[] escaleras)  // Dibuja al PJ
	{
		//entorno.dibujarRectangulo(posicion.x, posicion.y-(altura/2), this.ancho, this.altura, 0, Color.white);
		
		/* Dibujo al personaje dependiendo de que accion está realizando */
		if(!corriendo && !subiendo)
		{
			dibujarseQuieto(entorno);	
		}
		else if(subiendo)
		{
			dibujarseSubiendo(entorno, escaleras);
		}
		else if(corriendo)
		{
			dibujarseCorriendo(entorno);
		}
		
		/*Intercalo la textura para crear "animaciones" */
		intercalarTextura();
			
	}	
	public void moverDerecha()
	{
		this.direccion = 'd';
		this.corriendo = true;
		this.posicion.x += this.velocidadMovimiento;
	}
	public void moverIzquierda()
	{
		this.direccion = 'i';
		this.corriendo = true;
		this.posicion.x -= this.velocidadMovimiento;
	}
	public void subir()
	{
		this.direccion = 'a';
		this.corriendo = false;
		this.subiendo = true;
		this.posicion.y -= this.velocidadMovimiento;
	}
	public void bajar()
	{
		this.direccion = 'a';
		this.corriendo = false;
		this.subiendo = true;
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
			this.corriendo = false;
			this.alturaSalto = 20; /* cantidad de ticks que va a saltar */
		}
	}
	public void restarVida() 
	{
		this.vidas--;
	}
	public void sumarVida()
	{
		this.vidas++;
	}
	public void devolverAlInicio() 
	{
		this.posicion.x = 50;
		this.posicion.y = 550;
		this.alturaSalto = 0;
				
	}
	public boolean colisionaCon(int x, int y, int ancho, int alto)
	{
		Point[] puntosDelAgente = generarVertices(this.getX(), this.getY(), this.ancho, this.alto);
		Point[] puntosDelObjeto = generarVertices(x, y, ancho, alto);
		
		boolean hayColision = false;
		
		for(int i = 0 ; i < puntosDelAgente.length ; i++)
		{
			if(estaDentro(puntosDelAgente[i],x,y,ancho,alto))
			{
				hayColision = true;
				break;
			}
		}
		for(int i = 0 ; i < puntosDelObjeto.length ; i++)
		{
			if(estaDentro(puntosDelObjeto[i],this.getX(),this.getY(),this.ancho,this.alto))
			{
				hayColision = true;
				break;
			}
		}
		
		if(hayColision)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	private void intercalarTextura()
	{
		if(this.intercalado <= 0)
		{
			if(!pasoIntermedio)
			{
				this.pasoIntermedio = true;
			}
			if(!this.elegirTextura)
			{
				this.elegirTextura = true;
			}
			else 
			{
				this.elegirTextura = false;
			}
			this.intercalado = 10;
		}
		else
		{
			this.intercalado--;
		}
	}
	private void dibujarseQuieto(Entorno entorno)
	{
		if(direccion == 'd')
		{
			this.setTextura("Agente.png");
			entorno.dibujarImagen(this.textura, this.posicion.x, this.posicion.y-this.alto/2, 0);
		}
		else
		{
			this.setTextura("Agente2.png");
			entorno.dibujarImagen(this.textura, this.posicion.x, this.posicion.y-this.alto/2, 0);
		}
	}
	private void dibujarseCorriendo(Entorno entorno)
	{
		/*Si esta corriendo hacia la derecha: */
		if(direccion == 'd')
		{
			
			Image pasoIntermedio = Herramientas.cargarImagen("Correr2.png");//cargo la imagen del paso intermedio. Esta imagen se usa para dar una mejor sensacion de que el personaje esta corriendo
			
			/*en estas condiciones se decide cual de las 2 imagenes de la "animacion" se va a usar*/
			if(!this.elegirTextura)
			{
				this.setTextura("Correr1.png");
			}
			else
			{
				this.setTextura("Correr3.png");
			}
			
			/*Si tiene que hacer el paso intermedio de la animacion lo ejecuta, sino dibuja la imagen seleccionada anteriormente*/
			if(this.pasoIntermedio)
			{
				entorno.dibujarImagen(pasoIntermedio, this.posicion.x, this.posicion.y-this.alto/2, 0);
				this.pasoIntermedio = false;
			}
			else
			{
				entorno.dibujarImagen(this.textura, this.posicion.x, this.posicion.y-this.alto/2, 0);
			}
			
			/*reinicio la variable*/
			this.corriendo = false;
		}
		else
		{
			Image pasoIntermedio = Herramientas.cargarImagen("Correr5.png");
			if(!this.elegirTextura)
				this.setTextura("Correr4.png");
			else
				this.setTextura("Correr6.png");
			if(this.pasoIntermedio)
			{
				entorno.dibujarImagen(pasoIntermedio, this.posicion.x, this.posicion.y-this.alto/2, 0);
				this.pasoIntermedio = false;
			}
			else
			{
				entorno.dibujarImagen(this.textura, this.posicion.x, this.posicion.y-this.alto/2, 0);
			}
			
			this.corriendo = false;
		}
	}
	private void dibujarseSubiendo(Entorno entorno, Escalera[] escaleras)
	{
		/*flag*/
		boolean toca = false;
		
		for(int i = 0; i < escaleras.length ; i++)//verifico si esta tocando escalera
		{
			if(escaleras[i].laToca(this))
			{
				toca = true;
				break;
			}
		}
		
		if(toca)
		{
			/*estas condiciones son para hacer la "animacion" de subir la escalera*/
			if(elegirTextura)
			{
				this.setTextura("SubirEscalera1.png");
			}
			else
			{
				this.setTextura("SubirEscalera2.png");
			}
		}
		
		/* dibujo al personaje con la textura definida anteriormente*/
		entorno.dibujarImagen(this.textura, this.posicion.x, this.posicion.y-this.alto/2, 0);
		
		/* reinicio la variable */
		this.subiendo = false;
	}
	private void setTextura(String textura)
	{
		this.textura = Herramientas.cargarImagen(textura);
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
		if(agente.getY()+agente.getAlto()/2 > entorno.alto() || agente.getY()-agente.getAlto()/2 < 0)
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
	private static boolean estaDentro(Point p, int x, int y, int ancho, int alto)
	{
		if(p.x >= x-ancho/2 && p.x <= x+ancho/2
				&& p.y >= y-alto/2 && p.y <= y+alto/2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	private static Point[] generarVertices(int x, int y, int ancho, int alto)
	{
		Point[] retorno = new Point[4];
		
		retorno[0] = new Point(x-ancho/2, y-alto/2);
		retorno[1] = new Point(x-ancho/2, y+alto/2);
		retorno[2] = new Point(x+ancho/2, y-alto/2);
		retorno[3] = new Point(x+ancho/2, y+alto/2);
		
		return retorno;
	}
}
