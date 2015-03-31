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
	
	//movie info
	private String movieCast;
	
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
		lblTitle.setText(movie.movieTitle);
		lblSummary.setText(movie.movieSummary);
		for(int i = 0; i < btnShowTimes.length; i++)
		{
			btnShowTimes[i].setText(Integer.toString(movie.showTimes[i]));
		};
		movieCast = "";
		for(int i = 0; i < movie.movieCast.length; i++)
		{
			movieCast += movie.movieCast[i] + " ";
		}
		lblCast.setText(movieCast);
		movieImageIcon = new ImageIcon(Main.class.getResource(movie.imageID + ".jpg"));
		movieImage.setIcon(movieImageIcon);
	}
	
	public void setCardPanel(JPanel masterCards)
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
				cl.show(cards, "main");
			}
		}
	}
}
