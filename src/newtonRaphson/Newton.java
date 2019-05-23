package newtonRaphson;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class Newton {
	// Declaracao das variaveis
	static double saldo;
	static int t_s;
	static double j = 0.5;
	static double jk = 0;
	static double funcao = 0;
	static double derivada = 0;
	static double verif;
	static double[] deposito = new double[10];
	static int[] tempo = new int[10];
	static double[] rendimentoAcumulado = new double[deposito.length];
	static double[] rendimentoMensal = new double[deposito.length];
	static LinkedList<Double> listaJ = new LinkedList<Double>();
	static LinkedList<Double> listaJk = new LinkedList<Double>();
	static LinkedList<Double> listaVerif = new LinkedList<Double>();
	static Stack<Double> saldoT = new Stack<Double>();
	static Stack<Double> depositos = new Stack<Double>();

	// metodo Newton solicitado
	static double newton(double epsilon) {
		// iniciando a lista com o primeiro juros
		listaJ.add(j);
		// Calculo da formula de NewtonRaphson
		do {
			for (int i = 1; i < 10; i++) {
				if (i == 1) {//Condicao para reset da variavel para cada iteracao
					funcao = deposito[0] * Math.pow(1 + j, t_s - tempo[0]);
					derivada = (t_s - tempo[0]) * deposito[0] * Math.pow(1 + j, (t_s - tempo[0] - 1));
				}
				funcao += deposito[i] * Math.pow(1 + j, t_s - tempo[i]);
				derivada += (t_s - tempo[i]) * deposito[i] * Math.pow(1 + j, (t_s - tempo[i] - 1));
			}
			jk = j - ((funcao - saldo) / derivada);
			verif = Math.abs(jk - j);
			j = jk;

			//Armazenando os juros nas listas
			listaJ.add(j);
			listaJk.add(jk);
			listaVerif.add(verif);
		} while (verif >= epsilon);

		imprimiTabelas();

		return jk;
	}

	// Metodo que calcula os depositos acumulados e armazena em uma pilha
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

	// Metodo que calcula o saldo total e armazena em uma pilha
	static void armazenaSaldo() {
		Stack<Double> temp = new Stack<Double>();
		for (int i = 1; i < deposito.length; i++) {
			if (i == 1) {
				temp.push(deposito[0]);
			}
			temp.push(temp.peek() + (temp.peek() * jk) + deposito[i]);//formula do saldo total
		}
		for (int i = 0; i < deposito.length; i++) {
			saldoT.push(temp.pop());
		}
	}

	// Metodo que calcula o rendimento acumulado a partir da diferenca entre o saldo
	// total e os depositos acumulados
	static void calculaRendimentoAcumulado() {
		armazenaDeposito();
		armazenaSaldo();
		for (int i = 0; i < rendimentoAcumulado.length; i++) {
			rendimentoAcumulado[i] = saldoT.peek() - depositos.peek();
			depositos.pop();
			saldoT.pop();
		}
	}

	// Metodo que calcula o rendimento mensal para cada saldo vezes o juros
	static void calculaRendimentoMensal() {
		armazenaSaldo();
		for (int i = 0; i < rendimentoMensal.length; i++) {
			if (i == 0) {
				rendimentoMensal[i] = 0;
			} else {
				rendimentoMensal[i] = saldoT.peek() * jk;
				saldoT.pop();
			}
		}
	}

	// Imprime todas as tabelas
	static void imprimiTabelas() {
		DecimalFormat df = new DecimalFormat("#");
		calculaRendimentoAcumulado();
		calculaRendimentoMensal();
		// Print de todos os juros
		listaJ.removeLast();
		System.out.println("");
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

		// Print geral de todos os dados
		armazenaDeposito();
		armazenaSaldo();
		System.out.println("");

		System.out.println(
				"Mes ------------------ Deposito ----------- DepositoAcumulado ---------- Saldo ----------- RendimentoMensal ------ RendimentoAcumulado");
		for (int i = 0; i < t_s; i++) {
			if (i == 0|| i == 1) {
				System.out.println("  " + (i + 1) + "\t|-------------|" + 0 + "\t|-------------|\t" + 0
						+ "\t|-------------|\t" + 0 + "\t|-------------|\t" + 0 + "\t|-------------|\t" + 0 + "\t|");
			} else {
				System.out.println("  " + (i + 1) + "\t|-------------|" + df.format(deposito[i - 2])
						+ "\t|-------------|\t" + df.format(depositos.peek()) + "\t|-------------|\t"
						+ df.format(saldoT.peek()) + "\t|-------------|\t" + df.format(rendimentoMensal[i - 2])
						+ "\t|-------------|\t" + df.format(rendimentoAcumulado[i - 2]) + "\t|");
				depositos.pop();
				saldoT.pop();
			}
		}
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------------");
	}

	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		DecimalFormat porcent = new DecimalFormat("#.00");
		double epsilon;
		//entradas do usuario
		for (int i = 0; i < 10; i++) {
			System.out.println("Digite o " + (i + 1) + " depósito");
			deposito[i] = entrada.nextDouble();
			System.out.println("Digite o mês do " + (i + 1) + " depósito");
			tempo[i] = entrada.nextInt();
		}
		System.out.println("Digite o saldo");
		saldo = entrada.nextDouble();
		System.out.println("Digite o tf final");
		t_s = entrada.nextInt();
		System.out.println("Digite o epsilon");
		epsilon = entrada.nextDouble();
		
		//chama a funcao newton e mostra o resultado
		double chernoirengousRosa = newton(epsilon);
		System.out.println("\nJuros mensais: " + chernoirengousRosa + "(ou aprox. "
				+ (porcent.format(chernoirengousRosa * 100)) + "%)");
		entrada.close();
	}

}
