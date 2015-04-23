import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class CheckOutPanel extends JPanel
{
	private static JPanel cards;
	private static JLabel info;
	private static MovieRecord record;
	
	private static JButton btnConfirm;
	
	private static JPanel infoPanel;
	
	public CheckOutPanel()
	{
		setLayout(new BorderLayout());
		infoPanel = new JPanel();
		info = new JLabel();
		btnConfirm = new JButton("Back to Browsing");
		
		ButtonHandler onClick = new ButtonHandler();
		btnConfirm.addActionListener(onClick);
		
		infoPanel.add(info);
		
		add(btnConfirm, BorderLayout.PAGE_END);
		add(infoPanel, BorderLayout.CENTER);
	}
	
	private static class ButtonHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals(btnConfirm.getActionCommand()))
			{
				CardLayout cl = (CardLayout)cards.getLayout();
				cl.show(cards, Value.SEARCH);
			}
		}
	}	
	
	public void setInfo(CustomerRecord record)
	{
	
		String formatBirthDate = util.getMonth(record.birthday / 1000000) + " " + (record.birthday / 10000) % 100 + ", " + record.birthday % 10000;
		String formatExpDate = util.getMonth(record.expiryDate / 1000000) + " " + (record.expiryDate / 10000) % 100 + ", " + record.expiryDate % 10000;
		String showTimeString = Integer.toString(record.showTime);
		String formatShowTime = record.showTime / 100 + ":" + showTimeString.substring(showTimeString.length() - 1, showTimeString.length()) + "PM";
		info.setText("<html>Movie: " +record.movie + "<br>Time: " + formatShowTime + "<br>Date: " + record.date + "<br>Row: " + (record.seatRow + 1) + "<br>Col: " + (record.seatCol + 1) + "<br>Name: "
				 + record.name + "<br>Birthdate: " + formatBirthDate + "<br>Address: " + record.address + "<br>Phone Num: " + record.telephone + "<br>Credit Card: "
				 + record.creditCardNum + "<br> Exp Date: " + formatExpDate + "<br>Security Code: " + record.securityCode + "<br></html>");
	}

	
	public void setCards(JPanel masterCard)
	{
		cards = masterCard;
	}
}
