
package playgo.systemmodel.classes;

import il.ac.wis.cs.systemmodel.SystemModelGen;
import playgo.systemmodel.classes.generated.EnvGenerated;


/**
 * This file was automatically generated using PlayGo system-model.
 * Usage of the generated code is permitted for non-commercial research and evaluation purposes..
 * This class can be updated. Changes will not be overridden
 * 
 */
public class Env
    extends EnvGenerated
{


    public Env(String name) {
        super(name);
    }

    @SystemModelGen
    public void noop() {
        if(System.getProperty("SYSTEM_MODEL_DEBUG")!=null){
        	System.out.println(">>> method call: >>> noop ");
        }
        return ;
    }

}
