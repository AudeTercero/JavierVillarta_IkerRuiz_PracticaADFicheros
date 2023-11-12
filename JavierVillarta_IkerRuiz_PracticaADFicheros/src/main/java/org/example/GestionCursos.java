package org.example;


import java.util.ArrayList;
import java.util.Scanner;


/**
 * Clase que implementa operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para gestionar cursos.
 */
public class GestionCursos implements CRUD {
    private Fichero fich = new Fichero();
    private Scanner sc = new Scanner(System.in);
    private Verificaciones verif = new Verificaciones();

    /**
     * Muestra un menu interactivo para gestionar cursos.
     */
    public void menu() {
        System.out.println("-- GESTION CURSOS --");

        String op ;

        do {
            System.out.println("\n Selecciona una opcion: \n 1.Crear Cursos \n 2.Borrar Cursos "
                    + "\n 3.Modificar Cursos \n 4.Buscar Cursos \n 5.Mostrar Cursos \n 0.Salir");
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

    /**
     * Metodo para crear cursos
     */
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
                fich.cursRepe(nombre);
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

                fich.guardarText(curso);
            }
        }
    }

    /**
     * Metodo para eliminar cursos
     */
    public void baja() {
        String op;
        boolean existe = false;
        System.out.println("Introduzca el nombre de curso");
        String nombreCurso = sc.nextLine();
        Curso curso = null;
        ArrayList<Curso> cursos = fich.leerText();

        if (!cursos.isEmpty()) {

            for (Curso c : cursos) {
                if (c.getNombre().equalsIgnoreCase(nombreCurso.trim())) {
                    curso = c;
                    existe = true;
                }
            }
            if (existe) {
                do {
                    System.out.println("Seguro que quieres eliminar este curso? \n [S/N]");
                    op = sc.nextLine();

                    if (op.equalsIgnoreCase("s")) {
                        System.out.println("Curso borrado con exito!");
                        cursos.remove(curso);


                    } else if (op.equalsIgnoreCase("n")) {
                        System.out.println("Saliendo...");

                    } else {
                        System.out.println("Eleccion no valida, prueba de nuevo.");
                    }
                } while (!op.equalsIgnoreCase("S") && !op.equalsIgnoreCase("n"));
            } else {
                System.out.println("El curso solicitado no existe");
            }

            fich.guardarText(cursos);

        } else {
            System.out.println("Aun no hay cursos guardados");
        }
    }

    /**
     * Metodo para modificar cursos
     */
    public void modificar() {
        System.out.println("Introduzca el nombre de curso");
        String nombreCurso = sc.nextLine();
        ArrayList<Curso> cursos = fich.leerText();
        Curso curso = null;


        if (!cursos.isEmpty()) {
            for (Curso c : cursos) {
                if (nombreCurso.trim().equalsIgnoreCase(c.getNombre())) {
                    curso = c;
                }
            }

            cursos.remove(curso);
            String opc;
            do {
                System.out.println(
                        "****MENU MODIFICACION PARA EL CURSO: " + curso.getNombre() + "****");
                System.out.println(
                        "1. Modificar Nombre \n2. Modificar descripcion \n0. Salir \n");
                opc = sc.nextLine();
                switch (opc) {
                    case "1":
                        boolean nombreBien = true;
                        boolean nombreExist = false;
                        String oldNombre = curso.getNombre();
                        String newNombre;
                        do {
                            System.out.println("Introduzca el nuevo nombre:");
                            newNombre = sc.nextLine();
                            for (Curso c : cursos) {
                                if (newNombre.equalsIgnoreCase(c.getNombre())) {
                                    nombreExist = true;
                                }
                            }
                            if (!nombreExist) {
                                try {
                                    verif.hayAlgo(newNombre);
                                } catch (MisExceptions e) {
                                    System.err.println(e.getMessage());
                                    nombreBien = false;
                                }

                            } else {
                                System.out.println("Ese dni ya existe");
                                nombreBien = false;
                            }

                        } while (!nombreBien);
                        curso.setNombre(newNombre);
                        System.out.println("Se ha modificado el nombre " + oldNombre + " por el dni " + newNombre);
                        break;
                    case "2":
                        String oldDes = curso.getDescripcion();
                        String newDes;
                        boolean nomBien = true;
                        do {
                            System.out.println("Introduzca la nueva descripcion:");
                            newDes = sc.nextLine();
                            try {
                                verif.hayAlgo(newDes);
                            } catch (MisExceptions e) {
                                System.err.println(e.getMessage());
                                nomBien = false;
                            }
                        } while (!nomBien);
                        curso.setDescripcion(newDes);
                        System.out.println("Se ha modificado la descripcion: " + oldDes + " por la descripcion: " + newDes);
                        break;

                    case "0":

                        System.out.println("Saliendo");
                        break;
                    default:
                        System.err.println("Error, opcion inexistente");
                }

            } while (!opc.equalsIgnoreCase("0"));


            cursos.add(curso);
            fich.guardarText(cursos);
        } else {
            System.out.println("Aun no hay cursos guardados");
        }
    }

    /**
     * Metodo para buscar cursos
     */
    public void buscar() {
        System.out.println("Introduzca el nombre de curso");
        String nombreCurso = sc.nextLine();
        fich.mostrarUnoText(nombreCurso);

    }

    /**
     * Metodo para mostrar cursos
     */
    public void mostrar() {
       fich.mostrarText();

    }


}
