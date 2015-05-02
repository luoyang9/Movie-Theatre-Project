
public class log {
	protected static boolean enabled = true;
	protected static int level = 1;

	private static void printer(String tag,String message,int lev){
		if(lev>=level&&enabled){
			System.out.printf("%5s\t%s\n", tag,message);
		}
	}
	public static void v(String message){ //Verbose logging
		printer("--V--",message,1);
	}
	public static void i(String message){ //Information Logging
		printer("**i**",message,2);
	}
	public static void w(String message){ //Warning Logging
		printer("##W##",message,3);
	}
	public static void e(String message){ //Error logging
		printer("@@E@@",message,4);
	}
	public static void wtf(String message){ //We're screwed, the code should've never happened
		printer("!WTF!",message,Integer.MAX_VALUE);
	}
}
