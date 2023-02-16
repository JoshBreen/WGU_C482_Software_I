package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/***
 * Joshua Breen C482 Project
 * Product Class
 */


public class Product {
    //Instance Fields

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /***
     * associatedParts list for the Products
     */
    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();

   //Constructor
    public Product(int id, String name, double price, int stock, int min, int max, ObservableList list)
    {   this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = list;
    }

    /***
     * @param id the id to set
     */
    public void setID(int id)
    {
        this.id = id;
    }

    /***
     * @param name the name to set
     */
    public void setName(String name)
    {   this.name = name;
    }

    /***
     * @param price the price to set
     */
    public void setPrice(double price)
    {   this.price = price;
    }

    /***
     * @param stock the stock to set
     */
    public void setStock(int stock)
    {   this.stock = stock;
    }

    /***
     * @param min the min to set
     */
    public void setMin(int min)
    {   this.min = min;
    }

    /***
     * @param max the max to set
     */
    public void setMax(int max)
    {   this.max = max;
    }

    /***
     * Adds associatedParts to the product
     * @param part associatedPart to add to the Product
     */
    public void addAssociatedPart(Part part)
    {   associatedParts.add(part);
    }

    /***
     * Delete Associated Part from the Product
     * @param selectedAssociatedPart the associated part selected from either AddProduct or ModifyProduct window
     * @return removes the associatedPart from the Product
     */
   public boolean deleteAssociatedPart(Part selectedAssociatedPart)
    {
        for(Part i : associatedParts) {
            if (i.getId() == selectedAssociatedPart.getId()) {
                associatedParts.remove(i);
                return true;
            }
        }
        return false;
    }


    /***
     *
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /***
     *
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /***
     *
     * @return the price
     */
    public double getPrice()
    {
        return price;
    }

    /***
     *
     * @return the stock
     */
    public int getStock()
    {
        return stock;
    }

    /***
     *
     * @return the min
     */
    public int getMin()
    {
        return min;
    }

    /***
     *
     * @return the max
     */
    public int getMax()
    {
        return max;
    }

    /***
     *
     * @return associatedParts for the Product
     */
    public static ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }


}
