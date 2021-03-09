import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * The controller class responds to all input from UI with appropriate
 * calls to methods in other classes.
 * Provides client methods: formatDate, add, remove, clear, setHours, importFile,
 * exportFile, disableFields, readProfile, process
 * Provides accessor methods: getEnteredProfile, getManagementPosition
 * Provides print methods: printDatabase
 * @author Ashley Stankovits, Matthew Walker
 *
 */
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

    /**
     * This is a helper method to fix a date formatting issue with the UI.
     * The date selector returns the date in localdate format which includes
     * "-"s rather than "/"s. This method fixes the string to return in the
     * format which includes "/"s.
     *
     * @return a String reformatting the inputted date into the date
     * formatted to create a date object from date.class
     */
    public String formatDate() {
        final int MONTH_INDEX = 1;
        final int DAY_INDEX = 2;
        final int YEAR_INDEX = 0;

        String tempDate = "";
        String[] tokens;

            tokens = dateBox.getValue().toString().split("-");
            tempDate += Integer.parseInt(tokens[MONTH_INDEX]) + "/";
            tempDate += Integer.parseInt(tokens[DAY_INDEX]) + "/";
            tempDate += Integer.parseInt(tokens[YEAR_INDEX]);

        return tempDate;
    }

    /**
     * This is a helper method to create a profile corresponding to the
     * information inputted into the UI.
     *
     * @return a Profile object with the specified information
     */
    public Profile getEnteredProfile() {
        String name = nameBox.getText();
        if(nameBox.getText().equals("")) {
            display.setText(display.getText() + "Please enter a name.\n");
            if(dateBox.getValue() == null)
                display.setText(display.getText() + "Please enter a date.\n");
            return null;
        }
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
        return empProfile;
    }

    /**
     * This method takes the input from the UI text fields and adds the
     * specified employee to the company array.
     *
     * @param actionEvent which is the export button being triggered
     */
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

    /**
     * This is a helper method to return the number corresponding with the manager
     * position of the employee based on which radiobutton is clicked.
     *
     * @return an integer specifying the employees management position
     */
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

    /**
     * This method takes input from the UI to then remove the
     * specified employee from the database.
     *
     * @param actionEvent which is the export button being triggered
     */
    public void remove(ActionEvent actionEvent) {
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

    /**
     * This method clears all the text fields in the UI when the clear
     * button is clicked.
     *
     * @param actionEvent which is the export button being triggered
     */
    public void clear(ActionEvent actionEvent) {
        nameBox.clear();
        payBox.clear();
        hourBox.clear();
        dateBox.getEditor().clear();
        display.setText("Prompts cleared.");
    }

    /**
     * This method responds to the Set Hours button being pressed by
     * setting the hours of a specific Parttime employee.
     *
     * @param actionEvent which is the Set Hours button being triggered
     */
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

    /**
     * This method opens the open file dialogue on your computer
     * and allows you to import a file which will then add any
     * employees from the file to the current company array.
     *
     * @param actionEvent which is the export button being triggered
     */
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

    /**
     * This method opens the save dialogue on your computer
     * and allows you to either create or override a file with
     * the new database. It calls to exportDatabase in the company
     * class.
     *
     * @param actionEvent which is the export button being triggered
     */
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

    /**
     * This method responds to the print buttons in the second tab
     * being pressed by calling the appropriate print method from
     * company class.
     *
     * @param actionEvent which is the print buttons being triggered
     */
    public void printDatabase(ActionEvent actionEvent) {
        if (Print.getSelectedToggle() == printByDeptRB)
            display.setText(com.printByDepartment());
        else if (Print.getSelectedToggle() == printByDateRB)
            display.setText(com.printByDate());
        else
            display.setText(com.print());
    }

    /**
     * This method disabled appropriate fields with the position radiobutton is selected.
     * For example, with Fulltime selected, the hours text box, set hours button,
     * and manager radiobuttons are disabled.
     *
     * @param actionEvent which is the radio buttons being triggered
     */
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
     * Helper method to clean up code. Created profile from file input for employees.
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

    /**
     * This method processes payments from company when the appropriate button is pressed.
     *
     * @param e which is the button being pressed.
     */
    public void process(ActionEvent e){
        com.processPayments();
        display.setText("Calculation of employee payments are done.");
    }

}