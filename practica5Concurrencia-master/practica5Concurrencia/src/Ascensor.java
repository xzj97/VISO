
/**
 * Clase que permite simular el movimiento de un ascensor en un edificio de 5 plantas (0 a 4).
 * Cada planta tiene dos zonas: un pasillo y una zona de espera cercana al ascensor
 * 
 * @author (J. Javier Gutierrez) 
 * @version (25-9-2017)
 */

//import fundamentos.*;
import java.util.*;
import java.util.concurrent.Semaphore;


public class Ascensor extends Thread
{
	// Ventana en la que se dibuja la simulación
	private Dibujo d;

	// Constantes para posicionamiento de las zonas y dimensiones
	private final int referenciaY=50;
	private final int pasilloX=50;
	private final int esperaX=250;
	private final int ascensorX=550;
	private final int anchoAscensor=200;
	private final int altoAscensor=100;
	private final int anchoTrazo=10;

	// Gestion de la posicion del ascensor y estado del mismo
	// El ascensor tiene un número de posiciones posibles
	private int plantaActual;
	private int posicionActual;
	private int [] listaPosicionesY;
	private int paso=altoAscensor/5; //Paso para el movimiento del ascensor
	private boolean sube;            // True si sube
	private boolean parado;          // True si esta parado
	private Semaphore boton;
	private int personas = 0;

	// Listas de personas
	private class ListaDePersonas extends ArrayList <Persona>
	{      
	}
	private ListaDePersonas [] listaPasillo;
	private ListaDePersonas [] listaEspera;
	private ArrayList<Persona> listaAscensor;

	/**
	 * Constructor del ascensor. Crea la ventana de simulación y las listas necesarias para el control
	 * de las personas
	 * Establece el estado y posicion iniciales
	 */
	public Ascensor()
	{
		// Crea la ventana para dibujar la simulacion
		d= new Dibujo("Simulación ascensor",800,600);
		d.ponColorLapiz(ColorFig.azul);
		d.ponGrosorLapiz(anchoTrazo);
		d.dibujaRectangulo(ascensorX-anchoAscensor/2,referenciaY,
				ascensorX+anchoAscensor/2,referenciaY+altoAscensor*5);
		for (int i=1;i<=5;i++)
		{
			d.dibujaLinea(pasilloX,referenciaY+altoAscensor*i-anchoTrazo*3/2,
					ascensorX-anchoAscensor/2,referenciaY+altoAscensor*i-anchoTrazo*3/2);
		}
		// Crea el array de posiciones para mover el ascensor
		listaPosicionesY=new int[21];
		for (int i=0;i<21;i++)
		{
			listaPosicionesY[i]=referenciaY+altoAscensor*4-i*paso;   
		}

		// Crea las listas de espera
		listaPasillo = new ListaDePersonas [5];
		listaEspera = new ListaDePersonas [5];
		for (int i=0;i<5;i++)
		{
			listaPasillo[i]=new ListaDePersonas();
			listaEspera[i]=new ListaDePersonas();
		}
		listaAscensor = new ArrayList<Persona>();

		// Establece la posicion y estados iniciales y muestra el dibujo
		plantaActual=0;
		posicionActual=0;
		sube=true;
		parado=true;
		dibujaAscensor();
		d.pinta();
		boton = new Semaphore(0);
	}

	/**
	 * Observador que devuelve la planta actual en la que se encuentra el ascensor
	 * 
	 * @return     entero con la planta actual 
	 */
	public int plantaActual ()
	{
		return plantaActual;
	}

	/**
	 * Observador que devuelve el estado de parado 
	 * 
	 * @return     true si esta parado 
	 */
	public boolean estaParado ()
	{
		return parado;
	}

	public synchronized void pulsaBoton(){
		boton.release();
	}
	public  synchronized void subePersona(){
		personas++;
	}
	public synchronized void atendido(){
		personas--;
	}


	/**
	 * Refresca el dibujo
	 */
	public synchronized void refresca ()
	{
		borraAscensor();
		dibujaAscensor();
		d.pinta();
	}

	/**
	 * Dibuja el ascensor y las personas que lo ocupan, y tambien las personas de las zonas
	 * de pasillo y espera
	 */
	private synchronized void dibujaAscensor()
	{
		// Dibuja el ascensor en su posicion actual
		d.ponColorLapiz(ColorFig.naranja);
		d.ponGrosorLapiz(anchoTrazo);
		d.dibujaRectangulo(ascensorX+anchoTrazo-anchoAscensor/2,
				listaPosicionesY[posicionActual]+anchoTrazo,
				ascensorX+anchoAscensor-anchoTrazo-anchoAscensor/2,
				listaPosicionesY[posicionActual]+altoAscensor-anchoTrazo);
		// Dibuja las personas que ocupan el ascensor
		for (int i=0; i<listaAscensor.size();i++)
		{
			d.ponColorLapiz(listaAscensor.get(i).color());
			d.dibujaPunto(ascensorX-anchoAscensor/2+anchoTrazo*2*(i+2),
					listaPosicionesY[posicionActual]+altoAscensor-anchoTrazo*5/2);
		}
		// Dibuja las personas de los pasillos y de la zona de espera
		for (int i=0;i<listaPasillo.length;i++)
		{
			for (int j=0;j<listaPasillo[i].size();j++)
			{
				d.ponColorLapiz(listaPasillo[i].get(j).color());
				d.dibujaPunto(pasilloX+anchoTrazo*2*(j+1),referenciaY+altoAscensor*(5-i)-anchoTrazo*5/2);
			}
		}

		for (int i=0;i<listaEspera.length;i++)
		{
			for (int j=0;j<listaEspera[i].size();j++)
			{
				d.ponColorLapiz(listaEspera[i].get(j).color());
				d.dibujaPunto(esperaX+anchoTrazo*2*(j+1),referenciaY+altoAscensor*(5-i)-anchoTrazo*5/2);
			}
		}

	}

	/**
	 * Borra del dibujo el ascensor y las personas que lo ocupan, y tambien las personas de las zonas
	 * de pasillo y espera
	 */
	private synchronized void borraAscensor()
	{
		// Borra el ascensor den su posicion actual
		d.ponColorLapiz(ColorFig.blanco);
		d.ponGrosorLapiz(anchoTrazo);
		d.dibujaRectangulo(ascensorX+anchoTrazo-anchoAscensor/2,
				listaPosicionesY[posicionActual]+anchoTrazo,
				ascensorX+anchoAscensor-anchoTrazo-anchoAscensor/2,
				listaPosicionesY[posicionActual]+altoAscensor-anchoTrazo);
		// Borra las personas que ocupan el ascensor
		for (int i=0; i<6;i++)
		{
			d.dibujaPunto(ascensorX-anchoAscensor/2+anchoTrazo*2*(i+2),
					listaPosicionesY[posicionActual]+altoAscensor-anchoTrazo*5/2);
		}
		// Borra las personas de los pasillos y de la zona de espera

		for (int i=0;i<listaPasillo.length;i++)
		{
			for (int j=0;j<6;j++)
			{
				d.dibujaPunto(pasilloX+anchoTrazo*2*(j+1),referenciaY+altoAscensor*(5-i)-anchoTrazo*5/2);
			}
		}



		for (int i=0;i<listaEspera.length;i++)
		{
			for (int j=0;j<6;j++)
			{
				d.dibujaPunto(esperaX+anchoTrazo*2*(j+1),referenciaY+altoAscensor*(5-i)-anchoTrazo*5/2);
			}
		}    

	}

	/**
	 * Metodo auxiliar para esperar un número de milisegundos
	 * 
	 * @param  milisegundos entero que representa el numero de milisegundos a esperar
	 */

	public static void espera(int milisegundos)
	{
		try{
			Thread.sleep(milisegundos);
		} catch (InterruptedException e) {}

	}

	/**
	 * Inserta la persona indicada en el pasillo de la planta indicada
	 * 
	 * @param  p persona a insertar
	 * @param  planta entero con la planta (rango 0-4) en la que se inserta la persona
	 */
	public synchronized void insertaEnPasillo(Persona p, int planta)
	{
		listaPasillo[planta].add(p);
	}

	/**
	 * Borra la persona indicada del pasillo de la planta indicada 
	 * 
	 * @param  p persona a borrar
	 * @param  planta entero con la planta (rango 0-4) de la que se borra la persona
	 */
	public synchronized void borraDePasillo(Persona p, int planta)
	{
		listaPasillo[planta].remove(p);
	}

	/**
	 * Inserta la persona indicada en la zona de espera de la planta indicada
	 * 
	 * @param  p persona a insertar
	 * @param  planta entero con la planta (rango 0-4) en la que se inserta la persona
	 */
	public synchronized void insertaEnEspera(Persona p, int planta)
	{
		listaEspera[planta].add(p);
	}

	/**
	 * Borra la persona indicada de la zona de espera de la planta indicada 
	 * 
	 * @param  p persona a borrar
	 * @param  planta entero con la planta (rango 0-4) de la que se borra la persona
	 */
	public synchronized void borraDeEspera(Persona p, int planta)
	{
		listaEspera[planta].remove(p);
	}

	/**
	 * Inserta la persona indicada en el ascensor
	 * 
	 * @param  p persona a insertar
	 */
	public synchronized void insertaEnAscensor(Persona p)
	{
		listaAscensor.add(p);
	}

	/**
	 * Borra la persona indicada del ascensor
	 * 
	 * @param  p persona a borrar
	 */
	public synchronized void borraDeAscensor(Persona p)
	{
		listaAscensor.remove(p);
	}

	/**
	 * Metodo que mueve el ascensor a la planta siguiente en funcion de si sube o baja
	 * Ejecuta una parada de 1 segundo y despues avanza a la siguiente planta en cinco
	 * pasos que tardan 200 milisegundos cada uno
	 * @throws InterruptedException 
	 */
	private void mueve()
	{
		espera(1000);
		if (posicionActual==0) sube=true;
		if (posicionActual==listaPosicionesY.length-1) sube=false;
		parado=false;
		for (int i=0;i<5;i++)
		{
			borraAscensor();
			if (sube) 
				posicionActual++;
			else 
				posicionActual--;

			dibujaAscensor();
			try{
				Thread.sleep(200);
			} catch (InterruptedException e) {}
		}
		if (sube) 
			plantaActual++;
		else 
			plantaActual--;

		parado=true;
		synchronized(this){
			notifyAll();
		}

	}

	/**
	 * tarea a realizar por el hilo
	 */
	public void run(){

		while(true){

			if(personas == 0){
				try {
					boton.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			mueve();
		}
	}
	/**
	 * Metodo que hace esperar a la persona que quiera subirse al ascensor
	 * @param planta
	 * @throws InterruptedException 
	 */
	public synchronized void subirAscensor(int planta) 
			throws InterruptedException {

		while(!parado || planta != plantaActual){
			wait();
		}


	}
	/**
	 * Metodo que hacer esperar a la persona que quiera bajarse del ascensor
	 * @param planta
	 * @throws InterruptedException 
	 */
	public synchronized void bajarAscensor(int planta) 
			throws InterruptedException{

		while(!parado || planta != plantaActual){
			wait();
		}
	}

}
