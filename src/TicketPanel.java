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
	private int dateIndex;
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
		for(int i = 0; i < movie.seatplan.getSeats().length; i++) //loop thoruhg dates
		{
			//convert dates
			dateIndex = Integer.parseInt(date) - movie.releaseDate;
			if(i == dateIndex) //find date we are on
			{
				for(int j = 0; j < movie.seatplan.getSeats()[i].length; j++)//loop through showtimes
				{
					//convert showtime
					String showTimeString = time;
					showTimeString = showTimeString.replaceAll(":", "");
					showTimeString = showTimeString.replaceAll("AM", "0");
					showTimeString = showTimeString.replaceAll("PM", "1");

					if(Integer.toString(movie.showTimes[j]).equalsIgnoreCase(showTimeString)) //find showtime we are on
					{
						showTimeIndex = j;
						for(int k = 0; k < movie.seatplan.getSeats()[i][j].length; k++) //loop row
						{
							for(int l = 0; l < movie.seatplan.getSeats()[i][j][k].length; l++) //loop col
							{
								seatBlock seat = new seatBlock();
								if(movie.seatplan.getSeats()[i][j][k][l]) seat.setEnabled(false);
								seat.setActionCommand(k + "" +  l);
								seatBlocks[k][l] = seat;
								seatPanel.add(seat);
							}
						}
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
						movie.seatplan.getSeats()[dateIndex][showTimeIndex][i][j] = true;
						log.v("Seat row index " + i + " and col index " + j + " booked for record " + recordNum + " at show index " + showTimeIndex + " for date index"  + dateIndex);
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
