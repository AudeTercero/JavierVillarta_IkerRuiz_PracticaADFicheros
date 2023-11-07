package org.example;

import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {

        System.out.println("-------------------------");
        System.out.println("-- CENTRO DE FORMACION --\n");

        GestionAlumnos gesAlu = new GestionAlumnos();
        GestionCursos gesCur = new GestionCursos();
        GestionProfesores gesProf = new GestionProfesores();
        GestorInscripciones gesIns = new GestorInscripciones();


        Scanner sc = new Scanner(System.in);
        String op = null;

        do {
            System.out.println(
                    "Selecciona una opcion: \n 1.Gestionar Alumnos \n 2.Gestionar Profesores \n 3.Gestionar Cursos \n 4.Gestionar Inscripciones \n 0.Salir");
            op = sc.nextLine();

            switch (op) {
                case "1":
                    gesAlu.menu();
                    break;
                case "2":
                    gesProf.menu();
                    break;
                case "3":
                    gesCur.menu();
                    break;
                case "4":
                    gesIns.menu();
                    break;
                case "0":
                    System.out.println("Saliendo... \n");
                    break;
                default:
                    System.out.println("Operacion no valida, prueba de nuevo.");
            }

        } while (!op.equalsIgnoreCase("0"));

        System.out.println("Fin Centro de Formacion");
        System.out.println("---------------------");

    }
}
