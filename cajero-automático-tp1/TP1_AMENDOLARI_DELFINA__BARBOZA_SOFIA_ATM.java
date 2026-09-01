
package tp1_amendolari_delfina__barboza_sofia_atm;

import java.util.Scanner;

public class TP1_AMENDOLARI_DELFINA__BARBOZA_SOFIA_ATM {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		/* Array de los 5 usuarios disponibles */
		String[] nombres = { "Ana Gonzáles", "Delfina Gómez", "Mateo Rodríguez", "Benjamin López", "Emilia Fernández" };
		/* Array de las claves de cada usuario */
		int[] claves = { 1234, 4321, 6549, 9456, 7894 };
		/* Arrray balance */
		double[] balances = { 10000.00, 10000.00, 10000.00, 10000.00, 10000.00 };

		/* Variables que controlan el correr de cada operación */
		int transacciones = 0;
		int intentos = 0;
		int controlMenu = 0;

		/* Ingreso al sistema por medio de numero de usuario */
		System.out.println("=== BIENVENIDO AL CAJERO AUTOMÁTICO TP1 ===");
		System.out.println("Ingrese su numero de usuario: ");

		/* Comparación y validación de número de usuario */
		int numUser = sc.nextInt();
		while (numUser < 1 || numUser > 5) {
			System.out.println("El número de usuario ingresado es invalido, ingrese un numero entre 1 y 5");
			numUser = sc.nextInt();
		}

		/* Claves de acceso de usuario */
		System.out.println("Ahora ingrese su clave de usuario: ");
		int claveUser = sc.nextInt();

		/* Boolean que evalúa si la clave es correcta */
		boolean clavecorrecta = false;
		if (numUser >= 1 && numUser <= claves.length) {
			clavecorrecta = (claveUser == claves[numUser - 1]);
		}

		while (!clavecorrecta) {
			intentos++;
			if (intentos == 3) {
				System.out.println("Se ha quedado sin intentos. Su usuario ha sido bloqueado.");
				sc.close();
				System.exit(0);
			}
			System.out.println("Su contraseña es incorrecta, intente de nuevo: ");
			claveUser = sc.nextInt();
			if (numUser >= 1 && numUser <= claves.length) {
				clavecorrecta = (claveUser == claves[numUser - 1]);
			}
		}

		System.out.println("Bienvenido/a, " + nombres[numUser - 1]);

		/*
		 * Switch case de operaciones, con un while limitaando el número de
		 * transacciones
		 */
		while (transacciones <= 6) {
			/* Menú de operaciones */
			int operaciones;
			System.out.println("Por favor, ingrese el número de operación que desea realizar: \n"
					+ "1. Consulta de saldo. \n"
					+ "2. Depósito de dinero \n"
					+ "3. Transferencia de dinero. \n"
					+ "4. Retiro de dinero. \n"
					+ "5. Historial de movimientos. \n"
					+ "6. Salida. \n");
			operaciones = sc.nextInt();

			switch (operaciones) {
				case 1: /* Consulta de saldo */
					System.out.println("Su saldo es: $" + balances[numUser - 1]);
					transacciones++;
					break;

				case 2: /* Depósito de dinero por montos mayores a $0 */
					System.out.println("Ingrese el monto a depositar: ");
					double montoDeposito = sc.nextDouble();
					while (montoDeposito <= 0) {
						System.out.println("El monto a depositar debe ser mayor a $0. Ingrese nuevamente: ");
						montoDeposito = sc.nextDouble();
					}
					balances[numUser - 1] += montoDeposito;
					System.out.println("Depósito realizado con éxito. Su saldo actual es: $" + balances[numUser - 1]);
					transacciones++;
					break;

				case 3: /* Transferencia de dinero regulado con while */
					System.out.println("Ingrese el número de cuenta a la que desea transferir: ");
					int numCuentaTransferencia = sc.nextInt();
					while (numCuentaTransferencia == numUser || numCuentaTransferencia < 1
							|| numCuentaTransferencia > balances.length) {
						if (numCuentaTransferencia == numUser) {
							System.out.println("No puede transferirse dinero a sí mismo. Ingrese nuevamente: ");
						} else {
							System.out.println("Número de cuenta inválido. Ingrese nuevamente: ");
						}
						numCuentaTransferencia = sc.nextInt();
					}
					System.out.println("Ingrese el monto a transferir: ");
					double montoTransferencia = sc.nextDouble();
					while (montoTransferencia <= 0 || montoTransferencia > balances[numUser - 1]) {
						if (montoTransferencia <= 0) {
							System.out.println("El monto a transferir debe ser mayor a $0. Ingrese nuevamente: ");
						} else {
							System.out.println("Fondos insuficientes. Ingrese nuevamente: ");
						}
						montoTransferencia = sc.nextDouble();
					}
					balances[numUser - 1] -= montoTransferencia;
					balances[numCuentaTransferencia - 1] += montoTransferencia;
					System.out.println("Transferencia realizada con éxito a " + nombres[numCuentaTransferencia - 1]
							+ ". Su saldo actual es: $" + balances[numUser - 1]);
					transacciones++;
					break;

				case 4: /* Retiro de dinero regulado con while */
					System.out.println("Ingrese el monto a retirar: ");
					double montoRetiro = sc.nextDouble();
					while (montoRetiro <= 0 || montoRetiro > balances[numUser - 1] || montoRetiro >= 2000) {
						if (montoRetiro <= 0) {
							System.out.println("El monto a retirar debe ser mayor a $0. Ingrese nuevamente: ");
						} else if (montoRetiro > balances[numUser - 1]) {
							System.out.println("Fondos insuficientes. Ingrese nuevamente: ");
						} else {
							System.out.println("No es posible retirar un monto mayor a $2000. Ingrese nuevamente: ");
						}
						montoRetiro = sc.nextDouble();
					}
					balances[numUser - 1] -= montoRetiro;
					System.out.println("Retiro realizado con éxito. Su saldo actual es: $" + balances[numUser - 1]);
					transacciones++;
					break;

				case 5: /* Resumen de sesión y Salida */
					System.out.println("=== RESUMEN DE SESIÓN ===");
					System.out.println("Titular: " + nombres[numUser - 1]);
					System.out.println("Saldo Actual: " + balances[numUser - 1]);
					System.out.println("Gracias por utilizar nuestro cajero automático.");
					break;

				case 6: /* Reporte general, recorrido por un for */
					System.out.println("=== REPORTE GENERAL DE CUENTAS ===");
					for (int i = 0; i < nombres.length; i++) {
						System.out.println("Número de cuenta: " + (i + 1) + " - Titular: " + nombres[i]
								+ " - Saldo actual: $" + balances[i]);
					}
					sc.close();
					System.exit(0);
					break;

				default:
					System.out.println("Operación inexitente.");
			}
		}

		System.out.println("Ha excedido el número maximo de operaciones permitidas por sesión.");
		sc.close();
	}
}