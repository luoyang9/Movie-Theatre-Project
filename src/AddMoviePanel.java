import java.awt.BorderLayout;
import java.awt.CardLayout;
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
		txtTitle = new JTextField();
		lblSummary = new JLabel("Summary: ");
		txtSummary = new JTextField();
		lblReleaseDate = new JLabel("Release Date: ");
		txtReleaseDate = new JTextField();
		lblFinalDate = new JLabel("FinalDate: ");
		txtFinalDate = new JTextField();
		lblImage = new JLabel("Image File: ");
		txtImage = new JTextField();
		
		btnAdd = new JButton("Add");
		btnBack = new JButton("Cancel");
		
		lblShowTimes = new JLabel[4];
		txtShowTimes = new JTextField[4];
		lblCast = new JLabel[3];
		txtCast = new JTextField[3];
		for(int i = 0; i < lblShowTimes.length; i++)
		{
			lblShowTimes[i] = new JLabel("Show Time " + (i + 1));
			txtShowTimes[i] = new JTextField();
		}
		
		for(int i = 0; i < lblCast.length; i++)
		{
			lblCast[i] = new JLabel("Actor " + (i + 1));
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
		
		add(moviePanel, BorderLayout.PAGE_START);
		add(btnAdd, BorderLayout.CENTER);
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
			else if(e.getActionCommand().equals("Cancel"))
			{
				CardLayout cl = (CardLayout)cards.getLayout();
				cl.show(cards, "4");
			}
		}
		
	}
	
	public void setCards(JPanel masterCards)
	{
		cards = masterCards;
	}
}
