import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


@SuppressWarnings("serial")
public class TicketPanel extends JPanel
{
	private MovieRecord movie;
	
	private JPanel seatPanel;
	private JPanel moviePanel;
	private JPanel ticketPanel;
	private JPanel seatInfoPanel;
	private JPanel cards;
	private JPanel container;
	
	private JLabel movieTitle;
	private JLabel movieDateTime;
	private JLabel lblSeats;
	private JLabel lblTickets;
	private JLabel lblScreen;
	private JLabel errorMessage;
	private JRadioButton childTicket;
	private JRadioButton adultTicket;
	private JRadioButton seniorTicket;
	private ButtonGroup typeGroup;
	private int showTimeIndex;
	private int dateIndex;
	private seatBlock[][] seatBlocks;
	
	private ButtonHandler onClick;
	
	protected static JButton btnBack;
	
	public TicketPanel()
	{
		setBackground(Value.GREY);

		btnBack = new JButton("Cancel ");
		btnBack.setAlignmentX(JButton.CENTER_ALIGNMENT);
		btnBack.setBackground(Value.RED);
		btnBack.setForeground(Color.black);
		btnBack.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		btnBack.setCursor (Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBack.setIcon(Value.getImage("back",15,15));
		
		
		container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		container.setSize(Value.WIDTH - 200, Value.HEIGHT);
		seatPanel = new JPanel(new GridLayout(3, 4));
		moviePanel = new JPanel(new GridLayout(2, 1));
		ticketPanel = new JPanel(new GridLayout(5, 1));
		seatInfoPanel = new JPanel(new GridLayout(3, 1));
		seatBlocks = new seatBlock[3][4];
		movieTitle = new JLabel("", JLabel.CENTER);
		movieTitle.setFont(new Font("Arial", Font.BOLD, 25));
		movieDateTime = new JLabel("", JLabel.CENTER);
		movieDateTime.setFont(new Font("Arial", Font.PLAIN, 20));
		lblSeats = new JLabel("Seats: ");
		lblSeats.setFont(new Font("Arial", Font.BOLD, 20));
		lblSeats.setHorizontalAlignment(JLabel.LEFT);
		lblTickets = new JLabel("Tickets: ", JLabel.LEFT);
		lblTickets.setFont(new Font("Arial", Font.BOLD, 20));
		errorMessage = new JLabel("");
		errorMessage.setFont(new Font("Arial", Font.PLAIN, 15));
		errorMessage.setForeground(Value.RED);
		lblScreen = new JLabel("");
		lblScreen.setIcon(Value.getImage("Screen",300,20));
		childTicket = new JRadioButton("Child - $" + Value.CHILD_PRICE + " (3-13)");
		adultTicket = new JRadioButton("Adult - $" + Value.ADULT_PRICE + " (14-64)");
		seniorTicket = new JRadioButton("Senior - $" + Value.SENIOR_PRICE + "(65+)");
		typeGroup = new ButtonGroup();
		typeGroup.add(childTicket);
		typeGroup.add(adultTicket);
		typeGroup.add(seniorTicket);
		
		
		onClick = new ButtonHandler();
		btnBack.addActionListener(onClick);
		
		ticketPanel.add(lblTickets);
		ticketPanel.add(childTicket);
		ticketPanel.add(adultTicket);
		ticketPanel.add(seniorTicket);
		ticketPanel.add(errorMessage);
		moviePanel.add(movieTitle);
		moviePanel.add(movieDateTime);
		seatInfoPanel.add(lblSeats);
		seatInfoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		seatInfoPanel.add(lblScreen);
		
		container.add(moviePanel);
		container.add(Box.createRigidArea(new Dimension(0, 20)));
		container.add(ticketPanel);
		container.add(Box.createRigidArea(new Dimension(0, 10)));
		container.add(seatInfoPanel);
		container.add(Box.createRigidArea(new Dimension(0, 10)));
		container.add(seatPanel);
		container.add(Box.createRigidArea(new Dimension(0, 20)));
		container.add(btnBack);
		
		add(container);
	}
	
	public void setMovie(MovieRecord movie, String date, int dateIndex, String time)
	{
		this.movie = movie;
		
		seatPanel.removeAll();
		
		movieTitle.setText(movie.movieTitle);
		movieDateTime.setText(date + " - " + time);
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
									seat.setBackground(Value.RED);
									seat.setText("BOOKED");
								}
								else
								{
									seat.setText("OPEN");
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
			setBackground(Value.BABY_BLUE);
			setBorder(BorderFactory.createLineBorder(Value.BLUE));
			setForeground(Color.white);
			setFont(new Font("Arial", Font.PLAIN, 15));
			setCursor (Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}

		
	}		
	
	public class ButtonHandler implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			CardLayout cl = (CardLayout)(cards.getLayout());
			if(e.getActionCommand().equals(btnBack.getActionCommand()))
			{
				errorMessage.setText("");
				cl.show(cards, Value.MOVIE);
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
						//get the record number
						long recordNum = 1;
						try {
							recordNum = MovieFile.getRecordNum(movieTitle.getText());
						} catch (IOException e2) {
							e2.printStackTrace();
						}
						
						int ticketType;
						if(childTicket.isSelected()) ticketType = Value.CHILD;
						else if(adultTicket.isSelected()) ticketType = Value.ADULT;
						else if(seniorTicket.isSelected()) ticketType = Value.SENIOR;
						else 
						{
							log.e("ticket type not selected");
							errorMessage.setText("ERROR! Please select a ticket type.");
							return;
						}
						
						errorMessage.setText("");
						
						//proceed to billing panel
						cl.show(cards, Value.BILLING);
					    BillingPanel billingPanel = (BillingPanel) cards.getComponent(5);
					    billingPanel.setMovie(movie, recordNum, dateIndex, showTimeIndex, i, j, ticketType);
						
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
