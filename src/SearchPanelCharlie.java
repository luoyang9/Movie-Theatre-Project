
/**
 * 
 * @author Charlie
 *
 **/

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SearchPanelCharlie extends JPanel
{
	//private
	private static JPanel cards;
	private static MovieInfoPanel movieInfoPanel;
	private static List<MovieRecord> movieList;
	
	//JButtons
	protected static JButton[] btnMovies;
	
	public SearchPanelCharlie() throws Exception
	{	
		//get all movies from file
		movieList = MovieFile.getAllRecords();
		
		//make buttons for all movies
		btnMovies = new JButton[(int) movieList.size()];
		
		ButtonHandler onClick = new ButtonHandler();
		for(int i = 0; i < movieList.size(); i++)
		{
			btnMovies[i] = new JButton(movieList.get(i).movieTitle); //set button title to movie title
			btnMovies[i].addActionListener(onClick);
			add(btnMovies[i]);
		}
		
	}
	
	public void setCardPanel(JPanel masterCards)
	{
		cards = masterCards;
	}
	
	public void setMoviePanel(MovieInfoPanel moviePanel)
	{
		movieInfoPanel = moviePanel;
	}
	
	public static class ButtonHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			CardLayout cl = (CardLayout)(cards.getLayout());
			
			for(int i = 0; i < btnMovies.length; i++)
			{
				if(command.equals(btnMovies[i].getActionCommand())) //check which button is clicked
				{
					movieInfoPanel.setMovie(movieList.get(i)); //set movie to the clicked movie
					cl.show(cards, "movie"); //show moviepanel
				} 
			}
		}
		
	}
}
