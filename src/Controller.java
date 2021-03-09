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
    @FXML
    Label hourLabel = new Label();
    @FXML
    Label positionLabel = new Label();

    StringTokenizer parse;
    final int NUM_TOKENS_PROFILE = 3;
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

    public String formatDate() {
        final int MONTH_INDEX = 1;
        final int DAY_INDEX = 2;
        final int YEAR_INDEX = 0;
        String[] tokens = dateBox.getValue().toString().split("-");
        String tempDate = "";
        tempDate += Integer.parseInt(tokens[MONTH_INDEX]) + "/";
        tempDate += Integer.parseInt(tokens[DAY_INDEX]) + "/";
        tempDate += Integer.parseInt(tokens[YEAR_INDEX]);
        return tempDate;
    }

    public Profile getEnteredProfile() {
        String name = nameBox.getText();
        String department;
        if (CSRB.isSelected()) {
            department = "CS";
        } else if (ECERB.isSelected()) {
            department = "ECE";
        } else {
            department = "IT";
        }
        Date hireDate = new Date(formatDate());
        Profile empProfile = new Profile(name, department, hireDate);
        return empProfile;
    }

    public void add(ActionEvent actionEvent) {
        Float rate = null;
        try {
            rate = Float.parseFloat(payBox.getText());
            //payBox.setText("Is Float: " + rate);
        } catch (NumberFormatException e) {
            display.setText("Please enter a valid ");
            if (Position.getSelectedToggle() == PTRB)
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
        } else if (MGRB.isSelected()) {
            Management newHire = new Management(hireProfile, rate, getManagementPosition());
            com.add(newHire);
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
            //payBox.setText("Is Float: " + rate);
        } catch (NumberFormatException e) {
            display.setText("Please enter a valid ");
            if (Position.getSelectedToggle() == PTRB)
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
        try {
            hours = Float.parseFloat(payBox.getText());
            //payBox.setText("Is Float: " + hours);
        } catch (NumberFormatException e) {
            display.setText("Please enter a valid ");
            if (Position.getSelectedToggle() == PTRB)
                display.setText(display.getText() + "Hourly Wage.\n");
            else
                display.setText(display.getText() + "Annual Salary.\n");
        }
        Profile empProfile = getEnteredProfile();
        Parttime toUpdate = new Parttime(empProfile);
        toUpdate.setHours(hours);
    }

    public void importFile(ActionEvent actionEvent) throws FileNotFoundException {
        File file = databaseFile.showOpenDialog(null);
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
            Scanner scLine = new Scanner(myReader.nextLine());
            if (!scLine.hasNext()) {
                continue;
            }
            String firstToken = scLine.next();
            if (firstToken.equals("PA")) {
                //print in sequence
                if (scLine.hasNext()) {
                    System.out.println("Invalid command!");
                    scLine.close();
                    continue;
                }
                scLine.close();
                if (com.getNumEmployees() == 0) {
                    System.out.println("Employee database is empty.");
                } else {
                    com.print();
                }
                continue;
            }
            if (firstToken.equals("PH")) {
                //print by date
                if (scLine.hasNext()) {
                    System.out.println("Invalid command!");
                    scLine.close();
                    continue;
                }
                scLine.close();
                if (com.getNumEmployees() == 0) {
                    System.out.println("Employee database is empty.");
                } else {
                    com.printByDate();
                }
                continue;
            }
            if (firstToken.equals("PD")) {
                //print by department
                if (scLine.hasNext()) {
                    System.out.println("Invalid command!");
                    scLine.close();
                    continue;
                }
                scLine.close();
                if (com.getNumEmployees() == 0) {
                    System.out.println("Employee database is empty.");
                } else {
                    com.printByDepartment();
                }
                continue;
            }
            if (firstToken.equals("AP")) {
                Profile hireProfile = readProfile(scLine);
                if (hireProfile == null) {
                    scLine.close();
                    continue;
                }
                //read rate
                float rate;
                if (scLine.hasNextFloat()) {
                    rate = scLine.nextFloat();
                } else {
                    System.out.println("Invalid command!");
                    scLine.close();
                    continue;
                }

                if (rate < 0) {
                    System.out.println("Pay rate cannot be negative.");
                    scLine.close();
                    continue;
                }

                //check for trailing tokens
                if (scLine.hasNext()) {
                    System.out.println("Invalid command!");
                    scLine.close();
                    continue;
                }
                scLine.close();

                Parttime newHire = new Parttime(hireProfile, rate);

                if (com.add(newHire)) {
                    System.out.println("Employee added.");
                } else {
                    System.out.println("Employee is already in the list.");
                }
                continue;
            }
            if (firstToken.equals("AF")) {
                Profile hireProfile = readProfile(scLine);
                if (hireProfile == null) {
                    scLine.close();
                    continue;
                }
                //read salary
                float salary;
                if (scLine.hasNextFloat()) {
                    salary = scLine.nextFloat();
                } else {
                    System.out.println("Invalid command!");
                    scLine.close();
                    continue;
                }
                if (salary < 0) {
                    System.out.println("Salary cannot be negative.");
                    scLine.close();
                    continue;
                }

                //check for trailing tokens
                if (scLine.hasNext()) {
                    System.out.println("Invalid command!");
                    scLine.close();
                    continue;
                }
                scLine.close();

                Fulltime newHire = new Fulltime(hireProfile, salary);

                if (com.add(newHire)) {
                    System.out.println("Employee added.");
                } else {
                    System.out.println("Employee is already in the list.");
                }
                continue;
            }
            if (firstToken.equals("AM")) {
                Profile hireProfile = readProfile(scLine);
                if (hireProfile == null) {
                    scLine.close();
                    continue;
                }
                //read salary
                float salary;
                if (scLine.hasNextFloat()) {
                    salary = scLine.nextFloat();
                } else {
                    System.out.println("Invalid command!");
                    scLine.close();
                    continue;
                }
                if (salary < 0) {
                    System.out.println("Salary cannot be negative.");
                    scLine.close();
                    continue;
                }
                //read management code
                int code;
                if (scLine.hasNextInt()) {
                    code = scLine.nextInt();
                } else {
                    System.out.println("Invalid command!");
                    scLine.close();
                    continue;
                }
                if (code < 1 || code > 3) {
                    System.out.println("Invalid management code.");
                    scLine.close();
                    continue;
                }

                //check for trailing tokens
                if (scLine.hasNext()) {
                    System.out.println("Invalid command!");
                    scLine.close();
                    continue;
                }
                scLine.close();

                Management newHire = new Management(hireProfile, salary, code);

                if (com.add(newHire)) {
                    System.out.println("Employee added.");
                } else {
                    System.out.println("Employee is already in the list.");
                }
                continue;
            }
            if (firstToken.equals("R")) {
                //remove an employee
                Profile rProfile = readProfile(scLine);
                if (rProfile == null) {
                    scLine.close();
                    continue;
                }

                //check for trailing tokens
                if (scLine.hasNext()) {
                    System.out.println("Invalid command!");
                    scLine.close();
                    continue;
                }
                scLine.close();
                if (com.getNumEmployees() == 0) {
                    System.out.println("Employee database is empty.");
                    continue;
                }
                Employee toRemove = new Employee(rProfile);
                if (com.remove(toRemove)) {
                    System.out.println("Employee removed.");
                } else {
                    System.out.println("Employee does not exist.");
                }

                continue;

            }
            if (firstToken.equals("S")) {
                //set working hours
                if (com.getNumEmployees() == 0) {
                    System.out.println("Employee database is empty.");
                    scLine.close();
                    continue;
                }

                Profile sProfile = readProfile(scLine);
                if (sProfile == null) {
                    scLine.close();
                    continue;
                }
                //read hours
                float hours;
                if (scLine.hasNextFloat()) {
                    hours = scLine.nextFloat();
                } else {
                    System.out.println("Invalid command!");
                    scLine.close();
                    continue;
                }
                if (hours < 0) {
                    System.out.println("Working hours cannot be negative.");
                    continue;
                }
                if (hours > 100) {
                    System.out.println("Invalid Hours: over 100.");
                    continue;
                }

                //check for trailing tokens
                if (scLine.hasNext()) {
                    System.out.println("Invalid command!");
                    scLine.close();
                    continue;
                }
                scLine.close();
                if (com.getNumEmployees() == 0) {
                    System.out.println("Employee database is empty.");
                    continue;
                }
                Parttime toUpdate = new Parttime(sProfile);
                toUpdate.setHours(hours);
                if (com.setHours(toUpdate)) {
                    System.out.println("Working hours set.");
                } else {
                    System.out.println("Employee does not exist.");
                }
                continue;
            }
            if (firstToken.equals("C")) {
                //calculate payments

                //check for trailing tokens
                if (scLine.hasNext()) {
                    System.out.println("Invalid command!");
                    scLine.close();
                    continue;
                }
                scLine.close();
                if (com.getNumEmployees() == 0) {
                    System.out.println("Employee database is empty.");
                    continue;
                } else {
                    com.processPayments();
                    System.out.println("Calculation of employee payments is done.");
                }
                continue;
            }
            if (firstToken.equals("Q")) {
                //check for trailing tokens
                if (scLine.hasNext()) {
                    System.out.println("Invalid command!");
                    scLine.close();
                    continue;
                }
                scLine.close();
                System.out.println("Payroll Processing completed.");
                break;
            }
            //invalid input
            System.out.println("Command \'" + firstToken + "\' not supported!");
            scLine.close();
            continue;
        }
        myReader.close();
    }


    public void exportDatabase(ActionEvent actionEvent) {
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
            payLabel.setText("Hourly Rate: $");
            rateLabel.setText("/ hour");
        } else if (FTRB.isSelected()) {
            managerRB.setDisable(true);
            DepartmentRB.setDisable(true);
            DirectorRB.setDisable(true);
            hourBox.setDisable(true);
            hourLabel.setDisable(true);
            positionLabel.setDisable(true);
            payLabel.setText("Annual Salary: $");
            rateLabel.setText("/ year");
        } else if (MGRB.isSelected()) {
            managerRB.setDisable(false);
            DepartmentRB.setDisable(false);
            DirectorRB.setDisable(false);
            hourBox.setDisable(true);
            hourLabel.setDisable(true);
            positionLabel.setDisable(false);
            payLabel.setText("Annual Salary: $");
            rateLabel.setText("/ year");
        }
    }

    /**
     * Helper method to clean up code. Checks for trailing tokens while parsing
     * input and looks for invalid input.
     *
     * @param sc initialized stringTokenizer object parsing a string
     * @return profile object with input information
     */
    private Profile readProfile(Scanner sc) {
        int iName = 0;
        int iDept = 1;
        int iDate = 2;
        String[] tokens = new String[NUM_TOKENS_PROFILE];
        for (int i = 0; i < NUM_TOKENS_PROFILE; i++) {
            if (sc.hasNext()) {
                tokens[i] = sc.next();
            } else {
                System.out.println("Invalid command!");
                return null;
            }
        }
        if (!(tokens[iDept].equals("CS") || tokens[iDept].equals("ECE") ||
                tokens[iDept].equals("IT"))) {
            System.out.println("\'" + tokens[iDept] +
                    "\' is not a valid department code.");
            return null;
        }
        Date hireDate = new Date(tokens[iDate]);
        if (!hireDate.isValid()) {
            System.out.println(tokens[iDate] + " is not a valid date!");
            return null;
        }

        return new Profile(tokens[iName], tokens[iDept], hireDate);
    }

}