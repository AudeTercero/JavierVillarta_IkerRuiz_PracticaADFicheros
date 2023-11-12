package org.example;

/**
 * Interfaz que define operaciones basicas de CRUD (Crear, Leer, Actualizar, Eliminar).
 */
public interface CRUD {
	/**
	 * Metodo para agregar un nuevo elemento.
	 */
    void alta();

	/**
	 * Metodo para eliminar un elemento.
	 */
	void baja();

	/**
	 * Metodo para modificar un elemento existente.
	 */
	void modificar();

	/**
	 * Metodo para buscar un elemento.
	 */
	void buscar();

	/**
	 * Metodo para mostrar informacion sobre los elementos.
	 */
	void mostrar();
}
