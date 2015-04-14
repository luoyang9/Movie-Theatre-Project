import java.awt.BorderLayout;
import java.awt.CardLayout;
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
	
	protected static JButton btnBack;
	
	public TicketPanel()
	{
		setLayout(new BorderLayout());

		btnBack = new JButton("Cancel");
		
		seatPanel = new JPanel(new GridLayout(3, 4));
		moviePanel = new JPanel(new BorderLayout());
		seatBlocks = new seatBlock[3][4];
		movieTitle = new JLabel("", JLabel.CENTER);
		movieDateTime = new JLabel("", JLabel.CENTER);
		onClick = new ButtonHandler();
		
		btnBack.addActionListener(onClick);
		
		moviePanel.add(movieTitle, BorderLayout.PAGE_START);
		moviePanel.add(movieDateTime, BorderLayout.CENTER);
		
		add(moviePanel, BorderLayout.PAGE_START);
		add(seatPanel, BorderLayout.CENTER);
		add(btnBack, BorderLayout.PAGE_END);
	}
	
	public void setMovie(MovieRecord movie, String date, int dateIndex, String time)
	{
		this.movie = movie;
		
		seatPanel.removeAll();
		
		movieTitle.setText(movie.movieTitle);
		movieDateTime.setText(date + " ---- " + time);
		for(int i = 0; i < movie.seatplan.getSeats().length; i++) //loop thoruhg dates
		{
			//convert dates
			this.dateIndex = dateIndex;
			if(i == dateIndex) //find date we are on
			{
				for(int j = 0; j < movie.seatplan.getSeats()[i].length; j++)//loop through showtimes
				{
					//convert showtime
					String showTimeString = time;
					showTimeString = showTimeString.replaceAll(":", "");
					showTimeString = showTimeString.replaceAll("PM", "");

					if(Integer.toString(movie.showTimes[j]).equalsIgnoreCase(showTimeString)) //find showtime we are on
					{
						showTimeIndex = j;
						for(int k = 0; k < movie.seatplan.getSeats()[i][j].length; k++) //loop row
						{
							for(int l = 0; l < movie.seatplan.getSeats()[i][j][k].length; l++) //loop col
							{
								seatBlock seat = new seatBlock();
								if(movie.seatplan.getSeats()[i][j][k][l]) 
								{
									seat.setEnabled(false);
									seat.setText("Booked");
								}
								else
								{
									seat.setText("Open");
								}
								seat.setActionCommand(k + "" +  l);
								seatBlocks[k][l] = seat;
								seatPanel.add(seat);
							}
						}
					}
				}
			}
		}
		

		seatPanel.repaint();
		seatPanel.validate();
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
			CardLayout cl = (CardLayout)(cards.getLayout());
			if(e.getActionCommand().equals("Cancel"))
			{
				cl.show(cards, "2");
				SearchPanel sp = (SearchPanel) cards.getComponent(0);
				sp.updateFilm();
			}
			
			//booking seats
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
						log.v("Seat row " + (i + 1) + " and col " + (j + 1) + " booked for " + movie.movieTitle + " at time " + movie.showTimes[showTimeIndex] + "PM for date "  + (movie.releaseDate + dateIndex));
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
