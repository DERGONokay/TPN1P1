package juego;

import entorno.*;
import java.awt.*;

public class Agente 
{
	private char direccion;
	private boolean corriendo;
	private Point posicion;
	private int altura;
	private int ancho;
	private int velocidadMovimiento;
	private boolean saltando;
	private int potenciaSalto;
	private int alturaSalto;
	private int vidas;
	private Image textura;
	private int intercalado;
	private boolean elegirTextura;
	private boolean pasoIntermedio;
	private boolean subiendo;
	
	public Agente()  // Constructor de un PJ unico
	{
		this.direccion = 'd';
		corriendo = false;
		posicion = new Point (50, 550);
		this.altura = 30;
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
	public void dibujarse(Entorno entorno, Escalera[] escaleras)  // Dibuja al PJ
	{
		//entorno.dibujarRectangulo(posicion.x, posicion.y-(altura/2), this.ancho, this.altura, 0, Color.white);
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
		intercalarTextura();
			
	}	
	public void moverDerecha()
	{
		this.corriendo = true;
		this.direccion = 'd';
		this.posicion.x += this.velocidadMovimiento;
	}
	public void moverIzquierda()
	{
		this.corriendo = true;
		this.direccion = 'i';
		this.posicion.x -= this.velocidadMovimiento;
	}
	public void subir()
	{
		this.corriendo = false;
		this.subiendo = true;
		this.direccion = 'a';
		this.posicion.y -= this.velocidadMovimiento;
	}
	public void bajar()
	{
		direccion = 'a';
		this.corriendo = false;
		subiendo = true;
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
		Point[] puntosDelAgente = generarVertices(this.getX(), this.getY(), this.ancho, this.altura);
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
			if(estaDentro(puntosDelObjeto[i],this.getX(),this.getY(),this.ancho,this.altura))
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
			entorno.dibujarImagen(this.textura, this.posicion.x, this.posicion.y-this.altura/2, 0);
		}
		else
		{
			this.setTextura("Agente2.png");
			entorno.dibujarImagen(this.textura, this.posicion.x, this.posicion.y-this.altura/2, 0);
		}
	}
	private void dibujarseCorriendo(Entorno entorno)
	{
		if(direccion == 'd')
		{
			Image pasoIntermedio = Herramientas.cargarImagen("Correr2.png");
			if(!this.elegirTextura)
				this.setTextura("Correr1.png");
			else
				this.setTextura("Correr3.png");
			if(this.pasoIntermedio)
			{
				entorno.dibujarImagen(pasoIntermedio, this.posicion.x, this.posicion.y-this.altura/2, 0);
				this.pasoIntermedio = false;
			}
			else
			{
				entorno.dibujarImagen(this.textura, this.posicion.x, this.posicion.y-this.altura/2, 0);
			}
			
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
				entorno.dibujarImagen(pasoIntermedio, this.posicion.x, this.posicion.y-this.altura/2, 0);
				this.pasoIntermedio = false;
			}
			else
			{
				entorno.dibujarImagen(this.textura, this.posicion.x, this.posicion.y-this.altura/2, 0);
			}
			
			this.corriendo = false;
		}
	}
	private void dibujarseSubiendo(Entorno entorno, Escalera[] escaleras)
	{
		boolean toca = false;
		
		
		for(int i = 0; i < escaleras.length ; i++)
		{
			if(escaleras[i].laToca(this))
			{
				toca = true;
				break;
			}
		}
		
		if(toca)
		{
			this.setTextura("SubirEscalera1.png");
		}
			
		entorno.dibujarImagen(this.textura, this.posicion.x, this.posicion.y-this.altura/2, 0);
		
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
