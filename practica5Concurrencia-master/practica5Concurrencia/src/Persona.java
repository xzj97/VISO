
/**
 * Clase que almacena la informacion del movimiento de una persona en un edificio
 * El movimiento de una se persona se caracterizará por el paso del pasillo de una planta origen
 * a la zona de espera de esa planta, para tomar el ascensor.
 * Una vez en la planta de destino, sale del ascensor a la zona de espera, y de alli al pasillo.
 * En ese momento se permutan las plantas origen y destino y se vuelve a iniciar el movimiento.
 * En todos los cambios de posicion se deja pasar el retraso indicado.
 * 
 * @author (J. Javier Gutierrez) 
 * @version (25-9-2017)
 */

//import fundamentos.*;

public class Persona extends Thread
{
	// Atributos de la persona en movimiento por el edificio
	private int plantaOrigen;   // Planta de partida
	private int plantaDestino;  // Planta destino
	private int retraso;        // Tiempo que transcurre en el cambio de posicion
	private ColorFig color;     // Color que representa a la persona en el dibujo
	private Ascensor ascensor;  // Ascensor que se utiliza

	// Estado del movimiento y tiempo de espera real
	int estado=0;
	int tiempo;

	/**
	 * Constructor al que se le suministran los valores de los atributos
	 */
	public Persona(int plantaOrigen, int plantaDestino,int retraso,ColorFig color, Ascensor ascensor)
	{
		this.plantaOrigen=plantaOrigen;
		this.plantaDestino=plantaDestino;
		this.retraso=retraso;
		this.color=color;
		this.ascensor=ascensor;
		ascensor.insertaEnPasillo(this,plantaOrigen);
	}

	/**
	 * Observador que devuelve la planta de origen
	 * 
	 * @return entero con el valor de la planta de origen
	 */
	public int plantaOrigen ()
	{
		return plantaOrigen;
	}

	/**
	 * Observador que devuelve la planta de destino
	 * 
	 * @return entero con el valor de la planta de destino
	 */
	public int plantaDestino ()
	{
		return plantaDestino;
	}

	/**
	 * Observador que devuelve el retraso en el cambio de posicion
	 * 
	 * @return entero con el valor del retraso
	 */
	public int retraso ()
	{
		return retraso;
	}

	/**
	 * Observador que devuelve el color que representa a la persona en el dibujo
	 * 
	 * @return ColorFig con el color de la personan
	 */
	public ColorFig color ()
	{
		return color;
	}

	/**
	 * Observador que devuelve el color que representa a la persona en el dibujo
	 * 
	 * @return ColorFig con el color de la personan
	 */
	public Ascensor ascensor ()
	{
		return ascensor;
	}

	/**
	 * Realiza el movimiento de la persona con la temporizacion adecuada entre cambios de posicion
	 * y controlando la subida y bajada de la persona al ascensor en las plantas adecuadas y cuando 
	 * el ascensor esta parado 
	 * @throws InterruptedException 
	 */
	private void camina () throws InterruptedException
	{

		//Si la persona esta en el pasillo
		sleep(retraso);
		ascensor.borraDePasillo(this, plantaOrigen);
		ascensor.insertaEnEspera(this, plantaOrigen);

		//Si la persona esta en espera

		sleep(retraso);
		//Si el ascensor ha llegado a la planta actual y esta parado
		
		ascensor.subePersona();
		ascensor.pulsaBoton();
		ascensor.subirAscensor(plantaOrigen);
		ascensor.atendido();
		//Se sube al ascensor
		
		ascensor.borraDeEspera(this, plantaOrigen);
		ascensor.insertaEnAscensor(this);
		
		
		//Si esta en el ascensor
		//sleep(retraso);
		ascensor.bajarAscensor(plantaDestino);
			
		ascensor.borraDeAscensor(this);
		ascensor.insertaEnEspera(this, plantaDestino);

		//Si la persona ha llegado a la sala de espera destino
		sleep(retraso);
		ascensor.borraDeEspera(this, plantaDestino);
		ascensor.insertaEnPasillo(this, plantaDestino);
		int planta = plantaOrigen;
		plantaOrigen = plantaDestino;
		plantaDestino = planta;

	}



	public void run(){

		try{
			while(true){
				camina();
			}
		}catch (InterruptedException e){
			return;
		}

	}
}













