package ejercicioArrays;

import java.util.Scanner;

public class Ejercicio2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc=new Scanner(System.in);
		
		int num[]=new int[10];
		String res="";
		int n, suma=0;
		
		for(int i=0; i < num.length; i++) {
			System.out.println("INTRODUZCA UN NÚMERO REAL");
			n=sc.nextInt();
			num[i]=n;
			res += String.valueOf(num[i]+" ");
			suma +=num[i];
		}
		System.out.println("LOS NÚMEROS INTRODUCIDOS SON: " + res);
		System.out.println("LA SUMA DE LOS NÚMEROS INTRODUCIDOS ES: " + suma);
	}

}
