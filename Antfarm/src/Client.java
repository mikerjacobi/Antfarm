
import api.DistributedSystemFactory;
import rpc.AnnotatedObject;
import utilities.A;
import communication.Recipient;
import communication.iPortal;


public class Client extends Recipient implements iClient, Runnable{

	private final iMaster master;
	private final iPortal networkPortal;
	private final static long threadSleepTime = 750;
	private int myAntID;
	private boolean hasFood = false;
	private Tuple nextMove;
	
	public static void main(String [] args)
	{
		int clientsToStart = 150;
		
		
		for(int i=0; i < clientsToStart; i++)
		{
			Client c = new Client();
			Thread t = new Thread(c);
			t.start();
		}
	}
	
	public Client()
	{
		SampleConstants constants = new SampleConstants();
		networkPortal = DistributedSystemFactory.newClient(this, constants);
		master = (iMaster) networkPortal.makeNewConnectionToResource("Master");
		
		myAntID = master.createAnt();
		nextMove = new Tuple(A.randomIntFromZeroToBound(2)-1, A.randomIntFromZeroToBound(2)-1);
	}
	
	@Override
	public void run() {
		while (true)
		{
			
			this.move();
			
			try 
			{
				Thread.sleep(threadSleepTime);
			} 
			catch (InterruptedException e)
			{
				//NOP
			}
		}
		
	}

	@Override
	public int getAntID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void move() 
	{
		int x=0;
		int y=0;
		if (nextMove.x!=0)
		{
			x = nextMove.x/Math.abs(nextMove.x);
			int xrando = A.randomIntFromZeroToBound(2); 
			if (xrando==2)
				x=x*2;
			else if ( xrando==1)
				x=0;
			
		}
		if (nextMove.y!=0)
		{
			y = nextMove.y/Math.abs(nextMove.y);
			int yrando = A.randomIntFromZeroToBound(2); 
			if (yrando==2)
				y=y*2;
			else if ( yrando==1)
				y=0;
		}
		
		nextMove = master.move(this.myAntID, x, y);
	}

	@Override
	public String getResourceName() {
		return "Client";
	}

	@Override
	public void newObjectHasConnected(AnnotatedObject arg0) {
		
	}

}
