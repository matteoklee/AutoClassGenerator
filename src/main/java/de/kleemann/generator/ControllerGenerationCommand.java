package de.kleemann.generator;

import de.kleemann.util.FileUtil;

/**
 * Class "ControllerGenerationCommand" is used for ...
 *
 * @author Matteo Kleemann
 * @version 1.0
 * @since 24.10.2024
 */
public class ControllerGenerationCommand implements Command {

    private final String className;
    private final String[] attributes;

    public ControllerGenerationCommand(String className, String[] attributes) {
        this.className = className;
        this.attributes = attributes;
    }

    @Override
    public void execute() {
        System.out.println("Generating controller class for: " + className);

        StringBuilder classBuilder = new StringBuilder();
        String serviceClassName = className + "Service";
        String requestDTOClassName = className + "RequestDTO";
        String responseDTOClassName = className + "ResponseDTO";

        // Klassenkopf und Konstruktor
        classBuilder.append("@RestController\n")
                .append("@RequestMapping(\"/api/v1/").append(className.toLowerCase()).append("\")\n")
                .append("public class ").append(className).append("Controller {\n\n")
                .append("    private final ").append(serviceClassName).append(" ").append(decapitalize(serviceClassName)).append(";\n\n")
                .append("    public ").append(className).append("Controller(").append(serviceClassName).append(" ")
                .append(decapitalize(serviceClassName)).append(") {\n")
                .append("        this.").append(decapitalize(serviceClassName)).append(" = ").append(decapitalize(serviceClassName)).append(";\n")
                .append("    }\n\n");

        // Get All Methode
        classBuilder.append("    @GetMapping\n")
                .append("    public ResponseEntity<List<").append(responseDTOClassName).append(">> getAll")
                .append(className).append("s() {\n")
                .append("        return ResponseEntity.ok(\n")
                .append("            ").append(decapitalize(serviceClassName)).append(".findAll").append(className).append("s()\n")
                .append("            .stream()\n")
                .append("            .map(").append(responseDTOClassName).append("::new)\n")
                .append("            .collect(Collectors.toList())\n")
                .append("        );\n")
                .append("    }\n\n");

        // Get By ID Methode
        classBuilder.append("    @GetMapping(\"/{id}\")\n")
                .append("    public ResponseEntity<").append(responseDTOClassName).append("> get").append(className).append("ById(@PathVariable(value = \"id\") long ")
                .append(decapitalize(className)).append("Id) {\n")
                .append("        return ResponseEntity.ok(new ").append(responseDTOClassName).append("(")
                .append(decapitalize(serviceClassName)).append(".find").append(className).append("ById(")
                .append(decapitalize(className)).append("Id)));\n")
                .append("    }\n\n");

        // Create Methode
        classBuilder.append("    @PostMapping\n")
                .append("    public ResponseEntity<").append(responseDTOClassName).append("> create")
                .append(className).append("(@RequestBody ").append(requestDTOClassName).append(" ")
                .append(decapitalize(requestDTOClassName)).append(") {\n")
                .append("        ").append(className).append(" new").append(className).append(" = mapTo").append(className)
                .append("(").append(decapitalize(requestDTOClassName)).append(");\n")
                .append("        ").append(className).append(" persisted").append(className).append(" = ")
                .append(decapitalize(serviceClassName)).append(".persist").append(className).append("(new")
                .append(className).append(");\n")
                .append("        return new ResponseEntity<>(new ").append(responseDTOClassName).append("(persisted")
                .append(className).append("), HttpStatus.CREATED);\n")
                .append("    }\n\n");

        // Update Methode
        classBuilder.append("    @PutMapping(\"/{id}\")\n")
                .append("    public ResponseEntity<").append(responseDTOClassName).append("> update")
                .append(className).append("(@PathVariable(value = \"id\") long ").append(decapitalize(className))
                .append("Id, @RequestBody ").append(requestDTOClassName).append(" ")
                .append(decapitalize(requestDTOClassName)).append(") {\n")
                .append("        ").append(className).append(" updated").append(className).append(" = mapTo")
                .append(className).append("(").append(decapitalize(requestDTOClassName)).append(");\n")
                .append("        updated").append(className).append(".set").append(className).append("Id(")
                .append(decapitalize(className)).append("Id);\n")
                .append("        ").append(className).append(" persisted").append(className).append(" = ")
                .append(decapitalize(serviceClassName)).append(".update").append(className).append("(")
                .append(decapitalize(className)).append("Id, updated").append(className).append(");\n")
                .append("        return ResponseEntity.ok(new ").append(responseDTOClassName).append("(persisted")
                .append(className).append("));\n")
                .append("    }\n\n");

        // Delete Methode
        classBuilder.append("    @DeleteMapping(\"/{id}\")\n")
                .append("    public ResponseEntity<Void> delete").append(className)
                .append("(@PathVariable(value = \"id\") long ").append(decapitalize(className)).append("Id) {\n")
                .append("        ").append(decapitalize(serviceClassName)).append(".delete").append(className)
                .append("ById(").append(decapitalize(className)).append("Id);\n")
                .append("        return ResponseEntity.noContent().build();\n")
                .append("    }\n\n");

        // mapToClass Methode
        classBuilder.append("    private ").append(className).append(" mapTo").append(className).append("(")
                .append(requestDTOClassName).append(" dto) {\n")
                .append("        ").append(className).append(" ").append(decapitalize(className)).append(" = ")
                .append(decapitalize(serviceClassName)).append(".create").append(className).append("();\n");

        // Setter für die Attribute hinzufügen
        for (String attribute : attributes) {
            String[] parts = attribute.split(":");
            if (parts.length == 2) {
                String attributeName = parts[0].trim();
                String capitalizedAttributeName = capitalize(attributeName);
                classBuilder.append("        ").append(decapitalize(className)).append(".set")
                        .append(capitalizedAttributeName).append("(dto.get").append(capitalizedAttributeName).append("());\n");
            }
        }

        classBuilder.append("        return ").append(decapitalize(className)).append(";\n")
                .append("    }\n");

        classBuilder.append("}\n");
        FileUtil.writeToFile("generated/" + className + "Controller.txt", classBuilder.toString());
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
