package il.ac.wis.cs.playgo.playtoolkit.api.intf;

public interface IPlayable{

	void stop();
	
	void setPlaygo(IPlayGo playgo);
	
	/**
	 *	Allows PlayGo to activate a given method for a specific object. 
	 *	If the method is synchronous, the GUI will indicate its completion by 
	 *	a specific ack message. 
	 */
	String activateMethod(final String className, final String objectName, 
			final String methodName, final String... arguments);
	
	/**
	 *	Allows PlayGo to set a value of a given property for a specific object.
	 *	May be implemented by forcing each object to implement a SetProperty method 
	 *	for each property and then calling this method instead. 	
	 * @param value 
	 * @param propertyName 
	 * @param objectName 
	 */
	void setPropertyValue(final String className, final String objectName, 
			final String propertyName, final String value);

	/**
	 *	Allows PlayGo to query a specific object for the value of a given property. 
	 */
	String getPropertyValue(final String className, final String objectName, final String propertyName);

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
	
	/**
	 * In the initial phase, dynamic construction and destruction of objects is not supported.	
	 */
	void construct();
	
	void destroy();
	
}

