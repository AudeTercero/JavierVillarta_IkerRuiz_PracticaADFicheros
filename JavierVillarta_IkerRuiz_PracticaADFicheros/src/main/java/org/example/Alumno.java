package org.example;

import java.util.ArrayList;


/**
 * Clase que representa a un alumno en el sistema de gestion del centro de formacion.
 */
public class Alumno {
    private int numExpediente;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String direccion;
    private String fechNac;

    /**
     * Constructor que crea un nuevo objeto Alumno con la informacion proporcionada y asigna un numero de expediente unico.
     *
     * @param nombre     El nombre del alumno.
     * @param apellidos  Los apellidos del alumno.
     * @param telefono   El número de telefono del alumno.
     * @param direccion  La direccion del alumno.
     * @param fechNac    La fecha de nacimiento del alumno.
     */
    public Alumno(String nombre, String apellidos, String telefono, String direccion, String fechNac) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fechNac = fechNac;
        this.numExpediente = nuevoExpediente();

    }

    /**
     * Constructor que crea un nuevo objeto Alumno con un numero de expediente especifico y la informacion proporcionada.
     *
     * @param expe       El numero de expediente del alumno.
     * @param nombre     El nombre del alumno.
     * @param apellidos  Los apellidos del alumno.
     * @param telefono   El numero de telefono del alumno.
     * @param direccion  La direccion del alumno.
     * @param fechNac    La fecha de nacimiento del alumno.
     */
    public Alumno(int expe, String nombre, String apellidos, String telefono, String direccion, String fechNac) {
        this.numExpediente = expe;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fechNac = fechNac;

    }

    /**
     * Metodo que genera un nuevo numero de expediente unico para el alumno.
     *
     * @return El nuevo numero de expediente.
     */
    public int nuevoExpediente() {
        Fichero fich = new Fichero();
        int aux = 0;
        ArrayList<Alumno> alumnos = fich.leerBin();
        if (!alumnos.isEmpty()) {
            for (Alumno a : alumnos) {
                if (a.getNumExpediente() > aux) {
                    aux = a.getNumExpediente();
                }
            }
            aux++;
        } else {
            aux = 1;
        }
        return aux;
    }

    /**
     * Obtiene el numero de expediente del alumno.
     *
     * @return El numero de expediente del alumno.
     */
    public int getNumExpediente() {
        return numExpediente;
    }

    /**
     * Establece el numero de expediente del alumno.
     *
     * @param numExpediente El nuevo numero de expediente a asignar.
     */
    public void setNumExpediente(int numExpediente) {
        this.numExpediente = numExpediente;
    }

    /**
     * Obtiene el nombre del alumno.
     *
     * @return El nombre del alumno.
     */

    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del alumno.
     *
     * @param nombre El nuevo nombre a asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los apellidos del alumno.
     *
     * @return Los apellidos del alumno.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos del alumno.
     *
     * @param apellidos Los nuevos apellidos a asignar.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el número de telefono del alumno.
     *
     * @return El número de telefono del alumno.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de telefono del alumno.
     *
     * @param telefono El nuevo numero de telefono a asignar.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la direccion del alumno.
     *
     * @return La direccion del alumno.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la direccion del alumno.
     *
     * @param direccion La nueva direccion a asignar.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene la fecha de nacimiento del alumno.
     *
     * @return La fecha de nacimiento del alumno.
     */
    public String getFechNac() {
        return fechNac;
    }

    /**
     * Establece la fecha de nacimiento del alumno.
     *
     * @param fechNac La nueva fecha de nacimiento a asignar.
     */
    public void setFechNac(String fechNac) {
        this.fechNac = fechNac;
    }


    /**
     * Metodo que devuelve una representacion en cadena del objeto Alumno.
     *
     * @return Una cadena que representa el objeto Alumno.
     */
    @Override
    public String toString() {
        return "Alumno [numExpediente=" + numExpediente + ", nombre=" + nombre + ", apellidos=" + apellidos
                + ", telefono=" + telefono + ", direccion=" + direccion + ", fechNac=" + fechNac + "]";
    }

}
