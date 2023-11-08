package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorInscripciones {
    private static Scanner sc = new Scanner(System.in);
    private static final String RUTA_CURSOS = "Cursos.txt";
    private static final String RUTA_PROFESORES = "Profesores.ser";
    private static final String RUTA_ALUMNOS = "Alumno.data";
    public void menu(){
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
    public void inscribirAlumno(){
        System.out.println("Escriba el nombre del alumno");
        String nom  = sc.nextLine();
        System.out.println("Escriba el apellido del alumno");
        String ape  = sc.nextLine();
        String nomApe = nom.trim()+ape.trim();
        if(existAlu(nomApe)){
            System.out.println("Escriba el nombre del curso");
            String cur  = sc.nextLine();
            Curso curso = null;
            ArrayList<Curso>cursos = leerFichCurs();
            for(Curso c: cursos){
                if(cur.equalsIgnoreCase(c.getNombre())){
                    curso = c;
                }
            }
            cursos.remove(curso);


            cursos.add(curso);

            try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(RUTA_CURSOS)))) {
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
        }else{
            System.out.println("No hay ningun alumno con ese nombre y apellido");
        }
    }
    public void inscribirProfesor(){
        System.out.println("Escriba el dni del profesor");
        String dni  = sc.nextLine();
        String nom = existProfe(dni);
        if(nom!=null){
            //aqui codigo para ingresar profesor en fichero curso
        }

    }
    public void bajaAlumno(){

    }
    public void bajaProfesor(){

    }
    public boolean existAlu(String nomApe){
        File file = new File(RUTA_ALUMNOS);
        String nom, ape;

        try(DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))){
            while(true){
                in.readInt();
                nom = in.readUTF();
                ape = in.readUTF();
                in.readUTF();
                in.readUTF();
                in.readUTF();
                if((nom.trim()+ape.trim()).equalsIgnoreCase(nomApe.trim())){
                    return true;
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        return false;
    }
    public String existProfe(String dni){
        File fichero = new File(RUTA_PROFESORES);
        Profesor profe = null;

        try(ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fichero)));){
            while(true){
                profe = (Profesor) in.readObject();
                if(profe.getDni().equalsIgnoreCase(dni.trim())){
                    return profe.getNombre();
                }

            }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Curso> leerFichCurs() {
        File file = new File(RUTA_CURSOS);
        ArrayList<Curso> listCursos = new ArrayList<>();
        String cod, nom, des, prof, alu;
        Curso curso;
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(RUTA_CURSOS))) {
                while ((cod = br.readLine()) != null) {
                    cod = cod.split(":")[1].trim();
                    nom = br.readLine().split(":")[1].trim();
                    des = br.readLine().split(":")[1].trim();
                    prof = br.readLine().split(":")[1].trim();
                    alu = br.readLine().split(":")[1].trim();
                    String[] listalu = alu.split(",");

                    int codCurso = Integer.parseInt(cod);
                    curso = new Curso(codCurso, nom, des, prof, listalu);
                    listCursos.add(curso);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return listCursos;
    }
    public ArrayList<Profesor> leerFichProf() {
        File fichero = new File(RUTA_PROFESORES);
        ArrayList<Profesor> profesores = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fichero)))){
            if (fichero.exists()) {// Comprobamos si existe

                while (true) {
                    profesores.add((Profesor) in.readObject());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return profesores;
    }
    public ArrayList<Alumno> leerFichAlu() {

        ArrayList<Alumno> alumnos = new ArrayList<>();
        int id = 0;
        String nom, ape, tel, dir, fech;
        File file = new File(RUTA_ALUMNOS);

        if (file.exists()) {
            try(DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {

                while (true) {
                    id = in.readInt();
                    if (id != -1) {
                        nom = in.readUTF();
                        ape = in.readUTF();
                        tel = in.readUTF();
                        dir = in.readUTF();
                        fech = in.readUTF();

                        Alumno a = new Alumno(id, nom, ape, tel, dir, fech);

                        alumnos.add(a);
                    } else {
                        break;
                    }

                }

            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
        return alumnos;

    }
}
