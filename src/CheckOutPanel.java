import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class CheckOutPanel extends JPanel
{
	private static JPanel cards;
	private static JPanel container;
	private static JLabel info;
	private static JLabel title;
	
	private static JButton btnConfirm;
	private static JButton btnViewTicket;
	
	private static JPanel infoPanel;

    private static File file;
	private static FileWriter fw;
	private static BufferedWriter bw;
	private static PrintWriter pw;
	
	public CheckOutPanel()
	{
		
		container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
		info = new JLabel();
		info.setHorizontalAlignment(JLabel.LEFT);
		title = new JLabel("You have successfully booked your seat. Please print your ticket.");
		title.setHorizontalAlignment(JLabel.LEFT);
		btnConfirm = new JButton("Back to Browsing");
		btnConfirm.setBackground(Value.RED);
		btnConfirm.setForeground(Color.black);
		btnConfirm.setAlignmentX(JButton.LEFT_ALIGNMENT);
		btnViewTicket = new JButton("View Ticket");
		btnViewTicket.setAlignmentX(JButton.LEFT_ALIGNMENT);
		btnViewTicket.setBackground(Value.BABY_BLUE);
		btnViewTicket.setForeground(Color.WHITE);
		
		ButtonHandler onClick = new ButtonHandler();
		btnConfirm.addActionListener(onClick);
		btnViewTicket.addActionListener(onClick);
		
		infoPanel.add(title);
		infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		infoPanel.add(info);
		infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		infoPanel.add(btnViewTicket);

		container.add(infoPanel);
		container.add(Box.createRigidArea(new Dimension(0, 20)));
		container.add(btnConfirm);
		
		add(container);
	}
	
	private static class ButtonHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals(btnConfirm.getActionCommand()))
			{
				CardLayout cl = (CardLayout)cards.getLayout();
				cl.show(cards, Value.SEARCH);
				SearchPanel searchPanel = (SearchPanel)cards.getComponent(0);
				searchPanel.updateFilm();
			}
			else if(e.getActionCommand().equals(btnViewTicket.getActionCommand()))
			{
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
	
	public void setInfo(CustomerRecord record) throws IOException
	{
	
		//formatted values
		String formatBirthDate = util.getMonth(record.birthday / 1000000) + " " + (record.birthday / 10000) % 100 + ", " + record.birthday % 10000;
		String formatExpDate = util.getMonth(record.expiryDate / 1000000) + " " + (record.expiryDate / 10000) % 100 + ", " + record.expiryDate % 10000;
		String showTimeString = Integer.toString(record.showTime);
		String formatShowTime = record.showTime / 100 + ":" + showTimeString.substring(showTimeString.length() - 2, showTimeString.length()) + "PM";
		String formatDate = util.getMonth(record.date/100) + " " + record.date % 100;
		
		//set info
		info.setText("<html>Movie: " +record.movie + "<br>Time: " + formatShowTime + "<br>Date: " + formatDate + ""
				+ "<br>Row: " + (record.seatRow + 1) + "<br>Col: " + (record.seatCol + 1) + "<br>Name: "
				 + record.name + "<br>Birthdate: " + formatBirthDate + "<br>Address: " + record.address + ""
				 + "<br>Phone Num: " + record.telephone + "<br>Credit Card: "
				 + record.creditCardNum + "<br> Exp Date: " + formatExpDate + "<br>Security Code: " + record.securityCode + "<br></html>");
	
		//printwriter
		file = new File(Value.TICKET_PATH + record.name + record.movie + record.date + record.showTime + ".txt");
		if(!file.exists())
			file.createNewFile();
		fw = new FileWriter(file);
		bw = new BufferedWriter(fw);
		pw = new PrintWriter(bw);
		
		//print ticket to txt
		pw.println("Ticket for " + record.name);
		pw.println();
		pw.println("Movie: " + record.movie);
		pw.println("Date: " + formatDate);
		pw.println("Time: " + formatShowTime);
		pw.println("Row: " + (record.seatRow + 1) + "\tColumn: " + (record.seatCol + 1));
		pw.println("Ticket: " + util.getTicketType(record.ticketType));
		pw.println("Price: " + util.getTicketPrice(record.ticketType));
		pw.println();
		pw.println("Show this ticket to gain entrance to the movie.");
		
		//close
		pw.close();
		bw.close();
		fw.close();
	}

	
	public void setCards(JPanel masterCard)
	{
		cards = masterCard;
	}
}
