package il.ac.wis.cs.playgo.playtoolkit.container;

import il.ac.wis.cs.playgo.playtoolkit.Utils;
import il.ac.wis.cs.playgo.playtoolkit.api.impl.java.GuiJAgent;
import il.ac.wis.cs.playgo.playtoolkit.api.impl.tcp.GuiTcpAgent;
import il.ac.wis.cs.playgo.playtoolkit.api.intf.IPlayGo;
import il.ac.wis.cs.playgo.playtoolkit.api.intf.IPlayable;
import il.ac.wis.cs.playgo.playtoolkit.api.intf.IPlayableComponent;
import il.ac.wis.cs.playgo.playtoolkit.api.intf.IPlayableContainer;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class PlayableFramework implements IPlayableContainer, IPlayable {

	protected HashMap<String, IPlayableComponent> allPlayableComponents = new HashMap<String, IPlayableComponent>();
	protected static IPlayGo guiAgent;

	static boolean originatedFromGui = true;
	
	private int PORT;
	
	public PlayableFramework() {
		super();
		PORT = Utils.getPort();
		
		initPlayable();
		initialize();
		appIsUp();
	}

	protected void initPlayable() 
	{
		if (guiAgent == null)
		{
//	        String playInStr = System.getProperty("playInMode");
//	        if(playInStr!=null && playInStr.equals("true")){
			if (Utils.isPlayInMode()){
	        	guiAgent = new GuiTcpAgent(this, PORT);	//play-in
	        }
	        else{
	        	guiAgent = new GuiJAgent(this);
	        }
		}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPlaygo(IPlayGo playgo) {
		// TODO Auto-generated method stub

	}

	@Override
	public String activateMethod(String className, String objectName,
			String methodName, String... arguments) {
		String retVal;
		IPlayableComponent obj = allPlayableComponents.get(objectName);
		
		if (obj!=null){
			originatedFromGui = false;
			retVal =  obj.activateMethod(methodName, arguments);
			originatedFromGui = true;
			return retVal;
		}
		
		return null;
	}

	@Override
	public void setPropertyValue(String className, String objectName,
			String propertyName, String value) {
		originatedFromGui = false;
		IPlayableComponent obj = allPlayableComponents.get(objectName);
		if (obj != null){
			obj.setPropertyValue(propertyName, value);
		}
		originatedFromGui = true;
	}

	@Override
	public String getPropertyValue(String className, String objectName,
			String propertyName) {
		IPlayableComponent obj = allPlayableComponents.get(objectName);
		if (obj != null){
			return obj.getPropertyValue(propertyName);
		}
		return null;
	}

	@Override
	public void highlightObject(String objectName) 
	{
		IPlayableComponent obj = allPlayableComponents.get(objectName);
		if(obj!=null){
			obj.highlightObject(objectName);
		}
	}

	@Override
	public void clearObject(){
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
	public void add(IPlayableComponent comp, String compName) {
		if (comp.getFramework() == null)
			comp.setFramework(this);
		
		allPlayableComponents.put(compName, comp);
	}
	
	public void add(IPlayableComponent comp) {
		add(comp, comp.getName());
	}

	@Override
	public void objectEvent(String className, String objectName,
			String eventName, ArrayList<String> args) {
		if (originatedFromGui){
			guiAgent.objectEvent(className, objectName, eventName, args);
		}
		else{
			originatedFromGui = true; // init to true again for the next "real" objectEvent
			return;
		}
	}

	@Override
	public void objectRightClicked(String className, String objectName) {
		guiAgent.objectRightClicked(className, objectName);
	}

	@Override
	public void objectPropertyChanged(String className, String objectName,
			String propertyName, String value) {
//		// during play-in, we do not update the behavior - that's a decision we made. we may change this in the future...
//		if (Utils.isPlayInMode())
//			return;

		if (originatedFromGui){
			guiAgent.objectPropertyChanged(className, objectName, propertyName, value);
		}
		else{
			originatedFromGui = true; // initialize to true again
			return;
		}
	}

	@Override
	public void objectPropertyChanged(String sourceClassName, String sourceObjectName,
			String targetClassName, String targetObjectName,
			String propertyName, String value) 
	{
//		// during play-in, we do not update the behavior - that's a decision we made. we may change this in the future...
//		if (Utils.isPlayInMode())
//			return;
		
		if (originatedFromGui){
			guiAgent.objectPropertyChanged(sourceClassName, sourceObjectName,
					targetClassName, targetObjectName,
					propertyName, value);
		}
		else{
			originatedFromGui = true; // initialize to true again
			return;
		}
	}
	
	abstract protected  void initialize();

	public IPlayableComponent get(String compName) {
		return allPlayableComponents.get(compName);
	}

	protected void appIsUp() {
		if( ! (this instanceof PlayableContainer) ){
			guiAgent.appIsUp();
		}
	}


}
