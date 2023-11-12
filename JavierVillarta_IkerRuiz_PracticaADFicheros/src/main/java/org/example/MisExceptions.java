package org.example;

/**
 * Clase de excepcion personalizada que extiende de la clase Exception.
 */
public class MisExceptions extends Exception {

	/**
	 * Constructor que recibe un mensaje de error.
	 *
	 * @param mensaje El mensaje de error asociado a la excepcion.
	 */
	public MisExceptions(String mensaje) {
		super(mensaje);
	}
}
