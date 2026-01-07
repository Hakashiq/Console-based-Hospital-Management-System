package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    // Read entire file → return list of lines
    public static List<String> readFile(String path) {
        List<String> lines = new ArrayList<>();

        try {
            File file = new File(path);
            file.getParentFile().mkdirs(); // ensure /data/ exists
            file.createNewFile(); // create if not exists

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) lines.add(line);
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return lines;
    }

    // Overwrite entire file
    public static void writeFile(String path, List<String> lines) {
        try {
            FileWriter fw = new FileWriter(path, false);

            for (String l : lines) {
                fw.write(l + "\n");
            }

            fw.close();
        } catch (Exception e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    // Add a new line at bottom of file
    public static void appendLine(String path, String line) {
        try {
            FileWriter fw = new FileWriter(path, true);
            fw.write(line + "\n");
            fw.close();
        } catch (Exception e) {
            System.out.println("Error appending file: " + e.getMessage());
        }
    }
}
