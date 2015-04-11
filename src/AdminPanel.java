/**
 * 
 * @author Charlie
 * 
 **/

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class AdminPanel extends JPanel
{
	private static JPanel cards;
	private static JButton viewAllMovies;
	private static JButton viewAllCustomers;
	
	public AdminPanel()
	{
		viewAllMovies = new JButton("View All Movies");
		viewAllCustomers = new JButton("View All Customers");
		
		add(viewAllMovies);
		add(viewAllCustomers);
	}
	
	public static void setCards(JPanel masterCard)
	{
		cards = masterCard;
	}
	
	private static class ButtonHandler implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String command = e.getActionCommand();
			
			if(command.equals(viewAllMovies.getActionCommand()))
			{
				CardLayout cl = (CardLayout)cards.getLayout();
				//cl.show(cards, "4");
			}
		}
		
	}
	
}
