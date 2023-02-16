package model;

/***
 * Joshua Breen C482 Project
 * Inventory class holds the lists of Parts and Products, and has methods for modifying them.
 * FUTURE UPDATES: New methods for modifying the lists could be added.
 */


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Inventory {

    /***
     *Establishes the part and product inventory number
     */
    public static int partInvNum = 0;
    public static int prodInvNum = 0;

    /***
     * Generates the part number for all new parts created
     * @return  returns a new part number when a new part is created
     */
    public static int getPartInvNum(){
       partInvNum ++;
       return partInvNum;
    }

    /***
     * Generates the product number for all new products created
     * @return returns a new product number when a new product is created
     */
    public static int getProdInvNum(){
        prodInvNum ++;
        return prodInvNum;
    }

    /***
     * Creates our two lists, allParts to store the parts and allProducts to store the Products
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /***
     * Adds a new part to the allParts list
     * @param newPart gets the newPart Part from AddPartController and ModifyPartController to insert it into the allParts list
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /***
     * Adds a new product to the allProducts list
     * @param newProduct gets the newProduct from AddProductController and ModifyProductController to insert it into the allProducts list
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /***
     * Runs the search for the part from the allParts list by ID number
     * @param partID gets the partID from the search text boxes on the Main/Add Product/Modify Product windows
     * @return returns a part if it finds a match, null if it doesn't match
     */
    public static Part lookupPart(int partID){
        ObservableList<Part> allParts = Inventory.getAllParts();
        //index loop

        for(int i = 0; i < allParts.size(); i++){
            Part pn = allParts.get(i);
            if(pn.getId() == partID){
                return pn;
            }
        }
        return null;
    }

    /***
     * Runs the search for the part from the allParts list by characters in its name and adds it to a temporary list
     * called searchedParts. Once it looks through all the parts it sends that generated list back to the table view
     * under the search box that was used.
     * @param partName gets the characters from the search text boxes on the Main/Add Product/Modify Product windows
     * @return returns a list of parts containing the searched term called searchedParts if it finds any parts contain the characters searched for
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> searchedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part pn : allParts){
            if(pn.getName().contains(partName)){
                searchedParts.add(pn);
            }
        }
        return searchedParts;
    }

    /***
     * Runs the search for the product from the allProducts list by ID number
     * @param productID gets the productID from the search text box on the main window
     * @return returns a product if it finds it matches a product with that ID number
     */
    public static Product lookupProduct(int productID){
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(int i = 0; i < allProducts.size(); i++) {
            Product pn = allProducts.get(i);
            if(pn.getId() == productID){
                return pn;
            }
        }
        return null;

    }

    /***
     * Runs the search for the product from allProducts list by characters in its name and adds it to a temporary
     * list called searchedProducts. Once it looks through all the products it sends that generated list back to the
     * table view under the search box that was used.
     * @param productName gets the characters from the search text box on the main window
     * @return returns a list of products containing the searched term called searchedProducts if it finds any products contain the characters searched for
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> searchedProducts = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = Inventory.getAllProducts();
        //enhanced loop
        for(Product pn : allProducts){
            if(pn.getName().contains(productName)){
                searchedProducts.add(pn);
            }
        }
        return searchedProducts;
    }

    /***
     * Modifies the part that is stored in allParts and is being modified in the ModifyPartController
     * RUNTIME ERROR: Before adding the break for once it found the part I ran into an error because the list size was changed in the middle of searching it
     * @param index is the ID number of the part
     * @param selectedPart the part that is being updated/removed so it can be replaced with the updated version of the part
     */
    public static void updatePart(int index, Part selectedPart) {
        for (Part i : allParts) {
            if (i.getId() == index) {
                allParts.remove(i);
                break;
            }
        }
        allParts.add(selectedPart);
    }

    /***
     * Modifies the product that is stored in allProducts and is being modified in the ModifyProductController
     * RUNTIME ERROR: Before adding the break for once it found the product I ran into an error because the list size was changed in the middle of searching it
     * @param index is the ID number of the product
     * @param selectedProduct is the product that is being updated/removed so it can be replaced with the updated version of the product
     */
    public static void updateProduct(int index, Product selectedProduct){
        for (Product i : allProducts) {
            if (i.getId() == index) {
                allProducts.remove(i);
                break;
            }
        }
        allProducts.add(selectedProduct);
    }

    /***
     * Deletes a part from the allParts list
     * @param selectedPart gets the selected part from the parts table view on the main window
     * @return removes the part and returns true
     */
    public static boolean deletePart(int selectedPart) {
        for(Part i : allParts) {
            if (i.getId() == selectedPart) {
                allParts.remove(i);
                return true;
            }
        }
        return false;
    }

    /***
     * Deletes a prodcut from the allProducts list
     * @param selectedProduct gets the selected product from the product table view on the main window
     * @return removes the product and returns true
     */
    public static boolean deleteProduct(int selectedProduct) {
        for(Product i : allProducts) {
            if (i.getId() == selectedProduct) {
                allProducts.remove(i);
                return true;
            }
        }
        return false;
    }

    /***
     * getter for the allParts list
     * @return returns the allParts list
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /***
     * getter for the allProducts list
     * @return returns the allProducts list
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

}
