package de.kleemann.generator;

import de.kleemann.util.FileUtil;

/**
 * Class "CoreServiceGenerationCommand" is used for ...
 *
 * @author Matteo Kleemann
 * @version 1.0
 * @since 24.10.2024
 */
public class CoreServiceGenerationCommand implements Command {

    private final String className;
    private final String[] attributes;

    public CoreServiceGenerationCommand(String className, String[] attributes) {
        this.className = className;
        this.attributes = attributes;
    }

    @Override
    public void execute() {
        System.out.println("Generating core service class for: " + className);

        StringBuilder classBuilder = new StringBuilder();
        String entityClassName = className + "Entity";
        String persistenceServiceClassName = className + "PersistenceService";
        String coreInterfaceName = className;

        // Klassenkopf und Konstruktor
        classBuilder.append("@Service\n")
                .append("public class ").append(className).append("Service {\n\n")
                .append("    private final ").append(persistenceServiceClassName).append(" ").append(decapitalize(persistenceServiceClassName)).append(";\n\n")
                .append("    public ").append(className).append("Service(").append(persistenceServiceClassName).append(" ")
                .append(decapitalize(persistenceServiceClassName)).append(") {\n")
                .append("        this.").append(decapitalize(persistenceServiceClassName)).append(" = ").append(decapitalize(persistenceServiceClassName)).append(";\n")
                .append("    }\n\n");

        // createEntity Methode
        classBuilder.append("    public ").append(coreInterfaceName).append(" create").append(coreInterfaceName).append("() {\n")
                .append("        return new ").append(coreInterfaceName).append("Impl(")
                .append(decapitalize(persistenceServiceClassName)).append(".create").append(entityClassName).append("());\n")
                .append("    }\n\n");

        // persistEntity Methode
        classBuilder.append("    public ").append(coreInterfaceName).append(" persist").append(coreInterfaceName)
                .append("(@NonNull ").append(coreInterfaceName).append(" ").append(decapitalize(coreInterfaceName)).append(") {\n")
                .append("        ").append(entityClassName).append(" persisted").append(entityClassName).append(" = ")
                .append(decapitalize(persistenceServiceClassName)).append(".persist").append(className).append("((( ")
                .append(coreInterfaceName).append("Impl) ").append(decapitalize(coreInterfaceName)).append(").get").append(entityClassName).append("());\n")
                .append("        return new ").append(coreInterfaceName).append("Impl(persisted").append(entityClassName).append(");\n")
                .append("    }\n\n");

        // findAllEntities Methode
        classBuilder.append("    public List<").append(coreInterfaceName).append("> findAll").append(coreInterfaceName).append("s() {\n")
                .append("        return ").append(decapitalize(persistenceServiceClassName)).append(".findAll").append(className).append("s()\n")
                .append("            .stream()\n")
                .append("            .map(").append(coreInterfaceName).append("Impl::new)\n")
                .append("            .collect(Collectors.toList());\n")
                .append("    }\n\n");

        // findById Methode
        classBuilder.append("    public ").append(coreInterfaceName).append(" find").append(className).append("ById(long ")
                .append(decapitalize(className)).append("Id) {\n")
                .append("        try {\n")
                .append("            return new ").append(coreInterfaceName).append("Impl(")
                .append(decapitalize(persistenceServiceClassName)).append(".find").append(className).append("ById(")
                .append(decapitalize(className)).append("Id));\n")
                .append("        } catch (Exception exception) {\n")
                .append("            throw new NoSuchElementException(\"").append(coreInterfaceName.toLowerCase()).append(" with id: \" + ")
                .append(decapitalize(className)).append("Id + \" could not be found.\");\n")
                .append("        }\n")
                .append("    }\n\n");

        // updateEntity Methode
        classBuilder.append("    public ").append(coreInterfaceName).append(" update").append(className).append("(long ")
                .append(decapitalize(className)).append("Id, @NonNull ").append(coreInterfaceName).append(" ")
                .append(decapitalize(coreInterfaceName)).append(") {\n")
                .append("        final ").append(entityClassName).append(" updated").append(entityClassName).append(";\n")
                .append("        try {\n")
                .append("            updated").append(entityClassName).append(" = ")
                .append(decapitalize(persistenceServiceClassName)).append(".update").append(className).append("(")
                .append(decapitalize(className)).append("Id, (( ").append(coreInterfaceName).append("Impl) ")
                .append(decapitalize(coreInterfaceName)).append(").get").append(entityClassName).append("());\n")
                .append("        } catch (Exception exception) {\n")
                .append("            throw new RuntimeException(\"").append(coreInterfaceName.toLowerCase()).append(" with id: \" + ")
                .append(decapitalize(className)).append("Id + \" could not be updated.\");\n")
                .append("        }\n")
                .append("        return new ").append(coreInterfaceName).append("Impl(updated").append(entityClassName).append(");\n")
                .append("    }\n\n");

        // deleteEntity Methode
        classBuilder.append("    public void delete").append(className).append("(@NonNull ").append(coreInterfaceName).append(" ")
                .append(decapitalize(coreInterfaceName)).append(") {\n")
                .append("        ").append(decapitalize(persistenceServiceClassName)).append(".delete").append(className).append("((( ")
                .append(coreInterfaceName).append("Impl) ").append(decapitalize(coreInterfaceName)).append(").get").append(entityClassName).append("());\n")
                .append("    }\n\n");

        // deleteById Methode
        classBuilder.append("    public void delete").append(className).append("ById(long ").append(decapitalize(className)).append("Id) {\n")
                .append("        ").append(decapitalize(persistenceServiceClassName)).append(".delete").append(className).append("ById(")
                .append(decapitalize(className)).append("Id);\n")
                .append("    }\n");

        classBuilder.append("}\n");

        FileUtil.writeToFile("generated/" + className + "Service.txt", classBuilder.toString());
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
