
import java.util.HashMap;
import java.util.LinkedList;


public interface iMaster 
{
	public Tuple move(int antID, int x, int y);
	public int createAnt();
	
	public HashMap<Integer,Ant> getGrid();
	public LinkedList<Food> getFood();
	
	public int getWidth();
	public int getHeight();
	
	public int getFoodCollected();
}
