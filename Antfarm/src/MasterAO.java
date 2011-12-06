
import java.util.HashMap;
import java.util.LinkedList;

import rpc.AnnotatedObject;
import rpc.Synchronous;


public class MasterAO extends AnnotatedObject implements iMaster
{
	@Synchronous 
	public Tuple move(int nodeID, int x, int y){return null;}
	
	@Synchronous
	public int createAnt(){return 0;}
	
	
	@Synchronous
	public HashMap<Integer,Ant> getGrid(){return null;}
	
	@Synchronous
	public LinkedList<Food> getFood(){return null;}
	
	@Synchronous
	public int getHeight(){return 0;}
	
	@Synchronous
	public int getWidth(){return 0;}
	
	@Synchronous
	public int getFoodCollected(){return 0;}
}
