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
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PlayGoTcpAgent implements IPlayable
{
	private static final String HOST = "localhost";
	
	private int PLAYGO_SERVER_PORT;	// = 8222;
	private int UI_SERVER_PORT;		// = PLAYGO_SERVER_PORT+1;
	//private static PrintWriter out = null;

	private IPlayGo playgo = null;
	private List<String> guiMessages = null;

    private ServerSocket listener = null;
    private Socket socket = null;

    private TimerTask playGoMessagesHandler = null;
	private boolean goOn = true;
	
	//CTOR
	public PlayGoTcpAgent(int port)
	{
		PLAYGO_SERVER_PORT = port;
		UI_SERVER_PORT = PLAYGO_SERVER_PORT+1;

		guiMessages = Collections.synchronizedList(new ArrayList<String>());
		
		collectGuiMessages();
		
		handleGuiMessages();
	}
	
	//start listening to messages from gui			
	private void collectGuiMessages()
	{
		Runnable uiEventListener = new Runnable() {
			@Override
			public void run() 
			{
				listenToGuiMessages();
			}
		}; 
		goOn=true;
		Thread th = new Thread(uiEventListener); 
		th.start();
	}

	//listenToGuiMessages
	private void listenToGuiMessages()
	{
        listener = null;
    	socket = null;
        try 
        {
        	listener = new ServerSocket(PLAYGO_SERVER_PORT);
            while (goOn) 
            {
                socket = listener.accept(); 
                try {
                    BufferedReader in = new BufferedReader(
                         new InputStreamReader(socket.getInputStream()));                	

                    String uiMessageStr = in.readLine();
                    guiMessages.add(uiMessageStr);
                
                }catch(Exception ex){
                	ex.printStackTrace();
                } finally{
                    socket.close();
                }            
            }
        }catch(Exception ex){
        	//ex.printStackTrace();
        }
        finally {
            try {
            	if(socket!=null){
            		socket.close();
            	}
            	if(listener!=null){            	
            		listener.close();
            	}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	
	//handle messages received from Gui;
	private void handleGuiMessages()
	{
		playGoMessagesHandler = new TimerTask() 
		{
			@Override
			public void run() 
			{
				if(guiMessages!=null && guiMessages.size()>0){
					synchronized(guiMessages){
						for(String guiMesStr:guiMessages)
						{
							XmlMessageHandler.handleXmlMessageReceivedFromGui(
									guiMesStr, playgo);
			   	        }
						guiMessages.clear();
					}
				}
			}
		};
		
		Timer timer = new Timer();
        timer.scheduleAtFixedRate(playGoMessagesHandler, 10, 100);
	}
	
	//sendMessage
	public void sendMessage(String setPropertyValueXmlMessage) 
	{
		Socket s = null; 
		try{
			s = new Socket(HOST, UI_SERVER_PORT);

			DataOutputStream out = 
	        		new DataOutputStream(s.getOutputStream());

			out.writeBytes(setPropertyValueXmlMessage + '\n');
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

	//sendMessageAndWaitForResponse
	public String sendMessage(String xmlMessage, boolean waitForResponse) 
	{
		Socket s = null; 
		try{
			s = new Socket(HOST, UI_SERVER_PORT);

			BufferedReader input = 
	        		new BufferedReader(new InputStreamReader(s.getInputStream()));
			DataOutputStream out = 
	        		new DataOutputStream(s.getOutputStream());

			out.writeBytes(xmlMessage + '\n');		//send message
			out.flush();
			
			if(waitForResponse)
			{
		    	String answerXml = input.readLine();	//waits for the answer
		    	
				if(answerXml==null || answerXml.equals("null")){
					return null;
				}
				String answer =XmlMessageHandler.
						extractResultFromXmlMessageReceivedFromGui(answerXml); 
				
				return answer;
			}
			else{
				return "noNeedForResponse";
			}
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
		return null;
	}


	//IPlayable
	//=========
	
	@Override
	public void setPlaygo(IPlayGo playgo) {
		this.playgo = playgo;
	}

	//BLOCKING until a done ack is received from the gui project
	@Override
	public String activateMethod(String className, String objectName, 
			String methodName, String... arguments)
	{
		String activateMethodXmlMessage = XmlMessageCreator.
				createActivateMethodMessage(className, objectName, methodName, arguments);
		
		String res = sendMessage(activateMethodXmlMessage, true);
		
		return res;
	}

	//BLOCKING until a done ack is received from the gui project
	@Override
	public void setPropertyValue(String className, String objectName, 
			String propertyName, String val) 
	{
		String setPropertyValueXmlMessage = XmlMessageCreator.
				createSetPropertyValueMessage(className, objectName, propertyName, val);
		
		sendMessage(setPropertyValueXmlMessage, true);
	}

	//BLOCKING until a return value is received from the gui project
	@Override
	public String getPropertyValue(String className, String objectName, String propertyName) 
	{
		String activateMethodXmlMessage = XmlMessageCreator.
				createGetPropertyMessage(className, objectName, propertyName);
		
		String res = sendMessage(activateMethodXmlMessage, true);
		
		return res;
	}

	//TODO Later
	@Override
	public void highlightObject(String objectName) 
	{
		String activateMethodXmlMessage = XmlMessageCreator.
			createHighlightObjectMessage(objectName);
		
		sendMessage(activateMethodXmlMessage, true);
	}

	@Override
	public void clearObject() 
	{
		String activateMethodXmlMessage = XmlMessageCreator.
				createClearObjectMessage();
			
		sendMessage(activateMethodXmlMessage, true);
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

	@Override
	public void stop() {
		goOn=false;
		try{
			if(playGoMessagesHandler!=null){
				playGoMessagesHandler.cancel();
			}
			if(listener!=null){
				listener.close();
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

}

