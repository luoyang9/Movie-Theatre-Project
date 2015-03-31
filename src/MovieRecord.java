/**
 * 
 * @author Charlie
 *
 **/

public class MovieRecord 
{
	protected String movieTitle; //20 chars - 40 bytes							 
	protected String movieSummary; //500 chars - 1000 bytes						
	protected int[] showTimes; //4 int - 16 bytes
	protected boolean[][] seats; //48 booleans - 48 bytes
	protected String[] movieCast; //3 * 20 chars - 120 bytes
	protected int imageID;//1 int - 4 bytes
	
	protected final int recSize = 1228; //40+1000+16+48+120+4
	
	public MovieRecord()
	{
		//initialize all properties
		movieTitle = "not set";
		movieSummary = "not set";
		showTimes = new int[4];
		seats = new boolean[4][12];
		movieCast = new String[3];
		imageID = 1;
	}
	
	public MovieRecord(String movieTitle, String movieSummary, int[] showTimes, String[] movieCast, int imageID)
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
		
		//set show times
		this.showTimes = showTimes;
		
		//initialize seats
		this.seats = new boolean[4][12];
		//sets all seats to open (not ordered)
		for(int i = 0; i < seats.length; i++)
		{
			for(int j = 0; j < seats[i].length; j++)
			{
				seats[i][j] = false;
			}
		}
		
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
	
	public void setTitle(String movieTitle)
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
	}
	
	public void setSummary(String movieSummary)
	{
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
	}
	
	public void setShowTimes(int[] showTimes)
	{
		this.showTimes = showTimes;
	}

	public void setSeats(boolean[][] seats)
	{
		this.seats = seats;
	}
	
	public void setMovieCast(String[] cast)
	{
		this.movieCast = cast;
	}
	
	public void setImage(int id)
	{
		this.imageID = id;
	}
	
	public String getTitle(){return movieTitle;}
	public String getSummary(){return movieSummary;}
	public int[] getShowTimes(){return showTimes;}
	public boolean[][] getSeats(){return seats;}
	public String[] getCast(){return movieCast;}
	public int getImageID(){return imageID;}
	
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
