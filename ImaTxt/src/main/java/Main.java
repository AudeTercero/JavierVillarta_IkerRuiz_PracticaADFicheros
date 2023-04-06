import fileReader.lecturaFicheros;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    static lecturaFicheros ficheros = new lecturaFicheros();

    public static void main(String[] args) {

        String path = "config.txt";
        Path ruta = Paths.get(path);
        try {
            ficheros.readLinesFiles(ruta);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}