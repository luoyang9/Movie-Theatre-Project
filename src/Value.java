import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;


public class Value 
{
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
	//Pictures
	public final static String POSTER_PATH = "Posters\\";
	public final static String RECEIPT_PATH = "Receipts\\";
	public static ImageIcon[] POSTER_IMAGES;
	
	//Admin
	public final static String PASSWORD = "123";
	//Font
	public final static Font LARGE_BOLD = new Font("Impact", Font.PLAIN, 80);
	
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
}
