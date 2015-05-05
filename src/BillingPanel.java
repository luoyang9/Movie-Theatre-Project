import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

@SuppressWarnings("serial")
public class BillingPanel extends JPanel
{
	private static MovieRecord record;
	private static int dateIndex, timeIndex, rowIndex, colIndex, ticket;
	private static long recordNum;
	
	private static JFrame calendarFrame;
	private static JPanel cards;
	
	private static JLabel banner, name, bDay, address, phoneNum, credCard, expDate, secureCode, errorMessage;
	private static JButton back, proceed, btnCalendar1, btnCalendar2, btnConfirm1, btnConfirm2;
	private static JTextField nameIn, addressIn, phoneNumIn, credCardIn, secureCodeIn, birthdateIn, expDateIn;
	private static JCalendar calendar1, calendar2;
	
	public BillingPanel(){
		setBackground(Value.GREY);
		
		JPanel calendarPnl1 = new JPanel();
		JPanel calendarPnl2 = new JPanel();
		JPanel namePnl = new JPanel();
		JPanel addressPnl = new JPanel();
		JPanel phonePnl = new JPanel();
		JPanel creditCardPnl = new JPanel();
		JPanel secCodePnl = new JPanel();
		JPanel personal = new JPanel();
		personal.setLayout(new BoxLayout(personal, BoxLayout.Y_AXIS));
		JPanel credInfo = new JPanel();
		credInfo.setLayout(new BoxLayout(credInfo, BoxLayout.Y_AXIS));
		JPanel allInfo = new JPanel();
		JPanel button = new JPanel();
		button.setLayout(new BoxLayout(button, BoxLayout.X_AXIS));
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		container.setSize(Value.WIDTH - 100, Value.HEIGHT);
		
		btnCalendar1 = new JButton("");
		btnCalendar1.setActionCommand("cal1");
		btnCalendar1.setIcon(Value.getImage("cal", 20, 20));
		btnCalendar2 = new JButton("");
		btnCalendar2.setActionCommand("cal2");
		btnCalendar2.setIcon(Value.getImage("cal", 20, 20));
		btnConfirm1 = new JButton("OK");
		btnConfirm1.setActionCommand("confirm1");
		btnConfirm2 = new JButton("OK");
		btnConfirm2.setActionCommand("confirm2");
		banner = new JLabel("Payment Information");
		banner.setFont(new Font("Arial", Font.BOLD, 25));
		name = new JLabel("Name");
		bDay = new JLabel("Birthdate");
		address = new JLabel("Address");
		phoneNum = new JLabel("Telephone");
		credCard = new JLabel("Credit Card No.");
		expDate = new JLabel("Expiry Date");
		secureCode = new JLabel("CVV");
		errorMessage = new JLabel("");
		calendar1 = new JCalendar();
		calendar2 = new JCalendar();
		
		back = new JButton("Cancel ");
		back.setBackground(Value.RED);
		back.setIcon(Value.getImage("back", 15, 15));
		back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		back.setForeground(Color.BLACK);
		back.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		proceed = new JButton(" Proceed ");
		proceed.setBackground(Value.BABY_BLUE);
		proceed.setForeground(Color.white);
		proceed.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		proceed.setBorder(BorderFactory.createLineBorder(Value.BLUE));
		
		nameIn = new JTextField("", 20);
		addressIn = new JTextField("", 20);
		phoneNumIn = new JTextField("", 20);
		credCardIn = new JTextField("", 20);
		secureCodeIn = new JTextField("", 20);
		birthdateIn = new JTextField("", 15);
		expDateIn = new JTextField("", 15);
		
		ButtonHandler onClick = new ButtonHandler();
		back.addActionListener(onClick);
		proceed.addActionListener(onClick);
		btnCalendar1.addActionListener(onClick);
		btnCalendar2.addActionListener(onClick);
		btnConfirm1.addActionListener(onClick);
		btnConfirm2.addActionListener(onClick);
		
		container.add(banner);

		calendarPnl1.add(bDay);
		calendarPnl1.add(birthdateIn);
		calendarPnl1.add(btnCalendar1);
		calendarPnl2.add(expDate);
		calendarPnl2.add(expDateIn);
		calendarPnl2.add(btnCalendar2);
		namePnl.add(name);
		namePnl.add(nameIn);
		addressPnl.add(address);
		addressPnl.add(addressIn);
		phonePnl.add(phoneNum);
		phonePnl.add(phoneNumIn);
		personal.add(namePnl);
		personal.add(calendarPnl1);
		personal.add(addressPnl);
		personal.add(phonePnl);
		allInfo.add(personal);
		
		creditCardPnl.add(credCard);
		creditCardPnl.add(credCardIn);
		secCodePnl.add(secureCode);
		secCodePnl.add(secureCodeIn);
		credInfo.add(creditCardPnl);
		credInfo.add(calendarPnl2);
		credInfo.add(secCodePnl);
		allInfo.add(credInfo);
		
		container.add(allInfo);
		
		button.add(back);
		button.add(Box.createHorizontalGlue());
		button.add(proceed);
		container.add(button);
		
		add(container);
		add(errorMessage);
	}
	private static class ButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();
			CardLayout cl = (CardLayout)cards.getLayout();
			
			if(action.equals(proceed.getActionCommand()))
			{
				try
				{
					if(nameIn.getText().equals("") || birthdateIn.getText().equals("") || addressIn.getText().equals("") || 
					phoneNumIn.getText().equals("") || credCardIn.getText().equals("") || expDateIn.getText().equals("") || 
					secureCodeIn.getText().equals(""))
					{
						errorMessage.setText("ERROR! Please fill out all fields.");
						return;
					}
					//formatting
					long formatTelephone = Long.parseLong(phoneNumIn.getText().replaceAll("\\(|\\)|\\-|\\s", ""));
					
					String[] birthdateString = birthdateIn.getText().split("/");
					int birthDate = (Integer.parseInt(birthdateString[0]) * 1000000 + Integer.parseInt(birthdateString[1]) *10000 
							+ Integer.parseInt(birthdateString[2]));

					String[] expDateString = expDateIn.getText().split("/");
					int expDate = (Integer.parseInt(expDateString[0]) * 1000000 + Integer.parseInt(expDateString[1]) *10000 
							+ Integer.parseInt(expDateString[2]));
					
					int secCode = Integer.parseInt(secureCodeIn.getText().replaceAll("\\s", ""));
					long credCardNum = Long.parseLong(credCardIn.getText());
					
					int realDate = record.releaseDate + dateIndex;
					int lastDayInMonth = util.getLastDay(record.releaseDate/100);
					if((realDate) % 100 > lastDayInMonth)
					{
						realDate = ((realDate / 100) + 1) * 100 + ((realDate % 100) - lastDayInMonth);
					}
					
					//write new customer record
					CustomerRecord customer = new CustomerRecord(record.movieTitle, record.showTimes[timeIndex], 
							realDate, rowIndex, colIndex, ticket, nameIn.getText(), birthDate, addressIn.getText(), formatTelephone, 
							credCardNum, expDate, secCode);
					
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
					errorMessage.setText("");
				}catch(IOException io){
					log.e("IOException occurred.");
					io.printStackTrace();
					errorMessage.setText("An error occurred while processing your request. Please contact the administrator for help.");
					return;
				}catch(NumberFormatException nf){
					log.e("Number Format Exception - A non-integer was entered into a field expecting integers.");
					nf.printStackTrace();
					errorMessage.setText("ERROR! An unexpected character was entered in one of the fields above. Did you accidently enter a letter in a field expecting all numbers?");
					return;
				}
			}
			else if(action.equals(back.getActionCommand()))
			{
				cl.show(cards, Value.TICKET);
				errorMessage.setText("");
			}
			else if(action.equals(btnCalendar1.getActionCommand()))
			{
				calendarFrame = new JFrame("Calendar");
				JPanel temp = new JPanel();
				temp.add(calendar1);
				temp.add(btnConfirm1);
				calendarFrame.add(temp);
				calendarFrame.pack();
				calendarFrame.setVisible(true);
			}
			else if(action.equalsIgnoreCase(btnConfirm1.getActionCommand()))
			{
				Calendar tempCal1 = calendar1.getCalendar();
				birthdateIn.setText((tempCal1.get(Calendar.MONTH) + 1) + "/" + tempCal1.get(Calendar.DATE) + "/" + tempCal1.get(Calendar.YEAR));
				calendarFrame.setVisible(false);
			}
			else if(action.equals(btnCalendar2.getActionCommand()))
			{
				calendarFrame = new JFrame("Calendar");
				JPanel temp = new JPanel();
				temp.add(calendar2);
				temp.add(btnConfirm2);
				calendarFrame.add(temp);
				calendarFrame.pack();
				calendarFrame.setVisible(true);
			}
			else if(action.equalsIgnoreCase(btnConfirm2.getActionCommand()))
			{
				Calendar tempCal1 = calendar2.getCalendar();
				expDateIn.setText((tempCal1.get(Calendar.MONTH) + 1) + "/" + tempCal1.get(Calendar.DATE) + "/" + tempCal1.get(Calendar.YEAR));
				calendarFrame.setVisible(false);
			}
			
		}
	}
	
	public void setMovie(MovieRecord movie, long recordNumber, int date, int time, int row, int col, int ticketType)
	{
		record = movie;
		recordNum = recordNumber;
		dateIndex = date;
		timeIndex = time;
		rowIndex = row;
		colIndex = col;
		ticket = ticketType;
	}
	
	public void setCards(JPanel masterCard)
	{
		cards = masterCard;
	}
}
