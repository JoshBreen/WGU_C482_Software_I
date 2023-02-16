package View_Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

/***
 * Joshua Breen C482 Project
 * Main Controller - main window for viewing parts and products
 * FUTURE UPDATE: Could implement more ways to combine and manipulate data.
 */

public class MainController implements Initializable {
    public TableView partsTable;
    public TableColumn partIDCol;
    public TableColumn partNameCol;
    public TableColumn partInvLvlCol;
    public TableColumn partPriceCol;
    public TableView prodTable;
    public TableColumn prodIDCol;
    public TableColumn prodNameCol;
    public TableColumn prodInvLvlCol;
    public TableColumn prodPriceCol;
    public Button addPartButton;
    public Button modifyPartButton;
    public Button deletePartButton;
    public Button addProductButton;
    public Button modifyProductButton;
    public Button exitButton;
    public Button deleteProductButton;
    public TextField mainPartSearch;
    public TextField mainProductSearch;
    public Label searchError;
    private static ObservableList<Part> assoParts = FXCollections.observableArrayList();

    private static Part partTransfer = null;
    private static Product productTransfer = null;
    public Label prodErrorLabel;

    /***
     * Used to send the selected part to Modify Part
     * @return partTransfer to send it to Modify Part Window
     */
    public static Part getPartTransfer()
    {
        return partTransfer;
    }

    /***
     * Used to send the selected product
     * @return productTransfer to send to Modify product window
     */
    public static Product getProductTransfer(){
        return productTransfer;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /***
         * Used to set the table to show price as currency.
         * RUNTIME ERROR: The table would only show one decimal place, converting it to currency gave me the
         * two decimal places as needed.
         */
        NumberFormat partFormat = NumberFormat.getCurrencyInstance();
        partPriceCol.setCellFactory(prc -> new TableCell<Part, Double>() {

            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if(empty) {
                    setText(null);
                } else {
                    setText(partFormat.format(price));
                }
            }
        });

        /***
         * Used to set the table to show price as currency.
         * RUNTIME ERROR: The table would only show one decimal place, converting it to currency gave me the
         * two decimal places as needed.
         */
        NumberFormat prodFormat = NumberFormat.getCurrencyInstance();
        prodPriceCol.setCellFactory(prc -> new TableCell<Product, Double>() {

            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if(empty) {
                    setText(null);
                } else {
                    setText(prodFormat.format(price));
                }
            }
        });

        //adds items to the parts and products tables
        partsTable.setItems(Inventory.getAllParts());
        prodTable.setItems(Inventory.getAllProducts());

        //sets up the tables
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        prodIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /***
     * Read text for searching parts table
     * RUNTIME ERROR: A problem I had was if it didn't find a result for the letter, it assumed it was an int.
     * Adding the try and catch fixed that problem.
     * @param actionEvent hit enter after typing search term into search text box
     */
    public void searchParts(ActionEvent actionEvent){
        String s = mainPartSearch.getText();
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
                    mainPartSearch.setText("");
                }
            }
            catch (NumberFormatException e){
                searchError.setText("No item name matches search term.");  //error message if no part name matches search term
            }
        }
            partsTable.setItems(ps);
            mainPartSearch.setText("");
    }

    /***
     * Read text for searching products table
     * @param actionEvent hit enter after typing search term into search text box
     */
    public void searchProducts(ActionEvent actionEvent){
        String s = mainProductSearch.getText();
        searchError.setText("");
        ObservableList<Product> ps = Inventory.lookupProduct(s);
        if(ps.size() == 0){
            try {
                int id = Integer.parseInt(s);
                Product pn = Inventory.lookupProduct(id);
                if (pn != null) {
                    ps.add(pn);
                    searchError.setText("");
                } else {
                    searchError.setText("No item number in list.");  //error message if no part id matches search term
                    mainPartSearch.setText("");
                }
            }
            catch (NumberFormatException e){
                searchError.setText("No item name matches search term.");  //error message if no part name matches search term
            }
        }
        prodTable.setItems(ps);
        mainPartSearch.setText("");
    }

    /***
     * Exits the program
     * @param actionEvent click exit button
     */
    public void clickExit(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
    }

    /***
     * Opens the add Part window
     * @param actionEvent click the add part button
     * @throws IOException
     */
    public void clickAddPart(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/AddPart.fxml"));
        Stage stage = (Stage) addPartButton.getScene().getWindow();
        Scene scene = new Scene(root, 612, 600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * Opens modify part window
     * @param actionEvent click modify part button
     * @throws IOException
     */
    public void clickModifyPart(ActionEvent actionEvent) throws IOException {
        if(partsTable.getSelectionModel().getSelectedIndex() != -1){
        partTransfer = (Part) partsTable.getSelectionModel().getSelectedItem();
            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/ModifyPart.fxml"));
            Stage stage = (Stage) modifyPartButton.getScene().getWindow();
            Scene scene = new Scene(root, 612, 600);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    /***
     * Deletes selected part from the table and allParts list
     * @param actionEvent click delete part button
     */
    public void clickDeletePart(ActionEvent actionEvent) {
        if (partsTable.getSelectionModel().getSelectedIndex() != -1) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete selected item?", "Confirm Delete", dialogButton);
            if (dialogResult == JOptionPane.YES_OPTION) {
                Part part = (Part) partsTable.getSelectionModel().getSelectedItem();
                Inventory.deletePart(part.getId());
            }
        }
    }


    /***
     * Opens the add product window
     * @param actionEvent click add product button
     * @throws IOException
     */
    public void clickAddProduct(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/AddProduct.fxml"));
        Stage stage = (Stage) addProductButton.getScene().getWindow();
        Scene scene = new Scene(root, 948, 715);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * Opens Modify Product Window
     * @param actionEvent click Modify Product button
     * @throws IOException
     */
    public void clickModifyProduct(ActionEvent actionEvent) throws IOException{

        if(prodTable.getSelectionModel().getSelectedIndex() != -1) {
            productTransfer = (Product) prodTable.getSelectionModel().getSelectedItem();
            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/ModifyProduct.fxml"));
            Stage stage = (Stage) modifyProductButton.getScene().getWindow();
            Scene scene = new Scene(root, 948, 715);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        }
    }

    /***
     * Deletes product from the list if it has no associated parts
     * @param actionEvent click delete product button
     */
    public void clickDeleteProduct(ActionEvent actionEvent) {
        if (prodTable.getSelectionModel().getSelectedIndex() != -1) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete selected item?", "Confirm Delete", dialogButton);
            if (dialogResult == JOptionPane.YES_OPTION) {
                //still need to add a check if a part is connected to a product
                Product product = (Product) prodTable.getSelectionModel().getSelectedItem();
                assoParts = product.getAllAssociatedParts();
                if (assoParts.size() == 0){
                    Inventory.deleteProduct(product.getId());
                } else {
                    prodErrorLabel.setText("Product has associated parts and can not be deleted.");
                    return;
                }

            }
        }
    }



}
