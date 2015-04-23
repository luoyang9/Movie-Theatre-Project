/**
 * 
 * @author Charlie
 * 
 **/

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


@SuppressWarnings("serial")
public class AdminPanel extends JPanel
{
	private static JPanel cards;
	private static JButton viewAllMovies;
	private static JButton viewAllCustomers;
	private static JButton addMovie;
	private static JButton btnBack;
	
	private static JTextField passwordField;
	private static JButton okButton;
	public AdminPanel()
	{
		JPanel passwordPanel = new JPanel(new BorderLayout());
		okButton = new JButton("Login");
		passwordField = new JTextField();
		
		JPanel controlPanel = new JPanel(new GridLayout(1,4));
		this.setLayout(new CardLayout());
		viewAllMovies = new JButton("View All Movies");
		viewAllCustomers = new JButton("View All Customers");
		addMovie = new JButton("Add Movie");
		btnBack = new JButton("Back");
		
		ButtonHandler onClick = new ButtonHandler();
		viewAllMovies.addActionListener(onClick);
		viewAllCustomers.addActionListener(onClick);
		addMovie.addActionListener(onClick);
		btnBack.addActionListener(onClick);
		okButton.addActionListener(onClick);
		
		
		passwordPanel.add(passwordField,BorderLayout.CENTER);
		passwordPanel.add(okButton, BorderLayout.PAGE_END);
		
		
		controlPanel.add(viewAllMovies);
		controlPanel.add(addMovie);
		controlPanel.add(viewAllCustomers);
		controlPanel.add(btnBack);
		
		add("1",controlPanel);
		add("2",passwordPanel);
		switchCard(this,2);
	}
	
	public void setCards(JPanel masterCard)
	{
		cards = masterCard;
	}
	
	private static void switchCard(JPanel panel, int card)
	{
		 CardLayout cardLayout = (CardLayout)panel.getLayout();
	     cardLayout.show(panel,""+card);
	}
	
	private static class ButtonHandler implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String command = e.getActionCommand();
			CardLayout cl = (CardLayout)cards.getLayout();
			
			if(command.equals("Login")){
				if(passwordField.getText().equals(Value.PASSWORD)){
					switchCard((JPanel)cards.getComponent(3),1);
				}
			}
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
				switchCard((JPanel)cards.getComponent(3),2);
				passwordField.setText("");
			}
		}
		
	}
	
}
