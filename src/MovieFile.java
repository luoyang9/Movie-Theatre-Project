
/**
 * 
 * @author Charlie
 *
 **/

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class MovieFile 
{
	private static RandomAccessFile raf;
	private static long numRecords;
	private static MovieRecord record;
	
	
	public static void initialize() throws IOException
	{
		record = new MovieRecord();
		raf = new RandomAccessFile("movie_info", "rw");
		numRecords = raf.length() / record.recSize;
	}
	
	//get a specific record
	public static MovieRecord getRecord(long recordNum) throws Exception
	{
		long position;
		
		position = record.recSize*(recordNum - 1);
		raf.seek(position);
		
		//get chars for movie title
		char title[] = new char[20];
		for(int i = 0; i < title.length; i++)
		{
			title [i] = raf.readChar ();
		}
		record.movieTitle = new String (title);
		
		// get chars for summary
		char summary[] = new char [500];
		for (int i = 0 ; i < summary.length ; i++)
		{
			summary [i] = raf.readChar ();
		}
		record.movieSummary = new String (summary);
		
		//get show times
		for(int i = 0; i < record.showTimes.length; i++)
		{
			record.showTimes[i] = raf.readInt();
		}
		
		//get seats
		for(int i = 0; i < record.seats.length; i++)
		{
			for(int j = 0; j < record.seats[i].length; j++)
			{
				record.seats[i][j] = raf.readBoolean();
			}
		}
		
		//get movie cast
		for(int i = 0; i < record.movieCast.length; i++)
		{
			char cast[] = new char[20];
			for(int j = 0; j < cast.length; j++)
			{
				cast [j] = raf.readChar ();
			}
			record.movieCast[i] = new String(cast);
		}
		
		//get image id
		record.imageID = raf.readInt();
		
		return record;
	}
	
	//get all records
	public static List<MovieRecord> getAllRecords() throws Exception
	{
		List<MovieRecord> list = new ArrayList<MovieRecord>();
		MovieRecord currMovie;
		for(int i = 0; i < numRecords; i++)
		{	
			currMovie = getRecord(i+1);
			list.add(new MovieRecord(currMovie.movieTitle, currMovie.movieSummary, currMovie.showTimes, currMovie.movieCast, currMovie.imageID));
		}
		return list;
	}
	
	public static void writeRecord(long recordNum, MovieRecord record) throws IOException
	{
		long position = record.recSize * (recordNum - 1);
		raf.seek(position);
		
		StringBuffer temp;
		
		//put movie title data into StringBuffer
		temp = new StringBuffer(record.movieTitle);
		temp.setLength(20); //max length	
		raf.writeChars(temp.toString()); //write to file	
		
		//put movie summary data into StringBuffer
		temp = new StringBuffer(record.movieSummary);
		temp.setLength(500); //max length	
		raf.writeChars(temp.toString()); //write to file
		
		//write show times
		for(int i = 0; i < record.showTimes.length; i++)
		{
			raf.writeInt(record.showTimes[i]);
		}
		
		//write seats
		for(int i = 0; i < record.seats.length; i++)
		{
			for(int j = 0; j < record.seats[i].length; j++)
			{
				raf.writeBoolean(record.seats[i][j]);
			}
		}
		
		//write cast
		for(int i = 0; i < record.movieCast.length; i++)
		{
			temp = new StringBuffer(record.movieCast[i]);
			temp.setLength(20); //max length	
			raf.writeChars(temp.toString()); //write to file
		}
		
		//write image id
		raf.writeInt(record.imageID);

		numRecords = raf.length() / record.recSize;
	}
	
	public static RandomAccessFile getMovieFile(){return raf;}
	public static long getNumRecords(){return numRecords;}
	public static void close() throws IOException
	{
		raf.close();
	}
	
	
}
