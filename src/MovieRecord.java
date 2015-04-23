/**
 * 
 * @author Charlie
 *
 **/

public class MovieRecord 
{
	protected String movieTitle; //20 chars - 40 bytes							 
	protected String movieSummary; //500 chars - 1000 bytes	
	protected int releaseDate; //1 int - 4 bytes
	protected int finalDate;//1 int - 4 bytes
	protected int[] showTimes; //4 int - 16 bytes
	protected SeatPlan seatplan; //48*5 booleans - 240 bytes
	protected String[] movieCast; //3 * 20 chars - 120 bytes
	protected int imageID;//1 int - 4 bytes
	
	protected final int recSize = 1428; //40+1000+16+192+120+4 + 4 + 4
	

	
	public MovieRecord()
	{
		//initialize all properties
		movieTitle = "not set";
		movieSummary = "not set";
		releaseDate = 0;
		finalDate = 0;
		showTimes = new int[4];
		seatplan = new SeatPlan();
		movieCast = new String[3];
		imageID = 1;
	}
	
	public MovieRecord(String movieTitle, String movieSummary, int releaseDate, int finalDate, int[] showTimes, boolean[][][][] seats, String[] movieCast, int imageID)
	{
		//set movieTitle
		if (movieTitle.length () > 20)
		{
			StringBuffer temp = new StringBuffer (movieTitle);
			temp.setLength (20); // cut off extra characters
			this.movieTitle = temp.toString (); // use truncated string
		}
		else
		{
			this.movieTitle = movieTitle;
		}
		
		//set movieSummary
		if (movieSummary.length () > 500)
		{
			StringBuffer temp = new StringBuffer (movieSummary);
			temp.setLength (500); // cut off extra characters
			this.movieSummary = temp.toString (); // use truncated string
		}
		else
		{
			this.movieSummary = movieSummary;
		}
		
		//set release and final dates
		this.releaseDate = releaseDate;
		this.finalDate = finalDate;
		
		//set show times
		this.showTimes = showTimes;
		
		//set seats
		seatplan = new SeatPlan();
		seatplan.setSeats(seats);
		
		//declare movieCast
		this.movieCast = new String[3];
		
		//set movieCast
		for(int i = 0; i < movieCast.length; i++)
		{
			if (movieCast[i].length () > 20)
			{
				StringBuffer temp = new StringBuffer (movieCast[i]);
				temp.setLength (20); // cut off extra characters
				this.movieCast[i] = temp.toString (); // use truncated string
			}
			else
			{
				this.movieCast[i] = movieCast[i];
			}
			
			//set image
			this.imageID = imageID;
		}
	}
	
	public String toString ()
	{
		String output = "Title: " + this.movieTitle;
		output += "- Summary: " + this.movieSummary;
		output += "- ShowTimes: ";
		for(int i = 0; i < showTimes.length; i++)
		{
			output += showTimes[i] + " ";
		}
		output += "- Cast: ";
		for(int i = 0; i < movieCast.length; i++)
		{
			output+= movieCast[i] + " ";
		}
		return output;
	}
}
