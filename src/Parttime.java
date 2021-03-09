/**
 * Container class that defines the data type Parttime to hold the parttime employee information.
 * Provides default constructor for creating instances
 * Provides client methods: toString, equals, setHours, setRate, calculatePayment
 * Provides accessor methods: getRate, getHours
 * @author Ashley Stankovits, Philip Severinov
 *
 */
public class Parttime extends Employee {

    private float rate;
    private float hours;
    private static final float WORK_WEEK_HOURS = 80;
    private static final float OVERTIME_PAY = (float) 1.5;

    /**
     * This constructor takes a profile and rate and creates a parttime employee object with the corresponding information. 
     * @param profile a reference to a profile instance
     * @param rate which is the hourly rate of the referenced employee
     */
    public Parttime(Profile profile, float rate) {
        super(profile);
        this.rate = rate;
    }

    public Parttime(Profile profile, int hour) {
        super(profile);
        this.hours = hour;
    }
    /**
     * This is a constructor that takes the reference to a profile and attaches it to a parttime employee.
     * @param profile a reference to a profile instance
     */
    public Parttime(Profile profile) {
        super(profile);
    }

    /**
     * Retrieve the rate an employee is earning.
     * @return A float representing the corresponding employees income rate
     */
    public float getRate() {
        return this.rate;
    }

    /**
     * Sets the earning rate for the corresponding parttime employee.
     * @param rate which is the rate being set
     */
    public void setRate(float rate) {
        this.rate = rate;
    }

    /**
     * Retrieve the hours a parttime employee is working.
     * @return A float the number of hours a parttime employee is working per pay period
     */
    public float getHours() {
        return this.hours;
    }

    /**
     * Sets the working hours for the corresponding parttime employee.
     * @param hours which the number of hours per pay period being set
     */
    public void setHours(float hours) {
        this.hours = hours;
    }

    /**
     * Converts the information corresponding to the referenced Parttime instance into a string
     * @return a string with the employees hourly rate and hours worked per pay period
     */
    @Override
    public String toString() {
        String message = String.format("%s::PART TIME::Hourly Rate $%,.2f::Hours worked this period: %d", super.toString(), rate, (int) hours);
        return message;
    }

    /**
     * Checks if two instances of an object in Parttime are equivalent
     * @param obj any java object
     * @return boolean true if the objects are equal false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Parttime) {
            return (super.equals(obj));
            //return (this.rate==employee.getRate()&&this.hours==employee.getHours());
        }
        return false;
    }

    /**
     * Calculates the payment for a parttime employee during one period
     */
    @Override
    public void calculatePayment() {
        float tempPay;
        if (hours > WORK_WEEK_HOURS) {
            tempPay = (float)(((hours - WORK_WEEK_HOURS) * rate * OVERTIME_PAY) + (WORK_WEEK_HOURS * rate));
        } else {
            tempPay = (float)(hours * rate);
        }
        setPayment(tempPay);
    }
}