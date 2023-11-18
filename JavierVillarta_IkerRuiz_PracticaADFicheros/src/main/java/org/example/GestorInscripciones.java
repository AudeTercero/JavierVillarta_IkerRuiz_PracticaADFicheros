package org.example;


import java.util.ArrayList;
import java.util.Scanner;

public class GestorInscripciones {
    private static Scanner sc = new Scanner(System.in);
    private Fichero fich = new Fichero();


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

    public void inscribirAlumno() {
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

            if (!cursos.isEmpty()) {
                for (Curso c : cursos) {
                    if (cur.equalsIgnoreCase(c.getNombre())) {
                        curso = c;
                    }
                }
                if (curso != null) {
                    alumnos = curso.getAlumnos();
                    if (alumnos.isEmpty()) {
                        puedeGuardar = true;
                    } else {
                        for (String a : alumnos) {
                            if (a.equalsIgnoreCase(nom + " " + ape)) {
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
                    System.out.println("Seguro que quieres inscribir a " + nom + " al curso de " + curso.getNombre() + "? \n [S/N]");
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


    public void inscribirProfesor() {
        String op;
        System.out.println("Escriba el DNI del profesor");
        String dni = sc.nextLine();
        Profesor profe = fich.existProfe(dni);

        if (profe != null) {

            System.out.println("Escriba el nombre del curso");
            String cur = sc.nextLine();
            Curso curso = null;
            ArrayList<Curso> cursos = fich.leerText();
            if (!cursos.isEmpty()) {
                for (Curso c : cursos) {
                    if (cur.equalsIgnoreCase(c.getNombre())) {
                        curso = c;
                    }
                }

                if (curso != null) {
                    do {
                        System.out.println("Seguro que quieres vincular a " + profe.getNombre() + " con DNI: " + profe.getDni() + " al curso de " + curso.getNombre() + " como profesor? \n [S/N]");
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

                } else {
                    System.out.println("No hay ningun curso con ese nombre");
                }


                fich.guardarText(cursos);
            } else {
                System.out.println("No hay ningun profesor con ese DNI");
            }
        }

    }

    public void bajaAlumno() {
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

    public void bajaProfesor() {
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
