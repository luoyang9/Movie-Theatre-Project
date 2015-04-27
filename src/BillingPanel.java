import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

@SuppressWarnings("serial")
public class BillingPanel extends JPanel
{
	private static MovieRecord record;
	private static int dateIndex, timeIndex, rowIndex, colIndex;
	private static long recordNum;
	
	private static JPanel cards;
	
	private static JLabel banner, name, bDay, address, phoneNum, credCard, expDate, secureCode, blank;
	private static JButton back, proceed;
	private static JTextField nameIn, addressIn, phoneNumIn, credCardIn, secureCodeIn;
	private static JCalendar calendar1, calendar2;
	
	public BillingPanel(){
		setLayout(new BorderLayout());
		
		JPanel personal = new JPanel(new GridLayout(4,2));
		JPanel credInfo = new JPanel(new GridLayout(5,1));
		JPanel inputCVV = new JPanel(new GridLayout(1,2));
		JPanel allInfo = new JPanel(new GridLayout(1,2));
		JPanel button = new JPanel(new GridLayout(1,3));
		
		banner = new JLabel("Payment Information");
		name = new JLabel("Name");
		bDay = new JLabel("Birthdate");
		address = new JLabel("Address");
		phoneNum = new JLabel("Telephone");
		credCard = new JLabel("Credit Card No.");
		expDate = new JLabel("Expiry Date");
		secureCode = new JLabel("CVV");
		blank = new JLabel(" ");
		calendar1 = new JCalendar();
		calendar2 = new JCalendar();
		
		back = new JButton("Cancel");
		proceed = new JButton("Proceed");
		
		nameIn = new JTextField("");
		addressIn = new JTextField("");
		phoneNumIn = new JTextField("");
		credCardIn = new JTextField("");
		secureCodeIn = new JTextField("");
		
		ButtonHandler onClick = new ButtonHandler();
		back.addActionListener(onClick);
		proceed.addActionListener(onClick);
		
		add(banner, BorderLayout.PAGE_START);
		
		personal.add(name);
		personal.add(nameIn);
		personal.add(bDay);
		personal.add(calendar1);
		personal.add(address);
		personal.add(addressIn);
		personal.add(phoneNum);
		personal.add(phoneNumIn);
		allInfo.add(personal);
		
		credInfo.add(credCard);
		credInfo.add(credCardIn);
		credInfo.add(expDate);
		credInfo.add(calendar2);
		inputCVV.add(secureCode);
		inputCVV.add(secureCodeIn);
		credInfo.add(inputCVV);
		allInfo.add(credInfo);
		
		add(allInfo, BorderLayout.CENTER);
		
		button.add(back);
		button.add(blank);
		button.add(proceed);
		add(button, BorderLayout.PAGE_END);
	}
	private static class ButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();
			CardLayout cl = (CardLayout)cards.getLayout();
			
			if(action.equals("Proceed"))
			{
				try
				{
					//formatting
					long formatTelephone = Long.parseLong(phoneNumIn.getText().replaceAll("\\(|\\)|\\-|\\s", ""));
					
					Calendar tempCal1 = calendar1.getCalendar();
					int birthDate = (tempCal1.get(Calendar.MONTH) + 1) * 1000000 + tempCal1.get(Calendar.DATE) *10000 + tempCal1.get(Calendar.YEAR);
					
					Calendar tempCal2 = calendar2.getCalendar();
					int expDate = (tempCal2.get(Calendar.MONTH) + 1) * 1000000 + tempCal2.get(Calendar.DATE) *10000 + tempCal2.get(Calendar.YEAR);
					
					int secCode = Integer.parseInt(secureCodeIn.getText().replaceAll("\\s", ""));
					
					int realDate = record.releaseDate + dateIndex;
					int lastDayInMonth = util.getLastDay(record.releaseDate/100);
					if((realDate) % 100 > lastDayInMonth)
					{
						realDate = ((realDate / 100) + 1) * 100 + ((realDate % 100) - lastDayInMonth);
					}
					
					//write new customer record
					CustomerRecord customer = new CustomerRecord(record.movieTitle, record.showTimes[timeIndex], realDate, rowIndex, colIndex, nameIn.getText(), birthDate, addressIn.getText(), formatTelephone, Long.parseLong(credCardIn.getText()), expDate, secCode);
					
					log.v("Customer record created at record number " + CustomerFile.getNumRecords() + 1);
					CustomerFile.writeRecord(CustomerFile.getNumRecords() + 1, customer);	

					//book the seat
					record.seatplan.getSeats()[dateIndex][timeIndex][rowIndex][colIndex] = true;	

					log.v("Seat row " + (rowIndex + 1) + " and col " + (colIndex + 1) + " booked for " + record.movieTitle + " at time " + record.showTimes[timeIndex] + "PM for date "  + realDate);
					MovieFile.writeRecord(recordNum, record);

					//proceed to check out panel
					cl.show(cards, Value.CHECK_OUT);
					CheckOutPanel checkOut = (CheckOutPanel)cards.getComponent(6);
					checkOut.setInfo(customer);
				}catch(IOException io){
					log.e("IOException occurred.");
				}catch(NumberFormatException nf){
					log.e("Number Format Exception - A non-integer was entered into a field expecting integers.");
				}
			}
			else if(action.equals("Cancel"))
			{
				cl.show(cards, Value.TICKET);
			}
			
		}
	}
	
	public void setMovie(MovieRecord movie, long recordNumber, int date, int time, int row, int col)
	{
		record = movie;
		recordNum = recordNumber;
		dateIndex = date;
		timeIndex = time;
		rowIndex = row;
		colIndex = col;
	}
	
	public void setCards(JPanel masterCard)
	{
		cards = masterCard;
	}
}
