/**
 * Harry Seigne 16/07/21
 */

package main.java.ATMApp.model;

import java.util.ArrayList;

public class ATM {
    private ArrayList<Integer> notes = new ArrayList<>();
    private ArrayList<Integer> quantities = new ArrayList<>();

    // Handles adding of cash to the ATM
    public void addCash(int denomination, int quantity) {
        if (notes.isEmpty()) {
            notes.add(denomination);
            quantities.add(quantity);
        } else {
            Boolean found = false;
            for (int i=0; i < this.notes.size(); i++) {
                if (notes.get(i) == denomination) {
                    int oldQuantity = quantities.get(i);
                    quantities.set(i, oldQuantity + quantity);
                    found = true;
                }
            }
            if (!found) {
                notes.add(denomination);
                quantities.add(quantity);
            }
        }

    }

    // Handles removal of cash from ATM
    public void removeCash(int denomination, int quantity) {
        for (int i=0; i < this.notes.size(); i++) {
            if (notes.get(i) == denomination) {
                int oldQuantity = quantities.get(i);
                if (oldQuantity - quantity == 0) {
                    notes.remove(i);
                    quantities.remove(i);
                } else {
                    quantities.set(i, oldQuantity-quantity);
                }
            }
        }
    }

    // Returns total cash in ATM
    public int getTotal() {
        int total = 0;
        for (int denom: notes) {
            total += denom * quantities.get(notes.indexOf(denom));
        }
        return total;
    }

    // Returns a String for easy display
    public String printNotes() {
        String printer = "";
        for (int i = 0; i < notes.size(); i++) {
            printer += Integer.toString(quantities.get(i)) + " x $" + Integer.toString(notes.get(i));
            printer += " total $" + Integer.toString(quantities.get(i)*notes.get(i)) + "\n";
        }
        printer += "Total: $" + Integer.toString(getTotal());
        return printer;
    }

    public int getArrayLength() {
        return this.notes.size();
    }

    public int getDenomAtIndex(int index) {
        return this.notes.get(index);
    }

    public int getQuantityAtIndex(int index) {
        return this.quantities.get(index);
    }

    public void setQuantityAtIndex(int index, int n) {
        int quantity = quantities.get(index);
        quantity += n;
        quantities.set(index, quantity);
    }

    public ArrayList<Integer> getQuantities() {
        return quantities;
    }

    public ArrayList<Integer> getNotes() {
        return notes;
    }
}
