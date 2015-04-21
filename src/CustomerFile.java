import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;


public class CustomerFile {

	private static RandomAccessFile raf;
	private static long numRecords;
	private static CustomerRecord record;
	
	public static void initialize() throws Exception
	{
		record = new CustomerRecord();
		raf = new RandomAccessFile("customer_info", "rw");
		numRecords = raf.length() / record.recSize;
		
		raf.close();
	}
	
	//get a specific record
	public static CustomerRecord getRecord(long recordNum) throws Exception
	{
		long position;
		record = new CustomerRecord();
		raf = new RandomAccessFile("customer_info", "rw");
		
		position = record.recSize*(recordNum - 1);
		raf.seek(position);
		char nullChar = (char)0;
		char nextChar;
		
		//get chars for movie
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
		record.movie = new String(title);

		record.showTime = raf.readInt();
		record.date = raf.readInt();
		record.seatRow = raf.readInt();
		record.seatCol = raf.readInt();
		
		//get chars for name
		StringBuilder name = new StringBuilder(40);
		for(int i = 0; i < 40; i++)
		{
			nextChar = raf.readChar();
			if(nextChar != nullChar) name.append(nextChar);
			else
			{
				raf.seek(position + 136);
				break;
			}
		}
		record.name = new String(name);
		
		record.age = raf.readInt();
		
		//get chars for address
		StringBuilder address = new StringBuilder(30);
		for(int i = 0; i < 30; i++)
		{
			nextChar = raf.readChar();
			if(nextChar != nullChar) address.append(nextChar);
			else
			{
				raf.seek(position + 200);
				break;
			}
		}
		record.address = new String(address);
		
		record.telephone = raf.readLong();
		record.creditCardNum = raf.readLong();
		record.expiryDate = raf.readInt();
		record.securityCode = raf.readInt();
		
		raf.close();
		return record;
	}
	
	//get all records
	public static List<CustomerRecord> getAllRecords() throws Exception
	{
		List<CustomerRecord> list = new ArrayList<CustomerRecord>();
		CustomerRecord currCustomer;
		System.out.println(numRecords);
		for(int i = 0; i < numRecords; i++)
		{	
			currCustomer = getRecord(i+1);
			CustomerRecord customer = new CustomerRecord(currCustomer.movie, currCustomer.showTime, currCustomer.date, currCustomer.seatRow, currCustomer.seatCol, currCustomer.name, currCustomer.age, currCustomer.address, currCustomer.telephone, currCustomer.creditCardNum, currCustomer.expiryDate, currCustomer.securityCode);
			list.add(customer);
		}
		log.i(list.size() +" Customer Records found");
		return list;
	}
	
	public static void writeRecord(long recordNum, CustomerRecord record) throws IOException
	{
		raf = new RandomAccessFile("customer_info", "rw");
		long position = record.recSize * (recordNum - 1);
		raf.seek(position);
		
		StringBuffer temp;
		
		//put movie title data into StringBuffer
		temp = new StringBuffer(record.movie);
		temp.setLength(20); //max length	
		raf.writeChars(temp.toString()); //write to file	
		
		raf.writeInt(record.showTime);
		raf.writeInt(record.date);
		raf.writeInt(record.seatRow);
		raf.writeInt(record.seatCol);
		
		//put name data into StringBuffer
		temp = new StringBuffer(record.name);
		temp.setLength(40); //max length	
		raf.writeChars(temp.toString()); //write to file

		raf.writeInt(record.age);
		
		//put address title data into StringBuffer
		temp = new StringBuffer(record.address);
		temp.setLength(30); //max length	
		raf.writeChars(temp.toString()); //write to file	
		
		raf.writeLong(record.telephone);
		raf.writeLong(record.creditCardNum);
		raf.writeInt(record.expiryDate);
		raf.writeInt(record.securityCode);

		numRecords = raf.length() / record.recSize;
		raf.close();
	}
	

	public static RandomAccessFile getMovieFile(){return raf;}
	public static long getNumRecords(){return numRecords;}
}
