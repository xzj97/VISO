


/**
 * Prueba de la simulacion del ascensor sin control de las reglas de movimiento de las personas
 * 
 * @author (J. Javier Gutierrez) 
 * @version (25-9-2017)
 */

//import fundamentos.*;

public class PruebaAscensor
{

	public static void main(String[] args) {

		Ascensor a=new Ascensor();
		//Las dos primeras personas pro con el doble de tiempo de retraso.
		Persona p1=new Persona(1,4,12000,ColorFig.azul,a);
		Persona p2=new Persona(3,0,8000,ColorFig.verde,a);
		//Persona p3=new Persona(2,1,5500,ColorFig.rojo,a);
		//Persona p4=new Persona(3,4,1500,ColorFig.naranja,a);
		//Persona p5=new Persona(1,3,700,ColorFig.gris,a);
		//Persona p6=new Persona(0,1,450,ColorFig.negro,a);
		//Persona p7=new Persona(1,3,350,ColorFig.magenta,a);
		//Persona p8=new Persona(0,1,200,ColorFig.rosa,a);

		a.start();
		p1.start();
		p2.start();
		
		//p3.start();
		//p4.start();
		//p5.start();
		//p6.start();
		//p7.start();
		//p8.start();

		//Thread periodico del programa principal que refresca el ascensor cada 200 milisegundos
		try{
			while(true){
				Thread.sleep(200);
				a.refresca();
			}
		} catch(InterruptedException e){
		}

	}
}
