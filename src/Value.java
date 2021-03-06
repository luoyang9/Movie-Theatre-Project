import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
	public static List<imageFile> ASSET_IMAGES;
	public static ImageIcon errorImage;
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
	public final static Font MEDIUM_LARGE = new Font("Impact",Font.BOLD,50);
	public final static Font MEDIUM = new Font("Arial",Font.BOLD,25);
	public final static Font SMALL_MEDIUM = new Font("Arial",Font.BOLD,20);
	public final static Font SMALL = new Font("Arial", Font.BOLD,15);
	//Colors
	public final static Color BABY_BLUE = new Color(93, 151, 245);
	public final static Color BLUE = new Color(66, 133, 244);
	public final static Color GREY = new Color(236, 236, 236);
	public final static Color RED = new Color(219, 68, 55);
	
	
	public static void loadImages(){
		errorImage = new ImageIcon(POSTER_PATH + "error.jpg");
		
		
		int numRecords = (int) MovieFile.getNumRecords();
		log.v("Loading all Posters count = " + numRecords);
		POSTER_IMAGES = new ImageIcon[numRecords];
		for(int x = 0;x<numRecords;x++){
			try{
			POSTER_IMAGES[x] = new ImageIcon(POSTER_PATH+(x+1)+".jpg");
			}catch(NullPointerException e){
				log.e("IMAGE " + x + " not found! Using backup image instead");
				POSTER_IMAGES[x] = errorImage;
			}
		}
		log.v("Posters done loading");
		
		ASSET_IMAGES = new ArrayList<imageFile>();
		for(File file: new File(ASSET_PATH).listFiles()){
			if(file.isFile()){
				ASSET_IMAGES.add(new imageFile(file,file.getName()) );
				
			}
		}
	}
	public static ImageIcon getImage(int imageId){
		try{
		return POSTER_IMAGES[imageId-1];
		}catch(ArrayIndexOutOfBoundsException e){
			log.e("The image <"+ imageId + "> requested doesn't exist, sending error image");
			return errorImage;
		}
	}
	public static ImageIcon getImage(int imageId, int width, int height){
		return new ImageIcon(getImage(imageId).getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH));
	}
	public static ImageIcon getImage(String imageName){
		for(imageFile i : ASSET_IMAGES){
			if(i.getName().equalsIgnoreCase(imageName))
				return i.getImage();
		}
		log.e("Image "+ imageName +" couldn't be found. Returning error image");
		return errorImage;
	}
	public static ImageIcon getImage(String imageName, int width, int height){
		return new ImageIcon(getImage(imageName).getImage().getScaledInstance(width, height,Image.SCALE_SMOOTH));
	}
	public static class imageFile{
		protected String imageName;
		protected ImageIcon image;
		
		public imageFile(File file, String name){
			
			imageName = name.substring(0,name.indexOf("."));
			try{
			image = new ImageIcon(file.getAbsolutePath());
			log.v("Loaded Image " + getName());
			}catch(NullPointerException e){
				log.wtf("WE CAN'T LOAD AN IMAGE WE JUST FOUND???");
				image = errorImage;
			}
		}
		public ImageIcon getImage(){return image;}
		public String getName(){return imageName;}
	}
	
}
