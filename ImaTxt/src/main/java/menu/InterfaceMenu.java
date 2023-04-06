package menu;

import java.util.Scanner;

public class InterfaceMenu {
    private Scanner sc = new Scanner(System.in);
    public void pintarMenu(){
        String opc;
        do {
            System.out.println("Introduzca la opcion que quiera realizar");
            System.out.println("1. Crear. \n 2. Actualizar. \n 0. Salir");
            opc = sc.nextLine();
        }while(opc.equalsIgnoreCase("0"));
    }

}
