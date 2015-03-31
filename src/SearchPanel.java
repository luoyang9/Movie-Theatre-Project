import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class SearchPanel extends JPanel{
	//Panels inside
	protected JPanel searchPnl;
	protected JPanel byDatePnl; //Cardlayout for search types
	protected JPanel byTitlePnl;
	protected JPanel filmsPnl; //Displays results and shows featured films
	protected JPanel centerPnl; //Contains filmsPnl and the picture
	protected JLabel searchType;
	//Buttons
	private static JButton byDateBtn;
	private static JButton byTitleBtn;
	private JButton optionsBtn;
	private JButton managementBtn;
	
	private static JTextField searchBox; //Search by title
	private static JList searchBox2; //Search by date
	
	public SearchPanel(){
		this.setLayout(new BorderLayout());
		byDatePnl = new JPanel(new CardLayout());
		byTitlePnl = new JPanel(new CardLayout());
		centerPnl = new JPanel(new BorderLayout());
		filmsPnl = new JPanel(new GridLayout(1,5));
		searchPnl = new JPanel(new BorderLayout());
		searchBox = new JTextField();
		byDateBtn = new JButton("Search By Date");
		byTitleBtn = new JButton("Search By Title");
		searchBox2 = new JList<String>();
		searchType = new JLabel("Featured");
		//Adding change Listener for search box
		searchBox.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
			  	}
				  public void removeUpdate(DocumentEvent e) {
					  updateFilm();
				  }
				  public void insertUpdate(DocumentEvent e) {
					  updateFilm();
				  }
				  public void updateFilm(){
					  String query = searchBox.getText();
					  filmsPnl.removeAll();
					  if(query.equals("")){
						  searchType.setText("Featured");
					  }else{
						  searchType.setText("Results:");
					  }
				  }
				});
		byDatePnl.add("1",byDateBtn);
		byDatePnl.add("2",searchBox2);
		byTitlePnl.add("1",byTitleBtn);
		byTitlePnl.add("2",searchBox);
		//add action listeners
		ButtonHandler onClick = new ButtonHandler();
		byDateBtn.addActionListener(onClick);
		byTitleBtn.addActionListener(onClick);
		
		//Add to panel
		centerPnl.add(searchType,BorderLayout.LINE_START);
		centerPnl.add(filmsPnl,BorderLayout.CENTER);
		searchPnl.add(byDatePnl,BorderLayout.LINE_START);
		searchPnl.add(byTitlePnl,BorderLayout.CENTER);
		
		add(searchPnl,BorderLayout.PAGE_START);
		add(centerPnl,BorderLayout.CENTER);
		
		
	}
	private static void switchCard(JPanel panel, int card){
		 CardLayout cardLayout = (CardLayout)panel.getLayout();
	     cardLayout.show(panel,""+card);
	}
	private void searchMode(int mode){
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
	private class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object command = e.getSource();
			
			if(command == byDateBtn){
				searchMode(1);
			}else if(command == byTitleBtn)
				searchMode(2);
		}
		
	}
	public class movieBlock extends JPanel{
		/*public movieBlock(MovieRecord record){
		  
		  
		  }
		 */
	}
	
}
