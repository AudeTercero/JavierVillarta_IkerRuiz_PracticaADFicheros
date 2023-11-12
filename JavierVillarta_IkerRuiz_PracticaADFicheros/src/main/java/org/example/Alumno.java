package org.example;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Alumno {
	private int numExpediente;
	private String nombre;
	private String apellidos;
	private String telefono;
	private String direccion;
	private String fechNac;

	/**
	 * @param nombre
	 * @param apellidos
	 * @param telefono
	 * @param direccion
	 * @param fechNac
	 */
	public Alumno(String nombre, String apellidos, String telefono, String direccion, String fechNac) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.direccion = direccion;
		this.fechNac = fechNac;
		this.numExpediente = nuevoExpediente();

	}

	public Alumno(int expe, String nombre, String apellidos, String telefono, String direccion, String fechNac) {
		this.numExpediente = expe;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.direccion = direccion;
		this.fechNac = fechNac;

	}

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



	public int getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(int numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getFechNac() {
		return fechNac;
	}

	public void setFechNac(String fechNac) {
		this.fechNac = fechNac;
	}

	@Override
	public String toString() {
		return "Alumno [numExpediente=" + numExpediente + ", nombre=" + nombre + ", apellidos=" + apellidos
				+ ", telefono=" + telefono + ", direccion=" + direccion + ", fechNac=" + fechNac + "]";
	}

}
