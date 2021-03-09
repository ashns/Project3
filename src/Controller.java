import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

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
    @FXML
    RadioButton PTRB = new RadioButton();
    @FXML
    RadioButton FTRB = new RadioButton();
    @FXML
    RadioButton MGRB = new RadioButton();
    @FXML
    RadioButton printRB = new RadioButton();
    @FXML
    RadioButton printByDateRB = new RadioButton();
    @FXML
    RadioButton printByDeptRB = new RadioButton();
    @FXML
    RadioButton CSRB = new RadioButton();
    @FXML
    RadioButton ECERB = new RadioButton();
    @FXML
    RadioButton ITRB = new RadioButton();
    @FXML
    RadioButton managerRB = new RadioButton();
    @FXML
    RadioButton DepartmentRB = new RadioButton();
    @FXML
    RadioButton DirectorRB = new RadioButton();

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
    public String formatDate(){
        final int MONTH_INDEX = 1;
        final int DAY_INDEX = 2;
        final int YEAR_INDEX = 0;
        String[] tokens = dateBox.getValue().toString().split("-");
        String tempDate = "";
        tempDate += Integer.parseInt(tokens[MONTH_INDEX]) + "/";
        tempDate += Integer.parseInt(tokens[DAY_INDEX]) +"/";
        tempDate += Integer.parseInt(tokens[YEAR_INDEX]);
        return tempDate;
    }
    public Profile getEnteredProfile() {
        String name = nameBox.getText();
        String department;
        if(CSRB.isSelected()){
            department = "CS";
        }
        else if(ECERB.isSelected()){
            department = "ECE";
        }
        else{
            department = "IT";
        }
        Date hireDate = new Date(formatDate());
        Profile empProfile = new Profile(name, department, hireDate);
        return empProfile;
    }

    public void add(ActionEvent actionEvent) {
        Float rate = null;
        try{
            rate = Float.parseFloat(payBox.getText());
            //payBox.setText("Is Float: " + rate);
        }catch (NumberFormatException e){
            display.setText("Please enter a valid ");
            if(Position.getSelectedToggle() == PTRB)
                display.setText(display.getText() + "Hourly Wage.\n");
            else
                display.setText(display.getText() + "Annual Salary.\n");
        }
        Profile hireProfile = getEnteredProfile();

        if (PTRB.isSelected()) {
            Parttime newHire = new Parttime(hireProfile, rate);
            com.add(newHire);
        } else if (FTRB.isSelected()) {
            Fulltime newHire = new Fulltime(hireProfile, rate);
            com.add(newHire);
        } else if (MGRB.isSelected()){
            Management newHire = new Management(hireProfile, rate, getManagementPosition());
            com.add(newHire);
        }
    }

    public int getManagementPosition(){
        final int manager = 1;
        final int departmentHead = 2;
        final int districtHead = 3;
        if(managerRB.isSelected()){
            return manager;
        }
        else if(DepartmentRB.isSelected()){
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
            //payBox.setText("Is Float: " + rate);
        }catch (NumberFormatException e){
            display.setText("Please enter a valid ");
            if(Position.getSelectedToggle() == PTRB)
                display.setText(display.getText() + "Hourly Wage.\n");
            else
                display.setText(display.getText() + "Annual Salary.\n");
        }
        Profile empProfile = getEnteredProfile();

        if (Position.getSelectedToggle() == PTRB) {
            Parttime oldHire = new Parttime(empProfile, rate);
            com.remove(oldHire);
        } else if (Position.getSelectedToggle() == FTRB) {
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
            //payBox.setText("Is Float: " + hours);
        }catch (NumberFormatException e){
            display.setText("Please enter a valid ");
            if(Position.getSelectedToggle() == PTRB)
                display.setText(display.getText() + "Hourly Wage.\n");
            else
                display.setText(display.getText() + "Annual Salary.\n");
        }
        Profile empProfile = getEnteredProfile();
        Parttime toUpdate = new Parttime(empProfile);
        toUpdate.setHours(hours);
    }

    public void importFile(ActionEvent actionEvent) throws FileNotFoundException {
       // Node node = (Node) actionEvent.getSource();
       // Window window = node.getScene().getWindow();
       // databaseFile.setTitle("Open Database File");
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

    public void printDatabase(ActionEvent actionEvent) {
        if(Print.getSelectedToggle() == printByDeptRB)
            display.setText(com.printByDepartment());
        else if(Print.getSelectedToggle() == printByDateRB)
            display.setText(com.printByDate());
        else
            display.setText(com.print());
    }

    public void disableFields(ActionEvent actionEvent) {
        if(PTRB.isSelected()){
            managerRB.setDisable(true);
            DepartmentRB.setDisable(true);
            DirectorRB.setDisable(true);
            hourBox.setDisable(false);
        }
        else if(FTRB.isSelected()){
            managerRB.setDisable(true);
            DepartmentRB.setDisable(true);
            DirectorRB.setDisable(true);
            hourBox.setDisable(true);
        }
        else if(MGRB.isSelected()){
            managerRB.setDisable(false);
            DepartmentRB.setDisable(false);
            DirectorRB.setDisable(false);
            hourBox.setDisable(true);
        }
    }
}
