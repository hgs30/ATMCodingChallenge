/**
 * Harry Seigne 16/07/21
 */

package main.java.ATMApp.controller;

import main.java.ATMApp.model.*;

import java.util.*;

public class ATMController {
    private ATM atm;

    public ATMController(ATM atm) {
        this.atm = atm;
    }

    public void deposit(int value, int quantity) {
        atm.addCash(value, quantity);
    }

    // Takes an int amount and calculates the notes required to withdraw this amount, while factoring in quantities
    // of notes remaining
    public String withdraw(int amount) {
        if (this.atm.getTotal() < amount || amount < 0) {
            System.out.println("Error! Not enough money in ATM");
            return null;
        }

        int len = atm.getArrayLength();

        ArrayList<ArrayList<Integer>> table;
        table = new ArrayList<>();

        // Creates a one dimensional table filled with arrays containing the notes required to withdraw the current index
        // E.G. index 20 has = [20] stored at index, with a positive quantity of 20 dollar notes
        // Note: the notes in ATM must be in order from highest value to lowest value
        for (int i = 0; i < amount+1; i++) {
            for (int j = 0; j < len; j++) {

                int denom = atm.getNotes().get(j);
                int denomQuantity = atm.getQuantities().get(j);

                if (i == denom) {
                    ArrayList<Integer> track;

                    if (denomQuantity == 0) {
                        track = new ArrayList<>();
                        table.add(i, track);
                        continue;
                    }

                    // Check if ArrayList is created at index
                    if (table.size() == i + 1) {
                        track = table.get(i);
                        track.add(denom);
                        table.set(i, track);
                    } else {
                        track = new ArrayList<>();
                        track.add(denom);
                        table.add(i, track);
                    }
                    break;
                } else if (i - denom < 0) {
                    if (j == 0) {
                        ArrayList<Integer> track = new ArrayList<>();
                        table.add(i, track);
                        continue;
                    }
                    break;
                } else if (table.get(i-denom).isEmpty()) {
                    if (j == 0) {
                        ArrayList<Integer> track = table.get(i-denom);
                        table.add(i, track);
                    }
                    continue;
                // This code checks quantity of a denomination and sees if there's a valid combination of notes for
                // amount i
                } else {

                    if (table.size() != i && !table.get(i).isEmpty()) {
                        continue;
                    }
                    ArrayList<Integer> oldTrack = table.get(i-denom);
                    ArrayList<Integer> newTrack = new ArrayList<>(oldTrack.size());

                    for (int k = 0; k < oldTrack.size(); k ++) {
                        newTrack.add(Integer.valueOf(oldTrack.get(k)));
                    }
                    ArrayList<Integer> quantityCopy = new ArrayList<>(len);
                    for (int k = 0; k < len; k++) {
                        quantityCopy.add(Integer.valueOf(atm.getQuantityAtIndex(k)));
                    }
                    for (Integer usedDenom: newTrack) {
                        for (int k = 0; k < len; k++) {
                            int index = atm.getNotes().indexOf(usedDenom);
                            if (k == index) {
                                int quantity = quantityCopy.get(k);
                                quantity--;
                                quantityCopy.set(k, quantity);
                        }
                        }
                    }

                    if (quantityCopy.get(j) == 0) {
                        ArrayList<Integer> track = new ArrayList<>();
                        table.add(i, track);
                        continue;
                    }

                    newTrack.add(denom);
                    if (i == table.size()) {
                        table.add(i, newTrack);
                    }
                    table.set(i, newTrack);
                }
            }
        }
        if (table.get(amount).isEmpty()) {
            return "Error! Not enough money in ATM";
        }

        // Find the notes used to get amount
        Set<Integer> usedNotes = new HashSet<>(table.get(amount));
        String withdrawnNotes = "\nWithdrawn funds: \n";
        for (int i: usedNotes) {
            int quantityUsed = Collections.frequency(table.get(amount), i);
            for (int j = 0; j < len; j++) {
                if (i == atm.getDenomAtIndex(j)) {
                    atm.setQuantityAtIndex(j, -quantityUsed);
                }
            }
            withdrawnNotes += Integer.toString(quantityUsed) + " x $" + Integer.toString(i) + "\n";
        }
        withdrawnNotes += "Total: $" + Integer.toString(amount);
        return withdrawnNotes;
    }

    public String getCurrentNotes() {
        return this.atm.printNotes();
    }

}
