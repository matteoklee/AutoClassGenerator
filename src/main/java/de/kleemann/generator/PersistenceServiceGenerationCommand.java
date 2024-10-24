package de.kleemann.generator;

import de.kleemann.util.FileUtil;

/**
 * Class "PersistenceServiceGenerationCommand" is used for ...
 *
 * @author Matteo Kleemann
 * @version 1.0
 * @since 24.10.2024
 */
public class PersistenceServiceGenerationCommand implements Command {

    private final String className;
    private final String[] attributes;

    public PersistenceServiceGenerationCommand(String className, String[] attributes) {
        this.className = className;
        this.attributes = attributes;
    }

    @Override
    public void execute() {
        System.out.println("Generating persistence service class for: " + className);

        StringBuilder classBuilder = new StringBuilder();

        String entityClassName = className + "Entity";
        String repositoryClassName = className + "Repository";

        // Klassenkopf und Konstruktor
        classBuilder.append("@Service\n")
                .append("public class ").append(className).append("PersistenceService {\n\n")
                .append("    private final ").append(repositoryClassName).append(" ").append(decapitalize(repositoryClassName)).append(";\n\n")
                .append("    public ").append(className).append("PersistenceService(").append(repositoryClassName).append(" ")
                .append(decapitalize(repositoryClassName)).append(") {\n")
                .append("        this.").append(decapitalize(repositoryClassName)).append(" = ").append(decapitalize(repositoryClassName)).append(";\n")
                .append("    }\n\n");

        // createEntity Methode
        classBuilder.append("    public ").append(entityClassName).append(" create").append(entityClassName).append("() {\n")
                .append("        return new ").append(entityClassName).append("();\n")
                .append("    }\n\n");

        // findAllEntities Methode
        classBuilder.append("    public List<").append(entityClassName).append("> findAll").append(className).append("s() {\n")
                .append("        return new ArrayList<>(").append(decapitalize(repositoryClassName)).append(".findAll());\n")
                .append("    }\n\n");

        // findById Methode
        classBuilder.append("    public ").append(entityClassName).append(" find").append(className).append("ById(long ").append(decapitalize(className)).append("Id) {\n")
                .append("        return ").append(decapitalize(repositoryClassName)).append(".findById(").append(decapitalize(className)).append("Id)\n")
                .append("            .orElseThrow(() -> new NoSuchElementException(\"unknown ").append(className.toLowerCase()).append(" entity with id: \" + ")
                .append(decapitalize(className)).append("Id));\n")
                .append("    }\n\n");

        // updateEntity Methode (abhängig von den Attributen)
        classBuilder.append("    public ").append(entityClassName).append(" update").append(className).append("(long ")
                .append(decapitalize(className)).append("Id, @NonNull ").append(entityClassName).append(" ").append(decapitalize(entityClassName)).append(") {\n")
                .append("        ").append(entityClassName).append(" updated").append(className).append("Entity = find").append(className).append("ById(")
                .append(decapitalize(className)).append("Id);\n");

        // Dynamisch für jedes Attribut einen Setter generieren
        for (String attribute : attributes) {
            String[] parts = attribute.split(":");
            if (parts.length == 2) {
                String attributeName = parts[0].trim();
                String capitalizedAttributeName = capitalize(attributeName);

                // Setter für jedes Attribut hinzufügen
                classBuilder.append("        updated").append(className).append("Entity.set").append(capitalizedAttributeName)
                        .append("(").append(decapitalize(entityClassName)).append(".get").append(capitalizedAttributeName).append("());\n");
            }
        }

        // Speichern
        classBuilder.append("        return persist").append(className).append("(updated").append(className).append("Entity);\n")
                .append("    }\n\n");

        // persistEntity Methode
        classBuilder.append("    public ").append(entityClassName).append(" persist").append(className).append("(@NonNull final ").append(entityClassName).append(" ")
                .append(decapitalize(entityClassName)).append(") {\n")
                .append("        try {\n")
                .append("            return ").append(decapitalize(repositoryClassName)).append(".save(").append(decapitalize(entityClassName)).append(");\n")
                .append("        } catch (EntityExistsException exception) {\n")
                .append("            throw new EntityExistsException(\"").append(entityClassName.toLowerCase()).append(" with id:\" + ")
                .append(decapitalize(entityClassName)).append(".getProjectId() + \" already exists.\");\n")
                .append("        }\n")
                .append("    }\n\n");

        // deleteEntity Methode
        classBuilder.append("    public void delete").append(className).append("(@NonNull final ").append(entityClassName).append(" ")
                .append(decapitalize(entityClassName)).append(") {\n")
                .append("        ").append(decapitalize(repositoryClassName)).append(".deleteById(").append(decapitalize(entityClassName)).append(".getProjectId());\n")
                .append("    }\n\n");

        // deleteById Methode
        classBuilder.append("    public void delete").append(className).append("ById(long ").append(decapitalize(className)).append("Id) {\n")
                .append("        ").append(decapitalize(repositoryClassName)).append(".deleteById(").append(decapitalize(className)).append("Id);\n")
                .append("    }\n");

        classBuilder.append("}\n");
        System.out.println("Generierter Code:\n");
        System.out.println(classBuilder.toString());

        FileUtil.writeToFile("generated/" + className + "PersistenceService.txt", classBuilder.toString());
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
