package il.ac.wis.cs.playgo.playtoolkit.api.intf;

/** 
 * An interface for GUI components that are playable when used in the context of PlayGo.
 * A playable GUI component must have a name (name is given in the constructor).This name has
 * to be equal to the name of the behavioral object it represents. 
 * If the component does not represent a behavioral object, but rather a property or a message,
 * then the name is meaningless. And the component is assumed to be contained in a {@link PlayableContainer} 
 * which handles the playable aspects of the components.
 * 
 * @author Smadar
 * 
 */
public interface IPlayableComponent{

	String getName();
	
	void setFramework(IPlayableContainer framework);
	
	IPlayableContainer getFramework();
	
	/**
	 *	Allows PlayGo to activate a given method for a specific object. 
	 *	If the method is synchronous, the GUI will indicate its completion by 
	 *	a specific ack message. 
	 */
	String activateMethod(final String methodName, final String... arguments);
	
	/**
	 *	Allows PlayGo to set a value of a given property for a specific object.
	 *	May be implemented by forcing each object to implement a SetProperty method 
	 *	for each property and then calling this method instead. 	
	 * @param value 
	 * @param propertyName 
	 * @param objectName 
	 */
	void setPropertyValue(final String propertyName, final String value);

	/**
	 *	Allows PlayGo to query a specific object for the value of a given property. 
	 */
	String getPropertyValue(final String propertyName);

	/**
	 * Used by PlayGo to order the GUI to highlight a specific object	
	 */
	void highlightObject(String objectName);
	
	/**
	 * Used by PlayGo to order the GUI to clear a highlighted object.	
	 */
	void clearObject();
	
	/**
	 * Used by PlayGo to know the position of an object on the screen so it can refer to it as part of the play-in/out process (show arrows, tooltips, context menus, etc.)
	 * Will be user for drawing arrows for example.. 	
	 */
	String getObjectPosition();
	
	int getPlayableWidth();
	int getPlayableHeight();
	
	/**
	 * In the initial phase, dynamic construction and destruction of objects is not supported.	
	 */
	void construct();
	
	void destroy();
}

