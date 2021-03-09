import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * Represents a calendar date and its properties.
 * Provides parameterized constructor to create specific dates, and a default 
 * constructor creating a Date object with representing today's date.
 * Provides accessor methods: getDay, getMonth, getYear
 * Provides client methods: isValid, isLeapYear, toString
 * @author Ashley Stankovits, Matthew Walker
 */
public class Date implements Comparable<Date>
{ 
	private int year; 
	private int month; 
	private int day; 
	
	//constants
	private static final int QUADRENNIAL = 4;
	private static final int CENTENNIAL = 100;
	private static final int QUARTER_CENTENNIAL = 400;
	private static final int JAN = 1;
	private static final int FEB = 2;
	private static final int MAR = 3;
	private static final int APR = 4;
	private static final int MAY = 5;
	private static final int JUN = 6;
	private static final int JUL = 7;
	private static final int AUG = 8;
	private static final int SEP = 9;
	private static final int OCT = 10;
	private static final int NOV = 11;
	private static final int DEC = 12;
	private static final int THIRTY_ONE_DAYS = 31;
	private static final int THIRTY_DAYS = 30;
	private static final int DAYS_IN_FEB_LEAP_YEAR = 29;
	private static final int DAYS_IN_FEB = 28; 
	private static final int EARLIEST_YEAR = 1900;
	private static final int LESS_THAN = -1;
	private static final int GREATER_THAN = 1;
	private static final int EQUAL_TO = 0;
	
	/**
	 * This method compares two dates.
	 * @param date which is the date being compared to
	 * @return int -1 if the date is less than the given date, 0 if the dates are equal, and 1 if the date is greater than the given date
	 */
    @Override
    public int compareTo(Date date){
    	if(this.year < date.year) {
    		return LESS_THAN;
    	}
    	else if(this.year > date.year) {
    		return GREATER_THAN;
    	}
    	else {
    		if(this.month < date.month) {
    			return LESS_THAN;
    		}
    		else if(this.month > date.month) {
    			return GREATER_THAN;
    		}
    		else {
    			if(this.day < date.day) {
        			return LESS_THAN;
        		}
        		else if(this.day > date.day) {
        			return GREATER_THAN;
        		}
        		else {
        			return EQUAL_TO;
        		}
    		}
    	}
    }
	
    /** 
	 * This constructor takes mm/dd/yyyy formatted date string and creates a 
	 * Date object corresponding this this information
	 * @param date The user inputted string date
	 */
	public Date(String date)
	{

		StringTokenizer parse = new StringTokenizer(date, "/");

		this.month = Integer.parseInt(parse.nextToken());
		this.day = Integer.parseInt(parse.nextToken());
		this.year = Integer.parseInt(parse.nextToken());

	}

	/** 
	 * This is the default constructor which uses the calendar class to format 
	 * the date objects
	 */
	public Date()
	{
		Calendar calendar = Calendar.getInstance();
		this.month = calendar.get(Calendar.MONTH)+1;		//offset zero indexed months
		this.day = calendar.get(Calendar.DAY_OF_MONTH);
		this.year = calendar.get(Calendar.YEAR);
	}
	
	/**
	 * Retrieve the value of month.
	 * @return An integer representing this date's month
	 */
	public int getMonth()
	{
		return this.month;
	}

	/**
	 * Retrieve the value of day.
	 * @return An integer representing this date's day
	 */
	public int getDay()
	{
		return this.day;
	}

	/**
	 * Retrieve the value of year.
	 * @return An integer representing this date's year
	 */
	public int getYear()
	{
		return this.year;
	}

	/** 
	 * Helper for isValid, checks if the year given is a leap year.
	 * @return true if the year is a leap year, false if not
	 */
	private boolean isLeapYear()
	{
		int newBookYear = this.year;
		
		if (newBookYear % QUADRENNIAL == 0)
		{
			if (newBookYear % CENTENNIAL == 0)
			{
				if (newBookYear % QUARTER_CENTENNIAL == 0)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return true;
			}

		}
		else
		{
			return false;
		}
	}

	/** 
	 * Takes a date object and checks if it is valid.
	 * An invalid date would be something like a year in the future or the day 
	 * being passed exceeding the number of days in the month passed.
	 * @return true if the date submitted was valid, false if not
	 */
	public boolean isValid()
	{
		Date today = new Date();

		if ((this.year < EARLIEST_YEAR) || (today.year < this.year))
		{
			return false;
		}
		if (this.year == today.year)
		{
			if (this.month > today.month)
			{
				return false;
			}
			if (this.month == today.month)
			{
				if (this.day > today.day)
				{
					return false;
				}
				else
				{
					return true;
				}
			}
		}
		//this.date falls in the range [1/1/1900 - today's date]
		switch (this.month)
		{
		case JAN:
			if (this.day > THIRTY_ONE_DAYS)
			{
				return false;
			}
			break;
		case FEB:
			if (isLeapYear() == true)
			{
				if(this.day > DAYS_IN_FEB_LEAP_YEAR)
				{
					return false;
				}
			}
			else
			{
				if (this.day > DAYS_IN_FEB)
				{
					return false;
				}
			}
			break;
		case MAR:
			if (this.day > THIRTY_ONE_DAYS)
			{
				return false;
			}
			break;
		case APR:
			if (this.day > THIRTY_DAYS)
			{
				return false;
			}
			break;
		case MAY:
			if (this.day > THIRTY_ONE_DAYS)
			{
				return false;
			}
			break;
		case JUN:
			if (this.day > THIRTY_DAYS)
			{
				return false;
			}
			break;
		case JUL:
			if (this.day > THIRTY_ONE_DAYS)
			{
				return false;
			}
			break;
		case AUG:
			if (this.day > THIRTY_ONE_DAYS)
			{
				return false;
			}
			break;
		case SEP:
			if (this.day > THIRTY_DAYS)
			{
				return false;
			}
			break;
		case OCT:
			if (this.day > THIRTY_ONE_DAYS)
			{
				return false;
			}
			break;
		case NOV:
			if (this.day > THIRTY_DAYS)
			{
				return false;
			}
			break;
		case DEC:
			if (this.day > THIRTY_ONE_DAYS)
			{
				return false;
			}
			break;
		default: 
			return false;
		}
		return true;
	}

	/** 
	 * This method will return a referenced date in string format.
	 * @return the referenced date in mm/dd/yyyy format
	 */
	@Override
	public String toString()
	{
		return(Integer.toString(this.month) + "/" + Integer.toString(this.day) + 
						"/" + Integer.toString(this.year));
	}
}


