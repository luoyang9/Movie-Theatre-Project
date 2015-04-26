import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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

    private static File file;
	private static FileWriter fw;
	private static BufferedWriter bw;
	private static PrintWriter pw;
	
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
	
	public void setInfo(CustomerRecord record) throws IOException
	{
	
		String formatBirthDate = util.getMonth(record.birthday / 1000000) + " " + (record.birthday / 10000) % 100 + ", " + record.birthday % 10000;
		String formatExpDate = util.getMonth(record.expiryDate / 1000000) + " " + (record.expiryDate / 10000) % 100 + ", " + record.expiryDate % 10000;
		String showTimeString = Integer.toString(record.showTime);
		String formatShowTime = record.showTime / 100 + ":" + showTimeString.substring(showTimeString.length() - 2, showTimeString.length()) + "PM";
		String formatDate = util.getMonth(record.date/100) + " " + record.date % 100;
		
		info.setText("<html>Movie: " +record.movie + "<br>Time: " + formatShowTime + "<br>Date: " + formatDate + "<br>Row: " + (record.seatRow + 1) + "<br>Col: " + (record.seatCol + 1) + "<br>Name: "
				 + record.name + "<br>Birthdate: " + formatBirthDate + "<br>Address: " + record.address + "<br>Phone Num: " + record.telephone + "<br>Credit Card: "
				 + record.creditCardNum + "<br> Exp Date: " + formatExpDate + "<br>Security Code: " + record.securityCode + "<br></html>");
	
		
		file = new File(Value.RECEIPT_PATH + record.name + record.movie + record.date + record.showTime + ".txt");
		if(!file.exists())
			file.createNewFile();
		fw = new FileWriter(file);
		bw = new BufferedWriter(fw);
		pw = new PrintWriter(bw);
		
		pw.println("Receipt for " + record.name);
		pw.println();
		pw.println("Movie: " + record.movie);
		pw.println("Date: " + formatDate);
		pw.println("Time: " + formatShowTime);
		pw.println("Row: " + (record.seatRow + 1) + "\tColumn: " + (record.seatCol + 1));
		pw.println("Name: " + record.name);
		pw.println("Birthdate: " + formatBirthDate);
		pw.println("Address: " + record.address);
		pw.println("Phone: " + record.telephone);
		pw.println("Credit Card #: " + record.creditCardNum);
		
		pw.close();
		bw.close();
		fw.close();
	}

	
	public void setCards(JPanel masterCard)
	{
		cards = masterCard;
	}
}
