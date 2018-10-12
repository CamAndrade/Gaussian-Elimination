package com.calculate;

import static java.lang.Math.pow;

public class Gauss {
	double[] coefficients;
	/**
	 * Gera um sistema linear por meio do método dos mínimos quadrados
	 * 
	 * @param polynom valor do polinomio
	 * @param x       valores do eixo de x
	 * @param y       valores do eixo de y
	 */
	public void leastSquare (int polynom, double[] x, double[] y) {
		double sumX,sumY;
		int power = 0;
		int index = polynom + 1;

		double[][] m = new double[index][index];
		double[][] b = new double[index][1];

		sumX = 0;
		for (int i = 0; i < index; i++) {
			for (int j = 0; j < index; j++) {
				power = i + 1 + j + 1 - 2;
				for (int t = 0; t < x.length; t++) {
					sumX += pow(x[t], power);
				}
				m[i][j] = sumX;
				sumX = 0;
			}
		}
		/*
		  System.out.println("Matriz A"); 
		  	for (int i = 0; i < index; i++){ 
		  		for (int j= 0; j < index; j++){ 
		  			System.out.print(M[i][j] + "\t"); 
		  		}
		  		System.out.println(""); 
		  	}
		 */
		
		sumY = 0;
		for (int i = 0; i < index; i++) {
			for (int j = 0; j < y.length; j++) {
				sumY = sumY + y[j] * pow(x[j], i);
			}
			b[i][0] = sumY;
			sumY = 0;
		}

		/*
		  System.out.println("Matriz B"); 
		  	for (int i = 0; i < polynom; i++) {
		  		System.out.println(b[i][0]); 
		  	}
		 */
		extendsMatrix(index, m, b);
	}

	/**
	 * Gera matriz estendida
	 * 
	 * @param index tamanho da vetor
	 * @param m     matriz de icógnitas
	 * @param b     matriz de termos independentes
	 */
	public void extendsMatrix(int index, double[][] m, double[][] b) {
		double[][] matrixExtended = new double[index][index + 1];
		for (int i = 0; i < index; i++) {
			for (int j = 0; j < index; j++) {
				matrixExtended[i][j] = m[i][j];
			}
			matrixExtended[i][index] = b[i][0];
		}

		/*System.out.println("Matriz Estendida");
		for (int i = 0; i < index; i++) {
			for (int j = 0; j < index + 1; j++) {
				System.out.print(matrixExtended[i][j] + "\t");
			}
			System.out.println("");
		}*/
		gauss(index, matrixExtended);
	}

	/**
	 * Efetua o cálculo de Gauss, com pivotamento parcial, escalonamento e
	 * retrossubistitução
	 * 
	 * @param index Tamanho máximo do vetor
	 * @param m     Matriz estendida
	 * 
	 */
	public void gauss(int index, double[][] m) {
		int currentLine, step = 0;;
		double pivot, factor;
		double[] stepLine = new double[index + 1];
		double[] pivoteamentLine = new double[index + 1];
		coefficients = new double[index];

		// pivotamento parcial
		for (; step < index - 1; step++) {
			pivot = m[step][step];
			for (int i = step + 1; i < index; i++) {
				currentLine = i;
				if (Math.abs(m[i][step]) > Math.abs(pivot)) {
					pivot = m[i][step];
					for (int colum = 0; colum < index + 1; colum++) {
						pivoteamentLine[colum] = m[currentLine][colum];
						stepLine[colum] = m[step][colum];
					}
					for (int colum = 0; colum < index + 1; colum++) {
						m[step][colum] = pivoteamentLine[colum];
						m[currentLine][colum] = stepLine[colum];
					}
				}
			}
			
			// escalonamento
			for (int i = step + 1; i < index; i++) {
				factor = m[i][step] / pivot;
				for (int j = 0; j < index + 1; j++) {
					m[i][j] = m[i][j] - factor * m[step][j];
				}
				factor = 0;
			}
			
			
			
			  System.out.println("Matriz C"); 
			  	for (int i = 0; i < index; i++) { 
			  		for (int j = 0; j < index + 1; j++) { 
			  			System.out.print(m[i][j] + "\t"); 
			  		}
			  	System.out.println(""); 
			  	}
		}

		// retrossubstituição
		for (int i = index - 2; i >= 0; i--) {
			double aux=0;
			coefficients[index - 1] = m[index - 1][index] / m[index - 1][index - 1];
			for (int k = index - 1; k >= 0; k--) {
				 aux = aux + coefficients[k] * m[i][k];
			}
			coefficients[i] = (m[i][index] - aux) / m[i][i];
		}

		System.out.println("Coeficientes - 0 -- Xn");
		for (int i = 0; i < coefficients.length; i++) {
			System.out.println(coefficients[i]);
		}
	}

	/*public static void main(String[] args) {
		Gauss gauss = new Gauss();

		double[] x = { 37.7, 37.9, 37.9, 38.1, 38.2, 38.4, 39.2, 40.1, 40.5, 41.1, 41.2, 41.3, 41.4, 42.2, 42.6, 42.8,	43.8 };
		double[] y = { 37.2, 37.7, 37.9, 38, 38.1, 38.8, 38.9, 40.1, 40.9, 41, 41.7, 41.8, 41.9, 42, 42.7, 42.8, 43 };

		gauss.leastSquare(4, x, y);
	}*/
}