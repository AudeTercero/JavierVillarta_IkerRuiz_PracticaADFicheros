package org.example;

import java.awt.print.PageFormat;
import java.io.*;
import java.util.ArrayList;

public class Fichero {
    private static final String RUTA_CURSOS = "Cursos.txt";
    private static final String RUTA_PROFESORES = "Profesores.ser";
    private static final String RUTA_ALUMNOS = "Alumno.data";

    //***************************FICHERO SERIALIZADO***************************

    /**
     * Metodo para guardar fichero serializado.
     *
     * @param profe
     */
    public void guardarSer(Profesor profe) {
        File fichero = new File(RUTA_PROFESORES);
        FileOutputStream fileOut = null;
        BufferedOutputStream bufOut = null;
        ObjectOutputStream out = null;
        ArrayList<Profesor> listProfe = leerSer();
        boolean existe = false;
        try {
            fileOut = new FileOutputStream(fichero);
            bufOut = new BufferedOutputStream(fileOut);
            out = new ObjectOutputStream(bufOut);
            for (Profesor p : listProfe) {
                out.writeObject(p);
                if (p.getDni().equalsIgnoreCase(profe.getDni())) {
                    existe = true;
                }
            }

            if (!existe) {
                out.writeObject(profe);
            } else {
                System.err.println("Ese profesor ya existe. No puede haber dos Profesores con el mismo dni.");
            }
        } catch (IOException ex) {
//	            ex.printStackTrace();
        } finally {
            try {
                out.close();
                bufOut.close();
                fileOut.close();
            } catch (Exception ex) {
                // ex.printStackTrace();
            }
        }


    }

    public void guardarSer(ArrayList<Profesor> profes){
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(RUTA_PROFESORES)))){

            for (Profesor p : profes) {
                out.writeObject(p);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para leer fichero serializado
     *
     * @return Coleccion con los objetos Profesor
     */

    public ArrayList<Profesor> leerSer() {
        File fichero = new File(RUTA_PROFESORES);
        ObjectInputStream in = null;
        ArrayList<Profesor> profesores = new ArrayList<>();

        try {
            if (fichero.exists()) {// Comprobamos si existe
                in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fichero)));
                while (true) {
                    profesores.add((Profesor) in.readObject());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return profesores;

    }
    public void mostrarSer(){
        File fichero = new File(RUTA_PROFESORES);
        boolean hayProf = false;
        if (fichero.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fichero)))){

                while (true) {
                    Profesor profe = (Profesor) in.readObject();
                    System.out.println("****PROFESOR****");
                    System.out.println("Dni: " + profe.getDni());
                    System.out.println("Nombre: " + profe.getNombre());
                    System.out.println("Direccion: " + profe.getDireccion());
                    System.out.println("Telefono: " + profe.getTelefono());
                    hayProf = true;
                }
            } catch (EOFException e) {
                e.printStackTrace();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error al mostrar Profesores");
                e.printStackTrace();
            }
        } else {
            System.out.println("El fichero no Existe");
        }
    }

    public void mostrarUnoSer(String dni){
        boolean existe = false;
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(RUTA_PROFESORES)))){

            while (true) {
                Profesor profe = (Profesor) in.readObject();
                if (dni.equalsIgnoreCase(profe.getDni())) {
                    System.out.println("****PROFESOR****");
                    System.out.println("Dni: " + profe.getDni());
                    System.out.println("Nombre: " + profe.getNombre());
                    System.out.println("Direccion: " + profe.getDireccion());
                    System.out.println("Telefono: " + profe.getTelefono());
                    existe = true;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (!existe) {
                System.out.println("Profesor no encontrado");
            }
        }
    }
    public void borrarUnoSer(String dni){
        ArrayList<Profesor> profes = leerSer();
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(RUTA_PROFESORES)))){

            for (Profesor p : profes) {
                if (!dni.equalsIgnoreCase(p.getDni())) {
                    out.writeObject(p);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Profesor eliminado correctamente");

    }


    //*****************************FICHERO BINARIO*****************************
    public void guardarBin() {

    }

    public void leerBin() {

    }


    //******************************FICHERO TEXTO******************************
    public void guardarText() {

    }

    public void leerSerText() {

    }

}
