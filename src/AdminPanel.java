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
	private static JButton addMovie;
	private static JButton btnBack;
	
	public AdminPanel()
	{
		viewAllMovies = new JButton("View All Movies");
		viewAllCustomers = new JButton("View All Customers");
		addMovie = new JButton("Add Movie");
		btnBack = new JButton("Back");
		
		ButtonHandler onClick = new ButtonHandler();
		viewAllMovies.addActionListener(onClick);
		viewAllCustomers.addActionListener(onClick);
		addMovie.addActionListener(onClick);
		btnBack.addActionListener(onClick);
		
		add(viewAllMovies);
		add(addMovie);
		add(viewAllCustomers);
		add(btnBack);
	}
	
	public void setCards(JPanel masterCard)
	{
		cards = masterCard;
	}
	
	private static class ButtonHandler implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String command = e.getActionCommand();
			CardLayout cl = (CardLayout)cards.getLayout();
			
			if(command.equals(viewAllMovies.getActionCommand()))
			{
				cl.show(cards, Value.ALL_MOVIES);
			    ViewAllMoviesPanel allMoviesPanel = (ViewAllMoviesPanel) cards.getComponent(4);
			    try {
					allMoviesPanel.refreshMovies();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(command.equalsIgnoreCase(viewAllCustomers.getActionCommand()))
			{
				cl.show(cards, Value.ALL_CUSTOMERS);
			}
			else if(command.equals("Add Movie"))
			{
				cl.show(cards, Value.ADD_MOVIE);
			}
			else if(command.equals("Back"))
			{
				cl.show(cards, Value.SEARCH);
			}
		}
		
	}
	
}
