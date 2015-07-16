
package playgo.systemmodel.classes.generated;

import il.ac.wis.cs.systemmodel.SMBaseClass;
import il.ac.wis.cs.systemmodel.SystemModelGen;


/**
 * This file was automatically generated using PlayGo system-model.
 * Usage of the generated code is permitted for non-commercial research and evaluation purposes..
 * This class shouldn't be changed. Any change to this class will be overridden!!!
 * 
 */
public class PersonGenerated
    extends SMBaseClass
{


    public PersonGenerated(java.lang.String name) {
        super(name);
    }

    @SystemModelGen
    public String getName() {
        return name;
    }

    @SystemModelGen
    public void trigger() {
        if(System.getProperty("SYSTEM_MODEL_DEBUG")!=null){
        	System.out.println(">>> method call: >>> trigger() : void");
        }
        	//GUI handling
        	playgo.systemmodel.SystemModelMain.getInstance().getPlayable().
        		activateMethod("Person", name, "trigger");
    }

    @SystemModelGen
    public void hello() {
        if(System.getProperty("SYSTEM_MODEL_DEBUG")!=null){
        	System.out.println(">>> method call: >>> hello() : void");
        }
        return;
    }

}
