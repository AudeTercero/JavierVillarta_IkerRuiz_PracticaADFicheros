package org.example;


import java.util.ArrayList;
import java.util.Scanner;

public class GestorInscripciones {
    private static Scanner sc = new Scanner(System.in);
    private Fichero fich = new Fichero();

    /**
     * Metodo de menu que llama a los otros metodos
     */
    public void menu() {
        System.out.println("-- GESTION INSCRIPCIONES --");

        String op = null;

        do {
            System.out.println("\n Selecciona una opcion: \n 1.Inscribir Alumno en Curso \n 2.Inscribir Profesor en curso "
                    + "\n 3.Dar de baja Alumno de curso \n 4.Dar de baja profesor de Curso \n 0.Salir");
            op = sc.nextLine();

            switch (op) {
                case "1":
                    inscribirAlumno();
                    break;
                case "2":
                    inscribirProfesor();
                    break;
                case "3":
                    bajaAlumno();
                    break;
                case "4":
                    bajaProfesor();
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
     * Metodo para inscribir un alumno en un curso
     */
    private void inscribirAlumno() {
        String op;
        boolean puedeGuardar = false;
        boolean repe = false;
        ArrayList<String> alumnos;
        System.out.println("Escriba el nombre del alumno");
        String nom = sc.nextLine();
        System.out.println("Escriba el apellido del alumno");
        String ape = sc.nextLine();
        String nomApe = nom.trim() + ape.trim();
        Alumno alu = fich.existAlu(nomApe);

        if (alu != null) {
            System.out.println("Escriba el nombre del curso");
            String cur = sc.nextLine();
            //mirar si hay algo escrito y si existe el curso
            Curso curso = null;
            ArrayList<Curso> cursos = fich.leerText();

            //Comprobamos que haya cursos
            if (!cursos.isEmpty()) {
                for (Curso c : cursos) {
                    //Comprobamos que el curso solicitado exista
                    if (cur.equalsIgnoreCase(c.getNombre())) {
                        curso = c;
                    }
                }
                //Comprobamos que el curso exista
                if (curso != null) {
                    alumnos = curso.getAlumnos();
                    //Comprobamos que el curso tenga alumnos guardados
                    if (alumnos.isEmpty()) {
                        puedeGuardar = true;
                    } else {
                        for (String a : alumnos) {
                            //Comprobamos que ningun ID de alumno guardado en el curso sea el mismo que el del alumno a guardar
                            if (a.equalsIgnoreCase(String.valueOf(alu.getNumExpediente()))) {
                                System.out.println("El alumno ya esta registrado en este curso");
                                repe = true;
                            }
                        }
                        if (!repe) {
                            puedeGuardar = true;
                        }
                    }
                } else {
                    System.out.println("No hay un curso con ese nombre.");
                }
            } else {
                System.out.println("No hay un curso con ese nombre.");
            }
            if (puedeGuardar) {
                do {
                    System.out.println("Seguro que quieres inscribir a " + nom
                            + " al curso de " + curso.getNombre()
                            + "? \n [S/N]");
                    op = sc.nextLine();

                    if (op.equalsIgnoreCase("S")) {
                        cursos.remove(curso);
                        curso.addAlumno(alu.getNumExpediente());
                        cursos.add(curso);
                        System.out.println("Alumno inscrito con exito!");

                    } else if (op.equalsIgnoreCase("N")) {
                        System.out.println("Saliendo...");
                    } else {
                        System.out.println("Eleccion no valida, prueba de nuevo.");
                    }
                } while (!op.equalsIgnoreCase("s") && !op.equalsIgnoreCase("n"));
            }

            fich.guardarText(cursos);

        } else {
            System.out.println("No hay un alumno con ese nombre.");
        }
    }

    /**
     * Metodo para inscribir un profesor en un curso
     */
    private void inscribirProfesor() {
        String op;
        boolean puedeGuardar = false;
        boolean registrado = false;

        System.out.println("Escriba el DNI del profesor");
        String dni = sc.nextLine();
        Profesor profe = fich.existProfe(dni);



        if (profe != null) {

            System.out.println("Escriba el nombre del curso");
            String cur = sc.nextLine();
            Curso curso = null;
            ArrayList<Curso> cursos = fich.leerText();

            //Comprobamos que hay cursos
            if (!cursos.isEmpty()) {
                for (Curso c : cursos) {
                    //Comprobamos que existe el curso solicitado
                    if (cur.equalsIgnoreCase(c.getNombre())) {
                        curso = c;
                    }
                }

                //Comprobamos que el curso no es nulo
                if (curso != null) {
                    //Comprobamos si tiene profesor registrado
                    if (curso.getProfe().isEmpty()) {
                        puedeGuardar = true;
                    } else {
                        //Comprobamos si el profesor ya esta registrado en ese curso
                        if (curso.getProfe().equalsIgnoreCase(String.valueOf(profe.getIdProfe()))) {
                            System.out.println("El profesor ya esta registrado en este curso");
                            registrado = true;
                        } else {
                            do {
                                System.out.println("Ya hay un profesor registrado en este curso, lo quieres sobreescribir? [S/N]");
                                op = sc.nextLine();

                                if (op.equalsIgnoreCase("S")) {
                                    cursos.remove(curso);
                                    curso.setProfe("" + profe.getIdProfe());
                                    cursos.add(curso);
                                    System.out.println("Profesor inscrito con exito!");
                                    puedeGuardar = false;
                                    registrado = true;

                                } else if (op.equalsIgnoreCase("N")) {
                                    puedeGuardar = false;

                                } else {
                                    System.out.println("Eleccion no valida, prueba de nuevo.");
                                }

                            } while (!op.equalsIgnoreCase("s") && !op.equalsIgnoreCase("n"));

                        }
                        if (!registrado) {
                            puedeGuardar = true;
                        }
                    }

                    if (puedeGuardar) {
                        do {
                            System.out.println("Seguro que quieres vincular a " + profe.getNombre()
                                    + " con DNI: " + profe.getDni()
                                    + " al curso de " + curso.getNombre()
                                    + " como profesor? \n [S/N]");
                            op = sc.nextLine();

                            if (op.equalsIgnoreCase("S")) {

                                cursos.remove(curso);
                                curso.setProfe("" + profe.getIdProfe());
                                cursos.add(curso);
                                System.out.println("Profesor inscrito con exito!");

                            } else if (op.equalsIgnoreCase("N")) {
                                System.out.println("Saliendo...");
                            } else {
                                System.out.println("Eleccion no valida, prueba de nuevo.");
                            }
                        } while (!op.equalsIgnoreCase("s") && !op.equalsIgnoreCase("n"));
                    }

                } else {
                    System.out.println("No hay ningun curso con ese nombre");
                }
                //Guardamos los cambios
                fich.guardarText(cursos);

            } else {
                System.out.println("No hay ningun profesor con ese DNI");
            }
        }else{
            System.out.println("No se ha encontrado un profesor con ese dni");
        }
    }

    /**
     * Metodo para dar de baja profesor en un curso
     */
    private void bajaAlumno() {
        String op;
        System.out.println("Introduce el nombre del alumno que deseas borrar");
        String nom = sc.nextLine();
        System.out.println("Introduce los apellidos del alumno que deseas borrar");
        String ape = sc.nextLine();

        String nomAlu = nom.trim() + ape.trim();
        Alumno alu = fich.existAlu(nomAlu);
        if (alu != null) {
            System.out.println("Escriba el nombre del curso");
            String cur = sc.nextLine();
            Curso curso = null;
            ArrayList<Curso> cursos = fich.leerText();
            for (Curso c : cursos) {
                if (cur.equalsIgnoreCase(c.getNombre())) {
                    curso = c;
                }

                if (curso != null) {
                    do {
                        System.out.println("Seguro que quieres eliminar a " + nom + " del curso de " + c.getNombre() + "? \n [S/N]");
                        op = sc.nextLine();

                        if (op.equalsIgnoreCase("S")) {
                            cursos.remove(curso);
                            if (curso.removeAlu(alu.getNumExpediente())) {
                                cursos.add(curso);
                                System.out.println("Alumno borrado con exito!");
                            }

                        } else if (op.equalsIgnoreCase("N")) {
                            System.out.println("Saliendo...");
                        } else {
                            System.out.println("Eleccion no valida, prueba de nuevo.");
                        }
                    } while (!op.equalsIgnoreCase("s") && !op.equalsIgnoreCase("n"));
                }

            }


            fich.guardarText(cursos);

        } else {
            System.out.println("No hay ningun alumno con ese nombre y apellido");
        }
    }

    /**
     * Metodo para dar de baja profesor en un curso
     */
    private void bajaProfesor() {
        String op;
        System.out.println("Escriba el DNI del profesor");
        String dni = sc.nextLine();
        Profesor profe = fich.existProfe(dni);
        if (profe != null) {

            System.out.println("Escriba el nombre del curso");
            String cur = sc.nextLine();
            Curso curso = null;
            ArrayList<Curso> cursos = fich.leerText();
            for (Curso c : cursos) {
                if (cur.equalsIgnoreCase(c.getNombre())) {
                    curso = c;
                }

                if (curso != null) {
                    int profCursId = Integer.parseInt(curso.getProfe().trim());
                    if (profCursId == profe.getIdProfe()) {
                        do {
                            System.out.println("Seguro que quieres desvincular a " + profe.getNombre() + " con DNI: " + profe.getDni() + " del curso de " + c.getNombre() + " como profesor? \n [S/N]");
                            op = sc.nextLine();

                            if (op.equalsIgnoreCase("S")) {
                                cursos.remove(curso);
                                curso.setProfe("");
                                cursos.add(curso);
                                System.out.println("Profesor desvinculado con exito!");

                            } else if (op.equalsIgnoreCase("n")) {
                                System.out.println("Saliendo...");
                            } else {
                                System.out.println("Eleccion no valida, prueba de nuevo.");
                            }
                        } while (!op.equalsIgnoreCase("s") && !op.equalsIgnoreCase("n"));
                    } else {
                        System.out.println("El profesor que quiere borrar no esta en ese curso");
                    }
                } else {
                    System.out.println("Ese curso no existe");
                }

            }

            fich.guardarText(cursos);
        } else {
            System.out.println("No hay ningun profesor con ese DNI");
        }

    }


}
