package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Curso {
    private int codCur;
    private String nombre;
    private String descripcion;
    private String profe;
    private String[] alumnos;
    private static final String FICHERO = "Cursos.txt";

    /**
     * @param nombre
     * @param descripcion
     */
    public Curso(String nombre, String descripcion) {
        this.codCur = nuevoIdentificador();
        this.nombre = nombre;
        this.descripcion = descripcion;

    }

    public Curso(int id, String nombre, String descripcion, String profe, String[] alumnos) {
        this.codCur = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.profe = profe;
        this.alumnos = alumnos;
    }

    public int nuevoIdentificador() {
        int aux = 0;
        ArrayList<Curso> cursos = leerFich();
        if (!cursos.isEmpty()) {
            for (Curso c : cursos) {
                if (c.getCodCur() > aux) {
                    aux = c.getCodCur();
                }
            }
            aux++;
        } else {
            aux = 1;
        }
        return aux;

    }

    public ArrayList<Curso> leerFich() {
        File file = new File(FICHERO);
        ArrayList<Curso> cursos = new ArrayList<>();
        String cod, nom, des;
        String prof;
        String[] alumnos;

        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(FICHERO))) {
                String line;
                while ((cod = br.readLine()) != null) {
                    cod = cod.split(":")[1].trim();
                    int id = Integer.parseInt(cod);
                    nom = br.readLine().split(":")[1].trim();
                    des = br.readLine().split(":")[1].trim();
                    prof = br.readLine().split(":")[1].trim();
                    alumnos = br.readLine().split(":")[1].trim().split(",");

                    Curso c = new Curso(id, nom, des, prof, alumnos);
                    cursos.add(c);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return cursos;
    }
    public void addAlumno (String nom, String Ape){

    }

    public int getCodCur() {
        return codCur;
    }

    public void setCodCur(int codCur) {
        this.codCur = codCur;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getProfe() {
        return profe;
    }

    public void setProfe(String profe) {
        this.profe = profe;
    }

    public String getAlumnos() {
        return alumnoCadena();
    }

    public void setAlumnos(String[] alumnos) {
        this.alumnos = alumnos;
    }
    public String alumnoCadena(){
        String lisAlu = "";
        for(String s : alumnos){
            if(lisAlu.length()==0)
                lisAlu = s;
            else
                lisAlu = lisAlu +", "+s;
        }
        return lisAlu;
    }

    @Override
    public String toString() {
        return "Curso [codCur=" + codCur + ", nombre=" + nombre + ", descripcion=" + descripcion + ", Profesor="+", Alumnos=" + alumnoCadena()
                + "]";
    }

}
