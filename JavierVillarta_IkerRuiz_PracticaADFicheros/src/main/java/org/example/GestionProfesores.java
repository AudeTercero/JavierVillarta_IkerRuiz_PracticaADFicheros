package org.example;

import java.util.Scanner;

import java.util.ArrayList;

/**
 * Clase que implementa operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para gestionar profesores.
 */
public class GestionProfesores implements ICRUD {

	private static Scanner sc = new Scanner(System.in);
	private static Verificaciones verif = new Verificaciones();
	private Fichero fich = new Fichero();

	/**
	 * Muestra un menu interactivo para gestionar profesores.
	 */
	public void menu() {
		System.out.println("-- GESTION PROFESORES --");

		String op ;

		do {
			System.out.println(
					"\n Selecciona una opcion: \n 1.Alta Profesores \n 2.Borrar Profesores \n 3.Modificar Profesores \n 4.Buscar Profesores \n 5.Mostrar Profesores \n 0.Salir");
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
				System.err.println("Operacion no valida, prueba de nuevo.");
			}

		} while (!op.equalsIgnoreCase("0"));
	}

	/**
	 * Realiza la operacion de dar de alta a un profesor.
	 */
	public void alta() {
		Profesor profe;
		String dni, nombre, direccion, telefono;
		int contError = 0;
		boolean fallo = false;

		do {// Inicio de do while que controla si hay fallo
			fallo = false;
			System.out.println("Introduce el dni del Profesor:");
			dni = sc.nextLine();
			try {
				verif.hayAlgo(dni);
			} catch (MisExceptions e) {
				System.err.println(e.getMessage());
				fallo = true;
				contError++;
			}
		} while (fallo && contError != 5);// fin de do while que controla si hay fallo

		if (contError < 5) {
			contError = 0;

			do {// Inicio de do while que controla si hay fallo
				fallo = false;
				System.out.println("Introduce el nombre del Profesor:");
				nombre = sc.nextLine();
				try {
					verif.hayAlgo(nombre);
				} catch (MisExceptions e) {
					System.err.println(e.getMessage());
					fallo = true;
					contError++;
				}
			} while (fallo && contError != 5);// fin de do while que controla si hay fallo

			if (contError < 5) {

				contError = 0;
				do {// Inicio de do while que controla si hay fallo
					fallo = false;
					System.out.println("Introduce la direccion del Profesor:");
					direccion = sc.nextLine();
					try {
						verif.hayAlgo(direccion);
					} catch (MisExceptions e) {
						System.err.println(e.getMessage());
						fallo = true;
						contError++;
					}
				} while (fallo && contError != 5);// Fin de do while que controla si hay fallo

				if (contError < 5) {

					contError = 0;
					/**
					 * Inicio de do while que controla si hay fallo
					 */
					do {
						fallo = false;
						System.out.println("Introduce el telefono del Profesor:");
						telefono = sc.nextLine();
						try {
							verif.esNum(telefono);
							verif.nueveCaracteres(telefono);
						} catch (MisExceptions e) {
							System.err.println(e.getMessage());
							fallo = true;
							contError++;
						}
					} while (fallo && contError != 5);// Fin de do while que controla si hay fallo

					if (contError != 5) {

						profe = new Profesor(dni, nombre, direccion, telefono);
						fich.guardarSer(profe);
					}

				} else {
					System.out.println("Se han superado el maximo de errores permitidos(5)");
				}

			} else {
				System.out.println("Se han superado el maximo de errores permitidos(5)");
			}

		} else {
			System.out.println("Se han superado el maximo de errores permitidos(5)");
		}

	}

	/**
	 * Metodo para dar de baja a profesores
	 */
	public void baja() {


		ArrayList<Profesor> profesores = fich.leerSer();
		String dni = null;

		if (!profesores.isEmpty()) {
			do {
				boolean exist = false;

				profesores = fich.leerSer();
				if (!profesores.isEmpty()) {
					System.out.println("Introduce el dni del Profesor que quiera dar de baja o pulsa 0 para salir");
					dni = sc.nextLine();
					try {
						verif.hayAlgo(dni);
						if (!dni.equalsIgnoreCase("0")) {
							for (Profesor p : profesores) {
								if (dni.equalsIgnoreCase(p.getDni())) {
									exist = true;
								}
							}
							if (exist) {
								String opc;
								do {
									System.out.println("Seguro que quiere eliminar el profesor con el dni: " + dni+". \n [S/N]");
									opc = sc.nextLine();
									if (opc.equalsIgnoreCase("S")) {

										fich.borrarUnoSer(dni);

									} else if (opc.equalsIgnoreCase("N")) {
										System.out.println("Porfesor no eliminado");

									} else {
										System.err.println("Error, no hay esa opcion");
									}

								} while (!opc.equalsIgnoreCase("S") && !opc.equalsIgnoreCase("N"));

							} else {
								System.err.println("Dni no encontrado");
							}
						}

					} catch (MisExceptions e) {
						System.err.println(e.getMessage());
					}
				} else {
					System.out.println("Ya no quedan mas profesores");
					dni = "0";
				}

			} while (!dni.equalsIgnoreCase("0"));
		} else {
			System.out.println("No hay profesores guardados");
		}

	}

	/**
	 * Metodo para modificar la informacion de un profesor
	 */
	public void modificar() {
		ArrayList<Profesor> profesores = fich.leerSer();
		Profesor profeAux = null;
		String dni;
		if (!profesores.isEmpty()) {// Comprobamos que hay profesores guardados
			do {
				boolean salir = false;
				do {
					System.out.println("Introduce el dni del profesor a modificar o 0 para salir");
					dni = sc.nextLine();
					if (dni.equalsIgnoreCase("0")) {
						salir = true;
					} else {
						try {

							verif.hayAlgo(dni);
							for (Profesor p : profesores) {
								if (dni.equalsIgnoreCase(p.getDni())) {
									profeAux = p;

								}
							}
							do {
								System.out.println("Quiere modificar al profesor " + profeAux.getNombre() + " con dni "
										+ profeAux.getDni() + " \n [S/N]");
								String confirm = sc.nextLine();
								if (confirm.equalsIgnoreCase("S")) {
									salir = true;

								} else if (confirm.equalsIgnoreCase("N")) {
									salir = true;
									dni = "0";
									System.out.println("Modificacion cancelada");

								} else {
									System.out.println("Error, no es ningun de las opciones ofrecidas");
								}
							} while (!salir && !dni.equalsIgnoreCase("0"));

						} catch (MisExceptions mi) {
							System.err.println(mi.getMessage());

						}
					}

				} while (!salir && !dni.equalsIgnoreCase("0"));

				if (!dni.equalsIgnoreCase("0")) {

					profesores.remove(profeAux);
					for (Profesor p : profesores) {
						System.out.println(p.getDni());
					}
					String opc;
					do {
						System.out.println(
								"****MENU MODIFICACION PARA EL PROFESOR CON DNI: " + profeAux.getDni() + "****");
						System.out.println(
								"1. Modificar Dni \n2. Modificar Nombre \n3. Modificar direccion \n4. Modificar telefono \n0. Salir \n");
						opc = sc.nextLine();
						switch (opc) {
						case "1":
							boolean dniBien = true;
							boolean dniExist = false;
							String oldDni = profeAux.getDni();
							String newDni;
							do {
								System.out.println("Introduzca el nuevo dni:");
								newDni = sc.nextLine();
								for (Profesor p : profesores) {
									if (newDni.equalsIgnoreCase(p.getDni())) {
										dniExist = true;
									}
								}
								if (!dniExist) {
									try {
										verif.hayAlgo(newDni);
									} catch (MisExceptions e) {
										System.err.println(e.getMessage());
										dniBien = false;
									}

								} else {
									System.out.println("Ese dni ya existe");
									dniBien = false;
								}

							} while (!dniBien);
							profeAux.setDni(newDni);
							System.out.println("Se ha modificado el dni " + oldDni + " por el dni " + newDni);
							break;
						case "2":
							String oldNom = profeAux.getNombre();
							String newNom;
							boolean nomBien = true;
							do {
								System.out.println("Introduzca el nuevo nombre:");
								newNom = sc.nextLine();
								try {
									verif.hayAlgo(newNom);
								} catch (MisExceptions e) {
									System.err.println(e.getMessage());
									nomBien = false;
								}
							} while (!nomBien);
							profeAux.setNombre(newNom);
							System.out.println("Se ha modificado el nombre " + oldNom + " por el nombre " + newNom);
							break;
						case "3":
							boolean direBien = true;
							String oldDire = profeAux.getDireccion();
							String newDire;
							do {
								System.out.println("Introduzca la nuevo direccion:");
								newDire = sc.nextLine();
								try {
									verif.hayAlgo(newDire);
								} catch (MisExceptions e) {
									System.err.println(e.getMessage());
									direBien = false;
								}

							} while (!direBien);
							profeAux.setDireccion(newDire);
							System.out.println(
									"Se ha modificado la direccion " + oldDire + " por la direccion " + newDire);
							break;
						case "4":
							boolean telBien = true;
							String oldTel = profeAux.getTelefono();
							String newTel;
							do {
								System.out.println("Introduzca el nuevo telefono:");
								newTel = sc.nextLine();
								try {
									verif.nueveCaracteres(newTel);
									verif.esNum(newTel);
								} catch (MisExceptions e) {
									System.err.println(e.getMessage());
									telBien = false;
								}
							} while (!telBien);
							profeAux.setTelefono(newTel);
							System.out.println("Se ha modificado el telefono " + oldTel + " por el telefono " + newTel);
							break;
						case "0":

							System.out.println("Saliendo");
							break;
						default:
							System.err.println("Error, opcion inexistente");
						}

					} while (!opc.equalsIgnoreCase("0"));

					profesores.add(profeAux);

					fich.guardarSer(profesores);
				}

			} while (!dni.equalsIgnoreCase("0"));
		} else {
			System.out.println("No hay Profesores guardados");
		}

	}

	/**
	 * Metodo para buscar profesores
	 */
	public void buscar() {
		String dni;

		do {
			System.out.println("Introduce el dni del profesor que quiera buscar o pulsa 0 para salir");
			dni = sc.nextLine();
			fich.mostrarUnoSer(dni.trim());
		} while (!dni.equalsIgnoreCase("0"));

	}

	/**
	 * Metodo que muestra todos los profesores del fichero
	 */
	public void mostrar() {
		fich.mostrarSer();

	}
}
