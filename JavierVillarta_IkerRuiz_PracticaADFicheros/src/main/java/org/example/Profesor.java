package org.example;

import java.io.Serializable;

/**
 * Clase que representa a un profesor en el sistema de gestion del centro de formacion.
 * Implementa la interfaz Serializable para permitir la serializacion.
 */
public class Profesor implements Serializable {
	private String dni;
	private String nombre;
	private String direccion;
	private String telefono;

	/**
	 * Constructor que crea un nuevo objeto Profesor con la informacion proporcionada.
	 *
	 * @param dni        El numero de identificacion del profesor.
	 * @param nombre     El nombre del profesor.
	 * @param direccion  La direccion del profesor.
	 * @param telefono   El numero de telefono del profesor.
	 */
	public Profesor(String dni, String nombre, String direccion, String telefono) {
		this.dni = dni;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;

	}

	/**
	 * Obtiene el numero de identificacion del profesor.
	 *
	 * @return El numero de identificacion del profesor.
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * Establece el numero de identificacion del profesor.
	 *
	 * @param dni El nuevo numero de identificacion a asignar.
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}

	/**
	 * Obtiene el nombre del profesor.
	 *
	 * @return El nombre del profesor.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del profesor.
	 *
	 * @param nombre El nuevo nombre a asignar.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene la direccion del profesor.
	 *
	 * @return La direccion del profesor.
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Establece la direccion del profesor.
	 *
	 * @param direccion La nueva direccion a asignar.
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Obtiene el numero de telefono del profesor.
	 *
	 * @return El numero de telefono del profesor.
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Establece el numero de telefono del profesor.
	 *
	 * @param telefono El nuevo numero de telefono a asignar.
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Devuelve una representacion en cadena del objeto Profesor.
	 *
	 * @return Una cadena que representa el objeto Profesor.
	 */
	@Override
	public String toString() {
		return "Profesor [dni=" + dni + ", nombre=" + nombre + ", direccion=" + direccion + ", telefono=" + telefono
				+ ", cursos=" + "]";
	}

}
