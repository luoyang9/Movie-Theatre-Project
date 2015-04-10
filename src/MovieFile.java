
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
	
	
	public static void initialize() throws Exception
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
			int[] tempShowTimes = {Integer.parseInt(movieInfo[4]), Integer.parseInt(movieInfo[5]), Integer.parseInt(movieInfo[6]), Integer.parseInt(movieInfo[7])};
			String[] tempCast = {movieInfo[8], movieInfo[9], movieInfo[10]};
			boolean[][][] seats = getRecord(i + 1).seats;
			if(seats == null) 
			{
				seats = new boolean[4][3][4];
				for(int a = 0; a < seats.length; a++)
				{
					for(int b = 0; b < seats[a].length; b++)
					{
						for(int c = 0; c < seats[a][b].length; c++)
						{
							seats[a][b][c] = false;
						}
					}
				}
			}
			record = new MovieRecord(movieInfo[0], movieInfo[1], Integer.parseInt(movieInfo[2]), Integer.parseInt(movieInfo[3]), tempShowTimes, seats, tempCast, Integer.parseInt(movieInfo[11]));
			writeRecord(i + 1, record);
		}
		raf.close();
	}
	
	//get a specific record
	public static MovieRecord getRecord(long recordNum) throws Exception
	{
		long position;
		record = new MovieRecord();
		raf = new RandomAccessFile("movie_info", "rw");
		
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
		
		record.releaseDate = raf.readInt();
		record.finalDate = raf.readInt();
		
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
				for(int k = 0; k < record.seats[i][j].length; k++)
				{
					record.seats[i][j][k] = raf.readBoolean();
				}
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
			}
			record.movieCast[i] = new String(cast);
		}
		
		//get image id
		record.imageID = raf.readInt();
		
		raf.close();
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
			MovieRecord movie = new MovieRecord(currMovie.movieTitle, currMovie.movieSummary, currMovie.releaseDate, currMovie.finalDate, currMovie.showTimes, currMovie.seats, currMovie.movieCast, currMovie.imageID);
			list.add(movie);
		}
		log.i(numRecords +" Records found");
		return list;
	}
	
	public static void writeRecord(long recordNum, MovieRecord record) throws IOException
	{
		raf = new RandomAccessFile("movie_info", "rw");
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
		
		//write release and final dates
		raf.writeInt(record.releaseDate);
		raf.writeInt(record.finalDate);
		
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
				for(int k = 0; k < record.seats[i][j].length; k++)
				{
					raf.writeBoolean(record.seats[i][j][k]);
				}
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
		raf.close();
	}
	
	public static int getRecordNum(String movieTitle) throws IOException
	{
		raf = new RandomAccessFile("movie_info", "rw");
		int recordNum = 1;
		for(int a = 0; a < numRecords * record.recSize; a+=record.recSize)
		{
			raf.seek(a);
			
			char nullChar = (char)0;
			char nextChar;
			//get chars for movie title
			StringBuilder title = new StringBuilder(20);
			for(int i = 0; i < 20; i++)
			{
				nextChar = raf.readChar();
				if(nextChar != nullChar) title.append(nextChar);
			}
			String currTitle = new String(title);
			if(currTitle.equalsIgnoreCase(movieTitle))
			{
				recordNum = a / record.recSize + 1;
				break;
			}
		}
		raf.close();
		return recordNum;
	}
	
	public static RandomAccessFile getMovieFile(){return raf;}
	public static long getNumRecords(){return numRecords;}
	public static void close() throws IOException
	{
		raf.close();
	}
	
	
	
	
}
