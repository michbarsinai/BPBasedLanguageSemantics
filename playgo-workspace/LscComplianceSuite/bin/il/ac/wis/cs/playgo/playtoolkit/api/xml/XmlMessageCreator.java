package il.ac.wis.cs.playgo.playtoolkit.api.xml;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlMessageCreator {

	public static final String OBJECT_EVENT = "objectEvent";
	public static final String OBJECT_PROPERTY_CHANGE = "objectPropertyChange";
	public static final String OBJECT_RIGHTCLICK_EVENT = "objectRightClickEvent";
	
	public static final String SET_PROPERTY_VALUE = "setPropertyValue";
	public static final String GET_PROPERTY_VALUE = "getPropertyValue";
	public static final String ACTIVATE_METHOD= "activateMethod";
	
	public static final String APP_IS_UP = "AppIsUp";
	public static final String HIGHLIGHT_OBJECT = "hightlightObject";
	public static final String CLEAR_OBJECT = "clearObject";

	public static final String GETTER_RETURN_VALUE = "getterReturnValue";
	public static final String VOID_ACK = "voidAck";
	
	//createObjectEventXml (IPlayGo)
	public static String createObjectEventXml(
			String className, String objectName, String eventName)
	{
		Document doc = createDoc();
		
		Element messageElement = doc.createElement("message");
		messageElement.setAttribute("name", OBJECT_EVENT);
		doc.appendChild(messageElement);

		Element classElement = doc.createElement("class");
		classElement.setAttribute("name", className);
		messageElement.appendChild(classElement);

		Element objectElement = doc.createElement("object");
		objectElement.setAttribute("name", objectName);
		messageElement.appendChild(objectElement);

		Element eventElement = doc.createElement("event");
		eventElement.setAttribute("name", eventName);
		messageElement.appendChild(eventElement);

		String xmlString = getString(doc);
		return xmlString;
	}

	//appIsUpEventXml
	public static String createAppIsUpEventXml()
	{
		Document doc = createDoc();
		
		Element messageElement = doc.createElement("message");
		messageElement.setAttribute("name", APP_IS_UP);
		doc.appendChild(messageElement);
		
		String xmlString = getString(doc);
		return xmlString;
	}
	
	//createClearObjectMessage
	public static String createClearObjectMessage()
	{
		Document doc = createDoc();
		
		Element messageElement = doc.createElement("message");
		messageElement.setAttribute("name", CLEAR_OBJECT);
		doc.appendChild(messageElement);
		
		String xmlString = getString(doc);
		return xmlString;
	}
	
	//createHighlightObjectMessage
	public static String createHighlightObjectMessage(String objectName)
	{
		Document doc = createDoc();
		
		Element messageElement = doc.createElement("message");
		messageElement.setAttribute("name", HIGHLIGHT_OBJECT);
		doc.appendChild(messageElement);
		
		Element objectElement = doc.createElement("object");
		objectElement.setAttribute("name", objectName);
		messageElement.appendChild(objectElement);

		String xmlString = getString(doc);
		return xmlString;
	}
	
	//createObjectPropertyChangedXml
	public static String createObjectPropertyChangedXml(String className,
			String objectName, String propertyName, String value) 
	{
		Document doc = createDoc();
		
		Element messageElement = doc.createElement("message");
		messageElement.setAttribute("name", OBJECT_PROPERTY_CHANGE);
		doc.appendChild(messageElement);

		Element classElement = doc.createElement("class");
		classElement.setAttribute("name", className);
		messageElement.appendChild(classElement);

		Element objectElement = doc.createElement("object");
		objectElement.setAttribute("name", objectName);
		messageElement.appendChild(objectElement);

		Element propertyElement = doc.createElement("property");
		propertyElement.setAttribute("name", propertyName);
		messageElement.appendChild(propertyElement);

		Element valueElement = doc.createElement("value");
		valueElement.setAttribute("name", value);
		messageElement.appendChild(valueElement);

		String xmlString = getString(doc);
		return xmlString;
	}

	//createObjectPropertyChangedXml
	public static String createObjectPropertyChangedXml(
			String sourceClass, String sourceObject, 
			String targetClass, String targetObject,
			String propertyName, String value) 
	{
		Document doc = createDoc();
		
		Element messageElement = doc.createElement("message");
		messageElement.setAttribute("name", OBJECT_PROPERTY_CHANGE);
		doc.appendChild(messageElement);

		Element sourceClassElement = doc.createElement("sourceClass");
		sourceClassElement.setAttribute("name", sourceClass);
		messageElement.appendChild(sourceClassElement);

		Element sourceObjectElement = doc.createElement("sourceObject");
		sourceObjectElement.setAttribute("name", sourceObject);
		messageElement.appendChild(sourceObjectElement);

		Element targetClassElement = doc.createElement("targetClass");
		targetClassElement.setAttribute("name", targetClass);
		messageElement.appendChild(targetClassElement);

		Element targetObjectElement = doc.createElement("targetObject");
		targetObjectElement.setAttribute("name", targetObject);
		messageElement.appendChild(targetObjectElement);

		Element propertyElement = doc.createElement("property");
		propertyElement.setAttribute("name", propertyName);
		messageElement.appendChild(propertyElement);

		Element valueElement = doc.createElement("value");
		valueElement.setAttribute("name", value);
		messageElement.appendChild(valueElement);

		String xmlString = getString(doc);
		return xmlString;
	}


	//createObjectRightClickEventXml (IPlayGo)
	public static String createObjectRightClickEventXml(String className, String objectName) 
	{
		Document doc = createDoc();
		
		Element messageElement = doc.createElement("message");
		messageElement.setAttribute("name", OBJECT_RIGHTCLICK_EVENT);
		doc.appendChild(messageElement);

		Element classElement = doc.createElement("class");
		classElement.setAttribute("name", className);
		messageElement.appendChild(classElement);

		Element objectElement = doc.createElement("object");
		objectElement.setAttribute("name", objectName);
		messageElement.appendChild(objectElement);

		String xmlString = getString(doc);
		return xmlString;
	}

	//createSetPropertyValueMessage
	public static String createSetPropertyValueMessage(String className,
			String objectName, String propertyName, String val)
	{
		Document doc = createDoc();
		
		Element messageElement = doc.createElement("message");
		messageElement.setAttribute("name", SET_PROPERTY_VALUE);
		doc.appendChild(messageElement);

		Element classElement = doc.createElement("class");
		classElement.setAttribute("name", className);
		messageElement.appendChild(classElement);

		Element objectElement = doc.createElement("object");
		objectElement.setAttribute("name", objectName);
		messageElement.appendChild(objectElement);

		Element eventElement = doc.createElement("property");
		eventElement.setAttribute("name", propertyName);
		messageElement.appendChild(eventElement);

		Element valueElement = doc.createElement("value");
		valueElement.setAttribute("name", val);
		messageElement.appendChild(valueElement);

		String xmlString = getString(doc);
		return xmlString;
	}

	//createGetPropertyMessage
	public static String createGetPropertyMessage(String className,
			String objectName, String propertyName) 
	{
		Document doc = createDoc();
		
		Element messageElement = doc.createElement("message");
		messageElement.setAttribute("name", GET_PROPERTY_VALUE);
		doc.appendChild(messageElement);

		Element classElement = doc.createElement("class");
		classElement.setAttribute("name", className);
		messageElement.appendChild(classElement);

		Element objectElement = doc.createElement("object");
		objectElement.setAttribute("name", objectName);
		messageElement.appendChild(objectElement);

		Element eventElement = doc.createElement("property");
		eventElement.setAttribute("name", propertyName);
		messageElement.appendChild(eventElement);

		String xmlString = getString(doc);
		return xmlString;
	}


	//createActivateMethodMessage
	public static String createActivateMethodMessage(String className,
			String objectName, String methodName, String[] arguments) 
	{
		Document doc = createDoc();
		
		Element messageElement = doc.createElement("message");
		messageElement.setAttribute("name", ACTIVATE_METHOD);
		doc.appendChild(messageElement);

		Element classElement = doc.createElement("class");
		classElement.setAttribute("name", className);
		messageElement.appendChild(classElement);

		Element objectElement = doc.createElement("object");
		objectElement.setAttribute("name", objectName);
		messageElement.appendChild(objectElement);

		Element methodElement = doc.createElement("method");
		methodElement.setAttribute("name", methodName);
		messageElement.appendChild(methodElement);

		if(arguments==null || arguments.length<1){
			Element numOfArgsElement = doc.createElement("numOfArgs");
			numOfArgsElement.setAttribute("name", "0");
			messageElement.appendChild(numOfArgsElement);
		}
		else{
			Element numOfArgsElement = doc.createElement("numOfArgs");
			numOfArgsElement.setAttribute("name", String.valueOf(arguments.length));
			messageElement.appendChild(numOfArgsElement);
			
			Element argVal;
			for(int i=0; i<arguments.length; i++){
				argVal = doc.createElement("arg"+i);
				argVal.setAttribute("value", arguments[i]);
				messageElement.appendChild(argVal);
			}
		}
		
		String xmlString = getString(doc);
		return xmlString;
	}

	//creategetPropertyMessageXml
	public static String creategetPropertyMessageXml(String val)
	{
		Document doc = createDoc();
		
		Element messageElement = doc.createElement("message");
		messageElement.setAttribute("name", GETTER_RETURN_VALUE);
		doc.appendChild(messageElement);

		Element returnValElement = doc.createElement("returnVal");
		returnValElement.setAttribute("name", val);
		messageElement.appendChild(returnValElement);

		String xmlString = getString(doc);
		return xmlString;
	}
	
	//createVoidAckMessageXml
	public static String createVoidAckMessageXml()
	{
		Document doc = createDoc();
		
		Element messageElement = doc.createElement("message");
		messageElement.setAttribute("name", VOID_ACK);
		doc.appendChild(messageElement);

		String xmlString = getString(doc);
		return xmlString;
	}
	
	//createEndOfSuperstepMessageXml
//	public static String createEndOfSuperstepMessageXml() 
//	{
//		Document doc = createDoc();
//		
//		Element messageElement = doc.createElement("message");
//		messageElement.setAttribute("name", END_OF_SUPERSTEP);
//		doc.appendChild(messageElement);
//
//		String xmlString = getString(doc);
//		return xmlString;
//	}


	//getString
	private static String getString(Document doc) {
		try{
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new StringWriter());
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(source, result);
			
			return result.getWriter().toString();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	//createDoc
	private static Document createDoc() {
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			return docBuilder.newDocument();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

}
