package ejercicioArrays;

import java.util.Scanner;

public class Ejercicio1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc=new Scanner(System.in);
		
		int num[]=new int[10];
		String res="";
		
		
		for(int i = 0; i < num.length; i++) {
			System.out.println("INTRODUZCA UN NÚMERO REAL");
			int numIntro=sc.nextInt();
			num[i] = numIntro;
			res += String.valueOf(num[i])+" "; // guardar valor anterior
			
			
		}
		//for(int i = 0; i < num.length; i++) {
			
			//res += String.valueOf(num[i])+" ";
		//}
		System.out.println("LOS NÚMEROS INTRODUCIDOS SON: " + res);
	}

}
