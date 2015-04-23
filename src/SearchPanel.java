
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
	protected JPanel leftBottomPanel;
	protected JFrame allFrame;
	//Buttons
	private static JButton byDateBtn;
	private static JButton byTitleBtn;
	private static JButton viewAll;
	private static JButton adminBtn;
	private JButton optionsBtn;
	private JButton managementBtn;
	private JScrollPane scroll;
	private static JTextField searchBox; //Search by title
	private static JComboBox searchBox2; //Search by date
	private static JSlider timeSlide;
	//Labels
	private static JLabel time;
	protected static movieBlock[] mBlocks;
	
	static int lastValue = -1;
	static boolean searchByTime;
	static String[] dates;
	static int[] datesInt;
	
	protected int searchDay;
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
		dates = new String[Value.SEARCH_DAYS];
		datesInt = new int[Value.SEARCH_DAYS];
		Calendar c = Calendar.getInstance(); 
		for(int x = 0;x<Value.SEARCH_DAYS;x++){	
			Date dt = c.getTime();
			datesInt[x] = Integer.parseInt((dt.getMonth()+1) +""+ dt.getDate());
			dates[x] = String.format("%-10s %-7s %3d",((x<Value.DATE_WORDS.length)?Value.DATE_WORDS[x]:Value.WEEK_DAYS[dt.getDay()]), util.getMonth(dt.getMonth()+1),dt.getDate()); 
			
			c.add(Calendar.DATE, 1);
		}
		searchDay = 0;
		
		searchBox2 = new JComboBox<String>(dates);
		
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
		leftBottomPanel = new JPanel(new GridLayout(2, 1));
		viewAll = new JButton("All Movies");
		adminBtn = new JButton("Management");
		time = new JLabel("Choose a time", JLabel.CENTER);
		timeSlide = new JSlider(0,12*Value.SLIDER_INTERVAL+1,12/2*Value.SLIDER_INTERVAL);
		byDateContent.add(timeSlide, BorderLayout.CENTER);
		byDateContent.add(time, BorderLayout.PAGE_START);
		byDateContent.add(searchBox2,BorderLayout.LINE_START);
		byDatePnl.add("1",byDateBtn);
		byDatePnl.add("2",byDateContent);
		byTitlePnl.add("1",byTitleBtn);
		byTitlePnl.add("2",searchBox);
		timeSlide.addChangeListener(new timeListener());
		searchBox2.addActionListener(new dateListener());
		//Styling the slider
		timeSlide.setMajorTickSpacing(4);
		timeSlide.setMinorTickSpacing(2);
		timeSlide.setPaintTicks(true);
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(12*Value.SLIDER_INTERVAL+1,new JLabel("All"));
		labelTable.put(12*Value.SLIDER_INTERVAL-3,new JLabel("12:00am"));
		labelTable.put(0,new JLabel("12:00pm"));
		timeSlide.setLabelTable(labelTable);
		timeSlide.setPaintLabels(true);
		//add action listeners
		ButtonHandler onClick = new ButtonHandler();
		byDateBtn.addActionListener(onClick);
		byTitleBtn.addActionListener(onClick);
		viewAll.addActionListener(onClick);
		adminBtn.addActionListener(onClick);
		//Adding document Listener for search box
		SearchHandler onSearch = new SearchHandler();
		searchBox.getDocument().addDocumentListener(onSearch);
		
		//
		scroll = new JScrollPane(filmsPnl);
		//Add to panel
		leftBottomPanel.add(viewAll);
		leftBottomPanel.add(adminBtn);
		leftPanel.add(searchType, BorderLayout.CENTER);
		leftPanel.add(leftBottomPanel,BorderLayout.PAGE_END);
		centerPnl.add(leftPanel,BorderLayout.LINE_START);
		centerPnl.add(scroll,BorderLayout.CENTER);
		searchPnl.add(byDatePnl,BorderLayout.LINE_START);
		searchPnl.add(byTitlePnl,BorderLayout.CENTER);
		
		add(searchPnl,BorderLayout.PAGE_START);
		add(centerPnl,BorderLayout.CENTER);
		
		updateFilm();
	}
	public void updateFilm(int time){
		int compareTime = -1;
		boolean all= false;
		if(time!= 12*Value.SLIDER_INTERVAL+1){
		searchByTime = true;
		filmsPnl.removeAll();
		searchType.setText("Results");
		int timeMin = (time*60/Value.SLIDER_INTERVAL);
		
		
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
		this.time.setText("Searching for movies on " + displayTime + ((am)?"AM":"PM"));
		}else{
			all = true;
			log.v("Searhcing for all movies on " + searchDay);
			this.time.setText("Searching for all movies on " + dates[searchDay]);
		}
		loop:
		
		for(movieBlock i:mBlocks){
			MovieRecord r = i.getRecord();
			for(int x:r.showTimes){
				if((x == compareTime||all)&&(datesInt[searchDay]>=r.releaseDate&&datesInt[searchDay]<=r.finalDate)){
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
	private class timeListener implements ChangeListener {
	    public void stateChanged(ChangeEvent e) {
	        JSlider source = (JSlider)e.getSource();
	            int value = source.getValue();
	            if(value!=lastValue){
	            	updateFilm(value);
	            	lastValue = value;
	            } 
	    }
	}
	private class dateListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			searchDay = ((JComboBox) arg0.getSource()).getSelectedIndex();
			updateFilm(timeSlide.getValue());
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
			else if(command == adminBtn)
			{
				 CardLayout cardLayout = (CardLayout)cards.getLayout();
			     cardLayout.show(cards,Value.ADMIN);
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
			image.setIcon(new ImageIcon(Value.getImage(record.imageID).getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH)));
			//new ImageIcon(Value.getImage(record.imageID).getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH))
			add(image, BorderLayout.CENTER);
			add(new JLabel(e.movieTitle,JLabel.CENTER),BorderLayout.PAGE_END);
			
			ButtonHandler onClick = new ButtonHandler();
			addActionListener(onClick);
		}

		public MovieRecord getRecord(){return record;}
		private class ButtonHandler implements ActionListener{
			public void actionPerformed(ActionEvent arg0) {
				 CardLayout cardLayout = (CardLayout)cards.getLayout();
			     cardLayout.show(cards, Value.MOVIE);
			     MovieInfoPanel moviePanel = (MovieInfoPanel) cards.getComponent(1);
			     if(searchByTime){
			    	// moviePanel.setMovie( ); set movie's date to selected date
			    	 moviePanel.setMovie(record);
			     }
			     else
			     {
				     moviePanel.setMovie(record);
			     }
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
