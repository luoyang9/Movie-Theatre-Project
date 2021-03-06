import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class ViewAllMoviesPanel extends JPanel
{
	private static JPanel cards;
	
	private static JScrollPane scroll;
	private static JPanel movieList;
	private static List<MovieRecord> allMovies;
	private static JButton btnBack;
	
	public ViewAllMoviesPanel()
	{
		setLayout(new BorderLayout());
		
		movieList = new JPanel();
		btnBack = new JButton("Back");
		btnBack.setBackground(Value.RED);
		btnBack.setForeground(Color.white);
		scroll = new JScrollPane(movieList);
		
		ButtonHandler onClick = new ButtonHandler();
		btnBack.addActionListener(onClick);
		
		add(scroll, BorderLayout.CENTER);
		add(btnBack, BorderLayout.PAGE_END);
	}
	
	private static class ButtonHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Back"))
			{
				CardLayout cl = (CardLayout)cards.getLayout();
				cl.show(cards, Value.ADMIN);
			}
		}
	}
	
	public void refreshMovies() throws Exception
	{
		allMovies = MovieFile.getAllRecords();
		movieList.removeAll();
		movieList.setLayout(new GridLayout(allMovies.size(), 1));
		
		for(int i = 0; i < allMovies.size(); i++)
		{
			movieBlock block = new movieBlock(allMovies.get(i));
			movieList.add(block);
		}
		movieList.repaint();
		scroll.repaint();
		movieList.validate();
		scroll.validate();
	}
	
	public void setCards(JPanel masterCards)
	{
		cards = masterCards;
	}
	
	private class movieBlock extends JPanel
	{
		private JLabel title;
		private JTextArea summary;
		private JLabel cast;
		private JLabel dateTime;
		private JLabel image;
		private JPanel bottom;
		
		public movieBlock(MovieRecord record)
		{
			setLayout(new BorderLayout());
			image = new JLabel();
			image.setIcon(Value.getImage(record.imageID,300, 400));
			title = new JLabel(record.movieTitle, JLabel.CENTER);
			title.setFont(Value.MEDIUM);
			title.setForeground(Value.BLUE);
			summary = new JTextArea(record.movieSummary);
			summary.setEditable(false);
			summary.setLineWrap(true);
			summary.setFont(Value.SMALL);
			summary.setForeground(Value.BLUE);
			cast = new JLabel(record.movieCast[0] + " " + record.movieCast[1] + " " + record.movieCast[2]);
			dateTime = new JLabel(record.releaseDate + " - " + record.finalDate + " Times: " + record.showTimes[0] +" - " + record.showTimes[1] + " - " + record.showTimes[2] + "-" + record.showTimes[3]);
			
			bottom = new JPanel(new BorderLayout());
			
			bottom.add(cast, BorderLayout.PAGE_START);
			bottom.add(dateTime, BorderLayout.CENTER);
			add(title, BorderLayout.PAGE_START);
			add(image, BorderLayout.LINE_START);
			add(summary, BorderLayout.CENTER);
			add(bottom, BorderLayout.PAGE_END);
		}
	}
	
}
