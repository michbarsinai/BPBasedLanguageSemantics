package il.ac.wis.cs.playgo.playtoolkit.api.xml;

import il.ac.wis.cs.playgo.playtoolkit.api.intf.IPlayGo;
import il.ac.wis.cs.playgo.playtoolkit.api.intf.IPlayable;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XmlMessageHandler 
{
//	public static final String ACTIVATE_METHOD = "activateMethod";
//	public static final String SET_PROPERTY_VALUE = "setPropertyValue";
//	public static final String GET_PROPERTY_VALUE = "getPropertyValue";	
//
//	//public static final String END_OF_SUPERSTEP = "endOfSuperstep";
//	public static final String OBJECT_RIGHTCLICK_EVENT = "objectRightClickEvent";
//	
//	public static final String GETTER_RETURN_VALUE = "getterReturnValue";
//	public static final String VOID_ACK = "voidAck";
//	public static final String APP_IS_UP = "AppIsUp";

	private static final String OBLECT_EVENT = "objectEvent";
	private static final String OBLECT_PROPERTY_CHANGE = "objectPropertyChange";

	
	public static String handleXmlMessageReceivedFromPlaygo(
			String message, IPlayable playable)
	{
	    try  
	    {
	    	Node messageNode = getMessageNode(message);

	        NamedNodeMap messageAttributes = messageNode.getAttributes();
	        Node messageNameNode = messageAttributes.getNamedItem("name");
	        if(messageNameNode==null){
	        	return null;
	        }
	        String messageName = messageNameNode.getNodeValue();
	        
	        Node classNode, objectNode, methodNode, propertyNode, valueNode;
	        Node classNameNode, objectNameNode, methodNameNode, propertyNameNode, valueNameNode;
	        NamedNodeMap classAttributes, objectAttributes, methodAttributes, propertyAttributes, valueAttributes;
	        String className, objectName, methodName, propertyName, numOfArgsStr; 
	        
	        switch(messageName)
	        {
	        	case XmlMessageCreator.ACTIVATE_METHOD:
	    	        //class
	    	        classNode = messageNode.getFirstChild();
	    	        classAttributes = classNode.getAttributes();
	    	        classNameNode = classAttributes.getNamedItem("name");
	    	        className = classNameNode.getNodeValue();

	    	        //object
	    	        objectNode = classNode.getNextSibling();
	    	        objectAttributes = objectNode.getAttributes();
	    	        objectNameNode = objectAttributes.getNamedItem("name");
	    	        objectName = objectNameNode.getNodeValue();

	    	        //method
	    	        methodNode = objectNode.getNextSibling();
	    	        methodAttributes = methodNode.getAttributes();
	    	        methodNameNode = methodAttributes.getNamedItem("name");
	    	        methodName = methodNameNode.getNodeValue();

	    	        numOfArgsStr = methodNode.getNextSibling().getAttributes().getNamedItem("name").getNodeValue();
	    	        int numOfArgs = Integer.valueOf(numOfArgsStr);
	    	        if(numOfArgs>0)
	    	        {
	    	        	String[] args = new String[numOfArgs];
	    	        	Node valNode;
	    	        	
	    	        	valNode = methodNode.getNextSibling();
	    	        	
	    	        	for(int i=0; i<numOfArgs; i++)
	    	        	{
	    	        		valNode = valNode.getNextSibling();
	    	        		args[i] = valNode.getAttributes().getNamedItem("value").getNodeValue();
	    	        	}
		        		playable.activateMethod(className, objectName, methodName, args);
	    	        }
	    	        else{	//no args
	    	        	playable.activateMethod(className, objectName, methodName);
	    	        }

	        		return XmlMessageCreator.createVoidAckMessageXml();
	        		
	        	case XmlMessageCreator.SET_PROPERTY_VALUE:
	    	        //class
	    	        classNode = messageNode.getFirstChild();
	    	        classAttributes = classNode.getAttributes();
	    	        classNameNode = classAttributes.getNamedItem("name");
	    	        className = classNameNode.getNodeValue();

	    	        //object
	    	        objectNode = classNode.getNextSibling();
	    	        objectAttributes = objectNode.getAttributes();
	    	        objectNameNode = objectAttributes.getNamedItem("name");
	    	        objectName = objectNameNode.getNodeValue();

	    	        //property
	    	        propertyNode = objectNode.getNextSibling();
	    	        propertyAttributes = propertyNode.getAttributes();
	    	        propertyNameNode = propertyAttributes.getNamedItem("name");
	    	        propertyName = propertyNameNode.getNodeValue();

	    	        //value
	    	        valueNode = propertyNode.getNextSibling();
	    	        valueAttributes = valueNode.getAttributes();
	    	        valueNameNode = valueAttributes.getNamedItem("name");
	    	        String value = valueNameNode.getNodeValue();

	        		playable.setPropertyValue(className, objectName, propertyName, value);
	        		
	        		return XmlMessageCreator.createVoidAckMessageXml();
	        		
	        	case XmlMessageCreator.GET_PROPERTY_VALUE:
	    	        //class
	    	        classNode = messageNode.getFirstChild();
	    	        classAttributes = classNode.getAttributes();
	    	        classNameNode = classAttributes.getNamedItem("name");
	    	        className = classNameNode.getNodeValue();

	    	        //object
	    	        objectNode = classNode.getNextSibling();
	    	        objectAttributes = objectNode.getAttributes();
	    	        objectNameNode = objectAttributes.getNamedItem("name");
	    	        objectName = objectNameNode.getNodeValue();

	    	        //property
	    	        propertyNode = objectNode.getNextSibling();
	    	        propertyAttributes = propertyNode.getAttributes();
	    	        propertyNameNode = propertyAttributes.getNamedItem("name");
	    	        propertyName = propertyNameNode.getNodeValue();

	        		String res = playable.getPropertyValue(className, objectName, propertyName);
	        		String getPropertyXmlMessage = 
	        				XmlMessageCreator.creategetPropertyMessageXml(res);	        		
	        		return getPropertyXmlMessage;
	        		
	        	case XmlMessageCreator.HIGHLIGHT_OBJECT:
	    	        objectNode = messageNode.getFirstChild();
	    	        objectAttributes = objectNode.getAttributes();

	    	        objectNameNode = objectAttributes.getNamedItem("name");
	    	        objectName = objectNameNode.getNodeValue();

	    	        playable.highlightObject(objectName);
	    	        
	    	        return XmlMessageCreator.createVoidAckMessageXml();	

	        	case XmlMessageCreator.CLEAR_OBJECT:
	    	        playable.clearObject();
	    	        return XmlMessageCreator.createVoidAckMessageXml();	

	        	default:
	        		break;
	        }
	        
	/*          
			TransformerFactory tranFactory = TransformerFactory.newInstance();  
	        Transformer aTransformer = tranFactory.newTransformer();  
	        Source src = new DOMSource( document );  
	        Result dest = new StreamResult( new File( "xmlFileName.xml" ) );  
	        aTransformer.transform( src, dest );  
	*/      
	    }
	    catch (Exception ex){  
	        ex.printStackTrace();  
	    }
	    return null;
	}

	//getMessageNode
	private static Node getMessageNode(String message) 
	{
		try{
	    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	        DocumentBuilder builder = factory.newDocumentBuilder();  
	
	        // Use String reader  
			Document document = builder.parse( new InputSource(  
	                new StringReader( message ) ) );  
	
	        NodeList nodes = document.getChildNodes();
	        if(nodes==null || nodes.getLength()<1){
	        	return null;
	        }
	        Node messageNode = nodes.item(0);
	        return messageNode;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	//handleMessage
	public static void handleXmlMessageReceivedFromGui(String message, IPlayGo playgo)
	{
	    try  
	    {
	    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	        DocumentBuilder builder = factory.newDocumentBuilder();  
	
	        // Use String reader  
			Document document = builder.parse( new InputSource(  
	                new StringReader( message ) ) );  
	
	        NodeList nodes = document.getChildNodes();
	        if(nodes==null || nodes.getLength()<1){
	        	return;
	        }
	        //message
	        Node messageNode = nodes.item(0);
	        NamedNodeMap messageAttributes = messageNode.getAttributes();
	        Node messageNameNode = messageAttributes.getNamedItem("name");
	        String messageName = messageNameNode.getNodeValue();
	        
	        switch(messageName)
	        {
	        	case OBLECT_EVENT:
	    	        //class
	    	        Node classNode = messageNode.getFirstChild();
	    	        NamedNodeMap classAttributes = classNode.getAttributes();
	    	        Node classNameNode = classAttributes.getNamedItem("name");
	    	        String className = classNameNode.getNodeValue();

	    	        //object
	    	        Node objectNode = classNode.getNextSibling();
	    	        NamedNodeMap objectAttributes = objectNode.getAttributes();
	    	        Node objectNameNode = objectAttributes.getNamedItem("name");
	    	        String objectName = objectNameNode.getNodeValue();

	    	        //event
	    	        Node eventNode = objectNode.getNextSibling();
	    	        NamedNodeMap eventAttributes = eventNode.getAttributes();
	    	        Node eventNameNode = eventAttributes.getNamedItem("name");
	    	        String eventName = eventNameNode.getNodeValue();

	    	        //TODO: add arguments
	    	        playgo.objectEvent(className, objectName, eventName, null);
	        		break;

	        	case OBLECT_PROPERTY_CHANGE:
	    	        //class
	    	        classNode = messageNode.getFirstChild();
	    	        classAttributes = classNode.getAttributes();
	    	        classNameNode = classAttributes.getNamedItem("name");
	    	        className = classNameNode.getNodeValue();

	    	        //object
	    	        objectNode = classNode.getNextSibling();
	    	        objectAttributes = objectNode.getAttributes();
	    	        objectNameNode = objectAttributes.getNamedItem("name");
	    	        objectName = objectNameNode.getNodeValue();

	    	        String targetClass = null, targetObject=null;
	    	        
	    	        if(messageNode.getChildNodes().getLength()>4){
		    	        //class
		    	        classNode = objectNode.getNextSibling();
		    	        classAttributes = classNode.getAttributes();
		    	        classNameNode = classAttributes.getNamedItem("name");
		    	        targetClass = classNameNode.getNodeValue();

		    	        //object
		    	        objectNode = classNode.getNextSibling();
		    	        objectAttributes = objectNode.getAttributes();
		    	        objectNameNode = objectAttributes.getNamedItem("name");
		    	        targetObject = objectNameNode.getNodeValue();
	    	        }
	    	        //property
	    	        Node propertyNode = objectNode.getNextSibling();
	    	        NamedNodeMap propertyAttributes = propertyNode.getAttributes();
	    	        Node propertyNameNode = propertyAttributes.getNamedItem("name");
	    	        String propertyName = propertyNameNode.getNodeValue();

	    	        //value 
	    	        Node valueNode = propertyNode.getNextSibling();
	    	        NamedNodeMap valueAttributes = valueNode.getAttributes();
	    	        valueNode = valueAttributes.getNamedItem("name");
	    	        String value = valueNode.getNodeValue();
	    	        
	    	        if(messageNode.getChildNodes().getLength()>4){
	    	        	playgo.objectPropertyChanged(className, objectName,
							targetClass, targetObject, propertyName, value);
	    	        }else{
	    	        	playgo.objectPropertyChanged(className, objectName, propertyName, value);
	    	        }
	        		break;
	        		
	        	case XmlMessageCreator.OBJECT_RIGHTCLICK_EVENT:
	    	        //class
	    	        classNode = messageNode.getFirstChild();
	    	        classAttributes = classNode.getAttributes();
	    	        classNameNode = classAttributes.getNamedItem("name");
	    	        className = classNameNode.getNodeValue();

	    	        //object
	    	        objectNode = classNode.getNextSibling();
	    	        objectAttributes = objectNode.getAttributes();
	    	        objectNameNode = objectAttributes.getNamedItem("name");
	    	        objectName = objectNameNode.getNodeValue();

	    	        playgo.objectRightClicked(className, objectName);
	    	        break;
	    	        
	        	case XmlMessageCreator.APP_IS_UP:
	        		playgo.appIsUp();
	        		break;
	    	        
	        	default:
	        		break;
	        }
	        
	/*          
			TransformerFactory tranFactory = TransformerFactory.newInstance();  
	        Transformer aTransformer = tranFactory.newTransformer();  
	        Source src = new DOMSource( document );  
	        Result dest = new StreamResult( new File( "xmlFileName.xml" ) );  
	        aTransformer.transform( src, dest );  
	*/      
	    }
	    catch (Exception ex){  
	        ex.printStackTrace();  
	    }
	}

	//sendEndOfSuperstepEvent
//	public static void sendEndOfSuperstepEvent()
//	{
//		String endOfSuperstepMessageXml = 
//				XmlMessageCreator.createEndOfSuperstepMessageXml();
//		
//		PlayGoTcpAgent.sendMessage(endOfSuperstepMessageXml);
//	}

	//extractResultFromXmlMessageReceivedFromGui
	public static String extractResultFromXmlMessageReceivedFromGui(String xmlMessage) 
	{
	    try  
	    {
	    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	        DocumentBuilder builder = factory.newDocumentBuilder();  
	
	        // Use String reader  
			Document document = builder.parse( new InputSource(  
	                new StringReader( xmlMessage ) ) );  
	
	        NodeList nodes = document.getChildNodes();
	        if(nodes==null || nodes.getLength()<1){
	        	return null;
	        }
	        //message
	        Node messageNode = nodes.item(0);
	        NamedNodeMap messageAttributes = messageNode.getAttributes();
	        Node messageNameNode = messageAttributes.getNamedItem("name");
	        String messageName = messageNameNode.getNodeValue();
	        
	        switch(messageName)
	        {
	        	case XmlMessageCreator.GETTER_RETURN_VALUE:
	    	        //return val
	    	        Node returnValNode = messageNode.getFirstChild();
	    	        NamedNodeMap returnValAttributes = returnValNode.getAttributes();
	    	        Node returnValNameNode = returnValAttributes.getNamedItem("name");
	    	        String returnVal = returnValNameNode.getNodeValue();
	        		return returnVal;	
	        	case XmlMessageCreator.VOID_ACK:
	        		return XmlMessageCreator.VOID_ACK;	
	        	default:
	        		break;
	        }
	    }
	    catch (Exception ex){  
	        ex.printStackTrace();  
	    }		
	    return null;
	}

	//isEndOfSuperstep
//	public static boolean isEndOfSuperstep(String playgoMes) 
//	{
//		Node messageNode = getMessageNode(playgoMes);
//		
//        NamedNodeMap messageAttributes = messageNode.getAttributes();
//        Node messageNameNode = messageAttributes.getNamedItem("name");
//        if(messageNameNode==null){
//        	return false;
//        }
//        String messageName = messageNameNode.getNodeValue();
//		if(messageName.equals(END_OF_SUPERSTEP)){
//			return true;
//		}
//		return false;
//	}


}

