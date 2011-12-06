
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import api.DistributedSystemFactory;

import rpc.AnnotatedObject;
import communication.Recipient;
import communication.iPortal;

import api.DistributedSystemFactory;

import rpc.AnnotatedObject;
import utilities.A;
import communication.Recipient;
import communication.iPortal;

public class Master extends Recipient implements iMaster, Runnable 
{
	public HashMap<Integer, Ant> ants = new HashMap<Integer, Ant>();
	public LinkedList<Food> food = new LinkedList<Food>();
	
	public final int gridWidth = 80;
	public final int gridHeight = 80;
	public final Tuple center = new Tuple(gridWidth/2, gridHeight/2);
	public final int foodSpawnRate = 20;
	
	private int antIDcounter = 1000;
	
	private final iPortal portal;
	private List<iClient> connectedClients;
	private final static int threadSleepTime = 750;
	
	private int foodCollected = 0;
	
	public static void main(String [] args)
	{
		
		Master m = new Master();
		Thread t = new Thread(m);
		t.start();
	}
	
	public Master()
	{
		SampleConstants constants = new SampleConstants();
		portal = DistributedSystemFactory.newServer(10011, this, constants);
		connectedClients = new ArrayList<iClient>();
	}

	@Override
	public void run() 
	{
		while (true)
		{
			
			//generate food
			int rando = A.randomIntFromZeroToBound(100);
			if (rando<foodSpawnRate)
			{
				System.out.println("food added");
				food.add(new Food(gridWidth, gridHeight));
			}
			
			
			Collection<Ant> vals = ants.values();
			for(Iterator<Ant> i = vals.iterator(); i.hasNext();)
			{
				Ant currAnt = i.next();
				
				//allow ants to return food
				if(currAnt.getHasFood()==true && currAnt.getLocation().distanceFrom(center)<3.0)
			    {
					currAnt.returnFood();
			    	foodCollected++;
			    	
			    }
				//allow ants to eat food
				else if (currAnt.getHasFood()==false)
				{
					ListIterator<Food> itr = food.listIterator();
				    while(itr.hasNext())
				    {
				    	Food currFood = itr.next();
				    	if (currAnt.getLocation().distanceFrom(currFood.getLocation()) < 3.0)
				    	{
				    		currFood.eat();
				    		currAnt.eat();
				    		if (currFood.getAmount()==0)
				    		{
				    			food.remove(currFood);
				    			break;
				    		}
				    		
				    		
				    	} 
				    }
				}
			    
			}
			
			
			
			try
			{
				Thread.sleep(threadSleepTime);
			}
			catch(InterruptedException e)
			{
				//NOP
			}
		}
	}

	@Override
	public Tuple move(int antID, int x, int y) 
	{
		int antX = ants.get(antID).getLocation().x;
		int antY = ants.get(antID).getLocation().y;
		
		
		if ((antX + x)>gridWidth || (antY + x) < 0){}
		else
			ants.get(antID).getLocation().x = antX + x;
		
		if ((antY + y)>gridHeight || (antY + y) < 0){}
		else
			ants.get(antID).getLocation().y = antY + y;
			
		
		//calculate next move
		if (ants.get(antID).getHasFood()==false) //nextmove to get to food
		{
			ListIterator<Food> itr = food.listIterator();
			double min = 9999;
			Food closest = null;
		    while(itr.hasNext())
		    {
		    	Food currFood = itr.next();
		    	if (ants.get(antID).getLocation().distanceFrom(currFood.getLocation()) < min)
		    	{
		    		min = ants.get(antID).getLocation().distanceFrom(currFood.getLocation());
		    		closest = currFood;
		    	} 
		    }
		    return (ants.get(antID).getLocation().tupleSubtraction(closest.getLocation()));
		}
		else //next food to get home
			return (ants.get(antID).getLocation().tupleSubtraction(center));
	}

	@Override
	public String getResourceName() {
		return "Master";
	}

	@Override
	public void newObjectHasConnected(AnnotatedObject newObject) 
	{
		connectedClients.add((iClient) newObject);
	}

	@Override
	public HashMap<Integer, Ant> getGrid() {
		return ants;
	}

	@Override
	public int createAnt() 
	{
		antIDcounter++;
		ants.put(antIDcounter, new Ant(center.x, center.y));
		return antIDcounter;
	}
	
	public int getWidth()
	{
		return gridWidth;
	}
	
	public int getHeight()
	{
		return gridHeight;
	}

	@Override
	public LinkedList<Food> getFood() {
		return food;
	}
	
	@Override
	public int getFoodCollected()
	{
		return foodCollected;
	}
	 
}
