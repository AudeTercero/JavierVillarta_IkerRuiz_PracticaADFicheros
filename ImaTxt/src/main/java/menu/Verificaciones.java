package menu;

public class Verificaciones {
    public boolean nuveCaracteres(String cadena) {
        cadena = cadena.trim(); // Eliminamos espacios de los laterales
        if (cadena.length() == 9) {
            return true;
        }

        return false;
    }

    /**
     * Metodo para pasar una variable String a Float
     *
     * @param num
     * @return devuelve una variable float
     */
    public float cadeNumF(String num) {//paso de String a float
        float numero;

        numero = Float.parseFloat(num);//metodo de java que pasa String a Float
        return numero;

    }

    /**
     * Metodo que verifica si la variable que recibe contiene al menos un caracter
     *
     * @param cadena
     * @return devuelve mediante un booleano si hay o no al menos un caracter
     */
    public boolean hayAlgo(String cadena) {
        if (cadena.length() != 0) {
            return true;
        }
        return false;
    }

    /**
     * Metodo que captura un fallo para saber si se puede pasar de String a float.
     *
     * @param num
     * @return
     */
    public boolean esFloat(String num) {
        try {
            Float.parseFloat(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

