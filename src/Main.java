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
		MovieFile.initialize();
		frame = new JFrame("Movie Theatre");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cards = new JPanel(new CardLayout());
		searchPanel = new SearchPanel();
		moviePanel = new MovieInfoPanel();
		

		//initialize movie raf (open the file connection)
		

		//make up data for an example movie (will use a database later)
		int[] ironManTimes = {600, 1200, 300, 500};
		String[] ironManCast = {"Michael Bay", "Bill Cosby", "Matt Damon"};
		
		//new movies 
		MovieRecord movie = new MovieRecord("Iron Man", "It's a cool movie", ironManTimes, ironManCast, 1);
		MovieRecord movie2 = new MovieRecord("Iron Man 2", "the same movie but the sequel", ironManTimes, ironManCast, 2);
		MovieRecord movie3 = new MovieRecord("Iron Man 3", "its iron man but with more 3", ironManTimes, ironManCast, 3);
		
		//write to movieFile
		MovieFile.writeRecord(1, movie);
		MovieFile.writeRecord(2, movie2);
		MovieFile.writeRecord(3, movie3);
		
		cards.add("1",searchPanel);
		cards.add("2", moviePanel);
		
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
