package org.example;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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

                try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(FICHERO, true)))) {
                    pw.write("CodigoCurso: " + curso.getCodCur());
                    pw.write("\n");
                    pw.write("Nombre: " + nombre);
                    pw.write("\n");
                    pw.write("Descripcion: " + descripcion);
                    pw.write("\n");
                    pw.write("Profesor: ");
                    pw.write("\n");
                    pw.write("Alumnos: ");
                    pw.write("\n");
                    System.out.println("Curso guardado correctamente");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void baja() {
        String op;
        boolean existe = false;
        System.out.println("Introduzca el nombre de curso");
        String nombreCurso = sc.nextLine();
        ArrayList<Curso> cursos = leerFich();

        if (!cursos.isEmpty()) {

            try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(FICHERO)))) {
                for (Curso c : cursos) {
                    if (!c.getNombre().equalsIgnoreCase(nombreCurso.trim())) {
                        pw.write("CodigoCurso: " + c.getCodCur());
                        pw.write("\n");
                        pw.write("Nombre: " + c.getNombre());
                        pw.write("\n");
                        pw.write("Descripcion: " + c.getDescripcion());
                        pw.write("\n");
                        pw.write("Profesor: " + c.getProfe());
                        pw.write("\n");
                        pw.write("Alumnos: " + c.getAlumnos());
                        pw.write("\n");

                    } else {
                        existe = true;
                        do {
                            System.out.println("Seguro que quieres eliminar este curso? \n [S/N]");
                            op = sc.nextLine();

                            if (op.equalsIgnoreCase("s")) {
                                System.out.println("Curso borrado con exito!");

                            } else if (op.equalsIgnoreCase("n")) {
                                System.out.println("Saliendo...");

                                pw.write("CodigoCurso: " + c.getCodCur());
                                pw.write("\n");
                                pw.write("Nombre: " + c.getNombre());
                                pw.write("\n");
                                pw.write("Descripcion: " + c.getDescripcion());
                                pw.write("\n");
                                pw.write("Profesor: " + c.getProfe());
                                pw.write("\n");
                                pw.write("Alumnos: " + c.getAlumnos());
                                pw.write("\n");
                            } else {
                                System.out.println("Eleccion no valida, prueba de nuevo.");
                            }
                        } while (!op.equalsIgnoreCase("S") && !op.equalsIgnoreCase("n"));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Aun no hay cursos guardados");
        }

        if (!existe){
            System.out.println("El curso solicitado no existe");
        }


    }

    public void modificar() {
        System.out.println("Introduzca el nombre de curso");
        String nombreCurso = sc.nextLine();
        ArrayList<Curso> cursos = leerFich();
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
            try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(FICHERO)))) {
                for (Curso c : cursos) {

                    pw.write("CodigoCurso: " + c.getCodCur());
                    pw.write("\n");
                    pw.write("Nombre: " + c.getNombre());
                    pw.write("\n");
                    pw.write("Descripcion: " + c.getDescripcion());
                    pw.write("\n");
                    pw.write("Profesor: " + c.getProfe());
                    pw.write("\n");
                    pw.write("Alumnos: " + c.getAlumnos());
                    pw.write("\n");


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Aun no hay cursos guardados");
        }
    }

    public void buscar() {
        System.out.println("Introduzca el nombre de curso");
        String nombreCurso = sc.nextLine();
        String cod, nom, des, prof, alu;
        boolean encontrado = false;
        try (BufferedReader br = new BufferedReader(new FileReader(FICHERO))) {
            while ((cod = br.readLine()) != null) {
                nom = br.readLine();
                des = br.readLine();
                prof = br.readLine();
                alu = br.readLine();
                if ((nom.split(":")[1].trim()).equalsIgnoreCase(nombreCurso.trim())) {
                    System.out.println("****CURSO*****");
                    System.out.println(cod);
                    System.out.println(nom);
                    System.out.println(des);
                    System.out.println(prof);
                    System.out.println(alu);
                    encontrado = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        if (!encontrado) {
            System.out.println("Curso no encontrado");
        }

    }

    public void mostrar() {
        String cod;
        try (BufferedReader br = new BufferedReader(new FileReader(FICHERO))) {
            while ((cod = br.readLine()) != null) {
                System.out.println("****CURSO*****");
                System.out.println(cod);
                System.out.println(br.readLine());
                System.out.println(br.readLine());
                System.out.println(br.readLine());
                System.out.println(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public ArrayList<Curso> leerFich() {
        File file = new File(FICHERO);
        ArrayList<Curso> listCursos = new ArrayList<>();
        String cod, nom, des, prof, alu;
        Curso curso;
        ArrayList<String> auxAlu = new ArrayList<>();
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(FICHERO))) {
                while ((cod = br.readLine()) != null) {
                    cod = cod.split(":")[1].trim();
                    nom = br.readLine().split(":")[1].trim();
                    des = br.readLine().split(":")[1].trim();
                    prof = br.readLine().split(":")[1].trim();
                    auxAlu.addAll(Arrays.asList(br.readLine().split(":")[1].trim().split(",")));

                    int codCurso = Integer.parseInt(cod);
                    curso = new Curso(codCurso, nom, des, prof, auxAlu);
                    listCursos.add(curso);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return listCursos;
    }
}
