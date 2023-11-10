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
	private String FICHERO = "Alumno.data";;

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
		int aux = 0;
		ArrayList<Alumno> alumnos = leerFich();
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

	public ArrayList<Alumno> leerFich() {
		File file = new File(FICHERO);
		ArrayList<Alumno> alumnos = new ArrayList<>();
		DataInputStream in = null;
		int id = 0;
		String nom, ape, tel, dir, fech;
		if (file.exists()) {
			try {
				in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
				while (true) {
					id = in.readInt();
					if (id != -1) {
						nom = in.readUTF();
						ape = in.readUTF();
						tel = in.readUTF();
						dir = in.readUTF();
						fech = in.readUTF();
					} else {
						break;
					}

					Alumno a = new Alumno(id, nom, ape, tel, dir, fech);
					alumnos.add(a);

				}

			} catch (IOException e) {
				//e.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		}

		return alumnos;

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
