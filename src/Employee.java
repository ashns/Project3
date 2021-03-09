/**
 * Container class that defines the data type Employee to hold the employee information.
 * Provides default constructor for creating instances
 * Provides client methods: toString, equals, addBonus, setPayment, calculatePayment
 * Provides accessor methods: getDepartment, getDateHired, getPayment
 * @author Ashley Stankovits, Matthew Walker
 *
 */
public class Employee {

    private Profile profile;
    private float payment;

    /**
     * This constructor takes a profile a creates an employee instance using it.
     * @param profile a reference to a profile instance
     */
    public Employee(Profile profile) {
        this.profile = profile;
    }

    /**
     * This is the super method for calculatePayment.
     */
    public void calculatePayment() {}

    /**
     * This method adds the employee bonus onto their base payment if they earn one
     * @param bonus which is the additional money a managing position receives each paycheck
     */
    protected void addBonus(float bonus) {
        this.payment += bonus;
    }

    /**
     * This method retrieves the department code for a specific employee
     * @return either "ITI", "CS", or "ECE" in the form of a string
     */
    public String getDepartment() {
        return this.profile.getDepartment();
    }

    /**
     * This method retrieves the date hired of a specific employee instance
     * @return the date hires in the form MM/DD/YYYY
     */
    public Date getDateHired() {
        return this.profile.getDateHired();
    }

    /**
     * This method retrieves the payment for an employee instance
     * @return the payment value for an employee for 1 pay period
     */
    public float getPayment() {
        return this.payment;
    }

    /**
     * This method sets the payment for a specific employee
     * @param payment which is the payment amount for the employee for one period
     */
    public void setPayment(float payment) {
        this.payment = payment;
    }

    /** 
     * This method converts all the information 
     * associated with an employee into one string.
     * @return string containing profile fields
     */
    @Override
    public String toString() {
        String message = String.format("%s::Payment $%,.2f",
            profile.toString(), payment);
        return message;
    }

    /**
     * Checks if two instances of an object in Employee are equivalent
     * @param obj any java object
     * @return boolean true if the objects are equal false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Employee) {
            Employee employee = (Employee) obj;
            if (this.profile.equals(employee.profile)) {
                return true;
            }
        }
        return false;
    }
}