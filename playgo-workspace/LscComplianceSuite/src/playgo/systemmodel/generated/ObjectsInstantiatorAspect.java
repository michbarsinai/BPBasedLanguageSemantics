
package playgo.systemmodel.generated;

import il.ac.wis.cs.playgo.playtoolkit.AppObjects;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


/**
 * This file was automatically generated using PlayGo system-model.
 * Usage of the generated code is permitted for non-commercial research and evaluation purposes..
 * This class shouldn't be changed. Any change to this class will be overridden!!!
 * 
 */
@Aspect
public class ObjectsInstantiatorAspect {

    private boolean smFirstTime = true;
    private boolean appFirstTime = true;

    @Pointcut("execution(il.ac.wis.cs.playgo.playtoolkit.api.impl.java.GuiJAgent.new(..)) ")
    void systemModelMainInit() {
    }

    @After("systemModelMainInit()")
    public void smInit(JoinPoint thisJoinPoint)
        throws Throwable
    {
        if (smFirstTime) {
        	smFirstTime = false;
        	playgo.systemmodel.SystemModelMain.getInstance();
        }
    }

    @Pointcut("call(* *.*(..)) && !within(ObjectsInstantiatorAspect)")
    void createAppObjects() {
    }

    @After("createAppObjects()")
    public void creObjects(JoinPoint thisJoinPoint)
        throws Throwable
    {
        if (appFirstTime) {
        	appFirstTime = false;
        	createSystemModelObjects();
        }
    }

    private void createSystemModelObjects() {
        try {
            Object object = null;
            object = new playgo.systemmodel.classes.Person("person");
            AppObjects.addObject(object);
            object = new playgo.systemmodel.classes.Person("Alice");
            AppObjects.addObject(object);
            object = new playgo.systemmodel.classes.Person("Bob");
            AppObjects.addObject(object);
            object = new playgo.systemmodel.classes.Env("Env");
            AppObjects.addObject(object);
            object = new playgo.systemmodel.classes.Env("User");
            AppObjects.addObject(object);
        } catch (Exception _x) {
            _x.printStackTrace();
        }
    }

}
