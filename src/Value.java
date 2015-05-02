import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;


public class Value 
{
	//dimensions
	public final static int WIDTH = 800;
	public final static int HEIGHT = 700;
	
	//panels
	public final static String SEARCH = "1";
	public final static String MOVIE = "2";
	public final static String TICKET = "3";
	public final static String ADMIN = "4";
	public final static String ALL_MOVIES = "5";
	public final static String BILLING = "6";
	public final static String CHECK_OUT = "7";
	public final static String ADD_MOVIE = "8";
	public final static String ALL_CUSTOMERS = "9";
	
	//Search Panel
	public final static int SLIDER_INTERVAL = 4;
	public final static int SEARCH_DAYS = 7;
	public final static String DATE_WORDS[] = {"Today", "Tomorrow"};
	public final static String WEEK_DAYS[] = {"Sunday" ,"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	public final static int MAX_ALL_FRAME_VERTICAL_DISPLAY = 4;
	public final static int MAX_ALL_FRAME_HORIZONTAL_DISPLAY = 7;
	//Pictures
	public final static String POSTER_PATH = "Posters\\";
	public final static String TICKET_PATH = "Tickets\\";
	public final static String ASSET_PATH = "Assets\\";
	public static ImageIcon[] POSTER_IMAGES;
	
	//Movies
	public final static int DAYS_RUNNING = 31;
	
	//Tickets
	public final static int CHILD = 1;
	public final static int ADULT = 2;
	public final static int SENIOR = 3;
	public final static double CHILD_PRICE = 7.99;
	public final static double ADULT_PRICE = 11.99;
	public final static double SENIOR_PRICE = 9.99;
	
	//Admin
	public final static String PASSWORD = "123";
	//Font
	public final static Font LARGE_BOLD = new Font("Impact", Font.PLAIN, 80);
	public final static Font MEDIUM = new Font("Arial",Font.BOLD,25);
	public final static Font SMALL_MEDIUM = new Font("Arial",Font.BOLD,20);
	public final static Font SMALL = new Font("Arial", Font.BOLD,15);
	//Colors
	public final static Color BABY_BLUE = new Color(93, 151, 245);
	public final static Color BLUE = new Color(66, 133, 244);
	public final static Color GREY = new Color(236, 236, 236);
	public final static Color RED = new Color(219, 68, 55);
	
	public static void loadImages(){
		
		int numRecords = (int) MovieFile.getNumRecords();
		log.v("Loading all images count = " + numRecords);
		POSTER_IMAGES = new ImageIcon[numRecords];
		for(int x = 0;x<numRecords;x++){
			try{
			POSTER_IMAGES[x] = new ImageIcon(POSTER_PATH+(x+1)+".jpg");
			}catch(NullPointerException e){
				log.e("IMAGE " + x + " not found! Using backup image instead");
				POSTER_IMAGES[x] = new ImageIcon(POSTER_PATH+"error.jpg");
			}
		}
		log.v("Images done loading");
	}
	public static ImageIcon getImage(int imageId){
		try{
		return POSTER_IMAGES[imageId-1];
		}catch(ArrayIndexOutOfBoundsException e){
			log.e("The image <"+ imageId + "> requested doesn't exist, sending error image");
			return new ImageIcon(POSTER_PATH + "error.jpg");
		}
	}
	public static ImageIcon getImage(String imageName){
		try{
			return new ImageIcon(ASSET_PATH+imageName+".jpg");
			}catch(NullPointerException e){
				return new ImageIcon(POSTER_PATH + "error.jpg");
			}
	}
}
