import java.awt.*;

import javax.swing.*;


public class Main {
	//Frame
	public static JFrame frame;
	public static JPanel mainPanel;
	//
	public static void main(String[] args) {
		guiApp();
	}
	
	private static void guiApp(){
		frame = new JFrame("Movie Tickets");
		mainPanel = new JPanel(new CardLayout());
		mainPanel.add("1",new SearchPanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Execute frame
		frame.add(mainPanel);
		frame.pack();
		frame.setVisible(true);
	}
	private static void switchCard(int card){
		 CardLayout cardLayout = (CardLayout)mainPanel.getLayout();
	     cardLayout.show(mainPanel,""+card);
	}
}
