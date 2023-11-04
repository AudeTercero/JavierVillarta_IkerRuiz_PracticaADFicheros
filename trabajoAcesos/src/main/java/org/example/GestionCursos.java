package org.example;

import java.util.Scanner;
import java.util.HashMap;

public class GestionCursos implements CRUD {
	private HashMap<String, Curso> cursos = new HashMap<>();
	private static final String FICHERO = "Cursos.txt";
	private Scanner sc = new Scanner(System.in);
	private Verificaciones verif = new Verificaciones();

	public void menu() {
		System.out.println("-- GESTION CURSOS --");

		String op = null;

		do {
			System.out.println("\n Selecciona una opcion: \n 1.Inscribir Cursos \n 2.Borrar Cursos "
					+ "\n 3.Modificar Cursos \n 4.Consultar Cursos \n 5.Mostrar Cursos \n 0.Salir");
			op = sc.nextLine();

			switch (op) {
			case "1":
				alta();
				break;
			case "2":
				baja();
				break;
			case "3":
				modificar();
				break;
			case "4":
				buscar();
				break;
			case "5":
				mostrar();
				break;
			case "0":
				System.out.println("Saliendo... \n");
				break;
			default:
				System.out.println("Operacion no valida, prueba de nuevo.");
			}

		} while (!op.equalsIgnoreCase("0"));
	}

	public void alta() {
		Curso curso;
		String nombre, descripcion;
		int contError = 0;
		boolean fallo = false;
		do {
			fallo = false;
			System.out.println("Introduce el nombre del curso:");
			nombre = sc.nextLine();
			try {
				verif.hayAlgo(nombre);
			} catch (MisExceptions e) {
				System.out.println(e);
				fallo = true;
				contError++;
			}

		} while (fallo && contError < 5);
		
		if (contError < 5) {
			contError = 0;

			do {// Inicio de do while que controla si hay fallo
				fallo = false;
				System.out.println("Introduce la descripcion del Curso:");
				descripcion = sc.nextLine();
				try {
					verif.hayAlgo(descripcion);
				} catch (MisExceptions e) {
					System.out.println(e);
					fallo = true;
					contError++;
				}
			} while (fallo == true && contError != 5);// fin de do while que controla si hay fallo
			if (contError != 5) {

				curso = new Curso( nombre, descripcion);
				guardarFich(curso);
			}

		}
		

	}

	public void baja() {

	}

	public void modificar() {

	}

	public void buscar() {

	}

	public void mostrar() {

	}
	public void guardarFich(Curso curso) {
		
	}
}
