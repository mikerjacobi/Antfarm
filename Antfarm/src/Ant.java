
import java.io.Serializable;


public class Ant implements Serializable  
{
	private static final long serialVersionUID = -6080858673622725395L;
	private Tuple location;
	private boolean hasFood = false;
	
	public Ant(int x, int y)
	{
		location = new Tuple(x,y);
	}
	
	public Tuple getLocation()
	{
		return location;
	}
	
	public void eat()
	{
		hasFood = true;
	}
	
	public boolean getHasFood()
	{
		return hasFood;
	}
	
	public void returnFood()
	{
		hasFood = false;
	}

}
