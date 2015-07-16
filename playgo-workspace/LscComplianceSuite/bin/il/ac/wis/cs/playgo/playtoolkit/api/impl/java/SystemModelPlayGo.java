package il.ac.wis.cs.playgo.playtoolkit.api.impl.java;

import il.ac.wis.cs.playgo.playtoolkit.Utils;
import il.ac.wis.cs.playgo.playtoolkit.api.intf.IPlayGo;
import il.ac.wis.cs.playgo.playtoolkit.api.intf.IPlayable;

import java.util.ArrayList;


public abstract class SystemModelPlayGo implements IPlayGo
{
	public String RIGHT_CLICK_EVENT = 	"RightClickEvent";

    //IPlayGo
    public IPlayable playable = null;
    
//    public static boolean informGui=true; 
//    
//    public static boolean informGui(){
//    	return informGui;
//    }
    
//	public String CLICK_EVENT = 		"ClickEvent";
//	public String SWIPE_EVENT = 		"SwipeEvent";
//	public String PUSH_EVENT = 			"PushEvent";
//	public String RELEASE_EVENT = 		"ReleaseEvent";
	
	
	/*
	 * Used by the GUI to inform the behavior that a certain event has occurred in a specific 
	 * object (e.g., click, swipe, push, release, etc.).
	 * It is also used to inform the behavior that a value of some object’s property was changed.
	 * Should it be used to notify PlayGo that a method was called 
	 * (in case one object calls another object within the GUI app, 
	 * not because it was instructed to do so by PlayGo).
	 */
	@Override
	public void objectEvent(final String className, 
			final String objectName, final String eventName, ArrayList<String> argValues) 
	{
		//informGui=false;

       	Utils.invokeMessage("User", "Env",
        		objectName,className,
        		eventName,argValues);
       	
       	//informGui=true;
	}
	
	@Override
	public void objectPropertyChanged(final String className, final String objectName,
			final String propertyName, String value) 
	{
		String methodtName = "set" + propertyName.substring(0,1).toUpperCase() +
				propertyName.substring(1);
		
		ArrayList<String> argValues = new ArrayList<String>();
		argValues.add(value);
		
		objectEvent(className, objectName, methodtName, argValues); //TODO: set correct arg value
	}

	@Override
	public void objectPropertyChanged(String sourceClass, String sourceObject,
			String targetClass, String targetObject, 
			String propertyName, String value) 
	{
		String methodtName = "set" + propertyName.substring(0,1).toUpperCase() +
				propertyName.substring(1);
		
		ArrayList<String> argValues = new ArrayList<String>();
		argValues.add(value);
		
       	Utils.invokeMessage(sourceObject, sourceClass,
       			targetObject, targetClass,
       			methodtName,argValues);
	}


	@Override
	public void objectRightClicked(String className, String objectName)
	{
		objectEvent(className, objectName, RIGHT_CLICK_EVENT, null);
	}

	@Override
	public void setPlayable(IPlayable playable) {
		this.playable = playable;
	}
	
	@Override
	public IPlayable getPlayable() {
		return playable;
	}

}