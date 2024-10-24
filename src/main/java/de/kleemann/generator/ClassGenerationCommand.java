package de.kleemann.generator;

/**
 * Class "ClassGenerationCommand" is used for ...
 *
 * @author Matteo Kleemann
 * @version 1.0
 * @since 24.10.2024
 */
public class ClassGenerationCommand implements Command {

    private final String className;
    private final String[] attributes;

    public ClassGenerationCommand(String className, String[] attributes) {
        this.className = className;
        this.attributes = attributes;
    }

    @Override
    public void execute() {
        System.out.println("Generating class with name: " + className);
        //Entity Klasse
        //Repository
        //PersistenceService
        //CoreService
        //Interface Klasse für Core
        //Core Impl von der Klasse
        //Controller

        StringBuilder classBuilder = new StringBuilder();

        classBuilder.append("public class ").append(className).append(" {\n");

        for(String attribute : attributes) {
            String[] parts = attribute.split(":");
            if(parts.length == 2) {
                String attributeName = parts[0].trim();
                String attributeType = parts[1].trim();
                classBuilder.append("    private ").append(attributeType).append(" ").append(attributeName).append(";\n");
            } else {
                System.err.println("Error: Invalid format '" + attribute + "'. Expected: 'name:typ'.");
            }
        }

        for(String attribute : attributes) {
            String[] parts = attribute.split(":");
            if(parts.length == 2) {
                String attributeName = parts[0].trim();
                String attributeType = parts[1].trim();

                String capitalizedAttribute = attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
                // Getter
                classBuilder.append("\n    public ").append(attributeType).append(" get").append(capitalizedAttribute).append("() {\n");
                classBuilder.append("        return ").append(attributeName).append(";\n");
                classBuilder.append("    }\n");

                // Setter
                classBuilder.append("\n    public void set").append(capitalizedAttribute).append("(").append(attributeType).append(" ").append(attributeName).append(") {\n");
                classBuilder.append("        this.").append(attributeName).append(" = ").append(attributeName).append(";\n");
                classBuilder.append("    }\n");
            }
        }

        System.out.println("Generierter Code:\n");
        System.out.println(classBuilder.toString());


    }
}
