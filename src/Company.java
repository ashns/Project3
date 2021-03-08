/**
 * Container class that defines the Company array to hold the employee information.
 * Provides default constructor for creating instances
 * Provides client methods: toString, equals, grow, add, remove, setHours, processPayments
 * Provides accessor methods: getNumEmployees
 * Provides print methods: genPrint, print, printByDate, printByDepartment
 * @author Ashley Stankovits, Philip Severinov
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
        int i;
        if ((i = this.find(employee)) != ERRNO) {
            if (this.emplist[i] instanceof Parttime) {
                Parttime partTimeEmployee = (Parttime) this.emplist[i];
                partTimeEmployee.setHours(((Parttime) employee).getHours());
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
    private void genPrint() {
        for (int i = 0; i < this.numEmployee; i++) {
            if (this.emplist[i] instanceof Parttime) {
                Parttime temp = (Parttime) this.emplist[i];
                System.out.println(temp.toString());
            } else if (this.emplist[i] instanceof Fulltime) {
                if (this.emplist[i] instanceof Management) {
                    Management temp = (Management) this.emplist[i];
                    System.out.println(temp.toString());
                    continue;
                }
                Fulltime temp = (Fulltime) this.emplist[i];
                System.out.println(temp.toString());
            }
        }
    }

    /**
     * Adds formatting text lines to the printing of the employee array
     */
    public void print() {
        System.out.println("--Printing earning statements for all employees--");
        this.toString();
    }

    /**
     * Prints all the objects in the employee array in order of department
     */
    public void printByDepartment() {
        System.out.println("--Printing earning statements by department--");
        for (int i = 0; i < numEmployee; i++) {
            Employee localMin = emplist[i];
            int iMin = i;
            for (int j = i + 1; j < numEmployee; j++) {
                if (emplist[j].getDepartment().compareTo(localMin.getDepartment()) < 0) {
                    localMin = emplist[j];
                    iMin = j;
                }
            }
            Employee temp = emplist[i];
            emplist[i] = localMin;
            emplist[iMin] = temp;
        }
        this.toString();
    }

    /**
     * Prints all the objects in the employee array in order of hiring date
     */
    public void printByDate() {
        System.out.println("--Printing earning statements by date hired--");
        for (int i = 0; i < numEmployee; i++) {
            Employee localMin = emplist[i];
            int iMin = i;
            for (int j = i + 1; j < numEmployee; j++) {
                if (emplist[j].getDateHired().compareTo(localMin.getDateHired()) < 0) {
                    localMin = emplist[j];
                    iMin = j;
                }
            }
            Employee temp = emplist[i];
            emplist[i] = localMin;
            emplist[iMin] = temp;
        }
        this.toString();
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
}