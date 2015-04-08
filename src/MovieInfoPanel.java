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
public class MovieInfoPanel extends JPanel
{
	//cards
	private static JPanel cards;
	
	//movie
	private static MovieRecord movie;
	
	//JPanels
	private static JPanel showTimesPanel;
	private static JPanel selectTimePanel;
	private static JPanel infoPanel;
	
	//JLabels
	protected static JLabel lblTitle;
	protected static JTextArea lblSummary;
	protected static JLabel lblCast;
	protected static JLabel movieImage;
	
	//JComboBox
	protected static JComboBox<String> showDate;
	protected static DefaultComboBoxModel<String> dates;
	protected static JList<String> dateList;
	
	//Image
	protected static ImageIcon movieImageIcon;
	
	//JButton
	protected static JButton[] btnShowTimes;
	protected static JButton btnBack;
	
	public MovieInfoPanel()
	{
		setLayout(new BorderLayout());
		
		//initialize
		lblTitle = new JLabel();
		lblSummary = new JTextArea();
		lblCast = new JLabel();
		movieImage = new JLabel();
		btnShowTimes = new JButton[4];
		btnBack = new JButton("Back");
		for(int i = 0; i < btnShowTimes.length; i++)
		{
			btnShowTimes[i] = new JButton();
		}
		dates = new DefaultComboBoxModel<String>();
		dates.addElement("Today");
		dates.addElement("March 28, 2015");
		dates.addElement("March 29, 2015");
		dates.addElement("March 30, 2015");
		showDate = new JComboBox<String>(dates);
		
		//set label properties
		lblTitle.setHorizontalAlignment(JLabel.CENTER);
		lblTitle.setFont(new Font("Impact", Font.BOLD, 30));
		lblSummary.setLineWrap(true);
		lblSummary.setEditable(false);
		lblSummary.setBorder(BorderFactory.createLineBorder(Color.black));
		lblSummary.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblCast.setBorder(BorderFactory.createLineBorder(Color.black));
		lblCast.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		
		//create jpanels
		infoPanel = new JPanel(new BorderLayout());
		showTimesPanel = new JPanel(new GridLayout(2, 2));
		selectTimePanel = new JPanel(new GridLayout(2, 1));
	
		//create movie poster
		movieImageIcon = new ImageIcon();
		movieImage.setIcon(movieImageIcon);
		
		//add components
		infoPanel.add(movieImage, BorderLayout.LINE_START);
		infoPanel.add(lblTitle, BorderLayout.PAGE_START);
		infoPanel.add(lblSummary, BorderLayout.CENTER);
		infoPanel.add(lblCast, BorderLayout.PAGE_END);
		selectTimePanel.add(showDate);
		selectTimePanel.add(showTimesPanel);
		for(int i = 0; i < btnShowTimes.length; i++)
		{
			showTimesPanel.add(btnShowTimes[i]);
		}
		add(infoPanel, BorderLayout.PAGE_START);
		add(selectTimePanel, BorderLayout.CENTER);
		add(btnBack, BorderLayout.PAGE_END);
		
		//add button handler
		ButtonHandler onClick = new ButtonHandler();
		for(int i = 0; i < btnShowTimes.length; i++)
		{
			btnShowTimes[i].addActionListener(onClick);
			btnBack.addActionListener(onClick);
		}
	}
	
	public void setMovie(MovieRecord movie)
	{	
		this.movie = movie;
		
		
		lblTitle.setText(movie.movieTitle);
		lblSummary.setText(movie.movieSummary);
		
		String releaseDate = Integer.toString(movie.releaseDate);
		if(releaseDate.length() == 3)
		{
			releaseDate = releaseDate.substring(0, 1) + "/" + releaseDate.substring(1, 3);
		}
		else if(releaseDate.length() == 4)
		{
			releaseDate = releaseDate.substring(0, 2) + "/" + releaseDate.substring(2, 4);
		}
		String finalDate = Integer.toString(movie.finalDate);
		if(finalDate.length() == 3)
		{
			finalDate = finalDate.substring(0, 1) + "/" + finalDate.substring(1, 3);
		}
		else if(finalDate.length() == 4)
		{
			finalDate = finalDate.substring(0, 2) + "/" + finalDate.substring(2, 4);
		}
		
		lblSummary.append("\nReleased: " + releaseDate + " --- Final date: " + finalDate);
		for(int i = 0; i < btnShowTimes.length; i++)
		{
			String formattedShowTime;
			String stringShowTime = Integer.toString(movie.showTimes[i]);
			if(stringShowTime.length() <= 4)
			{
				String timePeriod = stringShowTime.substring(3, 4).equals("0") ? "AM" : "PM";
				formattedShowTime = stringShowTime.substring(0, 1) + ":" + stringShowTime.substring(1, 3) + timePeriod;
			}
			else
			{
				String timePeriod = stringShowTime.substring(4, 5).equals("0") ? "AM" : "PM";
				formattedShowTime = stringShowTime.substring(0,  2) + ":" + stringShowTime.substring(2, 4) + timePeriod;
			}
			btnShowTimes[i].setText(formattedShowTime);
		}
		lblCast.setText("<html><b>Director:</b> " + movie.movieCast[0] + "&nbsp;&nbsp;&nbsp;<b>Producer</b>: " + movie.movieCast[1] + "&nbsp;&nbsp;&nbsp;<b>Featuring:</b> " + movie.movieCast[2] + "</html>");
		movieImageIcon = new ImageIcon(new ImageIcon(Main.class.getResource(movie.imageID + ".jpg")).getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH));
		movieImage.setIcon(movieImageIcon);
	}
	
	public void setCards(JPanel masterCards)
	{
		cards = masterCards;
	}

	public static class ButtonHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			CardLayout cl = (CardLayout)(cards.getLayout());
			if(command.equals("Back"))
			{
				cl.show(cards, "1");
				SearchPanel sp = (SearchPanel) cards.getComponent(0);
				sp.updateFilm();
			}
			
			for(int i = 0; i < btnShowTimes.length; i++)
			{
				if(command.equals(btnShowTimes[i].getActionCommand()))
				{
					cl.show(cards, "3");
					TicketPanel tp = (TicketPanel) cards.getComponent(2);
					tp.setMovie(movie, (String)showDate.getSelectedItem(), btnShowTimes[i].getText());
				}
			}
		}
	}
}
