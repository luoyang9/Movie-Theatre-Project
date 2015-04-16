import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class BillingPanel extends JPanel
{
	private static MovieRecord record;
	private static int dateIndex, timeIndex, rowIndex, colIndex;
	private static long recordNum;
	
	private static JPanel cards;
	
	private static JLabel banner, name, bDay, address, phoneNum, credCard, expDate, secureCode, blank;
	private static JButton back, proceed;
	private static JTextField nameIn, bDayIn, addressIn, phoneNumIn, credCardIn, expDateIn, secureCodeIn;
	private static JPanel mainPanel, personal, credInfo, inputCVV, allInfo, button;
	
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
		
		back = new JButton("Cancel");
		proceed = new JButton("Proceed");
		
		nameIn = new JTextField("");
		bDayIn = new JTextField("");
		addressIn = new JTextField("");
		phoneNumIn = new JTextField("");
		credCardIn = new JTextField("");
		expDateIn = new JTextField("");
		secureCodeIn = new JTextField("");
		
		ButtonHandler onClick = new ButtonHandler();
		back.addActionListener(onClick);
		proceed.addActionListener(onClick);
		
		add(banner, BorderLayout.PAGE_START);
		
		personal.add(name);
		personal.add(nameIn);
		personal.add(bDay);
		personal.add(bDayIn);
		personal.add(address);
		personal.add(addressIn);
		personal.add(phoneNum);
		personal.add(phoneNumIn);
		allInfo.add(personal);
		
		credInfo.add(credCard);
		credInfo.add(credCardIn);
		credInfo.add(expDate);
		credInfo.add(expDateIn);
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
				CustomerRecord customer = new CustomerRecord(record.movieTitle, record.showTimes[timeIndex], record.releaseDate + dateIndex, rowIndex, colIndex, nameIn.getText(), Integer.parseInt(bDayIn.getText()), addressIn.getText(), Long.parseLong(phoneNumIn.getText()), Integer.parseInt(credCardIn.getText()), Integer.parseInt(expDateIn.getText()), Integer.parseInt(secureCodeIn.getText()));
				
				//book the seat
				record.seatplan.getSeats()[dateIndex][timeIndex][rowIndex][colIndex] = true;
				log.v("Seat row " + (rowIndex + 1) + " and col " + (colIndex + 1) + " booked for " + record.movieTitle + " at time " + record.showTimes[timeIndex] + "PM for date "  + (record.releaseDate + dateIndex));
				try {
					MovieFile.writeRecord(recordNum, record);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//proceed to check out panel
				cl.show(cards, "7");
				CheckOutPanel checkOut = (CheckOutPanel)cards.getComponent(6);
				checkOut.setInfo(customer);
			}
			else if(action.equals("Cancel"))
			{
				cl.show(cards, "3");
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
