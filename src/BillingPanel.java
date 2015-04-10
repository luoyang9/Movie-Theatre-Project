import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class BillingPanel {
	static JLabel banner, name, bDay, address, phoneNum, credCard, expDate, secureCode, blank;
	static JButton back, proceed;
	static JTextField nameIn, bDayIn, addressIn, phoneNumIn, credCardIn, expDateIn, secureCodeIn;
	static JPanel mainPanel, personal, credInfo, inputCVV, allInfo, button;
	public static void guiApp(){
		JFrame mainFrame = new JFrame("Billing");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel personal = new JPanel(new GridLayout(4,2));
		JPanel credInfo = new JPanel(new GridLayout(5,1));
		JPanel inputCVV = new JPanel(new GridLayout(1,2));
		JPanel allInfo = new JPanel(new GridLayout(1,2));
		JPanel button = new JPanel(new GridLayout(1,3));
		
		banner = new JLabel("Payment Information");
		name = new JLabel("Name");
		bDay = new JLabel("Birthdate");
		address = new JLabel("Address");
		phoneNum = new JLabel("Telephone");
		credCard = new JLabel("Credit Card No.");
		expDate = new JLabel("Expiry Date");
		secureCode = new JLabel("CVV");
		blank = new JLabel(" ");
		
		back = new JButton("Back");
		proceed = new JButton("Proceed" +
				"");
		
		nameIn = new JTextField("");
		bDayIn = new JTextField("");
		addressIn = new JTextField("");
		phoneNumIn = new JTextField("");
		credCardIn = new JTextField("");
		expDateIn = new JTextField("");
		secureCodeIn = new JTextField("");
		
		ButtonHandler onClick = new ButtonHandler();
		back.addActionListener(onClick);
		proceed.addActionListener(onClick);
		
		mainPanel.add(banner, BorderLayout.PAGE_START);
		
		personal.add(name);
		personal.add(nameIn);
		personal.add(bDay);
		personal.add(bDayIn);
		personal.add(address);
		personal.add(addressIn);
		personal.add(phoneNum);
		personal.add(phoneNumIn);
		allInfo.add(personal);
		
		credInfo.add(credCard);
		credInfo.add(credCardIn);
		credInfo.add(expDate);
		credInfo.add(expDateIn);
		inputCVV.add(secureCode);
		inputCVV.add(secureCodeIn);
		credInfo.add(inputCVV);
		allInfo.add(credInfo);
		
		mainPanel.add(allInfo, BorderLayout.CENTER);
		
		button.add(back);
		button.add(blank);
		button.add(proceed);
		mainPanel.add(button, BorderLayout.PAGE_END);
		
		mainFrame.add(mainPanel);
		mainFrame.setSize(600,350);
		mainFrame.setVisible(true);
	}
	private static class ButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();
			if (action.equals("Proceed"))
				System.out.println("Proceed guys");
		}
	}
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				guiApp();
			}
		});
	}
}
