package il.ac.wis.cs.playgo.playtoolkit.api.impl.java;

import il.ac.wis.cs.playgo.playtoolkit.Utils;
import il.ac.wis.cs.playgo.playtoolkit.api.intf.IPlayGo;
import il.ac.wis.cs.playgo.playtoolkit.api.intf.IPlayable;

import java.util.ArrayList;

public class GuiJAgent implements IPlayGo
{
	//public final String RIGHT_CLICK_EVENT = 	"RightClickEvent";
	
	private IPlayable playable = null;
	private IPlayGo behavior = null;	
	
	private static GuiJAgent instance = null;
	
	//CTOR
	public GuiJAgent(IPlayable playable) {
		this.playable = playable;
        instance = this;
	}

	//getInstance
	public static GuiJAgent getInstance(){
		return instance;
	}
	
	@Override
	public void objectEvent(String className, String objectName,
			String eventName, ArrayList<String> argValues) 
	{
       	Utils.invokeMessage("User", "Env",
        		objectName,className,
        		eventName,argValues);
	}

	@Override
	public void objectRightClicked(String className, String objectName) 
	{
		//objectEvent(className, objectName, RIGHT_CLICK_EVENT, null);
	}

	@Override
	public void objectPropertyChanged(String className, String objectName,
			String propertyName, String value) 
	{
//		if(playIn){
//		tcpAgent.objectPropertyChanged(className, objectName, propertyName, value);
//	}
//	else{
//		String methodName = "set" + propertyName.substring(0,1).toUpperCase();
//		if(propertyName.length()>1){
//			methodName = methodName + propertyName.substring(1);
//		}
//		ArrayList<String> argTypes = new ArrayList<String>();
//		argTypes.add(value);
//		
//		objectEvent(className, objectName, methodName, argTypes);
//	}
	objectPropertyChanged(className, objectName, className, objectName, propertyName, value);
	}

	@Override
	public void objectPropertyChanged(String sourceClass, String sourceObject,
			String targetClass, String targetObject, 
			String propertyName,String value) 
	{
		String methodName = "set" + propertyName.substring(0,1).toUpperCase();
		if(propertyName.length()>1){
			methodName = methodName + propertyName.substring(1);
		}
		ArrayList<String> argTypes = new ArrayList<String>();
		argTypes.add(value);
		
       	Utils.invokeMessage(sourceObject, sourceClass,
       			targetObject, targetClass,
       			methodName, argTypes);
	}

	@Override
	public void setPlayable(IPlayable playable) {
		this.playable = playable;
	}

	@Override
	public IPlayable getPlayable() {
		return playable;
	}

	public void setBehavior(IPlayGo behavior){
		this.behavior = behavior;
		playable.setPlaygo(behavior);
		behavior.setPlayable(playable);
	}

	@Override
	public void appIsUp() {
		behavior.appIsUp();
	}
	
	@Override
	public void stop(){
	}
	
}
