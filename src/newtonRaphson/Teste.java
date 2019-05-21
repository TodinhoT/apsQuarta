package newtonRaphson;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class Teste {
	static double [] deposito = new double [10];
	static int [] tempo = new int [10];
	static double saldo;
	static int t_s;
	
	
	
	static double newton(double epsilon) {
		
		Stack depositos = new Stack();
		LinkedList lista = new LinkedList();
		double j = 0.5;
		double jk = 0.5;
		double funcao = 0;
		double derivada = 0;
		
		do {
			j = jk;
			
			for (int i = 0; i < 10; i++) {
				if(deposito[i] == 0 && tempo[i] == 0) continue;
				funcao += deposito[i]*Math.pow(1-j,t_s-tempo[i]);
				derivada +=(t_s-tempo[i])*deposito[i]*Math.pow(1+j, (t_s-tempo[i]-1));
			}
			jk = jk-(funcao/derivada);
		} while (Math.abs(jk-j)>=epsilon);
		
		return jk;
	}
	
	
	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		for (int i = 0; i < 10; i++) {
			System.out.println("Digite o " + (i+1) + " depósito");
			deposito[i] = Double.parseDouble(entrada.nextLine());
			System.out.println("Digite o mês do " + (i+1) + " depósito" );
			tempo[i] = Integer.parseInt(entrada.nextLine());
		}
		System.out.println("Digite o saldo");
		saldo = Double.parseDouble(entrada.nextLine());
		System.out.println("Digite o tf final");
		t_s = Integer.parseInt(entrada.nextLine());
		
		System.out.println(newton(0.001));
		entrada.close();
	}

}
