package newtonRaphson;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class Teste {
	static double[] deposito = new double[10];
	static int[] tempo = new int[10];
	static double saldo;
	static int t_s;

	static double newton(double epsilon) {

		Stack depositos = new Stack();
		LinkedList lista = new LinkedList();
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
		} while (verif >= epsilon);

		return jk;
	}

	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		for (int i = 0; i < 10; i++) {
			System.out.println("Digite o " + (i + 1) + " dep�sito");
			deposito[i] = Double.parseDouble(entrada.nextLine());
			System.out.println("Digite o m�s do " + (i + 1) + " dep�sito");
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
