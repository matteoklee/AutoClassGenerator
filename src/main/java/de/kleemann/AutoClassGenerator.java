package de.kleemann;

import de.kleemann.input.InputHandler;

/**
 * Hello world!
 *
 */
public class AutoClassGenerator
{
    public static void main( String[] args ) {

        System.out.println();
        System.out.println("     _         _         ____ _                ____                           _             ");
        System.out.println("    / \\  _   _| |_ ___  / ___| | __ _ ___ ___ / ___| ___ _ __   ___ _ __ __ _| |_ ___  _ __ ");
        System.out.println("   / _ \\| | | | __/ _ \\| |   | |/ _` / __/ __| |  _ / _ \\ '_ \\ / _ \\ '__/ _` | __/ _ \\| '__|");
        System.out.println("  / ___ \\ |_| | || (_) | |___| | (_| \\__ \\__ \\ |_| |  __/ | | |  __/ | | (_| | || (_) | |   ");
        System.out.println(" /_/   \\_\\__,_|\\__\\___/ \\____|_|\\__,_|___/___/\\____|\\___|_| |_|\\___|_|  \\__,_|\\__\\___/|_|   ");
        System.out.println();
        System.out.println("[*] Initializing AutoClassGenerator...");
        System.out.println();
        System.out.println("Welcome to AutoClassGenerator - A command line interface for generating classes for spring boot backend applications.");

        delay(2000);
        System.out.println("[*] Loading modules...");
        delay(1000);
        System.out.println("[*] Starting generator...");
        delay(2000);

        System.out.println("[*] Deploying...");
        animateDots();
        System.out.println("[*] AutoClassGenerator started!");

        InputHandler inputHandler = new InputHandler();
        inputHandler.handleUserInput();

        System.out.println("Program finished. Press Enter to close...");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void animateDots() {
        for (int i = 0; i < 3; i++) {
            System.out.print(".");
            delay(1000);
        }
        System.out.println();
    }

}
