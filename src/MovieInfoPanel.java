/**
 * 
 * @author Charlie
 *
 **/

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
	protected static ArrayList<Integer> numberDates;
	
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
			btnShowTimes[i].setBackground(Value.BABY_BLUE);;
			btnShowTimes[i].setForeground(Color.WHITE);
			btnShowTimes[i].setBorder(BorderFactory.createLineBorder(Value.BLUE));
		}
		dates = new DefaultComboBoxModel<String>();
		showDate = new JComboBox<String>(dates);
		numberDates = new ArrayList<Integer>();
		
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
	public void setMovie(MovieRecord movieRecord, int day, int timeSlot){
		setMovie(movieRecord);
		for(int x = 0; x<numberDates.size();x++){
			if(numberDates.get(x) == day){
				showDate.setSelectedItem(showDate.getItemAt(day));
				break;
			}
		}
		
	}
	public void setMovie(MovieRecord movieRecord)
	{	
		movie = movieRecord;
		
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

		dates = new DefaultComboBoxModel<String>();
		int lastDayInMonth = util.getLastDay(movie.releaseDate/100); //the last day in the movie's release month
		int dateCount = 0; //counts the days if the movie's dates overlap two months
		int numDays = util.getNumDaysPlaying(movie.releaseDate, movie.finalDate);
		for(int i = 0; i < numDays; i++) //loop through the movie's dates
		{
			int currDate = movie.releaseDate + i;
			
			//if the current date is over the month's last date, then move on to the next month
			if((currDate) % 100 >= lastDayInMonth + 1)
			{
				dateCount++;
				currDate = ((currDate / 100) + 1) * 100 + dateCount;
			}
			
			String dateString = Integer.toString(currDate);
			String formattedDate = util.getMonth(Integer.parseInt(dateString.substring(0, 1))) + " " + dateString.substring(1, 3);
			
			numberDates.add(currDate);
			dates.addElement(formattedDate);
		}
		showDate.setModel(dates);
		
		for(int i = 0; i < btnShowTimes.length; i++)
		{
			String formattedShowTime;
			String stringShowTime = Integer.toString(movie.showTimes[i]);
			if(stringShowTime.length() == 3)
			{
				formattedShowTime = stringShowTime.substring(0, 1) + ":" + stringShowTime.substring(1, 3) + "PM";
			}
			else
			{
				formattedShowTime = stringShowTime.substring(0,  2) + ":" + stringShowTime.substring(2, 4) + "PM";
			}
			btnShowTimes[i].setText(formattedShowTime);
		}
		lblCast.setText("<html><b>Director:</b> " + movie.movieCast[0] + "&nbsp;&nbsp;&nbsp;<b>Producer</b>: " + movie.movieCast[1] + "&nbsp;&nbsp;&nbsp;<b>Featuring:</b> " + movie.movieCast[2] + "</html>");
		movieImageIcon = new ImageIcon(Value.getImage(movie.imageID).getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH));
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
				cl.show(cards, Value.SEARCH);
				SearchPanel sp = (SearchPanel) cards.getComponent(0);
				sp.searchMode(3);
				sp.updateFilm();
			}
			
			for(int i = 0; i < btnShowTimes.length; i++)
			{
				if(command.equals(btnShowTimes[i].getActionCommand()))
				{
					cl.show(cards, Value.TICKET);
					TicketPanel tp = (TicketPanel) cards.getComponent(2);
					tp.setMovie(movie, (String)showDate.getSelectedItem(), showDate.getSelectedIndex(), btnShowTimes[i].getText());
				}
			}
		}
	}
}
