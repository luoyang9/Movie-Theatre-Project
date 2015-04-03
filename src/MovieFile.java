
/**
 * 
 * @author Charlie
 *
 **/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class MovieFile 
{
	private static RandomAccessFile raf;
	private static long numRecords;
	private static MovieRecord record;
	private static BufferedReader bR;
	private static ArrayList<String> databaseMovies;
	
	
	public static void initialize() throws IOException
	{
		record = new MovieRecord();
		raf = new RandomAccessFile("movie_info", "rw");
		numRecords = raf.length() / record.recSize;
		
		//csv file
		bR = new BufferedReader(new FileReader("movies.csv"));
		databaseMovies = new ArrayList<String>();
		String line = bR.readLine();
		line = bR.readLine();
		while(line != null)
		{
			databaseMovies.add(line);
			line = bR.readLine();
		}
		for(int i = 0; i < databaseMovies.size(); i++)
		{
			String[] movieInfo = databaseMovies.get(i).split(";");
			int[] tempShowTimes = {Integer.parseInt(movieInfo[2]), Integer.parseInt(movieInfo[3]), Integer.parseInt(movieInfo[4]), Integer.parseInt(movieInfo[5])};
			String[] tempCast = {movieInfo[6], movieInfo[7], movieInfo[8]};
			record = new MovieRecord(movieInfo[0], movieInfo[1], tempShowTimes, tempCast, Integer.parseInt(movieInfo[9]));
			writeRecord(i + 1, record);
		}
		
	}
	
	//get a specific record
	public static MovieRecord getRecord(long recordNum) throws Exception
	{
		long position;
		
		position = record.recSize*(recordNum - 1);
		raf.seek(position);
		char nullChar = (char)0;
		char nextChar;
		//get chars for movie title
		StringBuilder title = new StringBuilder(20);
		for(int i = 0; i < 20; i++)
		{
			nextChar = raf.readChar();
			if(nextChar != nullChar) title.append(nextChar);
			else
			{
				raf.seek(position + 40);
				break;
			}
		}
		record.movieTitle = new String(title);
		
		// get chars for summary
		StringBuilder summary = new StringBuilder(500);
		for (int i = 0 ; i < 500 ; i++)
		{
			nextChar = raf.readChar();
			if(nextChar != nullChar) summary.append(nextChar);
			else
			{
				raf.seek(position + 1040);
				break;
			}
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
			StringBuilder cast = new StringBuilder(20);
			for(int j = 0; j < 20; j++)
			{
				nextChar = raf.readChar();
				if(nextChar != nullChar) cast.append(nextChar);
				else
				{
					raf.seek(position + (1224 - (40 - 20 * i)));
					break;
				}
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
		log.i(numRecords +" Records found");
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
		raf.writeChars(String.format("%20s", temp.toString())); //write to file	
		
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
