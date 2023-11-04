import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Verificaciones {
	public void nueveCaracteres(String cadena) throws MisExceptions {// Este metodo pretende verificar si la cadena que
																		// recibe tiene
		// exactamente 9 caracteres.
		cadena = cadena.trim();
		if (cadena.length() != 9) {
			throw new MisExceptions("Debe de tener 9 caracteres");
		}

	}

	/*
	 * Metodo que verifica si un string solo contiene numeros
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

	public void esFech(String string) throws MisExceptions {
		string.trim();		
		String partes[] = string.split("-");
		
		
	}

	public float cadeNumF(String num) {// paso de String a float
		float numero;

		numero = Float.parseFloat(num);// metodo de java que pasa String a Float
		return numero;

	}

	public void hayAlgo(String cadena) throws MisExceptions {// verifica si ha escrito algo
		if (cadena.length() == 0) {
			throw new MisExceptions("No ha escrito nada, por favor rellene el campo");
		}

	}

	public void esFloat(String num) throws MisExceptions {// esto captura un fallo para saber si se puede pasar de
															// String a float.
		try {
			Float.parseFloat(num);

		} catch (NumberFormatException e) {
			throw new MisExceptions("Lo que has introducido no es un numero decimal");
		}
	}

}
