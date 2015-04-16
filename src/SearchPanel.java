
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class SearchPanel extends JPanel{
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	
	private static JPanel cards;
	
	//Panels inside
	protected JPanel searchPnl;
	protected JPanel byDatePnl; //Cardlayout for search types
	protected JPanel byDateContent;
	protected JPanel byTitlePnl;
	protected JPanel filmsPnl; //Displays results and shows featured films
	protected JPanel centerPnl; //Contains filmsPnl and the picture
	protected JLabel searchType;
	protected JPanel leftPanel;
	protected JFrame allFrame;
	//Buttons
	private static JButton byDateBtn;
	private static JButton byTitleBtn;
	private static JButton viewAll;
	private JButton optionsBtn;
	private JButton managementBtn;
	private JScrollPane scroll;
	private static JTextField searchBox; //Search by title
	private static JComboBox searchBox2; //Search by date
	private static JSlider timeSlide;
	//Labels
	private static JLabel time;
	protected static movieBlock[] mBlocks;
	final static int SLIDER_INTERVAL = 4;
	final static int SEARCH_DAYS = 7;
	static int lastValue = -1;
	static boolean searchByTime;
	static String[] dates;
	static int[] datesInt;
	public SearchPanel(){
		try {
			List<MovieRecord>records = MovieFile.getAllRecords();
			mBlocks = new movieBlock[records.size()];
			for(int x = 0;x<records.size();x++){
				mBlocks[x] = new movieBlock(records.get(x));
				log.v("Record " + (x + 1) + " loaded" );
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		dates = new String[SEARCH_DAYS];
		datesInt = new int[SEARCH_DAYS];
		Calendar c = Calendar.getInstance(); 
		c.setTime(new Date()); 
	
		for(int x = 0;x<SEARCH_DAYS;x++){	
			Date dt = c.getTime();
			datesInt[x] = Integer.parseInt(String.format("%d%d",dt.getMonth(),dt.getDay()));
			c.add(Calendar.DATE, 1);
		}
		
		
		searchBox2 = new JComboBox<String>(new String[]{"Today" , "Tomorrow", "Day after that", "Day after that"});
		
		setLayout(new BorderLayout());
		byDatePnl = new JPanel(new CardLayout());
		byTitlePnl = new JPanel(new CardLayout());
		centerPnl = new JPanel(new BorderLayout());
		filmsPnl = new JPanel(new GridLayout(1,10));
		searchPnl = new JPanel(new BorderLayout());
		searchBox = new JTextField();
		byDateBtn = new JButton("Search By Date");
		byTitleBtn = new JButton("Search By Title");
		searchType = new JLabel("Featured");
		byDateContent = new JPanel(new BorderLayout());
		leftPanel = new JPanel(new BorderLayout());
		viewAll = new JButton("All Movies");
		time = new JLabel("Choose a time", JLabel.CENTER);
		timeSlide = new JSlider(0,12*SLIDER_INTERVAL,12/2*SLIDER_INTERVAL);
		byDateContent.add(timeSlide, BorderLayout.CENTER);
		byDateContent.add(time, BorderLayout.PAGE_START);
		byDateContent.add(searchBox2,BorderLayout.LINE_START);
		byDatePnl.add("1",byDateBtn);
		byDatePnl.add("2",byDateContent);
		byTitlePnl.add("1",byTitleBtn);
		byTitlePnl.add("2",searchBox);
		timeSlide.addChangeListener(new SliderListener());
		
		
		//add action listeners
		ButtonHandler onClick = new ButtonHandler();
		byDateBtn.addActionListener(onClick);
		byTitleBtn.addActionListener(onClick);
		viewAll.addActionListener(onClick);
		//Adding document Listener for search box
		SearchHandler onSearch = new SearchHandler();
		searchBox.getDocument().addDocumentListener(onSearch);
		
		//
		scroll = new JScrollPane(filmsPnl);
		//Add to panel
		leftPanel.add(searchType, BorderLayout.CENTER);
		leftPanel.add(viewAll,BorderLayout.PAGE_END);
		centerPnl.add(leftPanel,BorderLayout.LINE_START);
		centerPnl.add(scroll,BorderLayout.CENTER);
		searchPnl.add(byDatePnl,BorderLayout.LINE_START);
		searchPnl.add(byTitlePnl,BorderLayout.CENTER);
		
		add(searchPnl,BorderLayout.PAGE_START);
		add(centerPnl,BorderLayout.CENTER);
		
		updateFilm();
	}
	public void updateFilm(int time){
		searchByTime = true;
		filmsPnl.removeAll();
		searchType.setText("Results");
		int timeMin = (time*60/SLIDER_INTERVAL);
		int compareTime;
		
		String displayTime;
		boolean am = false;
		if(timeMin == 720) am = true;
		if(timeMin>=60){
			displayTime = ""+((int)timeMin/60)+":"+ ((timeMin%60 ==0)?"00":timeMin%60);
			compareTime = Integer.parseInt(""+((int)timeMin/60)+((timeMin%60 ==0)?"00":timeMin%60));
		}else{
			displayTime = "12:" + ((timeMin ==0)?"00":timeMin);
			compareTime = Integer.parseInt("12" + ((timeMin ==0)?"00":timeMin));
		}
		log.v("Searching for time " + compareTime);
		this.time.setText(displayTime + ((am)?"AM":"PM"));
		loop:
		for(movieBlock i:mBlocks){
			for(int x:i.getRecord().showTimes){
				if(x == compareTime){
					filmsPnl.add(i);
					continue loop;
				}
			}
		}
		
		filmsPnl.repaint();
		scroll.repaint();
		filmsPnl.validate();
		scroll.validate();
	}
	public void updateFilm()
	{
		searchByTime = false;
		String query = searchBox.getText();
		filmsPnl.removeAll();
		
		if(query.equals(""))
		{
			searchType.setText("Featured");
			for(int i = 0; i < mBlocks.length; i++)
			{
				if(i < 5)
				{
					filmsPnl.add(mBlocks[i]);
				}
			}
		}
		else
		{
			searchType.setText("Results:");
			for(int x = 0;x<mBlocks.length;x++)
			{
				if(mBlocks[x].getRecord().movieTitle.toLowerCase().contains(query.toLowerCase()))
				{
					filmsPnl.add(mBlocks[x]);
				}
			}
			log.v("Search Query: " + query);
			
		}
		filmsPnl.repaint();
		scroll.repaint();
		filmsPnl.validate();
		scroll.validate();
	}
	private static void switchCard(JPanel panel, int card)
	{
		 CardLayout cardLayout = (CardLayout)panel.getLayout();
	     cardLayout.show(panel,""+card);
	}
	private void searchMode(int mode)
	{
		if(mode ==1){
			switchCard(byDatePnl,2);
			switchCard(byTitlePnl,1);
			searchPnl.add(byDatePnl,BorderLayout.CENTER);
			searchPnl.add(byTitlePnl,BorderLayout.LINE_END);
			
		}else{
			switchCard(byDatePnl,1);
			switchCard(byTitlePnl,2);
			searchPnl.add(byDatePnl,BorderLayout.LINE_START);
			searchPnl.add(byTitlePnl,BorderLayout.CENTER);
		}
	}
	
	private class SearchHandler implements DocumentListener
	{

		public void changedUpdate(DocumentEvent e){}
		public void removeUpdate(DocumentEvent e) 
		{
		  updateFilm();
		}
		public void insertUpdate(DocumentEvent e) 
		{
		  updateFilm();
		  
		}
	}
	private class SliderListener implements ChangeListener {
		
	    public void stateChanged(ChangeEvent e) {
	        JSlider source = (JSlider)e.getSource();
	            int value = source.getValue();
	            if(value!=lastValue){
	            	updateFilm(value);
	            	lastValue = value;
	            }
	            
	            
	            
	    }
	}
	private class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object command = e.getSource();

			if(command == byDateBtn)
				searchMode(1);
			else if(command == byTitleBtn)
				searchMode(2);
			else if(command == viewAll){
				allFrame = new JFrame("All Movies");
				allFrame.addWindowListener(new WindowAdapter(){
	                public void windowClosing(WindowEvent e){
	                	Main.frame.setVisible(true);
	                	updateFilm();
	                    
	                }
	            });
				Container c = allFrame.getContentPane();
				c.setLayout(new GridLayout((int)Math.ceil(Math.sqrt(mBlocks.length)),(int)Math.ceil(mBlocks.length/Math.sqrt(mBlocks.length))));
				for(int x = 0;x<mBlocks.length;x++)
					allFrame.add(mBlocks[x]);
				allFrame.pack();
				allFrame.setVisible(true);
				Main.frame.setVisible(false);
		}
		}

		
	}
	public class movieBlock extends JButton{
		private JLabel image;
		protected MovieRecord record;
		public movieBlock(MovieRecord e) {
			record = e;
			setLayout(new BorderLayout());
			image = new JLabel("",JLabel.CENTER);
			image.setIcon(new ImageIcon(new ImageIcon(Main.class.getResource(record.imageID + ".jpg")).getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH)));
			add(image, BorderLayout.CENTER);
			add(new JLabel(e.movieTitle,JLabel.CENTER),BorderLayout.PAGE_END);
			
			ButtonHandler onClick = new ButtonHandler();
			addActionListener(onClick);
		}

		public MovieRecord getRecord(){return record;}
		private class ButtonHandler implements ActionListener{
			public void actionPerformed(ActionEvent arg0) {
				 CardLayout cardLayout = (CardLayout)cards.getLayout();
			     cardLayout.show(cards,"2");
			     MovieInfoPanel moviePanel = (MovieInfoPanel) cards.getComponent(1);
			     if(searchByTime){
			    	 
			     }
			     moviePanel.setMovie(record);
			     if(allFrame != null){
			    	 allFrame.dispose();
			    	 Main.frame.setVisible(true);
			     }
			}
			
		}
	}
	
	public void setCards(JPanel masterCards)
	{
		cards = masterCards;
	}
	
}
