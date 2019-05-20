/* *
 * Luke McDougall
 * Simple class to store information about a date
 *
 * Last updated 01/03/2019
 *
 * Previously submitted as part of Prac 01
 * */
class Date implements java.io.Serializable
{
	//Class fields
	private int day;
	private int month;
	private int year;
	
	//Default constructor
	public Date(int year, int month, int day)
	{
		setYear(year);
		setMonth(month);
		setDay(day);
	}

    public int getDay()
    {
        return day;
    }

    public int getMonth()
    {
        return month;
    }

    public int getYear()
    {
        return year;
    }
    	
	public String toString()
	{
		String str = "Day: " + day + "\n" +
					 "Month: " + month + "\n" +
					 "Year: " + year + "\n";
		return str;
	}
	
	public String toCSV()
	{
		String outDay = Integer.toString(day);
		String outMonth = Integer.toString(month);
		String outYear = Integer.toString(year);
		if(day < 10)
		{
			outDay = "0" + outDay;
		}
		if(month < 10)
		{
			outMonth = "0" + outMonth;
		}
		String csv = outYear + outMonth + outDay;
		return csv;
	}
	
	//Setters
	private void setDay(int day)
	{
		if(validateDay(day))
		{
			this.day = day;
		}
		else
		{
			throw new IllegalArgumentException("Invalid day! Day must be between 1 and 31");
		}
	}
	
	private void setMonth(int month)
	{
		if(validateMonth(month))
		{
			this.month = month;
		}
		else
		{
			throw new IllegalArgumentException("Invalid month! Month must be between 1 and 12");
		}
	}
	
	private void setYear(int year)
	{
		if(validateYear(year))
		{
			this.year = year;
		}
		else
		{
			throw new IllegalArgumentException("Invalid year! Year must be between 1900 and 2100");
		}
	}
	
	//Validation
	private boolean validateDay(int day)
	{
		boolean valid = false;
		if(day >= 1 && day <= 31)
		{
			valid = true;
		}
		return valid;
	}
	
	private boolean validateMonth(int month)
	{
		boolean valid = false;
		if(month >= 1 && month <= 12)
		{
			valid = true;
		}
		return valid;
	}
	
	private boolean validateYear(int year)
	{
		boolean valid = false;
		if(year >= 1900 && year <= 2100)
		{
			valid = true;
		}
		return valid;
	}
}
