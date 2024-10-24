package de.kleemann.generator;

import de.kleemann.util.FileUtil;

/**
 * Class "CoreServiceInterfaceGenerationCommand" is used for ...
 *
 * @author Matteo Kleemann
 * @version 1.0
 * @since 24.10.2024
 */
public class ClassInterfaceGenerationCommand implements Command {

    private final String className;
    private final String[] attributes;

    public ClassInterfaceGenerationCommand(String className, String[] attributes) {
        this.className = className;
        this.attributes = attributes;
    }

    @Override
    public void execute() {
        System.out.println("Generating interface class for: " + className);

        StringBuilder classBuilder = new StringBuilder();

        classBuilder.append("public interface ").append(className).append(" {\n\n");

        //Attribute
        for (String attribute : attributes) {
            String[] parts = attribute.split(":");
            if (parts.length == 2) {
                String attributeName = parts[0].trim();
                String attributeType = parts[1].trim();
                String capitalizedAttributeName = capitalize(attributeName);

                // Getter
                classBuilder.append("    public ").append(attributeType).append(" get").append(capitalizedAttributeName).append("();\n\n");

                // Setter
                classBuilder.append("    public void set").append(capitalizedAttributeName)
                        .append("(").append(attributeType).append(" ").append(attributeName).append(");\n\n");
            }
        }

        classBuilder.append("}\n");
        FileUtil.writeToFile("generated/" + className + "Interface.txt", classBuilder.toString());
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
