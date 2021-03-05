/**
 * Container class that defines the data type Fulltime to hold the fulltime employee information.
 * Provides default constructor for creating instances
 * Provides client methods: toString, equals, calculatePayment, addBonus
 * Provides accessor methods: getSalary
 * @author Ashley Stankovits, Philip Severinov
 *
 */
public class Fulltime extends Employee
{
    private float salary;
    private static final float PAY_PERIODS = 26;
    
     /**
	 * This constructor takes a profile and a salary to create a fulltime employee object with the corresponding information. 
	 * @param profile a reference to a profile instance
     * @param salary the designated salary of the referenced employee
	 */
    public Fulltime(Profile profile, float salary) {
    	super(profile);
    	this.salary = salary;
    }
    /**
     * This method retrieves the salary of a fulltime employee.
     * @return the salary corresponding to a specific employee
     */
    public float getSalary(){
        return this.salary;
    }
    /**
     * This method will call to have the bonus of a management employee's salary added onto their payment
     */
    @Override
    protected void addBonus(float bonus) {
    	super.addBonus(bonus);
    }
    
    /** 
	 * This method converts all the information 
	 * associated with a Fulltime employee into one string.
	 * @return string containing fulltime fields
	 */
    @Override
    public String toString(){
        String message = String.format("%s::FULL TIME::Annual Salary $%,.2f", 
        				super.toString(), salary);
        return message; 
    }

    /**
     * Checks if two instances of an object in Fulltime are equivalent
     * @param obj any java object
     * @return boolean true if the objects are equal false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Fulltime){
        	return(super.equals(obj));
        }
        return false;
    }

    /**
     * Calculates the payment for a fulltime employee during one period
     */
    @Override
    public void calculatePayment(){
        setPayment((salary/PAY_PERIODS));
    }
}
