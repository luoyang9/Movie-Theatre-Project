
public class SeatPlan 
{
	private boolean[][][][] seats;
	
	public SeatPlan(int numDates, int numShowTimes, int rows, int cols)
	{
		seats = new boolean[numDates][numShowTimes][rows][cols];
	}	
	
	public SeatPlan()
	{
		seats = new boolean[4][4][3][4];
	}
	
	public SeatPlan(boolean[][][][] seats)
	{
		this.seats = seats;
	}
	
	public void setSeats(boolean[][][][] seats)
	{
		this.seats = seats;
	}
	
	public void setAllFalse()
	{
		for(int a = 0; a < seats.length; a++)
		{
			for(int b = 0; b < seats[a].length; b++)
			{
				for(int c = 0; c < seats[a][b].length; c++)
				{
					for(int d = 0; d < seats[a][b][c].length; d++)
					{
						seats[a][b][c][d] = false;
					}
				}
			}
		}
	}
	
	public boolean[][][][] getSeats() {return seats;}
}
