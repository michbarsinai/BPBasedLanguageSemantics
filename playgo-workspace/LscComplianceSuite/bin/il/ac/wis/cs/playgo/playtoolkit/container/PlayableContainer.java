package il.ac.wis.cs.playgo.playtoolkit.container;

import il.ac.wis.cs.playgo.playtoolkit.api.intf.IPlayableComponent;
import il.ac.wis.cs.playgo.playtoolkit.api.intf.IPlayableContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class PlayableContainer extends PlayableFramework implements IPlayableComponent, IPlayableContainer {
	
	protected HashMap<String, ContainedComponent> containedComponentsMap = new HashMap<String, ContainedComponent>(); // mapping of the various contained components to the container method and properties they represent
	
	String name;
	String className;
	
	private IPlayableContainer framework;

	public PlayableContainer(String name, String className, IPlayableContainer framework) {
		super();
		this.name = name;
		this.className = className;
		
		this.framework = framework;
		if (this.framework != null)
			this.framework.add(this, this.getName());
	}
	
	public void add(IPlayableComponent comp, String compName, String containerPropertyOrMethodName, String compPropertyOrMethodName)
	{
		super.add(comp, compName);
		map(containerPropertyOrMethodName, comp, compPropertyOrMethodName);
	}
	
	public void map(String containerPropertyOrMethodName, IPlayableComponent comp,  String compPropertyOrMethodName)
	{
		ContainedComponent containedComp = new ContainedComponent(comp.getName());
		
		containedComp.setPropertyOrMethodName(compPropertyOrMethodName);
		
		containedComponentsMap.put(containerPropertyOrMethodName, containedComp);
	}

	@Override
	/**
	 * Direction: GUI --> Behaviour
	 * A method of a GUI component was invoked.
	 * Need to find the real name of the method it represents in the behaviour, and trigger the 
	 * framework.objectEvent with the found method name (e.g., invoking the method 'clear' in a component that is included in the container 
	 * may represent the invocation of the method clearMsg in the container).
	 */

	public void objectEvent(String className, String objectName,
			String eventName, ArrayList<String> args) {
				
		Iterator<ContainedComponent> components = containedComponentsMap.values().iterator();
		int index = 0;
		
		while (components.hasNext())
		{
			ContainedComponent next = components.next();
			String containerMethodName;
			if ((next.compName.equals(objectName)) && 
				(next.getPropertyOrMethodName()!= null) && next.propertyOrMethodName.equals(eventName))
			{
				containerMethodName = (String) containedComponentsMap.keySet().toArray()[index];
				this.framework.objectEvent(this.className, this.name, containerMethodName, args);
			}
			index++;
		}
				
	}

	/**
	 * Direction: GUI --> Behaviour
	 * When the GUI object is right-clicked notify PlayGo which is then creates the relevant right-click menu based on the system model.
	 */
	@Override
	public void objectRightClicked(String className, String objectName) 
	{
		Iterator<ContainedComponent> components = containedComponentsMap.values().iterator();
		
		while (components.hasNext())
		{
			ContainedComponent next = components.next();
			if (next.compName.equals(objectName)) 
			{
				this.framework.objectRightClicked(this.className, this.name);
				return;
			}
		}
		this.framework.objectRightClicked(className, objectName);
	}



	@Override
	/**
	 * Direction: GUI --> Behaviour
	 * A property of a GUI component was changed.
	 * Need to find the real name of the property it represents in the behaviour, and trigger the 
	 * framework.objectPropertyChanged with the found property name (e.g., setting a text field of the Text component that is included in the container 
	 * may represent the change of a property named msg in the container).
	 */
	public void objectPropertyChanged(String className, String objectName,
			String propertyName, String value) {
		
		Iterator<ContainedComponent> components = containedComponentsMap.values().iterator();
		int index = 0;
		
		while (components.hasNext())
		{
			ContainedComponent next = components.next();
			String containerMethodName;
			if ((next.compName.equals(objectName)) && 
				(next.getPropertyOrMethodName()!= null) && next.getPropertyOrMethodName().equals(propertyName))
			{
				containerMethodName = (String) containedComponentsMap.keySet().toArray()[index];
				this.framework.objectPropertyChanged(this.className, this.name, containerMethodName, value);
			}
			index++;
		}
				
				
	}
	
	@Override
	public void objectPropertyChanged(String sourceClassName, String sourceObjectName,
			String targetClassName, String targetObjectName,
			String propertyName, String value) 
	{
		Iterator<ContainedComponent> components = containedComponentsMap.values().iterator();
		int index = 0;
		
		while (components.hasNext())
		{
			ContainedComponent next = components.next();
			String containerMethodName;
			if ((next.compName.equals(targetObjectName)) && 
				(next.getPropertyOrMethodName()!= null) && next.getPropertyOrMethodName().equals(propertyName))
			{
				containerMethodName = (String) containedComponentsMap.keySet().toArray()[index];
				this.framework.objectPropertyChanged(sourceClassName, sourceObjectName, this.className, this.name, containerMethodName, value);
			}
			index++;
		}

	}


	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public IPlayableContainer getFramework() {
		return this.framework;
	}

	@Override
	public void setFramework(IPlayableContainer framework) {
		this.framework = framework;
	}
	
	/**
	 * Direction: Behaviour --> GUI
 	 * A method was invoked on the behavior side. If the object has a GUI representation it has to invoke the method on the actual GUI object. 
	 * Need to find the actual GUI object and invoke its actual method.
	 */
	@Override
	public String activateMethod(String methodName, String... arguments) {

		ContainedComponent comp = containedComponentsMap.get(methodName);
		
		if(comp == null)
			return null;
		
		IPlayableComponent guiComp = allPlayableComponents.get(comp.getName());
		String guiMethodName = comp.getPropertyOrMethodName();
		
		return guiComp.activateMethod(guiMethodName, arguments);
	}

	/**
	 * Direction: Behaviour --> GUI
 	 * A property change was invoked on the behavior side. If the object has a GUI representation it has to invoke the property change on the actual GUI object. 
	 * Need to find the actual GUI object and invoke its actual property change.
	 */
	@Override
	public void setPropertyValue(String propertyName, String value) {
		
		ContainedComponent comp = containedComponentsMap.get(propertyName);
		
		if (comp == null)
			return;
		
		IPlayableComponent guiComp = allPlayableComponents.get(comp.getName());
		String guiPropName = comp.getPropertyOrMethodName();
		
		guiComp.setPropertyValue(guiPropName, value);
	}

	@Override
	public String getPropertyValue(String propertyName) {
		
		ContainedComponent comp = containedComponentsMap.get(propertyName);
		
		if (comp == null)
			return null;
		
		IPlayableComponent guiComp = allPlayableComponents.get(comp.getName());
		
		String guiPropName = comp.getPropertyOrMethodName();
		
		return guiComp.getPropertyValue(guiPropName);
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
	
	class ContainedComponent
	{
		private String compName;
		private String propertyOrMethodName; // the actual name of the component's method or property that should be invoked or changed when the logical container's method or property is invoked or changed.
		
		
		public ContainedComponent(String compName) {
			this.compName = compName;
			
		}
		
		public Object getName() {
			return compName;
		}

		public String getPropertyOrMethodName()
		{
			return this.propertyOrMethodName;
		}
		
		public void setPropertyOrMethodName(String name)
		{
			this.propertyOrMethodName = name;
		}
	}

	@Override
	protected void initialize() {
	}

	@Override
	public int getPlayableWidth() 
	{
		if(allPlayableComponents==null || allPlayableComponents.values()==null ||
				allPlayableComponents.values().size()<1){
			return 0;
		}
		int width = 0;
		Iterator<IPlayableComponent> components = allPlayableComponents.values().iterator();
		IPlayableComponent pcomp;
		
		while (components.hasNext())
		{
			pcomp = components.next();
			if(pcomp.getPlayableWidth() > width){
				width = pcomp.getPlayableWidth();
			}
		}
		return width;
	}

	@Override
	public int getPlayableHeight() 
	{
		if(allPlayableComponents==null || allPlayableComponents.values()==null ||
				allPlayableComponents.values().size()<1){
			return 0;
		}
		int height = 0;
		Iterator<IPlayableComponent> components = allPlayableComponents.values().iterator();
		IPlayableComponent pcomp;
		
		while (components.hasNext())
		{
			pcomp = components.next();
			height += pcomp.getPlayableHeight(); 
		}
		return height;
	}

}
