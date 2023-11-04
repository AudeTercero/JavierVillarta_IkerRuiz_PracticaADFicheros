package menu;

import java.util.Scanner;


public class InterfaceMenu {

    private Scanner sc = new Scanner(System.in);

    /**
     * Metodo para el menu
     */
    public void pintarMenu(){
        String opc;//Variable para la opcion que eligira el usuario
        do {//Inicio de do/while para el control de la entrada del menu
            System.out.println("Introduzca la opcion que quiera realizar");
            System.out.println("1. Crear. \n 2. Actualizar. \n 0. Salir");
            opc = sc.nextLine();
            if(opc.equalsIgnoreCase("1")){
                System.out.println("Creando");
            }else if(opc.equalsIgnoreCase("2")){
                System.out.println("Actualizando");
            }else if(opc.equalsIgnoreCase("0")){
                System.out.println("Saliendo");
            }else{
                System.out.println("Error de entrada");
            }
        }while(!opc.equalsIgnoreCase("0")&&!opc.equalsIgnoreCase("1")&&!opc.equalsIgnoreCase("2"));//Fin do/while para el control de la entrada del menu

        switch(opc){
            case"1":
                break;
            case "2":
                break;

        }
    }

}
