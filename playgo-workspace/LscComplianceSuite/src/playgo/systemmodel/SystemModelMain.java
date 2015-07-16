
package playgo.systemmodel;

import il.ac.wis.cs.playgo.playtoolkit.Utils;
import il.ac.wis.cs.playgo.playtoolkit.api.intf.IPlayGo;
import playgo.systemmodel.generated.SystemModelMainGenerated;


/**
 * This file was automatically generated using PlayGo system-model.
 * 
 */
public class SystemModelMain
    extends SystemModelMainGenerated
    implements IPlayGo
{

    public static playgo.systemmodel.SystemModelMain instance = null;

    public SystemModelMain() {
        play();
    }

    public static playgo.systemmodel.SystemModelMain getInstance() {
        if(instance==null){
        	instance = new SystemModelMain();
        }
        return instance;
    }

    public static void main(String[] args) {
        System.setProperty("SYSTEM_MODEL_DEBUG","true");
        System.setProperty("REMOTE_GUI","true");
        getInstance();
    }

    private void play() {
        String playInStr = System.getProperty("playInMode");
        if(playInStr!=null && playInStr.equals("true")){
        	return;	//in play-in, the APP communicates with PlayGo and not with the Behavior
        }
        boolean remoteGui = false;
        String remoteGuiStr = System.getProperty("REMOTE_GUI");
        if(remoteGuiStr!=null && remoteGuiStr.equals("true")){
        	remoteGui = true;
        }
        if(remoteGui){
        	il.ac.wis.cs.playgo.playtoolkit.api.intf.IPlayable playable = 
        			new il.ac.wis.cs.playgo.playtoolkit.api.impl.tcp.PlayGoTcpAgent(Utils.getPort());
        	setPlayable(playable);
        	playable.setPlaygo(this);
        }
        else{
        	if (il.ac.wis.cs.playgo.playtoolkit.api.impl.java.GuiJAgent.getInstance() != null){
        		il.ac.wis.cs.playgo.playtoolkit.api.impl.java.GuiJAgent.getInstance().setBehavior(this);
        	}
        	//TcpAgent for receiving events from System Model View
        	il.ac.wis.cs.playgo.playtoolkit.api.intf.IPlayable systemModelPlayable = 
        			new il.ac.wis.cs.playgo.playtoolkit.api.impl.tcp.PlayGoTcpAgent(Utils.getPort());
        	systemModelPlayable.setPlaygo(this);
        }
    }

}
