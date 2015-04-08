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
	private JPanel cards;
	
	private JLabel movieTitle;
	private JLabel movieDateTime;
	private seatBlock[][] seatBlocks;
	
	private ButtonHandler onClick;
	
	public TicketPanel()
	{
		seatPanel = new JPanel(new GridLayout(3, 4));
		seatBlocks = new seatBlock[3][4];
		movieTitle = new JLabel();
		movieDateTime = new JLabel();
		onClick = new ButtonHandler();
		add(movieTitle);
		add(movieDateTime);
		add(seatPanel);
	}
	
	public void setMovie(MovieRecord movie, String date, String time)
	{
		this.movie = movie;
		
		movieTitle.setText(movie.movieTitle);
		movieDateTime.setText(date + " ---- " + time);
		for(int i = 0; i < movie.seats.length; i++)
		{
			for(int j = 0; j < movie.seats[i].length; j++)
			{
				seatBlock seat = new seatBlock();
				if(movie.seats[i][j]) seat.setEnabled(false);
				seat.setActionCommand(i + "" +  j);
				seatBlocks[i][j] = seat;
				seatPanel.add(seat);
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
						movie.seats[i][j] = true;
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
