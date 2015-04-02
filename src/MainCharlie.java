
/**
 * 
 * @author Charlie
 * 
 */

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainCharlie 
{
	public static JPanel cards;
	
	public static void guiApp() throws Exception
	{
		JFrame frame = new JFrame();
		
		//initialize movie raf (open the file connection)
		MovieFile.initialize();
		
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
		
		//movie panel
		MovieInfoPanel moviePanel = new MovieInfoPanel();

		//searchpanel
		SearchPanelCharlie main = new SearchPanelCharlie();
		main.setMoviePanel(moviePanel);
		
		//cards panel
		cards = new JPanel(new CardLayout());
		cards.add(main, "main");
		cards.add(moviePanel, "movie");
		moviePanel.setCards(cards);
		main.setCardPanel(cards);
		
		frame.add(cards);
		frame.setSize(700,  700);
		frame.setVisible(true);
	}
	
	//had to comment out the main method below as it conflicted with your main
	
	/* 
	public static void main (String[ ] args) 
    {
	javax.swing.SwingUtilities.invokeLater (new Runnable ( )
	{
	    public void run ( )
	    {
	    	try {
				guiApp ( );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	);
    } // end of main method
    */
}
