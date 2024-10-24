package de.kleemann.generator;

import de.kleemann.util.FileUtil;

/**
 * Class "EntityGenerationCommand" is used for ...
 *
 * @author Matteo Kleemann
 * @version 1.0
 * @since 24.10.2024
 */
public class EntityGenerationCommand implements Command {

    private final String className;
    private final String[] attributes;

    public EntityGenerationCommand(String className, String[] attributes) {
        this.className = className;
        this.attributes = attributes;
    }

    @Override
    public void execute() {
        System.out.println("Generating entity class for: " + className);

        StringBuilder classBuilder = new StringBuilder();

        classBuilder.append("@Entity\npublic class ").append(className).append("Entity {\n");

        //Attributes
        for(String attribute : attributes) {
            String[] parts = attribute.split(":");
            if(parts.length == 2) {
                String attributeName = parts[0].trim();
                String attributeType = parts[1].trim();
                if(attributeName.toLowerCase().contains("id")) {
                    classBuilder.append("    @Id\n       @GeneratedValue(strategy = GenerationType.IDENTITY)\n     private ")
                            .append(attributeType).append(" ").append(attributeName).append(";\n");
                } else {
                    classBuilder.append("    private ").append(attributeType).append(" ").append(attributeName).append(";\n");

                }
            } else {
                System.err.println("Error: Invalid format '" + attribute + "'. Expected: 'name:typ'.");
            }
        }

        //Constructor
        classBuilder.append("\n    public ").append(className).append("Entity() {\n}\n");

        //Getter & Setter
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

        classBuilder.append("}\n");
        System.out.println("Generierter Code:\n");
        System.out.println(classBuilder.toString());

        FileUtil.writeToFile("generated/" + className + "Entity.txt", classBuilder.toString());
    }

}
