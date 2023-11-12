package org.example;


import java.util.ArrayList;


/**
 * Clase que representa un curso en el sistema de gestion del centro de formacion.
 */
public class Curso {
    private int codCur;
    private String nombre;
    private String descripcion;
    private String profe;
    private ArrayList<String> alumnos;


    /**
     * Constructor que crea un nuevo objeto Curso con el nombre y la descripcion proporcionados.
     *
     * @param nombre      El nombre del curso.
     * @param descripcion La descripcion del curso.
     */
    public Curso(String nombre, String descripcion) {
        this.codCur = nuevoIdentificador();
        this.nombre = nombre;
        this.descripcion = descripcion;

    }

    /**
     * Constructor que crea un nuevo objeto Curso con la informacion proporcionada.
     *
     * @param id          El identificador del curso.
     * @param nombre      El nombre del curso.
     * @param descripcion La descripcion del curso.
     * @param profe       El profesor asignado al curso.
     * @param alumnos     La lista de alumnos inscritos en el curso.
     */
    public Curso(int id, String nombre, String descripcion, String profe, ArrayList<String> alumnos) {
        this.codCur = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.profe = profe;
        this.alumnos = alumnos;
    }

    /**
     * Metodo que genera un nuevo identificador unico para el curso.
     *
     * @return El nuevo identificador del curso.
     */
    public int nuevoIdentificador() {
        Fichero fich  = new Fichero();
        int aux = 0;
        ArrayList<Curso> cursos = fich.leerText();
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



    /**
     * Metodo que agrega un alumno al curso.
     *
     * @param nom El nombre del alumno a agregar.
     * @param ape El apellido del alumno a agregar.
     */
    public void addAlumno(String nom, String ape) {
        String nomAlu = nom.trim() + " " + ape.trim();
        this.alumnos.add(nomAlu);

    }

    /**
     * Metodo que elimina un alumno del curso.
     *
     * @param nom El nombre del alumno a eliminar.
     * @param ape El apellido del alumno a eliminar.
     * @return true si el alumno fue eliminado, false si no se encontro.
     */
    public boolean removeAlu(String nom, String ape) {
        String nomAlu = nom.trim() + " " + ape.trim();
        boolean borrado = false;

        for (String nombre : this.alumnos) {
            if (nombre.equalsIgnoreCase(nomAlu)) {
                this.alumnos.remove(nomAlu);
                borrado = true;
            }
        }
        return borrado;
    }

    /**
     * Obtiene el identificador del curso.
     *
     * @return El identificador del curso.
     */
    public int getCodCur() {
        return codCur;
    }

    /**
     * Establece el identificador del curso.
     *
     * @param codCur El nuevo identificador a asignar.
     */
    public void setCodCur(int codCur) {
        this.codCur = codCur;
    }

    /**
     * Obtiene el nombre del curso.
     *
     * @return El nombre del curso.
     **/
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del curso.
     *
     * @param nombre El nuevo nombre a asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripcion del curso.
     *
     * @return La descripcion del curso.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripcion del curso.
     *
     * @param descripcion La nueva descripcion a asignar.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el profesor asignado al curso.
     *
     * @return El nombre del profesor asignado al curso.
     */
    public String getProfe() {
        return profe;
    }

    /**
     * Establece el profesor asignado al curso.
     *
     * @param profe El nuevo nombre del profesor a asignar.
     */
    public void setProfe(String profe) {
        this.profe = profe;
    }

    /**
     * Obtiene la lista de alumnos inscritos en el curso.
     *
     * @return La lista de alumnos inscritos en el curso.
     */
    public ArrayList<String> getAlumnos() {
        return this.alumnos;
    }

    /**
     * Establece la lista de alumnos inscritos en el curso.
     *
     * @param alumnos La nueva lista de alumnos a asignar.
     */
    public void setAlumnos(ArrayList<String> alumnos) {

        this.alumnos = alumnos;
    }

    /**
     * Genera una representacion en cadena de la lista de alumnos inscritos en el curso.
     *
     * @return Una cadena que representa la lista de alumnos inscritos en el curso.
     */
    public String alumnoCadena() {
        String lisAlu = "";
        for (String s : alumnos) {
            if (lisAlu.length() == 0)
                lisAlu = s;
            else
                lisAlu = lisAlu + ", " + s;
        }
        return lisAlu;
    }

    /**
     * Devuelve una representacion en cadena del objeto Curso.
     *
     * @return Una cadena que representa el objeto Curso.
     */
    @Override
    public String toString() {
        return "Curso [codCur=" + codCur + ", nombre=" + nombre + ", descripcion=" + descripcion + ", Profesor=" + ", Alumnos=" + alumnoCadena()
                + "]";
    }

}
