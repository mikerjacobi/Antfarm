
import java.applet.Applet;
import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;

@SuppressWarnings("serial")
public class Visualizer extends Applet implements Runnable 
{
	private Image dbImage; 
	private Graphics dbg; 
	
   Thread t;
   boolean threadSuspended;
   Observer o;
   HashMap<Integer,Ant> ants = new HashMap<Integer,Ant>();
   LinkedList<Food> food = new LinkedList<Food>();
   int[][] antfarm;
   Tuple dims;
   Tuple center;
   private final int sleepTime = 1000;
   
   public void init() 
   {
      o = new Observer();
      Thread t = new Thread(o);
	  t.start();
      
	  dims = new Tuple(o.gridHeight*10, o.gridWidth*10);
	  center = new Tuple(dims.x/2, dims.y/2);	  
	  
      setSize(dims.x, dims.y);
      antfarm = new int[o.gridWidth][o.gridHeight];
      setBackground( Color.gray );
   }


   public void start() 
   {
      System.out.println("viz begin");
      if ( t == null ) 
      {
         t = new Thread( this );
         threadSuspended = false;
         t.start();
      }
      else 
      {
         if ( threadSuspended ) 
         {
            threadSuspended = false;
            synchronized( this ) 
            {
               notify();
            }
         }
      }
      System.out.println("viz end");
   }

   // Executed whenever the browser leaves the page containing the applet.
   public void stop() 
   {
      threadSuspended = true;
   }

   // Executed within the thread that this applet created.
   public void run() 
   {
	  
      System.out.println("run(): begin");
      try {
         while (true) {
        	ants = o.grid;
        	food = o.food;
        	 

            if ( threadSuspended ) 
            {
               synchronized( this ) 
               {
                  while ( threadSuspended ) 
                  {
                     wait();
                  }
               }
            }
            
            repaint();
            Thread.sleep( sleepTime );  // interval given in milliseconds
         }
      }
      catch (InterruptedException e) { }
   }

   public void paint( Graphics g ) 
   {
	  //display text
      g.setColor( Color.white );
      g.drawString("antfarm", 20, 20);
      g.drawString("food collected: "+Integer.toString(o.foodCollected), 20, 35);
      
      //dirt
      Color c = new Color(205,133,63);
      g.setColor( c);
      g.fillRect(50, 50, dims.x-100, dims.y-100);

      //ants
      Collection<Ant> vals = ants.values();
      for(Iterator<Ant> i = vals.iterator(); i.hasNext();)
      {
    	  Ant curr = i.next();
    	  g.setColor(Color.black);
    	  g.fillRect(center.x+5*(curr.getLocation().x-dims.x/20), center.y+5*(curr.getLocation().y-dims.y/20), 5, 5);
    	  if (curr.getHasFood()==true)
    	  {
    		  g.setColor(Color.red);
    		  g.fillRect(center.x+5*(curr.getLocation().x-dims.x/20), center.y+5*(curr.getLocation().y-dims.y/20), 3, 3);
    	  }
      }
      
      //food
      g.setColor(Color.red);
      ListIterator<Food> itr = food.listIterator();
      while(itr.hasNext())
      {
    	  Food currFood = itr.next();
    	  System.out.println(currFood.toString());
    	  Tuple loc = currFood.getLocation();
    	  
    	  g.fillRect(center.x+5*(loc.x-dims.x/20), center.y+5*(loc.y-dims.y/20), currFood.getAmount(), currFood.getAmount());
      }
      
      //anthole
      g.setColor(Color.gray);
      g.fillRect(center.x-10, center.y-10, 20, 20);
   }
}









