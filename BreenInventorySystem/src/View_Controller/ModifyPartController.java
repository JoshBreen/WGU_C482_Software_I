package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InhousePart;
import model.Inventory;
import model.OutsourcedPart;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/***
 * Joshua Breen C482 Project
 * Modify Part Controller - Window for modifying parts
 * FUTURE UPDATE: Could implement more data to track for a part and more ways to modify the data
 */

public class ModifyPartController implements Initializable {

    public TextField modifyPartIDTB;
    public TextField modifyPartNameTB;
    public TextField modifyPartInvTB;
    public TextField modifyPartPriceTB;
    public TextField modifyPartMaxTB;
    public TextField modifyPartMinTB;
    public TextField modifyPartMCIDTB;
    public RadioButton outsourcedRadio;
    public RadioButton inHouseRadio;
    public Label partInvErrorLabel;
    public Label partMCIDErrorLabel;
    public Label partMinErrorLabel;
    public Label partMaxErrorLabel;
    public Label partPriceErrorLabel;
    public Label radioChangeLabel;
    public Button modifyPartSaveButton;
    public Button modifyPartCancelButton;
    public Label partNameErrorLabel;

    private Part part = null;
    private int id = -1;


    /***
     * Sets part as the selected part from Main window and runs setText to fill in part data
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        part = MainController.getPartTransfer();   //get part selected from main screen
        setText();                                 //runs setText which fills in the fields

    }

    /***
     * Fills in the text boxes with data based on the selected part.
     */
    public void setText(){

        id = part.getId();
        /***
         * Runs for if it's an Inhouse part
         */
        if(part instanceof InhousePart) {
            inHouseRadio.setSelected(true);
            radioChangeLabel.setText("Machine ID");
            modifyPartIDTB.setText(String.valueOf(id));
            modifyPartNameTB.setText(part.getName());
            modifyPartInvTB.setText(String.valueOf(part.getStock()));
            modifyPartPriceTB.setText(String.valueOf(part.getPrice()));
            modifyPartMaxTB.setText(String.valueOf(part.getMax()));
            modifyPartMinTB.setText(String.valueOf(part.getMin()));
            modifyPartMCIDTB.setText(String.valueOf(((InhousePart) part).getMachineID()));
        }
        /***
         * runs for if it's an outsourced part
         */
        if(part instanceof OutsourcedPart){
            outsourcedRadio.setSelected(true);
            radioChangeLabel.setText("Company Name");
            modifyPartIDTB.setText(String.valueOf(id));
            modifyPartNameTB.setText(part.getName());
            modifyPartInvTB.setText(String.valueOf(part.getStock()));
            modifyPartPriceTB.setText(String.valueOf(part.getPrice()));
            modifyPartMaxTB.setText(String.valueOf(part.getMax()));
            modifyPartMinTB.setText(String.valueOf(part.getMin()));
            modifyPartMCIDTB.setText(String.valueOf(((OutsourcedPart) part).getCompanyName()));

        }

    }

    /***
     * Saves the part
     * @param actionEvent on click save button
     * @throws IOException
     */
    public void clickPartSave(ActionEvent actionEvent) throws IOException {

        /***
         * clears the error labels
         */
        partNameErrorLabel.setText("");
        partInvErrorLabel.setText("");
        partPriceErrorLabel.setText("");
        partMaxErrorLabel.setText("");
        partMinErrorLabel.setText("");
        partMCIDErrorLabel.setText("");

        /***
         * Runs if inHouseRadio is selected, saves part as inHouse
         */
        if(inHouseRadio.isSelected()) {
            isNameInputValid();
            isInvInputValid();
            isPriceInputValid();
            isMaxInputValid();
            isMinInputValid();
            isMachineIDInputValid();
            if(areInhouseInputsValid() == true){
                if(minMaxError() == true && invError() == true) {
                    inHouseSave();
                    Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/Main.fxml"));
                    Stage stage = (Stage) modifyPartSaveButton.getScene().getWindow();
                    Scene scene = new Scene(root, 920, 468);
                    stage.setTitle("Inventory System");
                    stage.setScene(scene);
                    stage.show();
                }
            }
        }
        /***
         * Runs if outsourced radio is selected, saves part as outsourced
         */
        if(outsourcedRadio.isSelected()){
            isNameInputValid();
            isInvInputValid();
            isPriceInputValid();
            isMaxInputValid();
            isMinInputValid();
            isCompanyNameInputValid();
            if(areOutsourceInputsValid() == true) {
                if(minMaxError() == true && invError() == true){
                    outsourcedSave();
                    Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/Main.fxml"));
                    Stage stage = (Stage) modifyPartSaveButton.getScene().getWindow();
                    Scene scene = new Scene(root, 920, 468);
                    stage.setTitle("Inventory System");
                    stage.setScene(scene);
                    stage.show();
                }
            }
        }
    }

    /***
     * Saves part as inhouse part
     */
    public void inHouseSave(){
        String name = modifyPartNameTB.getText();
        double price = Double.parseDouble(modifyPartPriceTB.getText());
        int inv = Integer.parseInt(modifyPartInvTB.getText());
        int max = Integer.parseInt(modifyPartMaxTB.getText());
        int min = Integer.parseInt(modifyPartMinTB.getText());
        int machineID = Integer.parseInt(modifyPartMCIDTB.getText());
        InhousePart modPart = new InhousePart(id, name, price, inv, min, max, machineID);
        Inventory.updatePart(id, modPart);
    }

    /***
     * Saves part as outsourced part
     */
    public void outsourcedSave(){
        String name = modifyPartNameTB.getText();
        double price = Double.parseDouble(modifyPartPriceTB.getText());
        int inv = Integer.parseInt(modifyPartInvTB.getText());
        int max = Integer.parseInt(modifyPartMaxTB.getText());
        int min = Integer.parseInt(modifyPartMinTB.getText());
        String comName = modifyPartMCIDTB.getText();
        OutsourcedPart modPart = new OutsourcedPart(id, name, price, inv, min, max, comName);
        Inventory.updatePart(id, modPart);
    }


    /***
     * Cancels out of the window back to main window
     * @param actionEvent on clicking cancel button
     * @throws IOException
     */
    public void clickPartCancel(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/Main.fxml"));
        Stage stage = (Stage) modifyPartCancelButton.getScene().getWindow();
        Scene scene = new Scene(root, 920, 468);
        stage.setTitle("Inventory System");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * Changes label to read Machine ID if inHouse is selected
     * @param actionEvent on clicking inhouse radio button
     */
    public void inHouseSelect(ActionEvent actionEvent) {
        radioChangeLabel.setText("Machine ID");
    }

    /***
     * Chancels label to read Company Name if Outsource is selected
     * @param actionEvent
     */
    public void outsourceSelect(ActionEvent actionEvent) {
        radioChangeLabel.setText("Company Name");
    }

    /***
     * Checks to make sure price is a double
     * @return true if input is a double
     */
    private boolean isPriceInputValid() {
        Boolean b = false;
        if ((modifyPartPriceTB.getText() != null || modifyPartPriceTB.getText().length() != 0)){
            try {
                Double.valueOf(modifyPartPriceTB.getText());
                b = true;
            } catch (NumberFormatException e) {
                partPriceErrorLabel.setText("Price is not a double.");
            }
        }
        return b;
    }

    /***
     * Checks to make sure name text box has input
     * @return true if text box has input
     */
    private boolean isNameInputValid(){
        Boolean b = false;
        if ((modifyPartNameTB.getText() == null || modifyPartNameTB.getText().length() == 0)){
            partNameErrorLabel.setText("No data in name field.");
        } else {
            b = true;
        }
        return b;
    }

    /***
     * Checks to make sure inv is int
     * @return true if inv is int
     */
    private boolean isInvInputValid(){
        Boolean b = false;
        if ((modifyPartInvTB.getText() != null || modifyPartInvTB.getText().length() != 0)){
            try {
                Integer.valueOf(modifyPartInvTB.getText());
                b = true;
            } catch (NumberFormatException e) {
                partInvErrorLabel.setText("Inventory is not an int.");
            }

        }
        return b;
    }

    /***
     * Checks to make sure max is an int
     * @return true if max is an int
     */
    private boolean isMaxInputValid(){
        Boolean b = false;
        if ((modifyPartMaxTB.getText() != null || modifyPartMaxTB.getText().length() != 0 )){}
        try {
            Integer.valueOf(modifyPartMaxTB.getText());
            b = true;
        } catch (NumberFormatException e) {
            partMaxErrorLabel.setText("Max is not an int.");
        }
        return b;
    }

    /***
     * Checks to make sure min is an int
     * @return true if min is an int
     */
    private boolean isMinInputValid(){
        Boolean b = false;
        if ((modifyPartMinTB.getText() != null || modifyPartMinTB.getText().length() != 0 )){}
        try {
            Integer.valueOf(modifyPartMinTB.getText());
            b = true;
        } catch (NumberFormatException e) {
            partMinErrorLabel.setText("Min is not an int.");
        }
        return b;
    }

    /***
     * Checks to make sure machine ID is an int
     * @return true if machine ID is an int
     */
    private boolean isMachineIDInputValid(){
        Boolean b = false;
        if ((modifyPartMCIDTB.getText() != null || modifyPartMCIDTB.getText().length() != 0)){
            try{
                Integer.valueOf(modifyPartMCIDTB.getText());
                b = true;
            } catch (NumberFormatException e) {
                partMCIDErrorLabel.setText("Machine ID is not an int.");
            }
        }
        return b;
    }

    /***
     * Checks to make sure Company name has input
     * @return true if company name has input
     */
    private boolean isCompanyNameInputValid(){
        Boolean b = false;
        if ((modifyPartMCIDTB.getText() == null || modifyPartMCIDTB.getText().length() == 0)){
            partMCIDErrorLabel.setText("No data in company name field");
        } else {
            b = true;
        }
        return b;
    }


    /***
     * Checks to make sure all inhouse inputs are valid
     * @return true if all innhouse inputs are valid
     */
    private boolean areInhouseInputsValid(){
        Boolean q = false;
        if(isPriceInputValid() == true && isNameInputValid() == true && isInvInputValid() == true && isMaxInputValid() == true && isMachineIDInputValid() == true){
            q = true;
        }
        return q;
    }

    /***
     * Checks to make sure all outsource inputs are valid
     * @return true if all outsource inputs are valid
     */
    private boolean areOutsourceInputsValid(){
        Boolean q = false;
        if(isPriceInputValid() == true && isNameInputValid() == true && isInvInputValid() == true && isMaxInputValid() == true && isCompanyNameInputValid() == true){
            q = true;
        }
        return q;
    }

    /***
     * Checks to make sure min is less than max
     * @return true if min is less than max
     */
    private boolean minMaxError(){
        Boolean e = false;
        if(Integer.valueOf(modifyPartMaxTB.getText()) > Integer.valueOf(modifyPartMinTB.getText())){
            e = true;
        }else{
            partMinErrorLabel.setText("Max must be greater then min.");
        }
        return e;
    }

    /***
     * Checks to make sure inv falls between min and max
     * @return true if inv falls between min and max
     */
    private boolean invError(){
        Boolean e = false;
        if (Integer.valueOf(modifyPartInvTB.getText()) >= Integer.valueOf(modifyPartMinTB.getText()) && Integer.valueOf(modifyPartInvTB.getText()) <= Integer.valueOf(modifyPartMaxTB.getText())){
            e = true;
        }else{
            partInvErrorLabel.setText("Inv must be between Min and Max");
        }
        return e;
    }

}
