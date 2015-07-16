package il.ac.wis.cs.playgo.playtoolkit.api.impl.tcp;

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

public class SystemModelTcpAgent implements IPlayGo
{
	private int PLAYGO_SERVER_PORT;								// = 8222;
	private int UI_SERVER_PORT;									// = PLAYGO_SERVER_PORT+1;
	private int SYSTEM_MODEL_SERVER_PORT;						// = UI_SERVER_PORT+1;	

	private static final String HOST = "localhost";
	
	private IPlayable playable = null;
	
	private Socket socket = null;
	
	//CTOR
	public SystemModelTcpAgent(IPlayable playable, int port) 
	{
		PLAYGO_SERVER_PORT = port;
		UI_SERVER_PORT = PLAYGO_SERVER_PORT+1;
		SYSTEM_MODEL_SERVER_PORT = UI_SERVER_PORT+1;
		
		this.playable = playable;
		listenAndHandlePlaygoMessages();
		appIsUp();
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
        try{
        	playgoListener = new ServerSocket(SYSTEM_MODEL_SERVER_PORT);
        	socket = null;        	       	
            while (true){
                try {
                    socket = playgoListener.accept(); 

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    String playgoMessage = in.readLine();
                    if(playgoMessage!=null)
                    {
                    	XmlMessageHandler.handleXmlMessageReceivedFromPlaygo(playgoMessage, playable);
                    }
                }catch(Exception ex){
                	ex.printStackTrace();
                } finally{
                    socket.close();
                }            
            }
        }catch(Exception ex){
        	ex.printStackTrace();
        }
        finally {
            try {
            	if(playgoListener!=null){
            		playgoListener.close();
            	}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }	
	}

	//IPlayGo
	//=======
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
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}

	@Override
	public void appIsUp()
	{
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
