package model;

/**
 * Joshua Breen C482 Project
 * Inhouse Part, subclass of Part. Adds Machine ID as an additional input for the class.
 * FUTURE ENHANCEMENT: Could add further data to the class, potentially other identifying information based
 * on who made the part.
 */

public class InhousePart extends Part {

    private int machineID;

    public InhousePart(int ID, String name, double price, int stock, int min, int max, int machineID){
        super(ID,name, price, stock, min, max);
        this.machineID = machineID;
    }

    public void setMachineID(int machineID){
        this.machineID = machineID;

    }

    public int getMachineID(){
        return machineID;
    }
}
