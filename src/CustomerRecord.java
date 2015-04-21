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
	protected String name;//40 chars - 80 bytes
	protected int age;//1 int - 4 bytes
	protected String address;//30 chars - 60 bytes
	protected long telephone;//1 long - 8 bytes
	protected long creditCardNum;//1 long - 8 bytes
	protected int expiryDate;//1 int - 4 bytes
	protected int securityCode;//1 int - 4 bytes
	
	//40 + 4 + 4 + 4 + 4 + 80 + 4 + 60 + 8 + 8 + 4 + 4 = 224
	protected int recSize = 224;
	
	public CustomerRecord()
	{
		
	}
	
	public CustomerRecord(String movie, int showTime, int date, int seatRow, int seatCol, String name, int age, String address, long telephone, long creditNum, int expDate, int secCode)
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
		
		//set name
		if (name.length () > 20)
		{
			StringBuffer temp = new StringBuffer (name);
			temp.setLength (20); // cut off extra characters
			this.name = temp.toString (); // use truncated string
		}
		else
		{
			this.name = name;
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
