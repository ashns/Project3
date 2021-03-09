import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Container class that defines the Company array to hold the employee information.
 * Provides default constructor for creating instances
 * Provides client methods: toString, equals, grow, add, remove, setHours,
 * processPayments, isEmpty, exportDatabase
 * Provides accessor methods: getNumEmployees
 * Provides print methods: genPrint, print, printByDate, printByDepartment
 * @author Ashley Stankovits, Matthew Walker
 *
 */
public class Company {
    private Employee[] emplist;
    private int numEmployee;

    private static final int BASE_SIZE = 4;
    private static final int GROW_INCREMENT = 4;
    private static final int ERRNO = -1;

    /**
     * Helper method to find an employee in the company.
     * @param employee a pointer to an employee who needs to be located in the company employee array
     * @return The index of the employee in the array if found, -1 otherwise
     */
    private int find(Employee employee) {
        for (int i = 0; i < this.numEmployee; i++) {
            if (employee.equals(this.emplist[i])) {
                return i;
            }
        }
        return ERRNO;
    }

    /**
     * This method returns the number of employees in the employee array
     * @return the value of numEmployee, the number of employees in the employee array
     */
    public int getNumEmployees() {
        return numEmployee;
    }

    /**
     * Helper method to grow the capacity by four.
     */
    private void grow() {
        Employee[] newEmplist = new Employee[this.emplist.length + GROW_INCREMENT];

        for (int i = 0; i < emplist.length; i++) {
            newEmplist[i] = emplist[i];
        }
        this.emplist = newEmplist;
    }

    /**
     * This method adds an employee to the employee array and increased the number of employees counter by 1
     * @param employee which is the employee object being added to the array
     * @return boolean true if the employee is successfully added, false otherwise
     */
    public boolean add(Employee employee) throws IllegalStateException {
        if (this.numEmployee == 0) {
            this.emplist = new Employee[BASE_SIZE];
            this.emplist[numEmployee++] = employee;
            return true;
        }
        if (!(this.find(employee) == ERRNO)) {
            return false;
        }
        if (numEmployee == this.emplist.length) {
            this.grow();
        }
        this.emplist[numEmployee++] = employee;
        return true;
    }

    /**
     * Removes an employee from the employee array, shifts array elements to maintain 
     * efficient storage.
     * @param employee employee to be removed
     * @return true if operation is successful, false if employee is not in the employee array
     */
    public boolean remove(Employee employee) {
        int employeeIndex = find(employee);
        if (employeeIndex == ERRNO) {
            return false;
        }
        int i = 0;
        for (i = employeeIndex; i < numEmployee - 1; i++) {
            emplist[i] = emplist[i + 1];
        }
        emplist[i] = null;
        numEmployee = numEmployee - 1;
        return true;
    }

    /**
     * This method sets the hours of a parttime employee
     * @param employee that the hours are being set for
     * @return boolean true if the hours are successfully set, false if otherwise
     */
    public boolean setHours(Employee employee) {
        if (!(employee instanceof Parttime)) {
            return false;
        }
        int i = this.find(employee);
        if (i != ERRNO){
            if (this.emplist[i] instanceof Parttime) {
                ((Parttime)emplist[i]).setHours(((Parttime)employee).getHours());
                return true;
            }
        }
        return false;
    }

    /**
     * This method calls to the corresponding calculatePayment methods for each object in the employee array
     */
    public void processPayments() {
        for (int i = 0; i < this.numEmployee; i++) {
            this.emplist[i].calculatePayment();
        }
    }

    /**
     * Prints all the employee objects in the employee array.
     */
    private String genPrint() {
        String printout = "";
        for (int i = 0; i < this.numEmployee; i++) {
            printout += emplist[i].toString() + "\n";
        }
        return printout;
    }

    /**
     * Adds formatting text lines to the printing of the employee array
     */
    public String print() {
        String output = isEmpty();
        if(output.equals("")) {
            output = "--Printing earning statements for all employees--\n";

            output += this.genPrint();
        }
        return output;

    }

    /**
     * Prints all the objects in the employee array in order of department
     */
    public String printByDepartment() {
        String output = isEmpty();
        if(output.equals("")) {
            output = "--Printing earning statements by department--\n";
            Employee temp;
            for (int i = 0; i < numEmployee; i++) {
                for (int j = 0; j < numEmployee-1; j++) {
                    if (emplist[j].getDepartment().compareTo(emplist[j+1].getDepartment()) > 0) {
                        temp = emplist[j];
                        emplist[j] = emplist[j+1];
                        emplist[j+1] = temp;
                    }

                }
            }
            output += this.genPrint();
        }
        return output;

    }

    /**
     * Prints all the objects in the employee array in order of hiring date
     * @return A string with all employees as they would be displayed
     */
    public String printByDate() {
        String output = isEmpty();
        if(output.equals("")) {
            output = "--Printing earning statements by date hired--\n";
            Employee temp;
            for (int i = 0; i < numEmployee; i++) {
                for (int j = 0; j < numEmployee-1; j++) {
                    if (emplist[j].getDateHired().compareTo(emplist[j+1].getDateHired()) > 0) {
                        temp = emplist[j];
                        emplist[j] = emplist[j+1];
                        emplist[j+1] = temp;
                    }
                }
            }
            output += this.genPrint();
        }
        return output;

    }

    /**
     * Prints all the employee objects in the employee array.
     */
    public String toString() {
        String contents = "";
        for (int i = 0; i < this.numEmployee; i++) {
            if (this.emplist[i] instanceof Parttime) {
                Parttime temp = (Parttime) this.emplist[i];
                contents+=temp.toString() + "\n";
            } else if (this.emplist[i] instanceof Fulltime) {
                if (this.emplist[i] instanceof Management) {
                    Management temp = (Management) this.emplist[i];
                    contents+=(temp.toString() + "\n");
                    continue;
                }
                Fulltime temp = (Fulltime) this.emplist[i];
                contents+=(temp.toString() + "\n");
            }
        }
        return contents;
    }

    /**
     * This method checks if the employee database is empty.
     * @return String which contains a specified message if the employee database is empty.
     */
    public String isEmpty(){
        String output = "";
        if(numEmployee == 0)
            output = "Employee database is empty.\n";
        return output;
    }

    /**
     * This method takes a file and writes the employee database
     * to it, which is then saved on the client computer.
     * @param file which is the file the database is being written to
     * @throws IOException if there is error in the PrintWriter
     */
    public void exportDatabase(File file) throws IOException {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(this.toString());
            writer.close();
        } catch (IOException ex) {

        }
    }
}