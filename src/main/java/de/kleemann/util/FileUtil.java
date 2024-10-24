package de.kleemann.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class "FileUtil" is used for ...
 *
 * @author Matteo Kleemann
 * @version 1.0
 * @since 24.10.2024
 */
public class FileUtil {

    public static void writeToFile(String fileName, String content) {
        File file = new File(fileName);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
            System.out.println("File " + fileName + " was generated successfully.");
        } catch (IOException e) {
            System.err.println("Error writing file " + fileName);
            e.printStackTrace();
        }
    }

}
