package org.example;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase para gestionar alumnos
 */
public class GestionAlumnos implements ICRUD {
    private Scanner sc = new Scanner(System.in);
    private static Verificaciones verif = new Verificaciones();
    private Fichero fich = new Fichero();

    /**
     * Muestra un menu interactivo para gestionar alumnos.
     */
    public void menu() {
        System.out.println("-- GESTION ALUMNOS --");

        String op;

        do {
            System.out.println("\n Selecciona una opcion: \n 1.Alta Alumnos \n 2.Borrar Alumnos "
                    + "\n 3.Modificar Alumnos \n 4.Buscar Alumnos \n 5.Mostrar Alumnos "
                    + "\n 0.Salir");
            op = sc.nextLine().trim();

            switch (op) {
                case "1":
                    alta();

                    break;
                case "2":
                    if (fich.existeCurso())
                        baja();
                    else
                        System.out.println("Aun no hay alumnos");

                    break;
                case "3":
                    if (fich.existeCurso())
                        modificar();
                    else
                        System.out.println("Aun no hay alumnos");

                    break;
                case "4":
                    if (fich.existeCurso())
                        buscar();
                    else
                        System.out.println("Aun no hay alumnos");

                    break;
                case "5":
                    if (fich.existeCurso())
                        mostrar();
                    else
                        System.out.println("Aun no hay alumnos");

                    break;
                case "0":
                    System.out.println("Saliendo... \n");
                    break;
                default:
                    System.out.println("Operacion no valida, prueba de nuevo.");
            }

        } while (!op.equalsIgnoreCase("0"));
    }

    /**
     * Metodo para crear alumnos
     */
    public void alta() {
        Scanner sc = new Scanner(System.in);
        int cont = 0;
        boolean fallo = false;

        String nom, ape, tel, dir, fech;
        ;

        System.out.println("-ALTA ALUMNOS- \n");

        do {
            fallo = false;
            System.out.println("Introduce el nombre del alumno:");
            nom = sc.nextLine().trim();
            try {
                verif.hayAlgo(nom);

            } catch (MisExceptions e) {
                System.err.println(e.getMessage());
                fallo = true;
                cont++;
            }

        } while (fallo && cont < 5);

        if (cont < 5) {
            cont = 0;
            do {
                fallo = false;
                System.out.println("Introduce el apellido del alumno:");
                ape = sc.nextLine().trim();
                try {
                    verif.hayAlgo(ape);

                } catch (MisExceptions e) {
                    System.err.println(e.getMessage());
                    fallo = true;
                    cont++;
                }

            } while (fallo && cont < 5);

            if (cont < 5) {
                do {
                    fallo = false;
                    System.out.println("Introduce el telefono del alumno:");
                    tel = sc.nextLine();
                    try {
                        verif.nueveCaracteres(tel);
                        verif.esNum(tel);

                    } catch (MisExceptions e) {
                        System.err.println(e.getMessage());
                        fallo = true;
                        cont++;
                    }

                } while (fallo && cont < 5);

                if (cont < 5) {
                    cont = 0;
                    do {
                        fallo = false;
                        System.out.println("Introduce la direccion del alumno:");
                        dir = sc.nextLine().trim();
                        try {
                            verif.hayAlgo(dir);

                        } catch (MisExceptions e) {
                            System.err.println(e.getMessage());
                            fallo = true;
                            cont++;
                        }

                    } while (fallo && cont < 5);

                    if (cont < 5) {
                        cont = 0;
                        do {
                            fallo = false;
                            System.out.println(
                                    "Introduce la fecha de nacimiento del alumno, por favor ingresa la fecha (en formato YYYY-MM-DD):");
                            fech = sc.nextLine();
                            try {
                                verif.esFech(fech);

                            } catch (MisExceptions e) {
                                System.err.println(e.getMessage());
                                fallo = true;
                                cont++;
                            }

                        } while (fallo && cont < 5);

                        if (cont < 5) {

                            Alumno alumno = new Alumno(nom, ape, tel, dir, fech);
                            fich.guardarBin(alumno);

                        } else {
                            System.out.println("Has llegado a 5 intentos, saliendo...");
                        }

                    } else {
                        System.out.println("Has llegado a 5 intentos, saliendo...");
                    }

                } else {
                    System.out.println("Has llegado a 5 intentos, saliendo...");
                }

            } else {
                System.out.println("Has llegado a 5 intentos, saliendo...");
            }

        } else {
            System.out.println("Has llegado a 5 intentos, saliendo...");
        }

    }

    /**
     * Metodo para eliminar alumnos
     */
    public void baja() {
        boolean existe = false;
        boolean salir = false;
        String op;
        ArrayList<Alumno> alumnos = fich.leerBin();
        Alumno eliminado = null;

        if (!alumnos.isEmpty()) {
            do {
                System.out.println("Introduce el nombre del alumno");
                String nom = sc.nextLine().trim();
                System.out.println("Introduce los apellidos del alumno");
                String ape = sc.nextLine().trim();

                for (Alumno alumno : alumnos) {
                    if (alumno.getNombre().equalsIgnoreCase(nom) && alumno.getApellidos().equalsIgnoreCase(ape)) {
                        existe = true;
                        eliminado = alumno;//Guardamos el alumno ya que no podemos eliminarlo mientras iteramos pues puede fallar
                    }
                }

                if (!existe) {
                    System.out.println("El alumno no existe");

                } else {
                    do {
                        System.out.println("Seguro que deseas borrar el alumno? \n [S/N]");
                        op = sc.nextLine();

                        if (op.equalsIgnoreCase("S")) {
                            fich.borrAluCurso(eliminado.getNumExpediente());
                            alumnos.remove(eliminado);
                            fich.guardarBin(alumnos);
                            System.out.println("Alumno Borrado.");
                            salir = true;
                        } else if ((op.equalsIgnoreCase("N"))) {
                            salir = true;
                        } else {
                            System.out.println("Entrada invalida");
                        }

                    } while (!salir);
                }
            } while (!alumnos.isEmpty() && !salir);

        } else {
            System.out.println("No hay alumnos registrados.");
        }
    }

    /**
     * Metodo para modificar alumnos
     */
    public void modificar() {
        boolean existe = false;
        boolean salir = false;
        boolean salir2 = false;
        String op;
        String newNom, newApe, newTel, newDir, newFech;
        ArrayList<Alumno> alumnos = fich.leerBin();

        if (!alumnos.isEmpty()) {
            System.out.println("Introduce el nombre del alumno");
            String nom = sc.nextLine().trim();
            System.out.println("Introduce los apellidos del alumno");
            String ape = sc.nextLine().trim();


            for (Alumno alumno : alumnos) {
                if (alumno.getNombre().equalsIgnoreCase(nom) && alumno.getApellidos().equalsIgnoreCase(ape)) {
                    existe = true;
                    newNom = alumno.getNombre();
                    newApe = alumno.getApellidos();
                    newTel = alumno.getTelefono();
                    newDir = alumno.getDireccion();
                    newFech = alumno.getFechNac();

                    do {
                        System.out.println("Introduce el valor que deseas modificar o pulsa 0 para salir \n"
                                + " 1.Nombre \n 2.Apellidos \n 3.Telefono \n 4.Direccion \n 5.Fecha de Nacimiento");

                        op = sc.nextLine();
                        switch (op) {
                            case "1":
                                System.out.println("Introduce el nuevo nombre:");
                                try {
                                    newNom = sc.nextLine();
                                    verif.hayAlgo(newNom);
                                    salir2 = true;

                                } catch (MisExceptions e) {
                                    System.err.println(e.getMessage());
                                }

                                break;
                            case "2":
                                System.out.println("Introduce los nuevos apellidos:");
                                try {
                                    newApe = sc.nextLine();
                                    verif.hayAlgo(newApe);
                                    salir2 = true;

                                } catch (MisExceptions e) {
                                    System.err.println(e.getMessage());
                                }
                                break;
                            case "3":
                                System.out.println("Introduce el nuevo telefono:");
                                try {
                                    newTel = sc.nextLine();
                                    verif.hayAlgo(newTel);
                                    verif.nueveCaracteres(newTel);
                                    verif.esNum(newTel);
                                    salir2 = true;

                                } catch (MisExceptions e) {
                                    System.err.println(e.getMessage());
                                }
                                break;
                            case "4":
                                System.out.println("Introduce la nueva direccion:");
                                try {
                                    newDir = sc.nextLine();
                                    verif.hayAlgo(newDir);
                                    salir2 = true;

                                } catch (MisExceptions e) {
                                    System.err.println(e.getMessage());
                                }
                                break;
                            case "5":
                                System.out.println("Introduce la nueva fecha:");
                                try {
                                    newFech = sc.nextLine();
                                    verif.hayAlgo(newFech);
                                    verif.esFech(newFech);


                                } catch (MisExceptions e) {
                                    System.err.println(e.getMessage());
                                }
                                break;
                            case "0":
                                salir2 = true;
                                break;

                            default:
                                System.out.println("Eleccion no valida, prueba de nuevo.");
                        }
                    } while (!salir2);
                    do {
                        System.out.println("Seguro que deseas modificar el alumno? \n [S/N}:");
                        op = sc.nextLine();

                        if (op.equalsIgnoreCase("S")) {
                            alumno.setNombre(newNom);
                            alumno.setApellidos(newApe);
                            alumno.setTelefono(newTel);
                            alumno.setDireccion(newDir);
                            alumno.setFechNac(newFech);

                            salir = true;
                        } else if ((op.equalsIgnoreCase("N"))) {
                            salir = true;
                        } else {
                            System.out.println("Entrada invalida");
                        }
                    } while (!salir);
                }
            }

            if (!existe) {
                System.out.println("El alumno no existe");
            } else {
                fich.guardarBin(alumnos);
            }

        } else {
            System.out.println("No hay alumnos registrados");
        }
    }

    /**
     * Metodo para buscar alumnos
     */
    public void buscar() {
        ArrayList<Alumno> alumnos = fich.leerBin();
        if (!alumnos.isEmpty()) {
            System.out.println("Introduce el nombre del alumno");
            String nom = sc.nextLine();
            System.out.println("Introduce los apellidos del alumno");
            String ape = sc.nextLine();
            String nomApe = nom.trim() + ape.trim();
            fich.mostrarUnoBin(nomApe);
        } else {
            System.out.println("No hay alumnos registrados.");
        }
    }

    /**
     * Metodo para mostrar alumnos
     */
    public void mostrar() {
        fich.mostrarBin();
    }


}
