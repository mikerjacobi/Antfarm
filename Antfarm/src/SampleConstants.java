
import java.util.HashMap;
import java.util.Map;

import communication.NodeId;

import constants.iConstants;


public class SampleConstants implements iConstants
{

	@Override
	public Map<String, Class> getAnnotatedObjectsByString() 
	{
		HashMap<String, Class> AOMap = new HashMap<String, Class>();
		
		AOMap.put("Client", ClientAO.class);
		AOMap.put("Master", MasterAO.class);
		
		return AOMap;
	}

	@Override
	public int getDefaultDistributorPort()
	{
		return 10010;
	}

	@Override
	public String getDefaultDistributorHostName()
	{
		return "localhost";
	}

	public NodeId getDistributorNodeId() 
	{
		return new NodeId(0,0);
	}

	@Override
	public boolean shutdownOnSocketError() {
		// TODO Auto-generated method stub
		return true;
	}

}
