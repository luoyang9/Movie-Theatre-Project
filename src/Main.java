import java.awt.*;

import javax.swing.*;


public class Main {
	//Frame
	public static JFrame frame;
	public static JPanel cards;
	public static MovieInfoPanel moviePanel;
	public static SearchPanel searchPanel;
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

		cards.add("1",searchPanel);
		cards.add("2", moviePanel);
		searchPanel.setCards(cards);
		moviePanel.setCards(cards);
		
		//Execute frame
		frame.add(cards);
		frame.pack();
		frame.setVisible(true);
	}
	public static void switchCard(int card){
		 CardLayout cardLayout = (CardLayout)cards.getLayout();
	     cardLayout.show(cards,""+card);
	}
}
