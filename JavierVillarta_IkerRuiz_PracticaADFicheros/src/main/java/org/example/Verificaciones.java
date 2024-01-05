package org.example;

import java.util.Calendar;

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
    public void nueveCaracteres(String cadena) throws MisExceptions {

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
        string = string.trim(); // Eliminamos espacios de los laterales
        String partes[] = string.split("-"); // Separamos los anios, meses y dias

        if (partes.length == 3) { // Comprobamos que solo hay tres partes en el split
            if (partes[0].length() != 4 || partes[1].length() > 2 || partes[2].length() > 2) {
                throw new MisExceptions("Introduce una fecha valida");
            }

            // Verificar que todas las partes sean numericas
            for (int i = 0; i < partes.length; i++) {
                for (char c : partes[i].toCharArray()) {
                    if (!Character.isDigit(c)) {
                        throw new MisExceptions("Introduce una fecha valida");
                    }
                }
            }

            // Convertir las partes a valores numericos
            int anio = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int dia = Integer.parseInt(partes[2]);

            // Verificar si el anio es valido
            if (anio < 2000 || anio > 2100) {
                throw new MisExceptions("Introduce un anio valido");
            }

            // Verificar si el mes es valido
            if (mes < 1 || mes > 12) {
                throw new MisExceptions("Introduce un mes valido");
            }

            // Verificar si el dia es valido para el mes
            if (dia < 1 || dia > diasMes(anio, mes)) {
                throw new MisExceptions("Introduce un dia valido para el mes y anio dados");
            }

        } else {
            throw new MisExceptions("Introduce una fecha valida");
        }
    }

    /**
     * Metodo que devuelve los dias que tiene un mes de un anio en concreto
     * @param anio
     * @param mes
     * @return
     */
    private static int diasMes(int anio, int mes) {
        Calendar cal = Calendar.getInstance();
        cal.set(anio, mes - 1, 1); // Meses en Calendar van de 0 a 11
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * Verifica si la cadena contiene algun caracter.
     *
     * @param cadena La cadena a verificar.
     * @throws MisExceptions Si la cadena esta vacia.
     */
    public void hayAlgo(String cadena) throws MisExceptions {// verifica si ha escrito algo
        if (cadena.length() == 4) {
            throw new MisExceptions("No ha escrito nada o es demasiado corto, por favor rellene el campo correctamente");
        }
    }
}
