import java.util.Scanner;

/**
 * User interface class that handles input and output.
 * Creates an instance of Company, takes input from user and calls appropriate 
 * commands.
 * @author Ashley Stankovits, Philip Severinov
 */
public class PayrollProcessing {
    final int NUM_TOKENS_PROFILE = 3;

    /**
     * Helper method to clean up code. Checks for trailing tokens while parsing 
     * input and looks for invalid input.
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

    /**
     * Creates a Company instance, takes input from the user, and calls Company functions.
     * Continues executing until terminating command "Q" is entered.
     */
    public void run() {
        Company com = new Company();

        Scanner sc = new Scanner(System.in);

        System.out.println("Payroll Processing starts.");

        while (sc.hasNext()) {
            Scanner scLine = new Scanner(sc.nextLine());

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
                if (code < Management.MANAGER || code > Management.DIRECTOR) {
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
        sc.close();
    }

}