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
        System.out.println("Escriba el nombre del alumno");
        String nom = sc.nextLine();
        System.out.println("Escriba el apellido del alumno");
        String ape = sc.nextLine();
        String nomApe = nom.trim() + ape.trim();
        int idAlu = fich.existAlu(nomApe);
        if (idAlu!=-1) {
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
                        System.out.println("Seguro que quieres inscribir a " + nom + " al curso de " + curso.getNombre() + "? \n [S/N]");
                        op = sc.nextLine();

                        if (op.equalsIgnoreCase("S")) {
                            cursos.remove(curso);
                            curso.addAlumno(idAlu);
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
                System.out.println("No hay ningun alumno con ese nombre y apellido");
            }
        }


    }

    public void inscribirProfesor() {
        String op;
        System.out.println("Escriba el DNI del profesor");
        String dni = sc.nextLine();

        if (fich.existProfe(dni) != null) {
            String nom = fich.existProfe(dni);
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
                        System.out.println("Seguro que quieres vincular a " + nom + " al curso de " + curso.getNombre() + " como profesor? \n [S/N]");
                        op = sc.nextLine();

                        if (op.equalsIgnoreCase("S")) {
                            cursos.remove(curso);
                            curso.setProfe(nom + " " + dni);
                            cursos.add(curso);
                            System.out.println("Profesor inscrito con exito!");

                        } else if (op.equalsIgnoreCase("N")) {
                            System.out.println("Saliendo...");
                        } else {
                            System.out.println("Eleccion no valida, prueba de nuevo.");
                        }
                    } while (!op.equalsIgnoreCase("s") && !op.equalsIgnoreCase("n"));

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
        int idAlu = fich.existAlu(nomAlu);
        if (idAlu!=-1) {
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
                            if (curso.removeAlu(idAlu)) {
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

        if (fich.existProfe(dni) != null) {
            String nom = fich.existProfe(dni);
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
                        System.out.println("Seguro que quieres desvincular a " + nom + " del curso de " + c.getNombre() + " como profesor? \n [S/N]");
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
                }

            }

            fich.guardarText(cursos);
        } else {
            System.out.println("No hay ningun profesor con ese DNI");
        }

    }


}
