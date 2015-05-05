import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class AddMoviePanel extends JPanel
{
	private static JPanel cards;
	
	protected JLabel lblTitle;
	protected JLabel lblSummary;
	protected JLabel lblReleaseDate;
	protected JLabel lblFinalDate;
	protected JLabel[] lblShowTimes;
	protected JLabel[] lblCast;
	protected JLabel lblImage;
	
	protected static JButton btnAdd;
	protected static JButton btnBack;
	
	protected static JTextField txtTitle;
	protected static JTextField txtSummary;
	protected static JTextField txtReleaseDate;
	protected static JTextField txtFinalDate;
	protected static JTextField[] txtShowTimes;
	protected static JTextField[] txtCast;
	protected static JTextField txtImage;
	
	public AddMoviePanel()
	{
		setLayout(new BorderLayout());
		
		JPanel moviePanel = new JPanel(new GridLayout(12, 2));
		
		lblTitle = new JLabel("Title: ");
		lblTitle.setFont(Value.SMALL_MEDIUM);
		lblTitle.setForeground(Value.BLUE);
		txtTitle = new JTextField();
		lblSummary = new JLabel("Summary: ");
		lblSummary.setFont(Value.SMALL_MEDIUM);
		lblSummary.setForeground(Value.BLUE);
		txtSummary = new JTextField();
		lblReleaseDate = new JLabel("Release Date: ");
		lblReleaseDate.setFont(Value.SMALL_MEDIUM);
		lblReleaseDate.setForeground(Value.BLUE);
		txtReleaseDate = new JTextField();
		lblFinalDate = new JLabel("FinalDate: ");
		lblFinalDate.setFont(Value.SMALL_MEDIUM);
		lblFinalDate.setForeground(Value.BLUE);
		txtFinalDate = new JTextField();
		lblImage = new JLabel("Image File: ");
		lblImage.setFont(Value.SMALL_MEDIUM);
		lblImage.setForeground(Value.BLUE);
		txtImage = new JTextField();
		
		btnAdd = new JButton("Add");
		btnAdd.setBackground(Value.BLUE);
		btnAdd.setForeground(Color.white);
		btnBack = new JButton("Back");
		btnBack.setBackground(Value.RED);
		btnBack.setForeground(Color.white);
		lblShowTimes = new JLabel[4];
		txtShowTimes = new JTextField[4];
		lblCast = new JLabel[3];
		txtCast = new JTextField[3];
		for(int i = 0; i < lblShowTimes.length; i++)
		{
			lblShowTimes[i] = new JLabel("Show Time " + (i + 1));
			lblShowTimes[i].setFont(Value.SMALL_MEDIUM);
			lblShowTimes[i].setForeground(Value.BLUE);
			txtShowTimes[i] = new JTextField();
		}
		
		for(int i = 0; i < lblCast.length; i++)
		{
			lblCast[i] = new JLabel("Actor " + (i + 1));
			lblCast[i] .setFont(Value.SMALL_MEDIUM);
			lblCast[i] .setForeground(Value.BLUE);
			txtCast[i] = new JTextField();
		}
		
		moviePanel.add(lblTitle);
		moviePanel.add(txtTitle);
		moviePanel.add(lblSummary);
		moviePanel.add(txtSummary);
		moviePanel.add(lblReleaseDate);
		moviePanel.add(txtReleaseDate);
		moviePanel.add(lblFinalDate);
		moviePanel.add(txtFinalDate);
		for(int i = 0; i < lblShowTimes.length; i++)
		{
			moviePanel.add(lblShowTimes[i]);
			moviePanel.add(txtShowTimes[i]);
		}
		for(int i = 0; i < lblCast.length; i++)
		{
			moviePanel.add(lblCast[i]);
			moviePanel.add(txtCast[i]);
		}
		moviePanel.add(lblImage);
		moviePanel.add(txtImage);
		
		ButtonHandler onClick = new ButtonHandler();
		btnAdd.addActionListener(onClick);
		btnBack.addActionListener(onClick);
		
		add(moviePanel, BorderLayout.CENTER);
		add(btnAdd, BorderLayout.PAGE_START);
		add(btnBack, BorderLayout.PAGE_END);
	}
	
	private static class ButtonHandler implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Add"))
			{
				try {
			        File file =new File("movies.csv");
			    	if(!file.exists())
			    		file.createNewFile();
					FileWriter fw = new FileWriter(file, true);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter pw = new PrintWriter(bw);
					pw.println();
					pw.print(txtTitle.getText() + ";" + txtSummary.getText() + ";" + txtReleaseDate.getText() + ";" +
					txtFinalDate.getText());
					for(int i = 0; i < txtShowTimes.length; i++)
					{
						pw.print(";" +txtShowTimes[i].getText());
					}
					for(int i = 0; i < txtCast.length; i++)
					{
						pw.print(";" + txtCast[i].getText());
					}
					pw.print(";" + txtImage.getText());
					pw.close();
					bw.close();
					fw.close();
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else if(e.getActionCommand().equals("Back"))
			{
				CardLayout cl = (CardLayout)cards.getLayout();
				cl.show(cards, Value.ADMIN);
			}
		}
		
	}
	
	public void setCards(JPanel masterCards)
	{
		cards = masterCards;
	}
}
