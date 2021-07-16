/**
 * Harry Seigne 16/07/21
 */

package main.java.ATMApp;

import main.java.ATMApp.controller.ATMController;
import main.java.ATMApp.model.ATM;

import java.util.Scanner;

public class Main {

    public static Scanner cli;
    private ATM atm;
    private ATMController controller;

    public Main() {
        cli = new Scanner(System.in);
        this.atm = new ATM();
        this.controller = new ATMController(atm);
    }

    public static void main(String[] args) {
        Main main = new Main();

        main.runCli();
    }

    // Main run loop of application
    private void runCli() {
        this.welcome();
        this.init();

        boolean exit = false;
        while (!exit) {
            mainMenu();
            String input = cli.nextLine();
            int menuItem;
            try {
                menuItem = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                menuItem = -1;
            }
            switch (menuItem) {
                case 1:
                    withdraw();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    exit = true;
                    System.out.println("See you later!");
                    break;
                case 4:
                    viewCash();
                    break;
                default:
                    System.out.println("Unknown value entered");
            }
        }
    }

    private void welcome() {
        System.out.println("\n######################################################\n\n"
                + "                    ATM SERVICE                         \n\n"
                + "######################################################");
    }

    private void withdraw() {
        System.out.println("\nHow much would you like to withdraw?: \n"
                + "Your Answer: ");
        String input = cli.nextLine();
        int toWithdraw;
        try {
            toWithdraw = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Error with input");
            return;
        }
        String change = this.controller.withdraw(toWithdraw);
        if (change != null) {
            System.out.println(change);
        }
    }

    private void deposit() {
        System.out.println("Please enter quantity of 50 dollar notes\n"
                + "Your Answer: ");
        String input = cli.nextLine();
        int quantity;
        try {
            quantity = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            quantity = 0;
        }
        this.controller.deposit(50, quantity);
        System.out.println("\nPlease enter quantity of 20 dollar notes: \n"
                + "Your Answer: ");
        input = cli.nextLine();
        try {
            quantity = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            quantity = 0;
        }
        this.controller.deposit(20, quantity);
    }

    private void viewCash() {
        System.out.println("\nTotal cash: \n");
        System.out.println(this.controller.getCurrentNotes());
    }

    private void init() {
        System.out.println("\nSetup: \n");
        this.deposit();
    }

    private void mainMenu() {
        System.out.println("\nWhat do you want to do next?\n"
                + "\t 1. Withdraw cash\n"
                + "\t 2. Deposit cash\n"
                + "\t 3. Exit\n"
                + "\t  ~Admin options~: \n"
                + "\t 4. View available cash\n"
                + "\n"
                + "Your Answer: ");
    }
}
