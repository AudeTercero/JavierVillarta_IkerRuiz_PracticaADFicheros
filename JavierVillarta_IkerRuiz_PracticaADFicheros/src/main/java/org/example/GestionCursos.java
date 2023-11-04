package org.example;

import java.io.*;
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
				System.err.println(e.getMessage());
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
					System.err.println(e.getMessage());
					fallo = true;
					contError++;
				}
			} while (fallo && contError != 5);// fin de do while que controla si hay fallo
			if (contError != 5) {

				curso = new Curso(nombre, descripcion);
				guardarFich(curso);
				try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(FICHERO,true)))){
					pw.write("CodigoCurso: "+curso.getCodCur());
					pw.write("\n");
					pw.write("Nombre: "+nombre);
					pw.write("\n");
					pw.write("Descripcion: "+descripcion);
					pw.write("\n");
					pw.write("Profesor: ");
					pw.write("\n");
					pw.write("Alumnos: ");
					pw.write("\n");
					System.out.println("Curso guardado correctamente");
				}catch(IOException e){
					e.printStackTrace();

				}
			}

		}
		

	}

	public void baja() {
		System.out.println("Introduzca el nombre de curso");
		String nombreCurso = sc.nextLine();

	}

	public void modificar() {
		System.out.println("Introduzca el nombre de curso");
		String nombreCurso = sc.nextLine();

	}

	public void buscar() {
		System.out.println("Introduzca el nombre de curso");
		String nombreCurso = sc.nextLine();
		String cod, nom, des, prof, alu;
		boolean encontrado = false;
		try(BufferedReader br = new BufferedReader(new FileReader(FICHERO))){
			while((cod = br.readLine())!=null){
				nom = br.readLine();
				des = br.readLine();
				prof = br.readLine();
				alu = br.readLine();
				if((nom.split(":")[1].trim()).equalsIgnoreCase(nombreCurso.trim())) {
					System.out.println("****CURSO*****");
					System.out.println(cod);
					System.out.println(nom);
					System.out.println(des);
					System.out.println(prof);
					System.out.println(alu);
					encontrado = true;
				}
			}
		}catch(IOException e){
			e.printStackTrace();

		}
		if(!encontrado){
			System.out.println("Curso no encontrado");
		}

	}

	public void mostrar() {
		String cod;
		try(BufferedReader br = new BufferedReader(new FileReader(FICHERO))){
			while((cod = br.readLine())!=null){
				System.out.println("****CURSO*****");
				System.out.println(cod);
				System.out.println(br.readLine());
				System.out.println(br.readLine());
				System.out.println(br.readLine());
				System.out.println(br.readLine());
			}
		}catch(IOException e){
			e.printStackTrace();

		}

	}
	public void guardarFich(Curso curso) {
		
	}
}
