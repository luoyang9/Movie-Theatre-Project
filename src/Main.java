import java.awt.*;

import javax.swing.*;


public class Main {
	//Frame
	public static JFrame frame;
	public static JPanel cards;
	public static MovieInfoPanel moviePanel;
	public static SearchPanel searchPanel;
	public static TicketPanel ticketPanel;
	public static AdminPanel adminPanel;
	public static ViewAllMoviesPanel allMoviesPanel;
	public static ViewAllCustomersPanel allCustomersPanel;
	public static BillingPanel billingPanel;
	public static CheckOutPanel checkOutPanel;
	public static AddMoviePanel addMoviePanel;
	//
	public static void main(String[] args) throws Exception 
	{
		javax.swing.SwingUtilities.invokeLater (new Runnable ( )
		{
		    public void run ( )
		    {
		    	try {
					guiApp();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		});
	}
	
	private static void guiApp() throws Exception
	{	
		//initialize movie raf (open the file connection)
		MovieFile.initialize();
		CustomerFile.initialize();
		
		frame = new JFrame("Movie Theatre");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cards = new JPanel(new CardLayout());
		searchPanel = new SearchPanel();
		moviePanel = new MovieInfoPanel();
		ticketPanel = new TicketPanel();
		adminPanel = new AdminPanel();
		allMoviesPanel = new ViewAllMoviesPanel();
		allCustomersPanel = new ViewAllCustomersPanel();
		billingPanel = new BillingPanel();
		checkOutPanel = new CheckOutPanel();
		addMoviePanel = new AddMoviePanel();
		
		cards.add(Value.SEARCH, searchPanel);
		cards.add(Value.MOVIE, moviePanel);
		cards.add(Value.TICKET, ticketPanel);
		cards.add(Value.ADMIN, adminPanel);
		cards.add(Value.ALL_MOVIES, allMoviesPanel);
		cards.add(Value.BILLING, billingPanel);
		cards.add(Value.CHECK_OUT, checkOutPanel);
		cards.add(Value.ADD_MOVIE, addMoviePanel);
		cards.add(Value.ALL_CUSTOMERS, allCustomersPanel);
		searchPanel.setCards(cards);
		moviePanel.setCards(cards);
		ticketPanel.setCards(cards);
		adminPanel.setCards(cards);
		allMoviesPanel.setCards(cards);
		billingPanel.setCards(cards);
		checkOutPanel.setCards(cards);
		addMoviePanel.setCards(cards);
		allCustomersPanel.setCards(cards);
		
		//Execute frame
		frame.add(cards);
		frame.pack();
		frame.setSize(800, 700);
		frame.setVisible(true);
	}
	public static void switchCard(int card){
		 CardLayout cardLayout = (CardLayout)cards.getLayout();
	     cardLayout.show(cards,""+card);
	}
}
