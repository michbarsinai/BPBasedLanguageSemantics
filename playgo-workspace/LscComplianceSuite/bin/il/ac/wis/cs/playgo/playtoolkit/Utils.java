package il.ac.wis.cs.playgo.playtoolkit;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Properties;

public class Utils 
{
	public static final String SM_TYPES = "playgo.systemmodel.types";
	
	public static final String RUNTIME_PROPERTIES_RELATIVE_PATH = "resources/runtime.properties";
	public static final String SYSTEMMODEL_DEBUG_PORT = "systemmodelDebugPort";

	
	//invokeMessage
	public static void invokeMessage(String sourceName, String sourceClassName, 
			String targetName,String targetClassName, String methodName, 
			ArrayList<String> argValues)
	{
		Method meth=null;
		try {
			Object targetObj = AppObjects.getObject(targetName, targetClassName);
						
			//Source object exists
			Object sourceObj = AppObjects.getObject(sourceName, sourceClassName);
			
			//No Args
			if(argValues==null || argValues.size()<1){
				try{
					meth = getMethod(sourceObj.getClass(), targetObj.getClass(), methodName, null, true);
					
				} catch (NoSuchMethodException e)
				{
					System.out.println("No such method: " + "Wrapper"+methodName+"("+targetObj.getClass()+")");
					meth = null;
				}
				if (meth != null)
				{
					meth.invoke(sourceObj,targetObj);
				}
			}
			else	//Args exist
			{
				boolean wrapperExists = true;
				
				meth = getMethod(sourceObj.getClass(), targetObj.getClass(), 
						methodName, argValues, wrapperExists);
				if(meth==null)
				{
					//we allow setter with no wrapper for cases where the GUI sets a property value and
					//sends the updated value to the behavior and no set message exists in the LSCs
					if(methodName.startsWith("set"))	//setter
					{
						wrapperExists = false;
						meth = getMethod(sourceObj.getClass(), null, methodName, argValues, wrapperExists);
						if(meth==null)
						{
							System.out.println("No such method: "
									+ sourceObj.getClass().getSimpleName() +"."+methodName);
							return;
						}
					}else{								//not a setter
						return;
					}
				}
				//loop vars
				int numOfArgs = argValues.size();
				Object[] values;
				if(wrapperExists){
					values = new Object[numOfArgs+1];
					values[0] = targetObj;
				}else{
					values = new Object[numOfArgs];					
				}
				for(int i=0; i<numOfArgs; i++){
					if(wrapperExists){
						values[i+1]=getObjectVal(meth.getParameterTypes()[i+1],argValues.get(i));
					}else{
						values[i]=getObjectVal(meth.getParameterTypes()[i],argValues.get(i));
					}
				}
				meth.invoke(sourceObj,values);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Method getMethod(Class<?> sourceClass, Class<?> targetClass,
			String methodName, ArrayList<String> argValues, boolean wrapperExists) throws NoSuchMethodException, SecurityException {
		try{
			if (wrapperExists){
				methodName = "Wrapper" + methodName;
			}
			Method meth = null;
			if (argValues == null){
				meth = sourceClass.getMethod(methodName,targetClass);
				return meth;
			}
			else {
				if (wrapperExists){
					meth = getMethod(sourceClass, targetClass, methodName, argValues.size() + 1);
				}
				else{
					meth = getMethod(sourceClass, methodName, argValues.size());
				}
				return meth;
			}
		}
		catch (NoSuchMethodException e){
			if (targetClass == null || targetClass.getSuperclass() == null){
				throw new NoSuchMethodException();
			}
			targetClass = targetClass.getSuperclass();
			return getMethod(sourceClass, targetClass, methodName, argValues, wrapperExists);
				
		}
		
	}

	//getMethod (for wrapper calls)
	private static Method getMethod(Class<?> cls, Class<?> targetCls, 
			String methodName, int numOfArgs) 
	{
		if(cls ==null){
			return null;
		}
		if(cls==null || cls.getMethods()==null || cls.getMethods().length<1){
			return null;
		}
		for(Method m:cls.getMethods()){
			if( ! m.getName().equals(methodName)){
				continue;
			}
			if(m.getParameterTypes()==null || m.getParameterTypes().length != numOfArgs){
				continue;
			}
			if(m.getParameterTypes()[0] != targetCls){
				continue;				
			}
			return m;
		}
		return null;
	}

	//getMethod
	private static Method getMethod(Class<?> cls, String methodName, int numOfArgs) 
	{
		if(cls ==null){
			return null;
		}
		if(cls==null || cls.getMethods()==null || cls.getMethods().length<1){
			return null;
		}
		for(Method m:cls.getMethods()){
			if( ! m.getName().equals(methodName)){
				continue;
			}
			if(m.getParameterTypes()==null || m.getParameterTypes().length != numOfArgs){
				continue;
			}
			
			return m;
		}
		return null;
	}
	
	//getObjectVal
	private static Object getObjectVal(Class<?> argType, String strValue) 
	{
		if (strValue == null)
			return null;
		
		Object argVal=null;
		
		if(argType == String.class){
			argVal=strValue;
		}
		else if(argType == Integer.class || argType == int.class){
			argVal=Integer.parseInt(strValue);
		}
		else if(argType == Boolean.class || argType == boolean.class){
			argVal=Boolean.parseBoolean(strValue);
		}
		else if(argType == Long.class || argType == long.class){
			argVal=Long.parseLong(strValue);
		}
		else if(argType == Float.class || argType == float.class){
			argVal=Float.parseFloat(strValue);
		}
		else if(argType == Double.class || argType == double.class){
			argVal=Double.parseDouble(strValue);
		}
		else if(argType == Byte.class || argType == byte.class){
			argVal=Byte.parseByte(strValue);
		}
		else if(argType == Short.class || argType == short.class){
			argVal=Short.parseShort(strValue);
		}
		else if(argType == Character.class || argType == char.class){
			argVal=new String(strValue).charAt(0);
		}
		//System Model Enum
		if(argType.getName().startsWith(SM_TYPES)){
			for(Object enumConst : argType.getEnumConstants()){
				if(enumConst.toString().equals(strValue)){
					return enumConst;
				}
			}
			//argVal = argType.getEnumConstants()[0].toString()
		}
		return argVal;
	}
	
	//invokeMessage
	public static void invokeSetter(String sourceName, String sourceClassName, 
			String targetName, String targetClassName,
			String setterName, Class<?> argType, String strValue)
	{
		Class<?>[] argTypes = new Class<?>[1];
		argTypes[0] = argType;
		ArrayList<String> parameterValues = new ArrayList<String>();
		parameterValues.add(strValue);
		
		invokeMessage(sourceName, sourceClassName, 
				targetName, targetClassName, 
				setterName, 
				//argTypes, 
				parameterValues);
	}
	
	public static boolean isPlayInMode(){
		String playInStr = System.getProperty("playInMode");
		if(playInStr!=null && playInStr.equals("true")){
			return true;
		}
		return false;
	}


	public static int getPort() 
	{
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(getRuntimePropertiesPath()));
			String portStr = properties.getProperty(SYSTEMMODEL_DEBUG_PORT);
			int port = Integer.valueOf(portStr);
			return port;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}		
	}

	//getRuntimePropertiesPath
	private static String getRuntimePropertiesPath(){
		try{
			return new File(RUNTIME_PROPERTIES_RELATIVE_PATH).getAbsolutePath().toString();
		}catch (Exception e) {
			System.out.println(RUNTIME_PROPERTIES_RELATIVE_PATH + " file does not exist");
			return null;
		}
	}

}
