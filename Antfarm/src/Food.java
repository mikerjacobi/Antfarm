
import java.io.Serializable;

import utilities.A;


public class Food implements Serializable 
{
	private static final long serialVersionUID = -1942671758992555552L;
	private Tuple location;
	private int amount;
	
	public Food(int width, int height)
	{
		int x = A.randomIntFromZeroToBound(width);
		int y = A.randomIntFromZeroToBound(height);
		amount = A.randomIntFromZeroToBound(10) + 5;
		location = new Tuple(x,y);
	}
	
	public void eat()
	{
		this.amount--;
	}
	
	public Tuple getLocation()
	{
		return location;
	}
	public int getAmount()
	{
		return amount;
	}
	
}
