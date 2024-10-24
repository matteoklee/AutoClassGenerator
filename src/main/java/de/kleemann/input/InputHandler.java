package de.kleemann.input;

import de.kleemann.generator.*;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Class "InputHandler" is used for ...
 *
 * @author Matteo Kleemann
 * @version 1.0
 * @since 24.10.2024
 */
public class InputHandler {

    private final Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public void handleUserInput() {
        System.out.println("Please enter the name of the class to be generated:");
        String className = scanner.nextLine();

        System.out.println("Please enter the attributes of the class in the format 'name:type' (separate multiple attributes with commas):");
        String classAttributes = scanner.nextLine();

        if(className.isEmpty() || classAttributes.isEmpty()) {
            System.err.println("Error: The class name and attributes must not be empty.");
            return;
        }
        String[] attributes = classAttributes.split(",");

        Set<String> classTypes = selectClassTypes();

        for (String classType : classTypes) {
            Command command = getCommandForClassType(classType, className, attributes);
            if (command != null) {
                command.execute();
            }
        }
    }

    private Set<String> selectClassTypes() {
        Set<String> classTypes = new HashSet<>();
        System.out.println("Select the classes to generate (enter the numbers separated by commas):");
        System.out.println("1. Entity\n2. Repository\n3. PersistenceService\n4. CoreService\n5. Interface Class\n6. Impl Class\n7. Controller");

        String input = scanner.nextLine();
        String[] selections = input.split(",");

        for (String selection : selections) {
            switch (selection.trim()) {
                case "1":
                    classTypes.add("Entity");
                    break;
                case "2":
                    classTypes.add("Repository");
                    break;
                case "3":
                    classTypes.add("PersistenceService");
                    break;
                case "4":
                    classTypes.add("CoreService");
                    break;
                case "5":
                    classTypes.add("ClassInterface");
                    break;
                case "6":
                    classTypes.add("ClassImpl");
                    break;
                case "7":
                    classTypes.add("Controller");
                    break;
                default:
                    System.out.println("Invalid selection: " + selection);
            }
        }
        return classTypes;
    }

    private Command getCommandForClassType(String classType, String className, String[] attributes) {
        switch (classType) {
            case "Entity":
                return new EntityGenerationCommand(className, attributes);
            case "Repository":
                return new RepositoryGenerationCommand(className);
            case "PersistenceService":
                return new PersistenceServiceGenerationCommand(className, attributes);
            case "CoreService":
                return new CoreServiceGenerationCommand(className, attributes);
            case "ClassInterface":
                return new ClassInterfaceGenerationCommand(className, attributes);
            case "ClassImpl":
                return new ClassImplGenerationCommand(className, attributes);
            case "Controller":
                return new ControllerGenerationCommand(className);
            default:
                return null;
        }
    }

}
