package newtonRaphson;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class Teste {
	static double[] deposito = {1000,1200,100,0,1100,0,900,0,0,0};
	static int[] tempo = {3,4,5,6,7,8,9,10,11,12};
	static double saldo = 5000;
	static int t_s = 12;

	static double newton(double epsilon) {

		Stack<Double> depositos = new Stack<Double>();
		LinkedList<Double> listaJ = new LinkedList<Double>();
		LinkedList<Double> listaJk = new LinkedList<Double>();
		LinkedList<Double> listaVerif = new LinkedList<Double>();
		double j = 0.5;
		double jk = 0;
		double funcao = 0;
		double derivada = 0;
		double verif;
		
		do {
			for (int i = 1; i < 10; i++) {
				if (i == 1) {
					funcao = deposito[0] * Math.pow(1 + j, t_s - tempo[0]);
					derivada = (t_s - tempo[0]) * deposito[0] * Math.pow(1 + j, (t_s - tempo[0] - 1));
				}
				funcao += deposito[i] * Math.pow(1 + j, t_s - tempo[i]);
				derivada += (t_s - tempo[i]) * deposito[i] * Math.pow(1 + j, (t_s - tempo[i] - 1));
			}
			jk = j - ((funcao - saldo) / derivada);
			verif = Math.abs(jk - j);			
			j = jk;
			
			listaJ.add(j);
			listaJk.add(jk);
			listaVerif.add(verif);
		} while (verif >= epsilon);
		System.out.println("Interacao ---------------------- Jk ------------------------------ Jk+1 -------------------------- |(Jk+1)-Jk| -----------------");
		for (int i = 0; i < listaJ.size(); i++) {

			System.out.println("   "+(i+1)+"     |-------------| " + listaJ.get(i) + " |-------------| " + listaJk.get(i) + " |-------------| " + listaVerif.get(i) +" |-------------|");
		}
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
		return jk;
	}

	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
//		for (int i = 0; i < 10; i++) {
//			System.out.println("Digite o " + (i + 1) + " depósito");
//			deposito[i] = Double.parseDouble(entrada.nextLine());
//			System.out.println("Digite o mês do " + (i + 1) + " depósito");
//			tempo[i] = Integer.parseInt(entrada.nextLine());
//		}
//		System.out.println("Digite o saldo");
//		saldo = Double.parseDouble(entrada.nextLine());
//		System.out.println("Digite o tf final");
//		t_s = Integer.parseInt(entrada.nextLine());
		

		System.out.println(newton(0.001));
		entrada.close();
	}

}
