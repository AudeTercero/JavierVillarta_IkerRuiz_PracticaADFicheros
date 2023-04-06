package fileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class lecturaFicheros {

    public List<String> readLinesFiles(Path filePath) throws IOException{
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(filePath.toFile()))){
            String line;
            while ((line = br.readLine()) !=null ) {
                lines.add(line);
            }

        }
        return lines;
    }

    private List<String> listTextFiles(Path filePath) {
        List<String> textFiles = new ArrayList<>();
        File folder = new File(filePath.toString());
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                textFiles.add(file.getName());
            }
        }
        return textFiles;
    }



}
