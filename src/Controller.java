import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


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
    @FXML
    Label hourLabel = new Label();
    @FXML
    Label positionLabel = new Label();
    @FXML
    Button hourBtn = new Button();

    final int MAX_HOURS = 100;

    Company com = new Company();

    public String formatDate() {
        final int MONTH_INDEX = 0;
        final int DAY_INDEX = 1;
        final int YEAR_INDEX = 2;

        String tempDate = "";
        String[] tokens;


            tokens = dateBox.getValue().toString().split("-");
            tempDate += Integer.parseInt(tokens[MONTH_INDEX]) + "/";
            tempDate += Integer.parseInt(tokens[DAY_INDEX]) + "/";
            tempDate += Integer.parseInt(tokens[YEAR_INDEX]);

        return tempDate;
    }

    public Profile getEnteredProfile() {
        String name = nameBox.getText();
        if(name.equals(""))
            display.setText(display.getText() + "Please enter a name.\n");
        String department;
        if (CSRB.isSelected()) {
            department = "CS";
        } else if (ECERB.isSelected()) {
            department = "ECE";
        } else {
            department = "IT";
        }
        Date hireDate = null;

        if(dateBox.getValue() != null)
            hireDate = new Date(formatDate());
        else
            display.setText(display.getText() + "Please enter a date.\n");

        Profile empProfile = null;
        if(hireDate != null)
            empProfile = new Profile(name, department, hireDate);

            //display.setText("Please select a valid date.");

        return empProfile;
    }

    public void add(ActionEvent actionEvent) {
        Float rate = null;
        try {
            rate = Float.parseFloat(payBox.getText());
        } catch (NumberFormatException e) {
            display.setText("Please enter a valid ");
            if (Position.getSelectedToggle() == PTRB)
                display.setText(display.getText() + "hourly wage.\n");
            else
                display.setText(display.getText() + "annual salary.\n");
        }

        Profile hireProfile = getEnteredProfile();
        if (hireProfile != null && rate != null) {
            if (PTRB.isSelected()) {
                Parttime newHire = new Parttime(hireProfile, rate);
                if (com.add(newHire))
                    display.setText("Employee added.");
                else
                    display.setText("Employee is already in the list.");
            } else if (FTRB.isSelected()) {
                Fulltime newHire = new Fulltime(hireProfile, rate);
                if (com.add(newHire))
                    display.setText("Employee added.");
                else
                    display.setText("Employee is already in the list.");
            } else if (MGRB.isSelected()) {
                Management newHire = new Management(hireProfile, rate, getManagementPosition());
                if (com.add(newHire))
                    display.setText("Employee added.");
                else
                    display.setText("Employee is already in the list.");
            }
        }
    }

    public int getManagementPosition() {
        final int manager = 1;
        final int departmentHead = 2;
        final int districtHead = 3;
        if (managerRB.isSelected()) {
            return manager;
        } else if (DepartmentRB.isSelected()) {
            return departmentHead;
        } else {
            return districtHead;
        }
    }

    public void remove(ActionEvent actionEvent) {
        Float rate = null;
        try {
            rate = Float.parseFloat(payBox.getText());
        } catch (NumberFormatException e) {
            display.setText("Please enter a valid ");
            if (Position.getSelectedToggle() == PTRB)
                display.setText(display.getText() + "Hourly Wage.\n");
            else
                display.setText(display.getText() + "Annual Salary.\n");
        }
        Profile empProfile = getEnteredProfile();
        if (empProfile != null && rate != null) {
            if (Position.getSelectedToggle() == PTRB) {
                Parttime oldHire = new Parttime(empProfile, rate);
                if (com.remove(oldHire))
                    display.setText("Employee removed.");
                else
                    display.setText("Employee does not exist.");
            } else if (Position.getSelectedToggle() == FTRB) {
                Fulltime oldHire = new Fulltime(empProfile, rate);
                if (com.remove(oldHire))
                    display.setText("Employee removed.");
                else
                    display.setText("Employee does not exist.");
            } else {
                Management oldHire = new Management(empProfile, rate, getManagementPosition());
                if (com.remove(oldHire))
                    display.setText("Employee removed.");
                else
                    display.setText("Employee does not exist.");
            }
        }
    }

    public void clear(ActionEvent actionEvent) {
        nameBox.clear();
        payBox.clear();
        hourBox.clear();
        dateBox.getEditor().clear();
        display.setText("Prompts cleared.");
    }

    public void setHours(ActionEvent actionEvent) {
        Integer hours = null;

        try {
            hours = Integer.parseInt(hourBox.getText());
        } catch (NumberFormatException e) {
            display.setText("Please enter a valid number of hours.");
        }
        if (hours == null)
            display.setText("Please enter working hours.");
        else if(hours < 0)
            display.setText("Hours cannot be negative.");
        else if(hours > MAX_HOURS)
            display.setText("Invalid hours: over 100");
        else {
            Profile empProfile = getEnteredProfile();
            Parttime toUpdate = new Parttime(empProfile, hours);
            if (com.setHours(toUpdate))
                display.setText("Working hours set.");
            else
                display.setText("Unable to update hours.");
        }
    }

    public void importFile(ActionEvent actionEvent) throws FileNotFoundException {
        File file = databaseFile.showOpenDialog(null);
        if(file != null) {
            Scanner fileReader = new Scanner(file);

            while (fileReader.hasNext()) {
                Scanner scLine = new Scanner(file);
                String currentLine = fileReader.nextLine();
                String[] arrayOfInput = currentLine.split(",", 6);
                String firstToken = arrayOfInput[0];

                if (firstToken.equals("P")) {
                    Profile hireProfile = readProfile(arrayOfInput);
                    float rate = Float.parseFloat(arrayOfInput[4]);
                    scLine.close();
                    Parttime newHire = new Parttime(hireProfile, rate);
                    com.add(newHire);
                    continue;
                }
                if (firstToken.equals("F")) {
                    Profile hireProfile = readProfile(arrayOfInput);
                    float salary = Float.parseFloat(arrayOfInput[4]);
                    scLine.close();
                    Fulltime newHire = new Fulltime(hireProfile, salary);
                    com.add(newHire);
                    continue;
                }
                if (firstToken.equals("M")) {
                    Profile hireProfile = readProfile(arrayOfInput);
                    float salary = Float.parseFloat(arrayOfInput[4]);
                    int code = Integer.parseInt(arrayOfInput[5]);
                    scLine.close();
                    Management newHire = new Management(hireProfile, salary, code);
                    com.add(newHire);
                    continue;
                }
                scLine.close();
            }
            fileReader.close();
            display.setText("Employees from file have been added.");
        }
        else
            display.setText("Import canceled.");
    }


    public void exportFile(ActionEvent actionEvent) throws IOException {
        File file = databaseFile.showSaveDialog(null);
        if(file != null) {
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                com.exportDatabase(file);
                display.setText("Database exported to selected file successfully.");
            } catch (IOException ex) {
                display.setText("Unable to export database.");
            }
        }
        else
            display.setText("Export canceled.");
    }

    public void printDatabase(ActionEvent actionEvent) {
        if (Print.getSelectedToggle() == printByDeptRB)
            display.setText(com.printByDepartment());
        else if (Print.getSelectedToggle() == printByDateRB)
            display.setText(com.printByDate());
        else
            display.setText(com.print());
    }

    public void disableFields(ActionEvent actionEvent) {
        if (PTRB.isSelected()) {
            managerRB.setDisable(true);
            DepartmentRB.setDisable(true);
            DirectorRB.setDisable(true);
            hourBox.setDisable(false);
            hourLabel.setDisable(false);
            positionLabel.setDisable(true);
            hourBtn.setDisable(false);
            payLabel.setText("Hourly Rate: $");
            rateLabel.setText("/ hour");
        } else if (FTRB.isSelected()) {
            managerRB.setDisable(true);
            DepartmentRB.setDisable(true);
            DirectorRB.setDisable(true);
            hourBox.setDisable(true);
            hourLabel.setDisable(true);
            positionLabel.setDisable(true);
            hourBtn.setDisable(true);
            payLabel.setText("Annual Salary: $");
            rateLabel.setText("/ year");
        } else if (MGRB.isSelected()) {
            managerRB.setDisable(false);
            DepartmentRB.setDisable(false);
            DirectorRB.setDisable(false);
            hourBox.setDisable(true);
            hourLabel.setDisable(true);
            positionLabel.setDisable(false);
            hourBtn.setDisable(true);
            payLabel.setText("Annual Salary: $");
            rateLabel.setText("/ year");
        }
    }

    /**
     * Helper method to clean up code. Checks for trailing tokens while parsing
     * input and looks for invalid input.
     *
     * @param array initialized array which contains employee information from file
     * @return profile object with input information
     */
    private Profile readProfile(String[] array) {
        int iName = 1;
        int iDept = 2;
        int iDate = 3;
        String name = array[iName];
        Date hireDate = new Date(array[iDate]);
        return new Profile(name, array[iDept], hireDate);
    }

    public void process(ActionEvent e){
        com.processPayments();
        display.setText("Calculation of employee payments are done.");
    }

}