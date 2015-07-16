package il.ac.wis.cs.playgo.playtoolkit.api.web;

import il.ac.wis.cs.playgo.playtoolkit.api.impl.tcp.GuiTcpAgent;
import il.ac.wis.cs.playgo.playtoolkit.api.intf.IPlayGo;
import il.ac.wis.cs.playgo.playtoolkit.api.intf.IPlayable;

public class NJAppServer implements IPlayable 
{
	private IPlayGo playgo = null;
	
	private MethodToActivate methodToActivate = null;

	private int PORT;
	
	//CTOR
	public NJAppServer(int port){
		PORT = port;
	}
	
	//start	(called from javascript when web page is loaded)
	public boolean start()
	{
		try{
			playgo = new GuiTcpAgent(this, PORT); 
			playgo.setPlayable(this);
		}
		catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	//playgoObjectEvent
	public void playgoObjectEvent(String className, String objectName, String eventName)
	{
		playgo.objectEvent(className, objectName, eventName, null);
	}

	//playgoObjectRightClicked
	public void playgoObjectRightClicked(String className, String objectName)
	{
		playgo.objectRightClicked(className, objectName);
	}

	//methodActivated
	public void methodActivated(String methodActivationResult)
	{
		res = methodActivationResult;
	}

	//getMethodToActivate
	public MethodToActivate getMethodToActivate()
	{
		MethodToActivate res = methodToActivate;
		methodToActivate = null;
		return res;
	}
	
	//Playable
	//========
	private String res=null;
	
	@Override
	public String activateMethod(String className, String objectName,
			String methodName, String... arguments) 
	{
		res = null;
		methodToActivate = new MethodToActivate(className, objectName, methodName, arguments);
		waitForExecution();
		return res;
	}
	
	@Override
	public void setPropertyValue(String className, String objectName,
			String propertyName, String value) 
	{
		res = null;
		String methodName = "set" + propertyName.substring(0,1).toUpperCase() +
				propertyName.substring(1);
		
		methodToActivate = new MethodToActivate(className, objectName, methodName, value);
		waitForExecution();
	}

	@Override
	public String getPropertyValue(String className, final String objectName,
			final String propertyName) 
	{
		res = null;
		String methodName = "get" + propertyName.substring(0,1).toUpperCase() +
				propertyName.substring(1);
		
		methodToActivate = new MethodToActivate(className, objectName, methodName);
		waitForExecution();
		return res;
	}
	
	//waitForExecution
	private void waitForExecution()
	{
		//wait loop
		int maxTRies = 100;
		while( res==null && maxTRies>0){
			try {
				Thread.sleep(100);
				maxTRies-=1;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return;		
	}
	
	@Override
	public void highlightObject(String objectName) {
		// TODO Auto-generated method stub
	}
	@Override
	public void clearObject() {
		// TODO Auto-generated method stub
	}
	@Override
	public String getObjectPosition() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void construct() {
		// TODO Auto-generated method stub
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setPlaygo(IPlayGo playgo) {
		// TODO Auto-generated method stub
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
