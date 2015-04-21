import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ViewAllCustomersPanel extends JPanel
{
	private static JPanel cards;
	
	private static JScrollPane scroll;
	private static JPanel customerList;
	private static List<CustomerRecord> customers;
	private static JButton btnBack;
	
	public ViewAllCustomersPanel()
	{
		setLayout(new BorderLayout());
		
		customerList = new JPanel();
		btnBack = new JButton("Back");
		scroll = new JScrollPane(customerList);
		
		ButtonHandler onClick = new ButtonHandler();
		btnBack.addActionListener(onClick);
		
		add(scroll, BorderLayout.CENTER);
		add(btnBack, BorderLayout.PAGE_END);
		

		try {
			customers = CustomerFile.getAllRecords();
		} catch (Exception e) {
			e.printStackTrace();
		}
		customerList.removeAll();
		customerList.setLayout(new GridLayout(customers.size(), 1));
		
		for(int i = 0; i < customers.size(); i++)
		{
			customerBlock block = new customerBlock(customers.get(i));
			customerList.add(block);
		}
	}
	
	private static class ButtonHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Back"))
			{
				CardLayout cl = (CardLayout)cards.getLayout();
				cl.show(cards, Value.ADMIN);
			}
		}
	}
	
	public void setCards(JPanel masterCards)
	{
		cards = masterCards;
	}
	
	private class customerBlock extends JPanel
	{
		private JLabel movie;
		private JLabel movieInfo;
		private JLabel customerInfo;
		private JLabel customerCard;
		private JPanel info;
		
		public customerBlock(CustomerRecord record)
		{
			setBorder(BorderFactory.createLineBorder(Color.black));
			setLayout(new BorderLayout());
			movie = new JLabel(record.movie);
			movieInfo = new JLabel("When: " + record.date + " at " + record.showTime + ". Seat: R-" + record.seatRow + " C-" + record.seatCol);
			customerInfo = new JLabel("Name: " + record.name + " Age: " + record.age + " Address: " + record.address + " Phone: " + record.telephone);
			customerCard = new JLabel("Credit Card Num: " + record.creditCardNum + " Exp: " + record.expiryDate + "Security Code: " + record.securityCode);
			
			info = new JPanel(new GridLayout(3, 1));
			
			info.add(movieInfo);
			info.add(customerInfo);
			info.add(customerCard);
			add(movie, BorderLayout.PAGE_START);
			add(info, BorderLayout.CENTER);
		}
	}
	
}
