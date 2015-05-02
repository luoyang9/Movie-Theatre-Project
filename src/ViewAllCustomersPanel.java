import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class ViewAllCustomersPanel extends JPanel
{
	private static JPanel cards;
	
	private static JScrollPane scroll;
	private static JPanel customerList;
	private static List<CustomerRecord> customers;
	private static JButton btnBack;
	
	private static ButtonHandler onClick;
	
	public ViewAllCustomersPanel()
	{
		setLayout(new BorderLayout());
		
		customerList = new JPanel();
		btnBack = new JButton("Back");
		scroll = new JScrollPane(customerList);
		
		onClick = new ButtonHandler();
		btnBack.addActionListener(onClick);
		
		add(scroll, BorderLayout.CENTER);
		add(btnBack, BorderLayout.PAGE_END);
		

	}
	
	public void refresh()
	{
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
		
		if(customerList.getComponentCount() == 0) customerList.add(new JLabel("No Customer Records Found"));
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
			else
			{
				for(int i = 0; i < customerList.getComponentCount(); i++)
				{
					if(e.getSource().equals(((customerBlock) customerList.getComponent(i)).getViewButton()))
					{
						CustomerRecord selCustomer = customers.get(i);
						File file = new File(Value.TICKET_PATH + selCustomer.name + selCustomer.movie + selCustomer.date + selCustomer.showTime + ".txt");
						if(Desktop.isDesktopSupported()) 
						{
						    try {
								Desktop.getDesktop().edit(file);
							} catch (IOException e1) {
								log.e("File Not Found");
							}
						} 
						else 
						{
							log.e("Desktop not supported");
						}
					}
				}
			}
		}
	}
	
	public void setCards(JPanel masterCards)
	{
		cards = masterCards;
	}
	
	private static class customerBlock extends JPanel
	{
		private JButton btnView;
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
			
			btnView = new JButton("View Ticket");
			btnView.addActionListener(onClick);;
			
			String formatBirthDate = util.getMonth(record.birthday / 1000000) + " " + (record.birthday / 10000) % 100 + ", " + record.birthday % 10000;
			String formatExpDate = util.getMonth(record.expiryDate / 1000000) + " " + (record.expiryDate / 10000) % 100 + ", " + record.expiryDate % 10000;
			String showTimeString = Integer.toString(record.showTime);
			String formatShowTime = record.showTime / 100 + ":" + showTimeString.substring(showTimeString.length() - 1, showTimeString.length()) + "PM";
		
			movieInfo = new JLabel("When: " + record.date + " at " + formatShowTime + ". Seat: R-" + record.seatRow + " C-" + record.seatCol);
			customerInfo = new JLabel("Name: " + record.name + " Birthday: " + formatBirthDate + " Address: " + record.address + " Phone: " + record.telephone);
			customerCard = new JLabel("Credit Card Num: " + record.creditCardNum + " Exp: " + formatExpDate + "Security Code: " + record.securityCode);
			
			info = new JPanel(new GridLayout(3, 1));
			
			info.add(movieInfo);
			info.add(customerInfo);
			info.add(customerCard);
			add(movie, BorderLayout.PAGE_START);
			add(info, BorderLayout.CENTER);
			add(btnView, BorderLayout.PAGE_END);
		
		}
		
		public JButton getViewButton()
		{
			return btnView;
		}
		
	}
	
}
