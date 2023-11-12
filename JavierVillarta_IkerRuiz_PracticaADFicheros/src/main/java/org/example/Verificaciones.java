package org.example;


/**
 * Clase que proporciona metodos para realizar verificaciones en cadenas de texto y conversiones de tipo.
 */
public class Verificaciones {
	/**
	 * Verifica que la cadena tenga exactamente 9 caracteres.
	 *
	 * @param cadena La cadena a verificar.
	 * @throws MisExceptions Si la cadena no tiene 9 caracteres.
	 */
	public void nueveCaracteres(String cadena) throws MisExceptions {// Este metodo pretende verificar si la cadena que
																		// recibe tiene
		// exactamente 9 caracteres.
		cadena = cadena.trim();
		if (cadena.length() != 9) {
			throw new MisExceptions("Debe de tener 9 caracteres");
		}

	}

	/**
	 * Verifica si un string contiene solo numeros.
	 *
	 * @param string El string a verificar.
	 * @throws MisExceptions Si el string no contiene solo numeros.
	 */
	public void esNum(String string) throws MisExceptions {

		string.trim();// Eliminamos espacios de los laterales

		// recorre el string pasandolo a un array de chars
		for (char c : string.toCharArray()) {
			if (!Character.isDigit(c)) {
				throw new MisExceptions("Solo puede haber numeros");
			}
		}
	}

	/**
	 * Verifica que un string tiene formato de fecha (yyyy-MM-dd) y solo contiene numeros.
	 *
	 * @param string El string a verificar.
	 * @throws MisExceptions Si el string no tiene formato de fecha valido.
	 */
	public void esFech(String string) throws MisExceptions {
		string.trim(); // Eliminamos espacios de los laterales
		String partes[] = string.split("-");// Separamos los anios, meses y dias

		if (partes.length == 3) {// Comprobamos que solo hay tres partes en el split

			if (partes[0].length() != 4) {// Comprobamos si el primer puesto corresponde al anio si tiene una longitud igual a 4
				throw new MisExceptions("1Introduce una fecha valida");

			} else if (partes[1].length() > 2) {//Comprobamos si el segundo puesto tiene una longitud mayor a 2
				throw new MisExceptions("2Introduce una fecha valida");

			} else if (partes[2].length() > 2) {//Comprobamos si el tercer puesto tiene una longitud mayor a 2
				throw new MisExceptions("3Introduce una fecha valida");

			} else { //Iteramos cada parte y comprobamos que todos los valores sean numericoss
				for (int i = 0; i < partes.length; i++) {
					for (char c : partes[i].toCharArray()) {
						if (!Character.isDigit(c)) {
							throw new MisExceptions("4Introduce una fecha valida");
						}
					}
				}
			}
		} else {
			throw new MisExceptions("5Introduce una fecha valida");
		}
	}

	/**
	 * Verifica si la cadena contiene algun caracter.
	 *
	 * @param cadena La cadena a verificar.
	 * @throws MisExceptions Si la cadena esta vacia.
	 */
	public void hayAlgo(String cadena) throws MisExceptions {// verifica si ha escrito algo
		if (cadena.length() == 0) {
			throw new MisExceptions("No ha escrito nada, por favor rellene el campo");
		}
	}
}
