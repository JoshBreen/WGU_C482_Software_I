package View_Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/***
 * Joshua Breen C482 Project
 * Add Product Controller - used to add products to the product list
 * FUTURE UPDATES: Further inputs could be added for tracking more data. More error checks could also be added.
 */

public class AddProductController implements Initializable {
    public Button addAssoPartButton;
    public Button removeAssoPartButton;
    public Button productSaveButton;
    public Button productCancelButton;
    public TextField addProdIDTB;
    public TextField addProdNameTB;
    public TextField addProdInvTB;
    public TextField addProdPriceTB;
    public TextField addProdMaxTB;
    public TextField addProdMinTB;
    public TextField addProdSearchTB;
    public Label prodInvErrorLabel;
    public Label prodPriceErrorLabel;
    public Label prodMaxErrorLabel;
    public Label prodMinErrorLabel;
    public Label prodNameErrorLabel;
    public TableView addProductPickTable;
    public TableView addProductAssoPartList;
    private ObservableList<Part> assoParts = FXCollections.observableArrayList();
    public TableColumn partIDCol;
    public TableColumn partNameCol;
    public TableColumn partInvLvlCol;
    public TableColumn partPriceCol;
    public TableColumn assoPartIDCol;
    public TableColumn assoPartNameCol;
    public TableColumn assoPartInvLvlCol;
    public TableColumn assoPartPriceCol;
    public Label searchError;

    /***
     * Creates the addProductPicktable and populates it with allParts.
     * Creates the addProductAssoPartList connected to the assoParts list.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        addProductPickTable.setItems(Inventory.getAllParts());

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        addProductAssoPartList.setItems(assoParts);

        assoPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assoPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assoPartInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assoPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }



    //add selected part as an Associated Part

    /***
     * Adds a part selected from addProductPickTable to addProductAssoPartList
     * @param actionEvent click addAssoPart button
     */
    public void clickAddAssoPart(ActionEvent actionEvent) {
        Part selectedPart = (Part) addProductPickTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            return;
        } else {
            assoParts.add(selectedPart);
        }
    }


    //Searches Part table

    /***
     * Searches allParts for parts by ID number and characters typed in the text box.
     * @param actionEvent hit enter after typing into search text box
     */
    public void searchPartList(ActionEvent actionEvent){
        String s = addProdSearchTB.getText();
        searchError.setText("");
        ObservableList<Part> ps = Inventory.lookupPart(s);
        if(ps.size() == 0){
            try {
                int id = Integer.parseInt(s);
                Part pn = Inventory.lookupPart(id);
                if (pn != null) {
                    ps.add(pn);
                    searchError.setText("");
                } else {
                    searchError.setText("No item number in list.");  //error message if no part id matches search term
                    addProdSearchTB.setText("");
                }
            }
            catch (NumberFormatException e){
                searchError.setText("No item name matches search term.");  //error message if no part name matches search term
            }
        }
        addProductPickTable.setItems(ps);
        addProdSearchTB.setText("");
    }


    //remove selected associated part

    /***
     * Removes selected part from addProductAssoPartList table.
     * @param actionEvent on clicking RemoveAssoButton
     */
    public void clickRemoveAssoPart(ActionEvent actionEvent) {
        if (addProductAssoPartList.getSelectionModel().getSelectedIndex() != -1) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove selected item?", "Confirm Remove", dialogButton);
            if (dialogResult == JOptionPane.YES_OPTION) {
                Part part = (Part) addProductAssoPartList.getSelectionModel().getSelectedItem();
                assoParts.remove(part);
            }
        }
    }

    /***
     * Clears error labels. Runs the checks to make sure all inputs are valid. Runs the error checks. Saves the Product
     * exits back to the main window.
     * @param actionEvent click product save button
     */
    public void clickProductSave(ActionEvent actionEvent) throws IOException {

        prodNameErrorLabel.setText("");
        prodInvErrorLabel.setText("");
        prodPriceErrorLabel.setText("");
        prodMinErrorLabel.setText("");
        prodMaxErrorLabel.setText("");

        isInvInputValid();
        isMaxInputValid();
        isMinInputValid();
        isNameInputValid();
        isPriceInputValid();
        if(areInputsValid() == true) {
            if(errorsFound() == false){
                productSave();
                Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/Main.fxml"));
                Stage stage = (Stage) productSaveButton.getScene().getWindow();
                Scene scene = new Scene(root, 920, 468);
                stage.setTitle("Inventory System");
                stage.setScene(scene);
                stage.show();

            }
        }
    }

    /***
     * Exits the Product window back to the main window.
     * @param actionEvent click cancel button
     */
    public void clickProductCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/Main.fxml"));
        Stage stage = (Stage) productCancelButton.getScene().getWindow();
        Scene scene = new Scene(root, 920, 468);
        stage.setTitle("Inventory System");
        stage.setScene(scene);
        stage.show();
        }

    /***
     * Saves the input in the text boxes into appropriate data types, takes the assoParts list. Saves it to a new
     * product.
     */
    public void productSave(){
        int id = Inventory.getProdInvNum(); //gets the generated ID number
        String name = addProdNameTB.getText(); //gets the product name
        int inv = Integer.parseInt(addProdInvTB.getText());  //gets the Inventory for Product
        double price = Double.parseDouble(addProdPriceTB.getText());  //gets the product price
        int max = Integer.parseInt(addProdMaxTB.getText());  //gets the max for product
        int min = Integer.parseInt(addProdMinTB.getText());  //gets the min for product
        ObservableList list = assoParts;
        Inventory.addProduct(new Product(id, name, price, inv, min, max, list));  //creates the product
    }

    /***
     * Checks to make sure that the input in the Price text box is a double. Displays error if it is not.
     * @return true if Price input is a double
     */
    private boolean isPriceInputValid(){
        Boolean b = false;
        if ((addProdPriceTB.getText() != null || addProdPriceTB.getText().length() != 0)){
            try{
                Double.valueOf(addProdPriceTB.getText());
                b = true;
            } catch (NumberFormatException e) {
                prodPriceErrorLabel.setText("Price is not a double.");
            }
        }
        return b;
    }

    /***
     * Checks to make sure that the input in the name text box is valid.
     * @return true if name input is valid
     */
    private boolean isNameInputValid() {
        Boolean b = false;
        if ((addProdNameTB.getText() == null || addProdNameTB.getText().length() == 0)) {
            prodNameErrorLabel.setText("No data in name field");
        } else {
            b = true;
        }
        return b;
    }

    /***
     * Checks to make sure Inventory input is an int.
     * @return true if Inv Input is valid
     */
    private boolean isInvInputValid() {
        Boolean b = false;
        if ((addProdInvTB.getText() != null || addProdInvTB.getText().length() != 0)) {
            try {
                Integer.valueOf(addProdInvTB.getText());
                b = true;
            } catch (NumberFormatException e) {
                prodInvErrorLabel.setText("Inventory is not an int.");
            }
        }
        return b;
    }

    /***
     * Checks to make sure the max input is an int.
     * @return true if Max input is valid
     */
    private boolean isMaxInputValid() {
        Boolean b = false;
        if ((addProdMaxTB.getText() != null || addProdMaxTB.getText().length() != 0)) {
            try {
                Integer.valueOf(addProdMaxTB.getText());
                b = true;
            } catch (NumberFormatException e) {
                prodMaxErrorLabel.setText("Max is not an int.");
            }
        }
        return b;
    }


    /***
     * Checks to make sure min input is an int.
     * @return true if Min input is valid
     */
    private boolean isMinInputValid() {
        Boolean b = false;
        if ((addProdMinTB.getText() != null || addProdMinTB.getText().length() != 0)) {
            try {
                // Do all the validation you need here such as
                Integer.valueOf(addProdMinTB.getText());
                b = true;
            } catch (NumberFormatException e) {
                prodMinErrorLabel.setText("Min is not an int.");
            }
        }
        return b;
    }

    /***
     * Checks to make sure all the inputs are valid.
     * @return true if all inputs are valid
     */
    private boolean areInputsValid(){
        Boolean q = false;
        if(isPriceInputValid() == true && isNameInputValid() == true && isInvInputValid() == true && isMaxInputValid() == true){
            q = true;
        }
        return q;
    }

    /***
     * Checks to make sure that Min is less than max
     * @return true if min is less than max
     */
    private boolean minMaxError(){
        Boolean e = false;
        if (Integer.valueOf(addProdMaxTB.getText()) > Integer.valueOf(addProdMinTB.getText())){
            e = true;
        }else{
            prodMinErrorLabel.setText("Max must be greater then min.");
        }
        return e;
    }

     /***
     * Checks to make sure Inv is between min and max
     * @return true if inv is between min and max
     */
    private boolean invError(){
        Boolean e = false;
        if (Integer.valueOf(addProdInvTB.getText()) >= Integer.valueOf(addProdMinTB.getText()) && Integer.valueOf(addProdInvTB.getText()) <= Integer.valueOf(addProdMaxTB.getText())){
            e = true;
        }else{
            prodInvErrorLabel.setText("Inv must be between Min and Max");
        }
        return e;
    }

    /***
     * Checks to see if there are any errors found that need to stop it from finishing
     * @return true if it finds an error
     */
    private boolean errorsFound(){
        Boolean e = false;
        if(minMaxError() == false || invError() == false){
            e = true;
        }
        return e;
    }






}