package il.ac.wis.cs.playgo.playtoolkit.api.web;

import java.util.ArrayList;
import java.util.List;

public class MethodToActivate 
{
	private String className;
	private String objectName;
	private String methodName;
	private List<String> arguments;
	
	//CTOR
	public MethodToActivate(String className, String objectName,
			String methodName, String... args) 
	{
		this.className = className;
		this.objectName = objectName;
		this.methodName = methodName;
		
		if(args!=null && args.length>0){
			this.arguments = new ArrayList<String>();
			for(String arg:args){
				this.arguments.add(arg);
			}
		}
		else{
			arguments = null;
		}
	}

	public String getClassName() {
		return className;
	}
	public String getObjectName() {
		return objectName;
	}
	public String getMethodName() {
		return methodName;
	}
	public List<String> getArguments() {
		return arguments;
	}
	
}
