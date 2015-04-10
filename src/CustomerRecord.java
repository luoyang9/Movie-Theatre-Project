/**
 * 
 * @author Charlie
 *
 **/

public class CustomerRecord 
{
	protected String movie; //20 chars - 40 bytes	
	protected int showTime; //1 int - 4 bytes
	protected int date;//1 int - 4 bytes
	protected int seatRow;//1 int - 4 bytes
	protected int seatCol;//1 int - 4 bytes
	protected String firstName;//20 chars - 40 bytes
	protected String lastName;//20 chars - 40 bytes
	protected int age;//1 int - 4 bytes
	protected String address;//30 chars - 60 bytes
	protected int telephone;//1 int - 4 bytes
	protected int creditCardNum;//1 int - 4 bytes
	protected int expiryDate;//1 int - 4 bytes
	protected int securityCode;//1 int - 4 bytes
	
	public CustomerRecord()
	{
		
	}
	
	public CustomerRecord(String movie, int showTime, int date, int seatRow, int seatCol, String fName, String lName, int age, String address, int telephone, int creditNum, int expDate, int secCode)
	{
		//set movie
		if (movie.length () > 20)
		{
			StringBuffer temp = new StringBuffer (movie);
			temp.setLength (20); // cut off extra characters
			this.movie = temp.toString (); // use truncated string
		}
		else
		{
			this.movie = movie;
		}

		this.showTime = showTime;

		this.date = date;

		this.seatRow = seatRow;

		this.seatCol = seatCol;
		
		//set fname
		if (fName.length () > 20)
		{
			StringBuffer temp = new StringBuffer (fName);
			temp.setLength (20); // cut off extra characters
			this.firstName = temp.toString (); // use truncated string
		}
		else
		{
			this.firstName = fName;
		}
		
		//set movie
		if (lName.length () > 20)
		{
			StringBuffer temp = new StringBuffer (lName);
			temp.setLength (20); // cut off extra characters
			this.lastName = temp.toString (); // use truncated string
		}
		else
		{
			this.lastName = lName;
		}
		
		this.age = age;
		
		//set movie
		if (address.length () > 30)
		{
			StringBuffer temp = new StringBuffer (address);
			temp.setLength (30); // cut off extra characters
			this.address = temp.toString (); // use truncated string
		}
		else
		{
			this.address = address;
		}
		
		this.telephone = telephone;
		this.creditCardNum = creditNum;
		this.expiryDate = expDate;
		this.securityCode = secCode;
	}
	
	
	
	
}
