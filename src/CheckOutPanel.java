import javax.swing.JLabel;
import javax.swing.JPanel;


public class CheckOutPanel extends JPanel
{
	private JPanel cards;
	private JLabel info;
	private MovieRecord record;
	
	private JPanel infoPanel;
	
	public CheckOutPanel()
	{
		infoPanel = new JPanel();
		info = new JLabel();
		
		infoPanel.add(info);
		
		add(infoPanel);
	}
	
	public void setInfo(CustomerRecord record)
	{
		info.setText("<html>Movie: " +record.movie + "<br>Time: " + record.showTime + "<br>Date: " + record.date + "<br>Row: " + (record.seatRow + 1) + "<br>Col: " + (record.seatCol + 1) + "<br>Name: "
				 + record.name + "<br>Birthdate: " + record.age + "<br>Address: " + record.address + "<br>Phone Num: " + record.telephone + "<br>Credit Card: "
				 + record.creditCardNum + "<br> Exp Date: " + record.expiryDate + "<br>Security Code: " + record.securityCode + "<br></html>");
	}

	
	public void setCards(JPanel masterCard)
	{
		cards = masterCard;
	}
}
