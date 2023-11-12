package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Fichero {
    private static final String RUTA_CURSOS = "Cursos.txt";
    private static final String RUTA_PROFESORES = "Profesores.ser";
    private static final String RUTA_ALUMNOS = "Alumno.data";

    //***************************FICHERO SERIALIZADO PROFESORES***************************

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

        if (fichero.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fichero)))) {

                while (true) {
                    Profesor profe = (Profesor) in.readObject();
                    System.out.println("****PROFESOR****");
                    String dni = profe.getDni();
                    System.out.println("Dni: " + dni);
                    String nom = profe.getNombre();
                    System.out.println("Nombre: " + nom);
                    System.out.println("Direccion: " + profe.getDireccion());
                    System.out.println("Telefono: " + profe.getTelefono());
                    System.out.println("Cursos: "+profeCurso(nom+" "+dni));

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
                    String nom = profe.getNombre();
                    System.out.println("Nombre: " + nom);
                    System.out.println("Direccion: " + profe.getDireccion());
                    System.out.println("Telefono: " + profe.getTelefono());
                    System.out.println("Cursos: "+profeCurso(nom+" "+dni));

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

                } else {
                    borrProfCurso(p.getNombre() + " " + dni);
                }
            }

        } catch (Exception e) {
            // e.printStackTrace();
        }
        System.out.println("Profesor eliminado correctamente");

    }

    public String existProfe(String dni) {
        File fichero = new File(RUTA_PROFESORES);
        Profesor profe;

        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fichero)));) {
            while (true) {
                profe = (Profesor) in.readObject();
                if (profe.getDni().equalsIgnoreCase(dni.trim())) {
                    return profe.getNombre();
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    //*****************************FICHERO BINARIO ALUMNOS*****************************

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
                    String nom = in.readUTF();
                    System.out.println("Nombre: " + nom);
                    String ape = in.readUTF();

                    System.out.println("Apellido: " + ape);
                    System.out.println("Telefono: " + in.readUTF());
                    System.out.println("Direccion: " + in.readUTF());
                    System.out.println("Fecha: " + in.readUTF());
                    System.out.println("Cursos: " + aluCurso(nom + " " + ape));

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
            if (!exist) {
                System.out.println("Alumno no encontrado");
            }
        } else
            System.out.println("No hay Alumnos guardados");

    }

    public boolean existAlu(String nomApe) {
        File file = new File(RUTA_ALUMNOS);
        String nom, ape;

        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            while (true) {
                in.readInt();
                nom = in.readUTF();
                ape = in.readUTF();
                in.readUTF();
                in.readUTF();
                in.readUTF();
                if ((nom.trim() + ape.trim()).equalsIgnoreCase(nomApe.trim())) {
                    return true;
                }
            }
        } catch (IOException e) {
            // e.printStackTrace();
        }

        return false;
    }


    //******************************FICHERO TEXTO CURSOS******************************
    public void guardarText(Curso curso) {
        ArrayList<Curso> cursos = leerText();
        boolean repe = false;
        for (Curso a : cursos) {
            if (curso.getNombre().equalsIgnoreCase(a.getNombre())) {
                repe = true;
            }

        }
        if (!repe) {
            try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(RUTA_CURSOS, true)))) {
                pw.write("CodigoCurso: " + curso.getCodCur());
                pw.write("\n");
                pw.write("Nombre: " + curso.getNombre());
                pw.write("\n");
                pw.write("Descripcion: " + curso.getDescripcion());
                pw.write("\n");
                pw.write("Profesor: ");
                pw.write("\n");
                pw.write("Alumnos: ");
                pw.write("\n");
                System.out.println("Curso guardado correctamente");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Ese Curso ya existe");
        }

    }

    public void guardarText(ArrayList<Curso> cursos) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(RUTA_CURSOS, true)))) {
            for (Curso curso : cursos) {
                pw.write("CodigoCurso: " + curso.getCodCur());
                pw.write("\n");
                pw.write("Nombre: " + curso.getNombre());
                pw.write("\n");
                pw.write("Descripcion: " + curso.getDescripcion());
                pw.write("\n");
                pw.write("Profesor: ");
                pw.write("\n");
                pw.write("Alumnos: ");
                pw.write("\n");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Curso> leerText() {
        File file = new File(RUTA_CURSOS);
        ArrayList<Curso> listCursos = new ArrayList<>();
        String cod, nom, des, prof;
        Curso curso;
        ArrayList<String> auxAlu = new ArrayList<>();
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
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

    public void mostrarText() {
        File file = new File(RUTA_CURSOS);
        String cod;
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
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
        } else {
            System.out.println("Aun no hay Cursos guardados");
        }
    }

    public void mostrarUnoText(String nombreCurso) {
        String cod, nom, des, prof, alu;
        boolean encontrado = false;
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_CURSOS))) {
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

    /**
     * Elimina los el alumno de los cursos en los que aperezca
     * @param nomApe recibe el nombre y el apellido
     */
    public void borrAluCurso(String nomApe) {
        ArrayList<Curso> cursos = leerText();
        if (!cursos.isEmpty()) {
            for (Curso c : cursos) {
                for (String s : c.getAlumnos()) {
                    if (nomApe.equalsIgnoreCase(s)) {
                        c.removeAlu(s.split(" ")[0], s.split(" ")[1]);

                    }
                }
            }
            guardarText(cursos);

        }


    }

    /**
     * Elimina los el porfesor de los cursos en los que aperezca
     * @param nomDni recibe el nombre y el dni
     */
    public void borrProfCurso(String nomDni) {
        ArrayList<Curso> cursos = leerText();
        ArrayList<String> nomCursos = new ArrayList<>();
        if (!cursos.isEmpty()) {
            for (Curso c : cursos) {

                if (nomDni.equalsIgnoreCase(c.getProfe())) {
                    c.setProfe("");

                }

            }
            guardarText(cursos);

        }


    }

    /**
     * Itera los cursos que hay y si existe uno con el mismo nombre lanza una excepcion
     * @param nomCurso
     * @throws MisExceptions
     */
    public void cursRepe(String nomCurso)throws MisExceptions{
        File file = new File(RUTA_CURSOS);
        if(file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                while ((br.readLine()) != null) {

                    String nom = br.readLine().split(":")[1].trim();
                    br.readLine();
                    br.readLine();
                    br.readLine();
                    if(nom.equalsIgnoreCase(nomCurso.trim())){
                        throw new MisExceptions("Ese curso ya existe, solo puede haber un curso con el mismo nombre");
                    }
                }
            }catch(IOException e){
               // e.printStackTrace();
            }
        }
    }

    /**
     * Metodo que guarda en una array y los devuelve los cursos en los que esta matriculado un alumno
     *
     * @param alumno una cadena formada por su nombre y apellido
     * @return nombres del Curso a los que esta matriculado un alumno
     */
    private ArrayList<String> aluCurso(String alumno) {
        ArrayList<Curso> cursos = leerText();
        ArrayList<String> nomCursos = new ArrayList<>();
        if (!cursos.isEmpty()) {
            for (Curso c : cursos) {
                for (String s : c.getAlumnos()) {
                    if (alumno.equalsIgnoreCase(s)) {
                        nomCursos.add(c.getNombre());

                    }
                }
            }

        }

        return nomCursos;
    }

    /**
     * Metodo que guarda en una array y los devuelve los cursos en los que participa un profesor
     *
     * @param profe una cadena formada con su nombre y dni
     * @return nombres del Curso a los que esta matriculado un profesor
     */
    private ArrayList<String> profeCurso(String profe) { //OJO HAY QUE MODIFICAR EL METODO DE GESiNSCRIPCIONES PARA QUE ESTO FUNCIONES NOMBRE + DNI
        ArrayList<Curso> cursos = leerText();
        ArrayList<String> nomCursos = new ArrayList<>();
        if (!cursos.isEmpty()) {
            for (Curso c : cursos) {
                if (profe.equalsIgnoreCase(c.getProfe())){
                    nomCursos.add(profe);
                }
            }

        }

        return nomCursos;
    }



}
