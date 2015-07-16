
package playgo.systemmodel.generated;

import il.ac.wis.cs.playgo.playtoolkit.api.impl.java.SystemModelPlayGo;


/**
 * This file was automatically generated using PlayGo system-model.
 * Usage of the generated code is permitted for non-commercial research and evaluation purposes..
 * This class shouldn't be changed. Any change to this class will be overridden!!!
 * 
 */
public class SystemModelMainGenerated
    extends SystemModelPlayGo
{


    public void runBThreads() {
    }

    @Override
    public void appIsUp() {
        try {
            Object object = null;
            String retval = null;
             
            object = il.ac.wis.cs.playgo.playtoolkit.AppObjects.getObject(
				"person", "Person");
             
            object = il.ac.wis.cs.playgo.playtoolkit.AppObjects.getObject(
				"Alice", "Person");
             
            object = il.ac.wis.cs.playgo.playtoolkit.AppObjects.getObject(
				"Bob", "Person");
             
            object = il.ac.wis.cs.playgo.playtoolkit.AppObjects.getObject(
				"Env", "Env");
             
            object = il.ac.wis.cs.playgo.playtoolkit.AppObjects.getObject(
				"User", "Env");
             
             
            runBThreads();
             
            //start clock
            playgo.systemmodel.classes.Clock clock = playgo.systemmodel.classes.Clock.getInstance();
            clock.startTimer();
        } catch (Exception _x) {
            _x.printStackTrace();
        }
    }

    @Override
    public void stop() {
    }

}
