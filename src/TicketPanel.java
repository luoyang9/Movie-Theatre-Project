import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class TicketPanel extends JPanel
{
	private MovieRecord movie;
	
	private JPanel seatPanel;
	private JPanel moviePanel;
	private JPanel cards;
	
	private JLabel movieTitle;
	private JLabel movieDateTime;
	private int showTimeIndex;
	private seatBlock[][] seatBlocks;
	
	private ButtonHandler onClick;
	
	public TicketPanel()
	{
		setLayout(new BorderLayout());
		
		seatPanel = new JPanel(new GridLayout(3, 4));
		moviePanel = new JPanel(new BorderLayout());
		seatBlocks = new seatBlock[3][4];
		movieTitle = new JLabel("", JLabel.CENTER);
		movieDateTime = new JLabel("", JLabel.CENTER);
		onClick = new ButtonHandler();
		
		moviePanel.add(movieTitle, BorderLayout.PAGE_START);
		moviePanel.add(movieDateTime, BorderLayout.CENTER);
		
		add(moviePanel, BorderLayout.PAGE_START);
		add(seatPanel, BorderLayout.CENTER);
	}
	
	public void setMovie(MovieRecord movie, String date, String time)
	{
		this.movie = movie;
		
		movieTitle.setText(movie.movieTitle);
		movieDateTime.setText(date + " ---- " + time);
		for(int i = 0; i < movie.seats.length; i++)
		{
			String showTimeString = time;
			showTimeString = showTimeString.replaceAll(":", "");
			showTimeString = showTimeString.replaceAll("AM", "0");
			showTimeString = showTimeString.replaceAll("PM", "1");
		
			if(Integer.toString(movie.showTimes[i]).equalsIgnoreCase(showTimeString))
			{
				showTimeIndex = i;
				for(int j = 0; j < movie.seats[i].length; j++)
				{
					for(int k = 0; k < movie.seats[i][j].length; k++)
					{
						seatBlock seat = new seatBlock();
						if(movie.seats[i][j][k]) seat.setEnabled(false);
						seat.setActionCommand(j + "" +  k);
						seatBlocks[j][k] = seat;
						seatPanel.add(seat);
					}
				}
			}
		}
	}
	
	public class seatBlock extends JButton
	{
		public seatBlock()
		{
			addActionListener(onClick);
		}

		
	}		
	
	public class ButtonHandler implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			for(int i = 0; i < seatBlocks.length; i++)
			{
				for(int j = 0; j < seatBlocks[i].length; j++)
				{
					if(e.getActionCommand() == seatBlocks[i][j].getActionCommand())
					{
						long recordNum = 1;
						try {
							recordNum = MovieFile.getRecordNum(movieTitle.getText());
							System.out.println(recordNum);
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						movie.seats[showTimeIndex][i][j] = true;
						log.v("Seat row index " + i + " and col index " + j + " booked for record " + recordNum + " at show index " + showTimeIndex);
						try {
							MovieFile.writeRecord(recordNum, movie);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		}
		
	}
	
	public void setCards(JPanel cards)
	{
		this.cards = cards;
	}
	
}
