package de.kleemann.generator;

import de.kleemann.util.FileUtil;

/**
 * Class "ClassGenerationCommand" is used for ...
 *
 * @author Matteo Kleemann
 * @version 1.0
 * @since 24.10.2024
 */
public class ClassImplGenerationCommand implements Command {

    private final String className;
    private final String[] attributes;

    public ClassImplGenerationCommand(String className, String[] attributes) {
        this.className = className;
        this.attributes = attributes;
    }

    @Override
    public void execute() {
        System.out.println("Generating class implementation for: " + className);

        StringBuilder classBuilder = new StringBuilder();
        String entityClassName = className + "Entity";
        String interfaceClassName = className;

        classBuilder.append("public class ").append(className).append("Impl implements ").append(interfaceClassName).append(" {\n\n")
                .append("    private final ").append(entityClassName).append(" ").append(decapitalize(entityClassName)).append(";\n\n")
                .append("    public ").append(className).append("Impl(").append(entityClassName).append(" ").append(decapitalize(entityClassName)).append(") {\n")
                .append("        this.").append(decapitalize(entityClassName)).append(" = ").append(decapitalize(entityClassName)).append(";\n")
                .append("    }\n\n");

        classBuilder.append("    public ").append(entityClassName).append(" get").append(entityClassName).append("() {\n")
                .append("        return ").append(decapitalize(entityClassName)).append(";\n")
                .append("    }\n\n");

        //Attribute
        for (String attribute : attributes) {
            String[] parts = attribute.split(":");
            if (parts.length == 2) {
                String attributeName = parts[0].trim();
                String attributeType = parts[1].trim();
                String capitalizedAttributeName = capitalize(attributeName);

                // Getter
                classBuilder.append("    @Override\n")
                        .append("    public ").append(attributeType).append(" get").append(capitalizedAttributeName).append("() {\n")
                        .append("        return ").append(decapitalize(entityClassName)).append(".get").append(capitalizedAttributeName).append("();\n")
                        .append("    }\n\n");

                // Setter
                classBuilder.append("    @Override\n")
                        .append("    public void set").append(capitalizedAttributeName)
                        .append("(").append(attributeType).append(" ").append(attributeName).append(") {\n")
                        .append("        ").append(decapitalize(entityClassName)).append(".set").append(capitalizedAttributeName)
                        .append("(").append(attributeName).append(");\n")
                        .append("    }\n\n");
            }
        }

        classBuilder.append("}\n");
        FileUtil.writeToFile("generated/" + className + "Impl.txt", classBuilder.toString());
    }

    private String decapitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
