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

    public void guardarSer(ArrayList<Profesor> profes) {
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(RUTA_PROFESORES)))) {

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
            // e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }

            } catch (IOException e) {
                // e.printStackTrace();
            }
        }

        return profesores;

    }

    public void mostrarSer() {
        File fichero = new File(RUTA_PROFESORES);
        boolean hayProf = false;
        if (fichero.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fichero)))) {

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

    public void mostrarUnoSer(String dni) {
        boolean existe = false;
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(RUTA_PROFESORES)))) {

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
            // e.printStackTrace();
        } finally {
            if (!existe) {
                System.out.println("Profesor no encontrado");
            }
        }
    }

    public void borrarUnoSer(String dni) {
        ArrayList<Profesor> profes = leerSer();
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(RUTA_PROFESORES)))) {

            for (Profesor p : profes) {
                if (!dni.equalsIgnoreCase(p.getDni())) {
                    out.writeObject(p);

                }
            }

        } catch (Exception e) {
            // e.printStackTrace();
        }
        System.out.println("Profesor eliminado correctamente");

    }


    //*****************************FICHERO BINARIO*****************************

    /**
     * Metodo para guardar los atributos de un objeto alumno en un fichero binario
     *
     * @param alumno lo recibe para guardarlo en un fichero binario
     */
    public void guardarBin(Alumno alumno) {
        File file = new File(RUTA_ALUMNOS);
        ArrayList<Alumno> alumnos = leerBin();
        boolean repe = false;
        for (Alumno a : alumnos) {
            if ((a.getNombre().equalsIgnoreCase(alumno.getNombre()) && (a.getApellidos().equalsIgnoreCase(alumno.getApellidos())))) {
                repe = true;
            }
        }
        if (!repe) {

            try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file, true)))) {

                out.writeInt(alumno.getNumExpediente());
                out.writeUTF(alumno.getNombre());
                out.writeUTF(alumno.getApellidos());
                out.writeUTF(alumno.getTelefono());
                out.writeUTF(alumno.getDireccion());
                out.writeUTF(alumno.getFechNac().toString());

            } catch (IOException e) {
                //e.printStackTrace();
            }
        } else {
            System.out.println("Alumno ya existente.");
        }

    }

    public void guardarBin(ArrayList<Alumno> alumnos) {
        File file = new File(RUTA_ALUMNOS);
        try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {

            for (Alumno alumno : alumnos) {
                out.writeInt(alumno.getNumExpediente());
                out.writeUTF(alumno.getNombre());
                out.writeUTF(alumno.getApellidos());
                out.writeUTF(alumno.getTelefono());
                out.writeUTF(alumno.getDireccion());
                out.writeUTF(alumno.getFechNac());
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    /**
     * Metodo para guardar en un ArrayList alumnos formados por los atributos
     * recibidos de un fichero binario
     */
    public ArrayList<Alumno> leerBin() {
        ArrayList<Alumno> alumnos = new ArrayList<>();
        int id = 0;
        String nom, ape, tel, dir, fech;
        File file = new File(RUTA_ALUMNOS);

        if (file.exists()) {
            try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {

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

    public void mostrarBin() {
        File file = new File(RUTA_ALUMNOS);

        if (file.exists()) {
            try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {

                while (true) {
                    System.out.println("****ALUMNO****");

                    System.out.println("Id Alumno: " + in.readInt());
                    System.out.println("Nombre: " + in.readUTF());
                    System.out.println("Apellido: " + in.readUTF());
                    System.out.println("Telefono: " + in.readUTF());
                    System.out.println("Direccion: " + in.readUTF());
                    System.out.println("Fecha: " + in.readUTF());

                }

            } catch (IOException e) {
                //e.printStackTrace();
            }
        } else
            System.out.println("No hay Alumnos guardados");
    }

    public void mostrarUnoBin(String alu) {
        File file = new File(RUTA_ALUMNOS);
        boolean exist = false;
        String nom, ape, tel, dire, fech, aux;
        if (file.exists()) {
            try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {

                while (true) {
                    int id = in.readInt();
                    nom = in.readUTF();
                    ape = in.readUTF();
                    tel = in.readUTF();
                    dire = in.readUTF();
                    fech = in.readUTF();
                    aux = nom + ape;
                    if (aux.equalsIgnoreCase(alu)) {
                        System.out.println("****ALUMNO****");
                        System.out.println("Id Alumno: " + id);
                        System.out.println("Nombre: " + nom);
                        System.out.println("Apellido: " + ape);
                        System.out.println("Telefono: " + tel);
                        System.out.println("Direccion: " + dire);
                        System.out.println("Fecha: " + fech);
                        exist = true;
                    }

                }

            } catch (IOException e) {
                //e.printStackTrace();
            }
            if(!exist){
                System.out.println("Alumno no encontrado");
            }
        } else
            System.out.println("No hay Alumnos guardados");

    }


    //******************************FICHERO TEXTO******************************
    public void guardarText() {

    }

    public void leerSerText() {

    }

}
