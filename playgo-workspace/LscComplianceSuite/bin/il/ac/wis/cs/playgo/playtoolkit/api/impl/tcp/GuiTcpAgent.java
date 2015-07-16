package il.ac.wis.cs.playgo.playtoolkit.api.impl.tcp;

import il.ac.wis.cs.playgo.playtoolkit.Utils;
import il.ac.wis.cs.playgo.playtoolkit.api.intf.IPlayGo;
import il.ac.wis.cs.playgo.playtoolkit.api.intf.IPlayable;
import il.ac.wis.cs.playgo.playtoolkit.api.xml.XmlMessageCreator;
import il.ac.wis.cs.playgo.playtoolkit.api.xml.XmlMessageHandler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GuiTcpAgent implements IPlayGo
{
	private int PLAYGO_SERVER_PORT;	// = 8222;
	private int UI_SERVER_PORT;		// = PLAYGO_SERVER_PORT+1;

	private static String HOST = "localhost";

//	public final String CLICK_EVENT = 			"ClickEvent";
//	public final String SWIPE_EVENT = 			"SwipeEvent";
//	public final String PUSH_EVENT = 			"PushEvent";
//	public final String RELEASE_EVENT = 		"ReleaseEvent";
//	
//	public final String RIGHT_CLICK_EVENT = 	"RightClickEvent";

	private IPlayable playable = null;

	private Socket socket = null;
	
	//CTOR
	public GuiTcpAgent(IPlayable playable, int port) 
	{
		PLAYGO_SERVER_PORT = port;
		UI_SERVER_PORT = PLAYGO_SERVER_PORT+1;
		
		this.playable = playable;
		listenAndHandlePlaygoMessages();
		appIsUp();
	}

	//CTOR
	public GuiTcpAgent(IPlayable playable, int port, String host) 
	{
		PLAYGO_SERVER_PORT = port;
		UI_SERVER_PORT = PLAYGO_SERVER_PORT+1;

		if(host!=null){
			HOST = host;
		}
		this.playable = playable;
		listenAndHandlePlaygoMessages();
	}

	//listenAndHandlelaygoMessages
	private void listenAndHandlePlaygoMessages()
	{
		Runnable guiListener = new Runnable() {
			public void run(){
				listenToPlaygoMessages();
			}
		}; 
		Thread th = new Thread(guiListener); 
		th.start();
	}
	
	//listenToPlaygoMessages
	private void listenToPlaygoMessages()
	{
        ServerSocket playgoListener = null;
        String res = null;
        try{
        	playgoListener = new ServerSocket(UI_SERVER_PORT);
        	socket = null;        	       	
            while (true){
                try {
                    socket = playgoListener.accept(); 

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
           			DataOutputStream out = 
          	        		new DataOutputStream(socket.getOutputStream());

                    String playgoMessage = in.readLine();
                    if(playgoMessage!=null)
                    {
                    	res= XmlMessageHandler.handleXmlMessageReceivedFromPlaygo(
                    			playgoMessage, playable);

                    	out.writeBytes(res + '\n');	//send response message to playgo
                    }                    
                } finally{
                    socket.close();
                }            
            }
        }catch(Exception ex){
        	ex.printStackTrace();
        }
        finally {
            try {
            	playgoListener.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }	
	}


	//IPlayGo
	//=======
	
	/*
	 * Used by the GUI to inform PlayGo that a certain event has occurred in a specific 
	 * object (e.g., click, swipe, push, release, etc.).
	 * It is also used to inform PlayGo that a value of some object’s property was changed.
	 * Should it be used to notify PlayGo that a method was called 
	 * (in case one object calls another object within the GUI app, 
	 * not because it was instructed to do so by PlayGo).
	 */
	@Override
	public void objectEvent(String className, String objectName, String eventName, ArrayList<String> argTypes) 
	{
        String eventXml = XmlMessageCreator.createObjectEventXml(
        		className, objectName, eventName);  
        sendEventXml(eventXml);
	}

	@Override
	public void objectRightClicked(String className, String objectName) 
	{
        String eventXml = XmlMessageCreator.createObjectRightClickEventXml(className, objectName);  
        sendEventXml(eventXml);
	}

	@Override
	public void objectPropertyChanged(String className, String objectName,
			String propertyName, String value) 
	{
        String eventXml = XmlMessageCreator.createObjectPropertyChangedXml(
        		className, objectName, propertyName, value);  
        sendEventXml(eventXml);
	}

	@Override
	public void objectPropertyChanged(String sourceClass, String sourceObject,
			String targetClass, String targetObject, String propertyName, String value) 
	{
        String eventXml = XmlMessageCreator.createObjectPropertyChangedXml(
        		sourceClass, sourceObject, 
        		targetClass, targetObject,
        		propertyName, value);  
        sendEventXml(eventXml);
	}

	@Override
	public void setPlayable(IPlayable playable) {
		this.playable = playable;
	}

	@Override
	public IPlayable getPlayable() {
		return playable;
	}

	//sendEventXml
	private void sendEventXml(String eventXml){
		Socket s = null; 
		try{
			s = new Socket(HOST, PLAYGO_SERVER_PORT);
			DataOutputStream out = new DataOutputStream(s.getOutputStream());

			out.writeBytes(eventXml + '\n');	//send message XML via tcp	
		}
		catch(Exception ex){
			ex.printStackTrace();
	    }finally{
	    	try {
	    		if (s != null){
	    			s.close();
	    		}
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}

	@Override
	public void appIsUp()
	{
		if (Utils.isPlayInMode())
			return;
		
//        String playInStr = System.getProperty("playInMode");
//        if(playInStr!=null && playInStr.equals("true")){
//        	return;	//play-in
//        }
        String eventXml = XmlMessageCreator.createAppIsUpEventXml();  
        sendEventXml(eventXml);
	}
	
	@Override
	public void stop()
	{
		try{
			if(socket!=null){
				socket.close();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
