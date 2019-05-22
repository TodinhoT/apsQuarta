package newtonRaphson;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class Teste {
	// Declaracao das variaveis
	static double[] deposito = { 1000, 1200, 100, 0, 1100, 0, 900, 0, 0, 0 };
	static int[] tempo = { 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
	static double saldo = 5000;
	static int t_s = 12;
	static double j = 0.5;
	static double jk = 0;
	static double funcao = 0;
	static double derivada = 0;
	static double verif;
	static Stack<Double> saldoT = new Stack<Double>();
	static Stack<Double> depositos = new Stack<Double>();

	// Metodo que armazena os depositos acumulados em uma pilha e a coloca em ordem
	static void armazenaDeposito() {
		Stack<Double> temp = new Stack<Double>();
		for (int i = 1; i < deposito.length; i++) {
			if (i == 1) {
				temp.push(deposito[0]);
			}
			temp.push(temp.peek() + deposito[i]);
		}
		for (int i = 0; i < deposito.length; i++) {
			depositos.push(temp.pop());
		}
	}

	// Metodo que armazena o saldo total em uma pilha e a coloca em ordem
	static void armazenaSaldo() {
		Stack<Double> temp = new Stack<Double>();
		for (int i = 1; i < deposito.length; i++) {
			if (i == 1) {
				temp.push(deposito[0]);
			}
			temp.push(temp.peek() + (temp.peek() * jk) + deposito[i]);
		}
		for (int i = 0; i < deposito.length; i++) {
			saldoT.push(temp.pop());
		}
	}

	// metodo Newton solicitado
	static double newton(double epsilon) {
		// Declaracao das variaveis
		LinkedList<Double> listaJ = new LinkedList<Double>();
		LinkedList<Double> listaJk = new LinkedList<Double>();
		LinkedList<Double> listaVerif = new LinkedList<Double>();
		DecimalFormat df = new DecimalFormat("####.00");
		double[] rendimentoAcumulado = new double[deposito.length];
		double[] rendimentoMensal = new double[deposito.length];
		// iniciando a lista com o primeiro juros
		listaJ.add(j);

		// Calculo da formula de NewtonRaphson
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

		// Calculo do rendimento acumulado
		armazenaDeposito();
		armazenaSaldo();
		for (int i = 0; i < rendimentoAcumulado.length; i++) {
			if (i == 0) {
				rendimentoAcumulado[i] = 0;
			} else {
				rendimentoAcumulado[i] = saldoT.peek() - depositos.peek();
				depositos.pop();
				saldoT.pop();
			}

		}

		// Calculo do rendimento mensal
		armazenaSaldo();
		for (int i = 0; i < rendimentoMensal.length; i++) {
			if (i == 0) {
				rendimentoMensal[i] = 0;
			} else {
				rendimentoMensal[i] = saldoT.peek() * jk;
				saldoT.pop();
			}
		}

		// Print de todos os juros
		listaJ.removeLast();
		System.out.println(
				"Interacao ---------------------- Jk ------------------------------------ Jk+1 ------------------------------ |(Jk+1)-Jk| --------");
		for (int i = 0; i < listaJ.size(); i++) {
			if (i == 0) {
				System.out.println(
						"   " + (i + 1) + "    |-------------|" + listaJ.get(i) + "               \t|-------------|\t"
								+ listaJk.get(i) + "\t|-------------|\t" + listaVerif.get(i) + "\t|");
			} else
				System.out.println("   " + (i + 1) + "    |-------------|" + listaJ.get(i) + "\t|-------------|\t"
						+ listaJk.get(i) + "\t|-------------|\t" + listaVerif.get(i) + "\t|");
		}
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("");

		// Print dos dados de todos os dados
		armazenaDeposito();
		armazenaSaldo();
		System.out.println("");

		System.out.println(
				"Mes ------------------ Deposito ----------- DepositoAcumulado ---------- Saldo ----------- RendimentoMensal ------ RendimentoAcumulado");
		for (int i = 0; i < t_s; i++) {
			if (i == 0 || i == 1) {
				System.out.println("  " + (i + 1) + "\t|-------------|" + 0 + "\t|-------------|\t" + 0
						+ "\t|-------------|\t" + 0 + "\t|-------------|\t" + 0 + "\t|-------------|\t" + 0 + "\t|");
			} else {
				System.out.println("  " + (i + 1) + "\t|-------------|" + deposito[i - 2] + "\t|-------------|\t"
						+ df.format(depositos.peek()) + "\t|-------------|\t" + df.format(saldoT.peek())
						+ "\t|-------------|\t" + df.format(rendimentoMensal[i - 2]) + "\t|-------------|\t"
						+ df.format(rendimentoAcumulado[i - 2]) + "\t|");
				depositos.pop();
				saldoT.pop();
			}
		}
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------------");
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
