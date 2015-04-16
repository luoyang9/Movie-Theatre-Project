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
		frame = new JFrame("Movie Theatre");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cards = new JPanel(new CardLayout());
		searchPanel = new SearchPanel();
		moviePanel = new MovieInfoPanel();
		ticketPanel = new TicketPanel();
		adminPanel = new AdminPanel();
		allMoviesPanel = new ViewAllMoviesPanel();

		cards.add("1",searchPanel);
		cards.add("2", moviePanel);
		cards.add("3", ticketPanel);
		cards.add("4", adminPanel);
		cards.add("5", allMoviesPanel);
		searchPanel.setCards(cards);
		moviePanel.setCards(cards);
		ticketPanel.setCards(cards);
		adminPanel.setCards(cards);
		allMoviesPanel.setCards(cards);
		
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
