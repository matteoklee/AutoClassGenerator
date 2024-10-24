package de.kleemann.generator;

import de.kleemann.generator.Command;
import de.kleemann.util.FileUtil;

/**
 * Class "RepositoryGenerationCommand" is used for ...
 *
 * @author Matteo Kleemann
 * @version 1.0
 * @since 24.10.2024
 */
public class RepositoryGenerationCommand implements Command {

    private String className;

    public RepositoryGenerationCommand(String className) {
        this.className = className;
    }

    @Override
    public void execute() {
        System.out.println("Generating repository class for: " + className);

        StringBuilder classBuilder = new StringBuilder();

        classBuilder.append("public interface ").append(className).append("Repository extends JpaRepository<")
                .append(className).append("Entity, Long>").append(" {\n}\n");

        FileUtil.writeToFile("generated/" + className + "Repository.txt", classBuilder.toString());

    }
}
