
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
	
	public static int getNumDaysPlaying(int releaseDate, int finalDate)
	{
		int numDays;
		int lastDay = getLastDay(releaseDate/100);
		
		if(releaseDate/100 != finalDate/100)
		{
			int numDays1 = ((releaseDate/100) * 100 + lastDay) - releaseDate + 1; //add 1 to compensate 
			int numDays2 = finalDate - (finalDate / 100) * 100;
			numDays = numDays1 + numDays2;
		}
		else
		{
			numDays = finalDate - releaseDate + 1;
		}
		return numDays;
	}
}
