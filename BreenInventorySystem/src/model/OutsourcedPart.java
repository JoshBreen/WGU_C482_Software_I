package model;

/***
 * Joshua Breen C482 Project
 * Outsourced Part, subclass of Part. Adds company name as an additional input for the class.
 * FUTURE ENHANCEMENT: Could add further data to the class, potentially other identifying information based
 * on who made the part.
 */


public class OutsourcedPart extends Part {

    private String companyName;

    public OutsourcedPart(int ID, String name, double price, int stock, int min, int max, String companyName){
        super(ID,name, price, stock, min, max);
        this.companyName = companyName;
    }

    public void setCompanyName(String companyName){

    }

    public String getCompanyName(){
        return companyName;
    }
}