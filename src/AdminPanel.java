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
	private static JButton btnBack2;
	
	private static JPasswordField passwordField;
	private static JButton okButton;
	
	private static JLabel passwordLabel;
	static String input = "";
	public AdminPanel()
	{
		JPanel passwordPanel = new JPanel(new BorderLayout());
		JPanel innerPasswordPanel = new JPanel(new GridLayout(5,1));
		innerPasswordPanel.setBackground(Color.WHITE);
		okButton = new JButton("Login");
		okButton.setBackground(Value.BLUE);
		okButton.setForeground(Color.white);
		okButton.setFont(Value.MEDIUM);
		passwordField = new JPasswordField(JTextField.CENTER);
		passwordField.setFont(Value.LARGE_BOLD);
		passwordField.setBackground(Value.GREY);
		passwordField.setForeground(Value.BLUE);
		passwordLabel = new JLabel("Administrator Log in",JLabel.CENTER);
		passwordLabel.setFont(Value.LARGE_BOLD);
		passwordLabel.setForeground(Value.BLUE);
		JPanel controlPanel = new JPanel(new GridLayout(1,4));
		this.setLayout(new CardLayout());
		viewAllMovies = new JButton("View All Movies");
		viewAllCustomers = new JButton("View All Customers");
		addMovie = new JButton("Add Movie");
		addMovie.setBackground(Value.BLUE);
		addMovie.setForeground(Color.WHITE);
		btnBack = new JButton("Back");
		btnBack.setBackground(Value.RED);
		btnBack.setForeground(Color.white);
		btnBack2 = new JButton("Back");
		btnBack2.setBackground(Value.RED);
		btnBack2.setForeground(Color.white);
		ButtonHandler onClick = new ButtonHandler();
		viewAllMovies.addActionListener(onClick);
		viewAllMovies.setBackground(Value.BLUE);
		viewAllMovies.setForeground(Color.WHITE);
		viewAllCustomers.addActionListener(onClick);
		viewAllCustomers.setBackground(Value.BLUE);
		viewAllCustomers.setForeground(Color.WHITE);
		addMovie.addActionListener(onClick);
		btnBack.addActionListener(onClick);
		btnBack2.addActionListener(onClick);
		okButton.addActionListener(onClick);
		
		innerPasswordPanel.add(new JLabel(""));
		innerPasswordPanel.add(passwordLabel);
		innerPasswordPanel.add(passwordField);
		innerPasswordPanel.add(okButton);
		innerPasswordPanel.add(new JLabel(""));
		passwordPanel.add(innerPasswordPanel,BorderLayout.CENTER);
		passwordPanel.add(btnBack2,BorderLayout.PAGE_END);

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
				if(String.valueOf(passwordField.getPassword()).equals(Value.PASSWORD)){
					switchCard((JPanel)cards.getComponent(3),1);
				}else{
					passwordField.setText("");
					passwordLabel.setForeground(Color.RED);
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
				ViewAllCustomersPanel allCustomersPanel = (ViewAllCustomersPanel) cards.getComponent(8);
				allCustomersPanel.refresh();
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
				passwordLabel.setForeground(Value.BLUE);
			}
		}
		
	}
	
}
