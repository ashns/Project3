/**
 * Container class that defines the data type Management to hold the managing employee information.
 * Provides default constructor for creating instances
 * Provides client methods: toString, equals, calculateBonus, calculatePayment
 * Provides accessor methods: getRole
 * @author Ashley Stankovits, Philip Severinov
 *
 */
public class Management extends Fulltime {
    private int role;
    private float bonus;
    private static final float MANAGER_BONUS = 5000;
    private static final float DEPT_HEAD_BONUS = 9500;
    private static final float DIRECTOR_BONUS = 12000;
    public static final int MANAGER = 1;
    public static final int DEPT_HEAD = 2;
    public static final int DIRECTOR = 3;
    private static final int PAY_PERIODS = 26;


    /**
     * This constructor takes a profile, salary, and role to create a mananging employee object with the corresponding information. 
     * @param profile a reference to a profile instance
     * @param salary the designated salary of the referenced employee
     * @param role an integer referencing one of the 3 management roles
     */
    public Management(Profile profile, float salary, int role) {
        super(profile, salary);
        this.role = role;
        calculateBonus();
    }
    /**
     * This method uses the role number to calculate the additional bonus money given to the employee each payment period
     */
    public void calculateBonus() {
        switch (role) {
            case MANAGER:
                bonus = MANAGER_BONUS / PAY_PERIODS;
                break;
            case DEPT_HEAD:
                bonus = DEPT_HEAD_BONUS / PAY_PERIODS;
                break;
            case DIRECTOR:
                bonus = DIRECTOR_BONUS / PAY_PERIODS;
                break;
        }
    }

    /**
     * This method retrieves the integer role of the employee
     * @return the number corresponding to the management role of the employee
     */
    public int getRole() {
        return this.role;
    }

    /** 
     * This method converts all the information 
     * associated with a management object into one string.
     * @return string containing management fields
     */
    @Override
    public String toString() {
        String message = null;
        //= super.toString()+ "::";
        switch (role) {
            case MANAGER:
                message = String.format("%s::Manager Compensation $%,.2f",
                    super.toString(), MANAGER_BONUS / PAY_PERIODS);
                break;
            case DEPT_HEAD:
                message = String.format("%s::Department Head Compensation $%,.2f",
                    super.toString(), DEPT_HEAD_BONUS / PAY_PERIODS);
                break;
            case DIRECTOR:

                message = String.format("%s::Director Compensation $%,.2f",
                    super.toString(), DIRECTOR_BONUS / PAY_PERIODS);
                break;
        }
        return message;
    }
    /**
     * Checks if two instances of an object in Management are equivalent
     * @param obj any java object
     * @return boolean true if the objects are equal false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Management) {
            return (super.equals(obj));
            /*
        	Management employee = (Management) obj;
            return (this.role==employee.getRole());
            */
        }
        return false;
    }

    /**
     * Calculates the payment for a parttime employee during one period
     */
    @Override
    public void calculatePayment() {
        super.calculatePayment();
        super.addBonus(bonus);
    }
}