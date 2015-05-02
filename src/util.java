
public class util {
	public static String getMonth(int month)
	{
		switch(month)
		{
		case 1 : return "January";
		case 2 : return "February";
		case 3 : return "March";
		case 4 : return "April";
		case 5 : return "May";
		case 6 : return "June";
		case 7 : return "July";
		case 8 : return "August";
		case 9 : return "September";
		case 10 : return "October";
		case 11: return "November";
		case 12: return "December";
		default: return "Error";
		}
	}
	
	public static int getLastDay(int month)
	{
		switch(month)
		{
		case 1 : return 31;
		case 2 : return 28;
		case 3 : return 31;
		case 4 : return 30;
		case 5 : return 31;
		case 6 : return 30;
		case 7 : return 31;
		case 8 : return 31;
		case 9 : return 30;
		case 10 : return 31;
		case 11: return 30;
		case 12: return 31;
		default: return 30;
		}
	}
	
	public static String getTicketType(int type)
	{
		switch(type)
		{
		case Value.CHILD: return "Child";
		case Value.ADULT: return "Adult";
		case Value.SENIOR: return "Senior";
		default: return "ERROR";
		}
	}
	
	public static double getTicketPrice(int type)
	{
		switch(type)
		{
		case Value.CHILD: return Value.CHILD_PRICE;
		case Value.ADULT: return Value.ADULT_PRICE;
		case Value.SENIOR: return Value.SENIOR_PRICE;
		default: return Value.ADULT_PRICE;
		}
	}
	
	public static int getDaysBetween(int start, int end)
	{
		if(start/100 != end/100)
		{
			int first = end - (end/100) * 100;
			int second = ((start/100)*100 + getLastDay(start/100)) - start + 1;
			return first + second;
		}
		else
		{
			return end - start;
		}
		
	}
}
