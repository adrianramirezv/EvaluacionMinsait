package com.indra.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class main {

	private static final String PIN = "1235";
	private static final String NAME_USER = "Miguel Adrian Ramirez Vargas";
	private static Scanner scan = new Scanner(System.in);
	private static float balance = 1000;
	private static List<Historical> historicalList = new ArrayList<>();
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) {
		inicio();
	}

	private static void inicio() {
		System.out.println("Por favor ingrese su número de Pin");
		int attemps = 0;
		boolean correctPin = false;
		while (!correctPin) {
			if (attemps == 3) {
				System.out.println("Número maximo de intentos alcanzado");
				System.exit(0);
			}
			String inputPinUser = scan.next();
			if (!isNumeric(inputPinUser)) {
				System.out.println("Ingrese solo números");
			} else if (inputPinUser.equals(PIN)) {
				correctPin = true;
				System.out.println("Acceso concedido");
				welcome();
			} else {
				System.out.println("Pin incorrecto");
				attemps++;
			}
		}
		scan.close();

	}

	private static void welcome() {
		boolean optionIsValid = false;
		System.out.println("Bienvenido " + NAME_USER);
		System.out.println("1. Consultar saldo");
		System.out.println("2. Retirar saldo");
		System.out.println("3. Histórico de Movimientos");

		while (!optionIsValid) {
			String option = scan.next();
			if (isNumeric(option)) {
				switch (option) {
				case "1": {
					showBalance();
					optionIsValid = true;
					break;
				}
				case "2": {
					takeBalance();
					optionIsValid = true;
					break;
				}
				case "3": {
					showHistorical();
					optionIsValid = true;
					break;
				}
				default:
					System.out.println("Opcion invalida ");
				}

			} else {
				System.out.println("Ingrese solo numeros");
			}

		}

	}

	private static void showBalance() {
		System.out.println("Su saldo actual es: " + balance);
		lastOptions();
	}

	private static void takeBalance() {
		System.out.println("Ingrese cantidad a retirar");
		boolean balanceValid = false;
		while (!balanceValid) {
			String balanceToTake = scan.next();
			if (isFloat(balanceToTake)) {
				balanceValid = true;
				float balanceFloat = Float.parseFloat(balanceToTake);
				if (balance < balanceFloat) {
					System.out.println("Fondos insuficientes");
					lastOptions();
				} else {
					newBalance(balanceFloat);
					lastOptions();
				}
			} else {
				System.out.println("Ingrese solo numeros decimales");
			}
		}

	}

	private static void showHistorical() {
		System.out.println("Historial de retiro");
		if (historicalList.size() == 0) {
			System.out.println("Aún no se han realizado retiros");
		}
		historicalList.forEach(p -> {
			System.out.println("Fecha " + p.getDate());
			System.out.println("Saldo anterior " + p.getOldBalance());
			System.out.println("Se retiró " + p.getBalanceTaked());
			System.out.println("********************************");
		});
		lastOptions();
	}

	private static void lastOptions() {
		System.out.println("1. Regresar al menú principal");
		System.out.println("2. Salir");
		boolean validInput = false;
		while (!validInput) {
			String input = scan.next();
			if (isNumeric(input)) {
				switch (input) {
				case "1": {
					welcome();
					break;
				}
				case "2": {
					System.exit(0);
				}
				default: {
					System.out.println("Opcion no valida");
				}
				}

			}else {
				System.out.println("Ingrese solo números");
			}
		}
	}

	private static void newBalance(float balanceToTake) {
		Historical historical = new Historical();
		Date date = new Date();
		historical.setOldBalance(balance);
		balance = balance - balanceToTake;
		historical.setDate(formatter.format(date));
		historical.setBalanceTaked(balanceToTake);
		historicalList.add(historical);

	}

	private static boolean isNumeric(String cadena) {
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	private static boolean isFloat(String cadena) {
		try {
			Float.parseFloat(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

}

class Historical {

	private String date;
	private float oldBalance;
	private float balanceTaked;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public float getOldBalance() {
		return oldBalance;
	}

	public void setOldBalance(float oldBalance) {
		this.oldBalance = oldBalance;
	}

	public float getBalanceTaked() {
		return balanceTaked;
	}

	public void setBalanceTaked(float balanceTaked) {
		this.balanceTaked = balanceTaked;
	}

}
