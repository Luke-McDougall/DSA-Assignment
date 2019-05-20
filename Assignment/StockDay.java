/* *
 * Luke McDougall
 * 
 * Class for storing information about stock trading for a day
 * 
 * Last updated 19/05/2019
 *
 * Previously submitted as part of Prac 01
 * */

class StockDay implements java.io.Serializable
{
	//Class fields
	private String ticker;
	private Date date;
	private double open;
	private double high;
	private double low;
	private double close;
	private int volume;
	
	//Default constructor
	public StockDay(String ticker, Date date, double open, double high, double low, double close, int volume)
	{
		setTicker(ticker);
		setDate(date);
		setOpen(open);
		setHigh(high);
		setLow(low);
		setClose(close);
		setVolume(volume);
	}
	
	public String toString()
	{
		String str = "ticker: " + ticker + "\n" +
					 date.toString() +
					 "open: " + open + "\n" +
					 "high: " + high + "\n" +
					 "low: " + low + "\n" +
					 "close: " + close + "\n" +
					 "volume: " + volume;
		return str;
	}
	
	public String toCSV()
	{
		String str = ticker + "," + date.toCSV() + "," + open + "," + high
				     + "," + low + "," + close + "," + volume;
		return str;
	}
	
	//getters
	public String getTicker()
	{
		return new String(ticker);
	}

    public int getDay()
    {
        return date.getDay();
    }

    public int getMonth()
    {
        return date.getMonth();
    }

    public int getYear()
    {
        return date.getYear();
    }

    public double getOpen()
    {
        return open;
    }

    public double getHigh()
    {
        return high;
    }

    public double getLow()
    {
        return low;
    }

    public double getClose()
    {
        return close;
    }
    
    public int getVolume()
    {
        return volume;
    }
	
	//setters
	private void setTicker(String ticker)
	{
		if(validateTicker(ticker))
		{
			this.ticker = ticker;
		}
		else
		{
			throw new IllegalArgumentException("Invalid ticker");
		}
	}
	
	private void setDate(Date date)
	{
		this.date = date;
	}
	
	private void setOpen(double open)
	{
		if(validateOpen(open))
		{
			this.open = open;
		}
		else
		{
			throw new IllegalArgumentException("Invalid open");
		}
	}
	
	private void setHigh(double high)
	{
		if(validateHigh(high))
		{
			this.high = high;
		}
		else
		{
			throw new IllegalArgumentException("Invalid high");
		}
	}
	
	private void setLow(double low)
	{
		if(validateLow(low))
		{
			this.low = low;
		}
		else
		{
			throw new IllegalArgumentException("Invalid low");
		}
	}
	
	private void setClose(double close)
	{
		if(validateClose(close))
		{
			this.close = close;
		}
		else
		{
			throw new IllegalArgumentException("Invalid close");
		}
	}
	
	private void setVolume(int volume)
	{
		if(validateVolume(volume))
		{
			this.volume = volume;
		}
		else
		{
			throw new IllegalArgumentException("Invalid volume");
		}
	}
	
	//validation
	private boolean validateTicker(String ticker)
	{
		boolean valid = false;
		if(ticker.length() == 3 && ticker.toUpperCase().equals(ticker))
		{
			valid = true;
		}
		return valid;
	}
	
	private boolean validateOpen(double open)
	{
		boolean valid = false;
		if(open > 0)
		{
			valid = true;
		}
		return valid;
	}
	
	private boolean validateHigh(double high)
	{
		boolean valid = false;
		if(high > 0)
		{
			valid = true;
		}
		return valid;
	}
	
	private boolean validateLow(double low)
	{
		boolean valid = false;
		if(low > 0)
		{
			valid = true;
		}
		return valid;
	}
	
	private boolean validateClose(double close)
	{
		boolean valid = false;
		if(close > 0)
		{
			valid = true;
		}
		return valid;
	}
	
	private boolean validateVolume(int volume)
	{
		boolean valid = false;
		if(volume >= 0)
		{
			valid = true;
		}
		return valid;
	}
}
