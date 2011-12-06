
import java.io.Serializable;


public class Tuple implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2747742285818273082L;
	public int x;
	public int y;
	
	public Tuple(int passedX, int passedY)
	{
		x = passedX;
		y = passedY;
	}
	
	public String toString()
	{
		String s = Integer.toString(x) +", "+Integer.toString(y);
		return s;
	}
	
	public double distanceFrom(Tuple otherTuple)
	{
		int xdiff = otherTuple.x - this.x;
		int ydiff = otherTuple.y - this.y;
		double hypo = Math.sqrt(xdiff*xdiff+ydiff*ydiff);
		return hypo;
	}
	
	public Tuple tupleSubtraction(Tuple other)
	{
		Tuple ret = new Tuple(other.x-this.x, other.y-this.y);
		return ret;
	}
}
