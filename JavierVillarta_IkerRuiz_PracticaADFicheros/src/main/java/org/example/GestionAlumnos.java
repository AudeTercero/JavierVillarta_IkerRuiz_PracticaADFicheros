package org.example;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GestionAlumnos implements CRUD {

    private static final String FICHERO = "Alumno.data";
    private Scanner sc = new Scanner(System.in);
    private static Verificaciones verif = new Verificaciones();

    /*
     * Metodo menu para seleccionar las acciones requeridas a ejecutar
     */
    public void menu() {
        System.out.println("-- GESTION ALUMNOS --");

        String op = null;

        do {
            System.out.println("\n Selecciona una opcion: \n 1.Alta Alumnos \n 2.Borrar Alumnos "
                    + "\n 3.Modificar Alumnos \n 4.Buscar Alumnos \n 5.Mostrar Alumnos "
                    + "\n 0.Salir");
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

    /*
     * Metodo para dar de alta alumnos. Recoje los diferentes atributos, los valida
     * y crea un objeto alumno que se guarda en un fichero
     */
    public void alta() {
        Scanner sc = new Scanner(System.in);
        int cont = 0;
        boolean fallo = false;

        String nom, ape, tel, dir, fech;
        ;

        System.out.println("-ALTA ALUMNOS- \n");

        do {
            fallo = false;
            System.out.println("Introduce el nombre del alumno:");
            nom = sc.nextLine();
            try {
                verif.hayAlgo(nom);

            } catch (MisExceptions e) {
                System.err.println(e);
                fallo = true;
                cont++;
            }

        } while (fallo && cont < 5);

        if (cont < 5) {
            cont = 0;
            do {
                fallo = false;
                System.out.println("Introduce el apellido del alumno:");
                ape = sc.nextLine();
                try {
                    verif.hayAlgo(ape);

                } catch (MisExceptions e) {
                    System.err.println(e);
                    fallo = true;
                    cont++;
                }

            } while (fallo && cont < 5);

            if (cont < 5) {
                do {
                    fallo = false;
                    System.out.println("Introduce el telefono del alumno:");
                    tel = sc.nextLine();
                    try {
                        verif.hayAlgo(tel);
                        verif.nueveCaracteres(tel);
                        verif.esNum(tel);

                    } catch (MisExceptions e) {
                        System.err.println(e);
                        fallo = true;
                        cont++;
                    }

                } while (fallo && cont < 5);

                if (cont < 5) {
                    cont = 0;
                    do {
                        fallo = false;
                        System.out.println("Introduce la direccion del alumno:");
                        dir = sc.nextLine();
                        try {
                            verif.hayAlgo(dir);

                        } catch (MisExceptions e) {
                            System.err.println(e);
                            fallo = true;
                            cont++;
                        }

                    } while (fallo && cont < 5);

                    if (cont < 5) {
                        cont = 0;
                        do {
                            fallo = false;
                            System.out.println(
                                    "Introduce la fecha de nacimiento del alumno, por favor ingresa la fecha (en formato YYYY-MM-DD):");
                            fech = sc.nextLine();
                            try {
                                verif.hayAlgo(fech);
                                verif.esFech(fech);

                            } catch (MisExceptions e) {
                                System.err.println(e);
                                fallo = true;
                                cont++;
                            }

                        } while (fallo && cont < 5);

                        if (cont < 5) {
                            cont = 0;

                            Alumno alumno = new Alumno(nom, ape, tel, dir, fech);
                            guardarFich(alumno);

                        } else {
                            System.out.println("Has llegado a 5 intentos, saliendo...");
                        }

                    } else {
                        System.out.println("Has llegado a 5 intentos, saliendo...");
                    }

                } else {
                    System.out.println("Has llegado a 5 intentos, saliendo...");
                }

            } else {
                System.out.println("Has llegado a 5 intentos, saliendo...");
            }

        } else {
            System.out.println("Has llegado a 5 intentos, saliendo...");
        }

    }

    public void baja() {
        System.out.println("Introduce el nombre del alumno");
        String nom = sc.nextLine();
        System.out.println("Introduce los apellidos del alumno");
        String ape = sc.nextLine();
        boolean existe = false;
        boolean salir = false;
        String op = null;
        ArrayList<Alumno> alumnos = leerFich();
        Alumno eliminado = null;
        File file = new File(FICHERO);
        DataOutputStream out = null;

        do {
            for (Alumno alumno : alumnos) {
                if (alumno.getNombre().equalsIgnoreCase(nom) && alumno.getApellidos().equalsIgnoreCase(ape)) {
                    existe = true;
                    do {
                        System.out.println("Seguro que deseas borrar el alumno? \n [S/N]");
                        op = sc.nextLine();

                        if (op.equalsIgnoreCase("S")) {
                            eliminado = alumno; //Guardamos el alumno ya que no podemos eliminarlo mientras iteramos pues puede fallar
                            System.out.println("Alumno Borrado.");
                            salir = true;
                        } else if ((op.equalsIgnoreCase("N"))) {
                            salir = true;
                        } else {
                            System.out.println("Entrada invalida");
                        }
                    } while (!salir);
                }
            }
        } while (!alumnos.isEmpty() && !salir);

        if (!existe) {
            System.out.println("El alumno no existe");
        } else {
            alumnos.remove(eliminado);
            try {
                out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
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
            } finally {
                try {
                    out.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    public void modificar() {
        System.out.println("Introduce el nombre del alumno");
        String nom = sc.nextLine();
        System.out.println("Introduce los apellidos del alumno");
        String ape = sc.nextLine();
        boolean existe = false;
        boolean salir = false;
        boolean salir2 = false;
        String op = null;
        String newNom, newApe, newTel, newDir, newFech;
        ArrayList<Alumno> alumnos = leerFich();


        if (!alumnos.isEmpty()) {
            for (Alumno alumno : alumnos) {
                if (alumno.getNombre().equalsIgnoreCase(nom) && alumno.getApellidos().equalsIgnoreCase(ape)) {
                    existe = true;
                    newNom = alumno.getNombre();
                    newApe = alumno.getApellidos();
                    newTel = alumno.getTelefono();
                    newDir = alumno.getDireccion();
                    newFech = alumno.getFechNac();

                    do {
                        System.out.println("Introduce el valor que deseas modificar o pulsa 0 para salir \n"
                                + "1.Nombre \n 2.Apellidos \n 3.Telefono \n 4.Direccion \n 5.Fecha de Nacimiento");

                        op = sc.nextLine();
                        switch (op) {
                            case "1":
                                System.out.println("Introduce el nuevo nombre:");
                                try {
                                    newNom = sc.nextLine();
                                    verif.hayAlgo(newNom);
                                    salir2 = true;

                                } catch (MisExceptions e) {
                                    System.out.println(e);
                                }

                                break;
                            case "2":
                                System.out.println("Introduce los nuevos apellidos:");
                                try {
                                    newApe = sc.nextLine();
                                    verif.hayAlgo(newApe);
                                    salir2 = true;

                                } catch (MisExceptions e) {
                                    System.out.println(e);
                                }
                                break;
                            case "3":
                                System.out.println("Introduce el nuevo telefono:");
                                try {
                                    newTel = sc.nextLine();
                                    verif.hayAlgo(newTel);
                                    verif.nueveCaracteres(newTel);
                                    verif.esNum(newTel);
                                    salir2 = true;

                                } catch (MisExceptions e) {
                                    System.out.println(e);
                                }
                                break;
                            case "4":
                                System.out.println("Introduce la nueva direccion:");
                                try {
                                    newDir = sc.nextLine();
                                    verif.hayAlgo(newDir);
                                    salir2 = true;

                                } catch (MisExceptions e) {
                                    System.out.println(e);
                                }
                                break;
                            case "5":
                                System.out.println("Introduce la nueva fecha:");
                                try {
                                    newFech = sc.nextLine();
                                    verif.hayAlgo(newFech);
                                    verif.esFech(newFech);


                                } catch (MisExceptions e) {
                                    System.out.println(e);
                                }
                                break;
                            case "0":
                                salir2 = true;
                                break;

                            default:
                                System.out.println("Eleccion no valida, prueba de nuevo.");
                        }
                    } while (!salir2);
                    do {
                        System.out.println("Seguro que deseas modificar el alumno? \n [S/N}:");
                        op = sc.nextLine();

                        if (op.equalsIgnoreCase("S")) {
                            alumno.setNombre(newNom);
                            alumno.setApellidos(newApe);
                            alumno.setTelefono(newTel);
                            alumno.setDireccion(newDir);
                            alumno.setFechNac(newFech);

                            salir = true;
                        } else if ((op.equalsIgnoreCase("N"))) {
                            salir = true;
                        } else {
                            System.out.println("Entrada invalida");
                        }
                    } while (!salir);
                }
            }

            if (!existe) {
                System.out.println("El alumno no existe");
            } else {
                File file = new File(FICHERO);
                DataOutputStream out = null;

                try {
                    out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
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
                } finally {
                    try {
                        out.close();
                    } catch (IOException e) {
                        //e.printStackTrace();
                    }
                }
            }
        }
    }

    public void buscar() {
        System.out.println("Introduce el nombre del alumno");
        String nom = sc.nextLine();
        System.out.println("Introduce los apellidos del alumno");
        String ape = sc.nextLine();
        boolean existe = false;

        ArrayList<Alumno> alumnos = leerFich();

        for (Alumno alumno : alumnos) {
            if (alumno.getNombre().equalsIgnoreCase(nom) && alumno.getApellidos().equalsIgnoreCase(ape)) {
                System.out.println(alumno.toString());
                existe = true;
            }
        }

        if (!existe) {
            System.out.println("El alumno no existe");
        }
    }

    public void mostrar() {
        ArrayList<Alumno> alumnos = leerFich();

        if(!alumnos.isEmpty()) {
            for (Alumno a : alumnos) {
                System.out.println(a.toString());
            }
        }else {
            System.out.println("No hay alumnos guardados.");
        }
    }

    /*
     * Metodo para guardar los atributos de un objeto alumno en un fichero binario
     */
    public void guardarFich(Alumno alumno) {
        File file = new File(FICHERO);
        DataOutputStream out = null;
        ArrayList<Alumno> alumnos = leerFich();
        boolean repe = false;

        for (Alumno a : alumnos) {
            if ((a.getNombre().equalsIgnoreCase(alumno.getNombre())
                    && (a.getApellidos().equalsIgnoreCase(alumno.getApellidos())))) {
                repe = true;
            }
        }
        if (!repe) {

            try {
                out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file, true)));

                out.writeInt(alumno.getNumExpediente());
                out.writeUTF(alumno.getNombre());
                out.writeUTF(alumno.getApellidos());
                out.writeUTF(alumno.getTelefono());
                out.writeUTF(alumno.getDireccion());
                out.writeUTF(alumno.getFechNac().toString());

            } catch (IOException e) {
                //e.printStackTrace();
            } finally {
                try {
                    out.close();
                } catch (IOException e) {
                    // e.printStackTrace();
                }
            }
        } else {
            System.out.println("Alumno ya existente.");
        }

    }

    /*
     * Metodo para guardar en un ArrayList alumnos formados por los atributos
     * recibidos de un fichero binario
     */
    public ArrayList<Alumno> leerFich() {

        ArrayList<Alumno> alumnos = new ArrayList<>();
        DataInputStream in = null;
        int id = 0;
        String nom, ape, tel, dir, fech;
        File file = new File(FICHERO);

        if (file.exists()) {
            try {
                in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
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
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }
        return alumnos;

    }
}
