package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InhousePart;
import model.Inventory;
import model.OutsourcedPart;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/***
 * Joshua Breen C482 Project
 * Add Part Controller - used to add parts to the part list
 * FUTURE UPDATES: Further inputs could be added for tracking more data. More error checks could also be added.
 */

public class AddPartController implements Initializable {
    public Button addPartSaveButton;
    public Button addPartCancelButton;
    public RadioButton inHouseRadio;
    public RadioButton outsourcedRadio;
    public Label radioChangeLabel;
    public TextField addPartIDTB;
    public TextField addPartNameTB;
    public TextField addPartInvTB;
    public TextField addPartPriceTB;
    public TextField addPartMaxTB;
    public TextField addPartMinTB;
    public TextField addPartMCIDTB;
    public Label partNameErrorLabel;
    public Label partInvErrorLabel;
    public Label partPriceErrorLabel;
    public Label partMaxErrorLabel;
    public Label partMinErrorLabel;
    public Label partMCIDErrorLabel;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    /***
     * Saves the part as inhouse. It saves all the information in the text boxes and converts it to the proper data type
     * to be inserted to the allParts list in Inventory at the end.
     */
    public void inHouseSave(){

            int id = Inventory.getPartInvNum();   //generates the ID number
            String name = addPartNameTB.getText();  //gets the part name
            double price = Double.parseDouble(addPartPriceTB.getText());  //gets the price
            int inv = Integer.parseInt(addPartInvTB.getText());  //gets the inventory number
            int max = Integer.parseInt(addPartMaxTB.getText());  //gets the max number
            int min = Integer.parseInt(addPartMinTB.getText());  //gets the min number
            int machineID = Integer.parseInt(addPartMCIDTB.getText());  //gets the company name
            Inventory.addPart(new InhousePart(id, name, price, inv, min, max, machineID));  //creates the part

        }


    /***
     * Saves the part as Outsource. It saves all the information from the text boxes and converts it to the proper data type
     * to be inserted to the allParts list in Inventory at the end.
     */

    public void outSourceSave(){

        int id = Inventory.getPartInvNum();    //generates the ID number
        String name = addPartNameTB.getText();  //gets the part name
        double price = Double.parseDouble(addPartPriceTB.getText());  //gets the price
        int inv = Integer.parseInt(addPartInvTB.getText());  //gets the inventory number
        int max = Integer.parseInt(addPartMaxTB.getText());  //gets the max number
        int min = Integer.parseInt(addPartMinTB.getText());  //gets the min number
        String companyName = addPartMCIDTB.getText();        //gets the company name
        Inventory.addPart(new OutsourcedPart(id, name, price, inv, min, max, companyName)); //creates the part
    }

    /***
     * This checks the input in the price text box to confirm that it is a double.
     * @return true if the data is tested as valid
     */
    private boolean isPriceInputValid() {   //Checks to see if Price is a double
        Boolean b = false;
        if ((addPartPriceTB.getText() != null || addPartPriceTB.getText().length() != 0)) {
            try {
                Double.valueOf(addPartPriceTB.getText());  //checks if part price is a double
                b = true;
            } catch (NumberFormatException e) {
                partPriceErrorLabel.setText("Price is not a double.");
            }
        }
        return b;
    }

    /***
     * This checks to make sure that the name text box has data in it.
     * @return true if the data is valid/not blank
     */
    private boolean isNameInputValid() {   //Checks if part name is input
        Boolean b = false;
        if ((addPartNameTB.getText() == null || addPartNameTB.getText().length() == 0)) {
            partNameErrorLabel.setText("No data in name field");

        } else {
            b = true;
        }
        return b;
    }

    /***
     * Checks to make sure the inventory input in the text box is an int.
     * @return true if Inv text box data is an int
     */
    private boolean isInvInputValid() {   //Checks to see if Inv input is Int
        Boolean b = false;
        if ((addPartInvTB.getText() != null || addPartInvTB.getText().length() != 0)) {
            try {
                Integer.valueOf(addPartInvTB.getText());
                b = true;
            } catch (NumberFormatException e) {
                partInvErrorLabel.setText("Inventory is not an int.");
            }
        }
        return b;
    }

    /***
     * Checks to make sure that Max input in the text box is an int.
     * @return true if Max input is an int.
     */
    private boolean isMaxInputValid() {   //Checks to see if max is an int
        Boolean b = false;
        if ((addPartMaxTB.getText() != null || addPartMaxTB.getText().length() != 0)) {
            try {
                Integer.valueOf(addPartMaxTB.getText());
                b = true;
            } catch (NumberFormatException e) {
                partMaxErrorLabel.setText("Max is not an int.");
            }
        }
        return b;
    }

    /***
     * Checks to make sure that the Min input in the text box is an int.
     * @return true if Min input is an int
     */
    private boolean isMinInputValid() {   //Checks to see if min is an int
        Boolean b = false;
        if ((addPartMinTB.getText() != null || addPartMinTB.getText().length() != 0)) {
            try {
                // Do all the validation you need here such as
                Integer.valueOf(addPartMinTB.getText());
                b = true;
            } catch (NumberFormatException e) {
                partMinErrorLabel.setText("Min is not an int.");
            }
        }
        return b;
    }

    /***
     * Checks to make sure that Machine ID input in the text box is an int.
     * @return true if Machine ID is an int.
     */
    private boolean isMachineIDInputValid() {   //Checks to see if machine id is int
        Boolean b = false;
        if ((addPartMCIDTB.getText() != null || addPartMCIDTB.getText().length() != 0)) {
            try {
                // Do all the validation you need here such as
                Integer.valueOf(addPartMCIDTB.getText());
                b = true;
            } catch (NumberFormatException e) {
                partMCIDErrorLabel.setText("Machine ID is not an int.");
            }
        }
        return b;
    }

    /***
     * Checks all the input text boxes for valid data for Inhouse Parts.
     * @return true if all inhouse input data checks are valid/true
     * RUNTIME ERROR: I had initially combined Inhouse and Outsourced checks and because some of the opposite part type would show up as false. I separated the checks to fix the problem.
     */
    private boolean areInhouseInputsValid(){  //checks to make sure all Inhouse inputs are valid
        Boolean q = false;
        if(isPriceInputValid() == true && isNameInputValid() == true && isInvInputValid() == true && isMaxInputValid() == true && isMachineIDInputValid() == true && minMaxError() == true && invError() == true){
             q = true;
        }
        return q;
    }

    /***
     * Checks to make sure all the outsource part inputs are valid
     * @return true if all outsource input data checks are valid/true
     */
    private boolean areOutsourceInputsValid(){ //checks to make sure all outsource inputs are valid
        Boolean q = false;
        if(isPriceInputValid() == true && isNameInputValid() == true && isInvInputValid() == true && isMaxInputValid() == true && isCompanyNameInputValid() == true && minMaxError() == true && invError() == true) {
            q = true;
        }
        return q;
    }

    /***
     * Checks to make sure that the Max input is higher then the Min
     * @return true if max is greater then min
     */
    private boolean minMaxError(){  //checks to make sure that max is greater then minimum
        Boolean e = false;
        if (Integer.valueOf(addPartMaxTB.getText()) > Integer.valueOf(addPartMinTB.getText())){
            e = true;
        }else{
            partMinErrorLabel.setText("Max must be greater then min.");
        }
        return e;
    }

    /***
     * Checks to make sure that the inventory amount falls between the Min and Max
     * @return true if Inventory falls between Min and Max
     */
    private boolean invError(){  //checks to make sure inv is between min and max
        Boolean e = false;
        if (Integer.valueOf(addPartInvTB.getText()) >= Integer.valueOf(addPartMinTB.getText()) && Integer.valueOf(addPartInvTB.getText()) <= Integer.valueOf(addPartMaxTB.getText())){
            e = true;
        }else{
            partInvErrorLabel.setText("Inv must be between Min and Max");
        }
        return e;
    }

    /***
     * Checks to see if minMaxError or invError found a problem with the input data
     * @return true if minMaxError or invError find a problem
     */
    private boolean errorsFound(){  //checks to see if there were any errors that need to stop
        Boolean e = false;
        if(minMaxError() == false || invError() == false){
            e = true;
        }
        return e;
    }

    /***
     * Checks to make sure the Company Name input is valid
     * @return true if Company Name Input is valid
     */
    private boolean isCompanyNameInputValid() {   //Checks to see Company Name field has input
        Boolean b = false;
        if ((addPartMCIDTB.getText() == null || addPartMCIDTB.getText().length() == 0)) {
            partMCIDErrorLabel.setText("No data in company name field");
        } else {
            b = true;
        }
        return b;
    }

    /***
     * When you click part save it clears the error labels, checks which radio button is checked inhouse or outsource
     * runs the validation checks, checks for any errors and if everything is accurate it saves the part. Then exits the
     * window back to the main window
     * @param actionEvent clicking the Part Save button
     * @throws IOException
     */
    public void clickPartSave(ActionEvent actionEvent) throws IOException{
        //clears labels when button is pushed so you can see if there are new errors
        partNameErrorLabel.setText("");
        partInvErrorLabel.setText("");
        partPriceErrorLabel.setText("");
        partMaxErrorLabel.setText("");
        partMinErrorLabel.setText("");
        partMCIDErrorLabel.setText("");

        //runs the input validation for inputs if In House radio is selected
        if(inHouseRadio.isSelected()){
            isPriceInputValid();
            isNameInputValid();
            isInvInputValid();
            isMaxInputValid();
            isMinInputValid();
            isMachineIDInputValid();
            if(areInhouseInputsValid() == true) {    //if all inputs are valid check for errors
                if(errorsFound() == false) {  //check inputs for errors if no errors it will save
                    inHouseSave();
                }
            }
        }
        //runs the input validation for inputs if In House radio is selected
        if(outsourcedRadio.isSelected()){
            isPriceInputValid();
            isNameInputValid();
            isInvInputValid();
            isMaxInputValid();
            isMinInputValid();
            isCompanyNameInputValid();
            if (areOutsourceInputsValid() == true) {    //if all inputs are valid check for errors

                    outSourceSave();

            }
        }
        if((areOutsourceInputsValid() == true || areInhouseInputsValid() == true) && errorsFound() == false) { //if all inputs are valid and no errors found, can close window

            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/Main.fxml"));
            Stage stage = (Stage) addPartSaveButton.getScene().getWindow();
            Scene scene = new Scene(root, 920, 468);
            stage.setTitle("Inventory System");
            stage.setScene(scene);
            stage.show();
        }


    }

    /***
     * Closes the window and goes back to the main screen.
     * @param actionEvent click the cancel button
     * @throws IOException
     */
    public void clickPartCancel(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/Main.fxml"));
        Stage stage = (Stage) addPartCancelButton.getScene().getWindow();
        Scene scene = new Scene(root, 920, 468);
        stage.setTitle("Inventory System");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * Clears error labels if inhouse radio button is selected and sets the text box to have Machine ID Label
     * @param actionEvent click inhouse radio button
     */
    public void inHouseSelect(ActionEvent actionEvent) {
        radioChangeLabel.setText("Machine ID");
        partNameErrorLabel.setText("");
        partInvErrorLabel.setText("");
        partPriceErrorLabel.setText("");
        partMaxErrorLabel.setText("");
        partMinErrorLabel.setText("");
        partMCIDErrorLabel.setText("");

    }

    /***
     * If you click the outsource radio button it clears the error labels and sets the text box to have company name label.
     * @param actionEvent click outsource radio button
     */
    public void outsourceSelect(ActionEvent actionEvent) {
        radioChangeLabel.setText("Company Name");
        partNameErrorLabel.setText("");
        partInvErrorLabel.setText("");
        partPriceErrorLabel.setText("");
        partMaxErrorLabel.setText("");
        partMinErrorLabel.setText("");
        partMCIDErrorLabel.setText("");

    }
}
