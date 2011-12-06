
import java.util.HashMap;
import java.util.LinkedList;

import rpc.AnnotatedObject;
import api.DistributedSystemFactory;

import communication.Recipient;
import communication.iPortal;

public class Observer extends Recipient implements iObserver, Runnable
{
	
	private final iMaster master;
	private final iPortal networkPortal;
	public HashMap<Integer,Ant> grid = new HashMap<Integer, Ant>();
	public LinkedList<Food> food = new LinkedList<Food>();
	public int gridWidth, gridHeight;
	public int foodCollected;
	private final int sleepTime = 500;
	
	public Observer()
	{
		
		SampleConstants constants = new SampleConstants();
		networkPortal = DistributedSystemFactory.newClient(this, constants);
		master = (iMaster) networkPortal.makeNewConnectionToResource("Master");
		
		gridWidth = master.getWidth();
		gridHeight = master.getHeight();
		foodCollected = master.getFoodCollected();
	}

	@Override
	public void run() 
	{
		while(true)
		{
			
			this.getGrid();
			this.getFood();
			this.getFoodCollected();
			
			try 
			{
				Thread.sleep(sleepTime);
			} 
			catch (InterruptedException e)
			{
				//NOP
			}
		}		
	}

	
	public void getGrid()
	{
		grid = master.getGrid();
		
	}
	
	public void getFood()
	{
		food = master.getFood();
	}

	@Override
	public String getResourceName() {
		return "Client";
	}

	@Override
	public void newObjectHasConnected(AnnotatedObject arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void getFoodCollected()
	{
		foodCollected = master.getFoodCollected();
	}
	
}