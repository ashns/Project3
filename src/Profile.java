/**
 * Container class that defines the abstract data type Profile to hold
 * the employee profiles and their various operations.
 * Provides default constructor for creating instances
 * Provides client methods: toString, equals
 * Provides accessor methods: getName, getDepartment, getDateHired
 * @author Ashley Stankovits, Matthew Walker
 *
 */
public class Profile {
    private String name;
    private String department;
    private Date dateHired;

    /**
     * This constructor takes a name, a department title, and hiring date and creates a
     *  Profile object corresponding with this information. 
     * @param name a string representing the employee's name for the profile
     * @param department a string representing the employee's department name for the profile
     * @param dateHired string in format mm/dd/yyyy representing the date of hiring 
     */
    public Profile(String name, String department, Date dateHired) {
        this.name = name;
        this.department = department;
        this.dateHired = dateHired;
    }

    /** 
     * This method converts all the information 
     * associated with a profile into one string.
     * @return string containing profile fields
     */
    @Override
    public String toString() {
        String message = String.format("%s::%s::%s", name, department, dateHired.toString());
        return message;
    }

    /** 
     * This method checks if the name department and date hired of two employee profiles
     * are the same.
     * @param obj which is the profile object being referenced
     * @return true if the information matches, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Profile) {
            Profile profile = (Profile) obj;
            if ((this.name.equals(profile.getName())) &&
                (this.department.equals(profile.getDepartment())) &&
                (this.dateHired.compareTo(profile.getDateHired()) == 0)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves name of employee.
     * @return The calling profile's name of employee
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves department of employee.
     * @return The calling profile's department of employee
     */
    public String getDepartment() {
        return this.department;
    }

    /**
     * Retrieves hiring date of employee.
     * @return The calling profile's hiring date of employee
     */
    public Date getDateHired() {
        return this.dateHired;
    }
}