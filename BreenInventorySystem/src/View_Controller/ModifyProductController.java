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
 * Modify Product Controller - Window for modifying products
 * FUTURE UPDATE: Could implement more ways to modify a product and data for tracking products
 */


public class ModifyProductController implements Initializable {
    public Button addAssoPartButton;
    public Button removeAssoPartButton;
    public Button productSaveButton;
    public Button productCancelButton;
    public Label prodInvErrorLabel;
    public Label prodPriceErrorLabel;
    public Label prodMaxErrorLabel;
    public Label prodMinErrorLabel;
    public TextField modifyProductIDTB;
    public TextField modifyProductNameTB;
    public TextField modifyProductInvTB;
    public TextField modifyProductPriceTB;
    public TextField modifyProductMaxTB;
    public TextField modifyProductMinTB;
    public Label prodNameErrorLabel;
    public TableView modifyProductPickTable;
    public TableView modifyProductAssociatedParts;
    public TableColumn partIDCol;
    public TableColumn partNameCol;
    public TableColumn partInvLvlCol;
    public TableColumn partPriceCol;
    public TableColumn assoPartIDCol;
    public TableColumn assoPartNameCol;
    public TableColumn assoPartInvLvlCol;
    public TableColumn assoPartPriceCol;
    private ObservableList<Part> assoParts = FXCollections.observableArrayList();
    public TextField modProdSearchTB;
    public Label searchError;


    private Product product;
    private int id = -1;

    /***
     * Establishes the Product as the selected product from the Main window. Fills in the text boxes with the appropriate
     * information, sets up the parts tables.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        product = MainController.getProductTransfer();  //gets the product selected in the main screen
        setText();


        modifyProductPickTable.setItems(Inventory.getAllParts());

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));



        assoPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assoPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assoPartInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assoPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));



        modifyProductAssociatedParts.setItems(assoParts);
        
    }

    /***
     * RUNTIME ERROR: I originally had assoParts = product.getAllAssociatedParts(); which resulted in it modifying
     * the associatedParts list instead of making a copy of it like I intended. So when I clicked Modify, removed
     * associated items, and hit cancel. They would be removed from the associatedParts list permanently instead of
     * the copy list I thought I was working with.
     */
    public void setText(){
        id = product.getId();
        modifyProductIDTB.setText(String.valueOf(id));
        modifyProductNameTB.setText(product.getName());
        modifyProductInvTB.setText(String.valueOf(product.getStock()));
        modifyProductPriceTB.setText(String.valueOf(product.getPrice()));
        modifyProductMaxTB.setText(String.valueOf(product.getMax()));
        modifyProductMinTB.setText(String.valueOf(product.getMin()));
        assoParts = FXCollections.observableArrayList(product.getAllAssociatedParts());
    }

    /***
     * Add selected part as an associated part
     * @param actionEvent click add associated part button
     */
    public void clickAddAssoPart(ActionEvent actionEvent) {
        Part selectedPart = (Part) modifyProductPickTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            return;
        } else {
            assoParts.add(selectedPart);
        }
    }

    /***
     * Removes selected associated part
     * @param actionEvent click remove associated part
     */
    public void clickRemoveAssoPart(ActionEvent actionEvent) {
        if (modifyProductAssociatedParts.getSelectionModel().getSelectedIndex() != -1) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove selected item?", "Confirm Remove", dialogButton);
            if (dialogResult == JOptionPane.YES_OPTION) {
                Part part = (Part) modifyProductAssociatedParts.getSelectionModel().getSelectedItem();
                assoParts.remove(part);
            }
        }
    }

    /***
     * Searches part list for term in text box
     * @param actionEvent press enter after typing term in search text box
     */
    public void searchPartList(ActionEvent actionEvent){
        String s = modProdSearchTB.getText();
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
                    modProdSearchTB.setText("");
                }
            }
            catch (NumberFormatException e){
                searchError.setText("No item name matches search term.");  //error message if no part name matches search term
            }
        }
        modifyProductPickTable.setItems(ps);
        modProdSearchTB.setText("");
    }

    /***
     * Clears the labels, runs the validation checks, checks for errors, assuming all is good it then saves and exits
     * back to the main window.
     * @param actionEvent click product save button
     * @throws IOException
     */
    public void clickProductSave(ActionEvent actionEvent) throws IOException {

        //clears error labels
        prodNameErrorLabel.setText("");
        prodInvErrorLabel.setText("");
        prodPriceErrorLabel.setText("");
        prodMaxErrorLabel.setText("");
        prodMinErrorLabel.setText("");

        isNameInputValid();
        isInvInputValid();
        isPriceInputValid();
        isMaxInputValid();
        isMinInputValid();
        if(areInputsValid() == true){
            if(minMaxError() == true && invError() == true){
                productSave();
                assoParts.removeAll();
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
     * Saves the input in the text boxes to the allProduct list
     */
    public void productSave(){
        String name = modifyProductNameTB.getText();
        double price = Double.parseDouble(modifyProductPriceTB.getText());
        int inv = Integer.parseInt(modifyProductInvTB.getText());
        int max = Integer.parseInt(modifyProductMaxTB.getText());
        int min = Integer.parseInt(modifyProductMinTB.getText());
        ObservableList list = assoParts;
        Product modProduct = new Product(id, name, price, inv, min, max, list);
        Inventory.updateProduct(id, modProduct);
    }

    /***
     * Checks to make sure the price input is double
     * @return true if input is a double
     */
    private boolean isPriceInputValid(){
        Boolean b = false;
        if((modifyProductPriceTB.getText() != null || modifyProductPriceTB.getText().length() != 0)){
            try {
                Double.valueOf(modifyProductPriceTB.getText());
                b = true;
            } catch (NumberFormatException e) {
                prodPriceErrorLabel.setText("Price is not a double.");
            }
        }
        return b;
    }

    /***
     * Checks to make sure the name input is there
     * @return true if name input is there
     */
    private boolean isNameInputValid(){
        Boolean b = false;
        if ((modifyProductNameTB.getText() == null || modifyProductNameTB.getText().length() == 0)){
            prodNameErrorLabel.setText("No data in name field.");
        } else {
            b = true;
        }
        return b;
    }

    /***
     * Checks to make sure inv input is an int
     * @return true if input is int
     */
    private boolean isInvInputValid(){
        Boolean b = false;
        if ((modifyProductInvTB.getText() != null || modifyProductInvTB.getText().length() != 0)){
            try {
                Integer.valueOf(modifyProductInvTB.getText());
                b = true;
            } catch (NumberFormatException e) {
                prodInvErrorLabel.setText("Inventory is not an int.");
            }
        }
        return b;
    }

     /***
     * Checks to make sure max input is an int
     * @return true if it is an int
     */
    private boolean isMaxInputValid(){
        Boolean b = false;
        if ((modifyProductMaxTB.getText() != null || modifyProductMaxTB.getText().length() != 0 )){}
        try {
            Integer.valueOf(modifyProductMaxTB.getText());
            b = true;
        } catch (NumberFormatException e) {
            prodMaxErrorLabel.setText("Max is not an int.");
        }
        return b;
    }

    /***
     * Checks to make sure min input is int
     * @return true if min input is int
     */
    private boolean isMinInputValid(){
        Boolean b = false;
        if ((modifyProductMinTB.getText() != null || modifyProductMinTB.getText().length() != 0 )){}
        try {
            Integer.valueOf(modifyProductMinTB.getText());
            b = true;
        } catch (NumberFormatException e) {
            prodMinErrorLabel.setText("Min is not an int.");
        }
        return b;
    }

    /***
     * Checks to make sure all inputs are valid
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
     * Check to make sure min is less than max
     * @return true if min is less than max
     */
    private boolean minMaxError(){
        Boolean e = false;
        if(Integer.valueOf(modifyProductMaxTB.getText()) > Integer.valueOf(modifyProductMinTB.getText())){
            e = true;
        }else{
            prodMinErrorLabel.setText("Max must be greater then min.");
        }
        return e;
    }

    /***
     * Checks to make sure inventory falls between min and max
     * @return true if inv is between min and max
     */
    private boolean invError(){
        Boolean e = false;
        if (Integer.valueOf(modifyProductInvTB.getText()) >= Integer.valueOf(modifyProductMinTB.getText()) && Integer.valueOf(modifyProductInvTB.getText()) <= Integer.valueOf(modifyProductMaxTB.getText())){
            e = true;
        }else{
            prodInvErrorLabel.setText("Inv must be between Min and Max");
        }
        return e;
    }

    /***
     * Returns to main window
     * @param actionEvent click cancel button
     * @throws IOException
     */
    public void clickProductCancel(ActionEvent actionEvent) throws IOException {
        assoParts.removeAll();
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/Main.fxml"));
        Stage stage = (Stage) productCancelButton.getScene().getWindow();
        Scene scene = new Scene(root, 920, 468);
        stage.setTitle("Inventory System");
        stage.setScene(scene);
        stage.show();
    }
}
