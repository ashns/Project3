import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Logger;

public class Controller {
    @FXML
    TextArea display = new TextArea();
    @FXML
    Label rateLabel = new Label();
    @FXML
    Label payLabel = new Label();
    @FXML
    TextField nameBox = new TextField();
    @FXML
    Button addBtn = new Button();
    @FXML
    ToggleGroup Dept = new ToggleGroup();
    @FXML
    DatePicker dateBox = new DatePicker();
    @FXML
    ToggleGroup Position = new ToggleGroup();
    @FXML
    TextField payBox = new TextField();
    @FXML
    TextField hourBox = new TextField();
    @FXML
    FileChooser databaseFile = new FileChooser();
    @FXML
    ToggleGroup Management = new ToggleGroup();
    @FXML
    ToggleGroup Print = new ToggleGroup();
    @FXML
    Button importBTN = new Button();
    @FXML
    Button exportBTN = new Button();
    @FXML
    Button processBTN = new Button();

    StringTokenizer parse;

    Company com = new Company();

    public void dataTest(ActionEvent actionEvent) {
        String last = "", first = "";
        char middle = ' ';
        parse = new StringTokenizer(nameBox.getText());
        switch (parse.countTokens()) {
            case 1:
                last = parse.nextToken();
                break;
            case 2:
                first = parse.nextToken();
                last = parse.nextToken();
                break;
            case 3:
                first = parse.nextToken();
                middle = parse.nextToken().charAt(0);
                last = parse.nextToken();
                break;
            default:
                display.setText("Please enter a valid name.");
                break;
        }

        if (first != "")
            last += ", " + first;

        if (middle != ' ')
            last += " " + middle + ".";

        if (last != "")
            display.setText("Recieved name: " + last + "\n");
    }

    public void rateChange(ActionEvent actionEvent) {
        payLabel.setText("Hourly Rate: $");
        rateLabel.setText("/ hour");
    }

    public void salaryChange(ActionEvent actionEvent) {
        payLabel.setText("Annual Salary: $");
        rateLabel.setText("/ year");
    }

    public Profile getEnteredProfile() {
        String name = nameBox.getText();
        String department = Dept.getSelectedToggle().toString();
        Date hireDate = new Date(dateBox.getValue().toString());
        Profile empProfile = new Profile(name, department, hireDate);
        return empProfile;
    }

    public void add(ActionEvent actionEvent) {
        Float rate = null;
        try{
            rate = Float.parseFloat(payBox.getText());
            payBox.setText("Is Float: " + rate);
        }catch (NumberFormatException e){
            payBox.setText("Not Float!");
        }
        Profile hireProfile = getEnteredProfile();

        if (Position.getSelectedToggle().toString() == "PTRB") {
            Parttime newHire = new Parttime(hireProfile, rate);
            com.add(newHire);
        } else if (Position.getSelectedToggle().toString() == "FTRB") {
            Fulltime newHire = new Fulltime(hireProfile, rate);
            com.add(newHire);
        } else {
            Management newHire = new Management(hireProfile, rate, getManagementPosition());
            com.add(newHire);
        }
        com.print();
    }

    public int getManagementPosition(){
        final int manager = 1;
        final int departmentHead = 2;
        final int districtHead = 3;
        if(Management.getSelectedToggle().toString()=="managerRB"){
            return manager;
        }
        else if(Management.getSelectedToggle().toString()=="DepartmentRB"){
            return departmentHead;
        }
        else{
            return districtHead;
        }
    }

    public void remove(ActionEvent actionEvent) {
        Float rate = null;
        try{
            rate = Float.parseFloat(payBox.getText());
            payBox.setText("Is Float: " + rate);
        }catch (NumberFormatException e){
            payBox.setText("Not Float!");
        }
        Profile empProfile = getEnteredProfile();

        if (Position.getSelectedToggle().toString() == "PTRB") {
            Parttime oldHire = new Parttime(empProfile, rate);
            com.remove(oldHire);
        } else if (Position.getSelectedToggle().toString() == "FTRB") {
            Fulltime oldHire = new Fulltime(empProfile, rate);
            com.remove(oldHire);
        } else {
            Management oldHire = new Management(empProfile, rate, getManagementPosition());
        }
    }

    public void clear(ActionEvent actionEvent) {
        nameBox.clear();
        payBox.clear();
        hourBox.clear();
        dateBox.getEditor().clear();

    }

    public void setHours(ActionEvent actionEvent) {
        Float hours = null;
        try{
            hours = Float.parseFloat(payBox.getText());
            payBox.setText("Is Float: " + hours);
        }catch (NumberFormatException e){
            payBox.setText("Not Float!");
        }
        Profile empProfile = getEnteredProfile();
        Parttime toUpdate = new Parttime(empProfile);
        toUpdate.setHours(hours);
    }

    public void importFile(ActionEvent actionEvent) throws FileNotFoundException {

        File file = databaseFile.showOpenDialog(null);
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            System.out.println(data);
        }
        myReader.close();
    }


    public void exportDatabase(ActionEvent actionEvent){
        File file = databaseFile.showSaveDialog(null);
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(com.toString());
            writer.close();
        } catch (IOException ex) {

        }
    }

}
