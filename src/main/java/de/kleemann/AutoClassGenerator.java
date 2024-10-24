package de.kleemann;

import de.kleemann.input.InputHandler;

/**
 * Hello world!
 *
 */
public class AutoClassGenerator
{
    public static void main( String[] args ) {
        System.out.println("Welcome to AutoClassGenerator - A command line interface for generating classes for spring boot backend applications.");

        InputHandler inputHandler = new InputHandler();
        inputHandler.handleUserInput();
    }
}
