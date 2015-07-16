//  The file MSDAspectmain.aj was automatically generated using S2A 2.0.0.201501061329.
//	 Usage of the generated code is permitted for non-commercial research and evaluation purposes..
//	 Removing or changing the above comment is prohibited by S2A 2.0.0.201501061329 license agreement.
//	 For information see http://www.wisdom.weizmann.ac.il/~maozs/s2a/

package aspects;

import playgo.systemmodel.classes.*;
import java.util.ArrayList;
import il.ac.wis.cs.s2a.runtime.lib.*;
import il.ac.wis.cs.playgo.playtoolkit.*;


public aspect MSDAspectmain extends MSDAspect
{


	//Constants for instances, locations and variables
	static final int Env_INST_User = 0;
	static final int Person_INST_Alice = 1;


	MSDAspectmain()
	{
		addMinimalEvent(MSDMethods.Env_Person_trigger);
		setLastCut(1,2);
		numberOfLifeLines = 2;
		numberOfInstances = 2;
		numberOfVariables = 0;
		interactionId = "1437031380060";
		setCutsExpressions();
	}

	//Pointcuts and advices:
	pointcut setCut() : execution(* *.setCut(..));

	after():setCut()
	{
		ArrayList<ActiveMSDAspect> tempActiveArr = new ArrayList<ActiveMSDAspect>();
		tempActiveArr.addAll(getActiveMSDArray());

		for(ActiveMSDAspect liveCopy:tempActiveArr){
			evaluateWaitConditions(liveCopy);	//re-evaluate wait conditions
		}
	}


	//this pointcut definition is identical to pointcut Clock_Clock_tick, aspectj does not allow
	//declaring on identical pointcuts, therefore this pointcut must be defined within the method
	after(Object clock):execution(void tick(..))
		&& if(clock instanceof Clock)
		&& target(clock)
	{
		ArrayList<ActiveMSDAspect> tempActiveArr = new ArrayList<ActiveMSDAspect>();
		tempActiveArr.addAll(getActiveMSDArray());

		for(ActiveMSDAspect liveCopy:tempActiveArr){
			evaluateWaitConditions(liveCopy);	//re-evaluate wait conditions
		}
	}

	pointcut Env_Person_trigger(Env user, Object alice):
		call(void trigger(..))
		&& if(alice instanceof Person)
		&& target(alice) && this(user) ;

	after(Env user,Object alice):Env_Person_trigger(user,alice)
	{
		changeCutState(MSDMethods.Env_Person_trigger,
					user,alice,null);
	}

	pointcut Person_Person_hello(Object alice):
		execution(void hello(..))
		&& if(alice instanceof Person)
		&& target(alice) ;

	after(Object alice):Person_Person_hello(alice)
	{
		changeCutState(MSDMethods.Person_Person_hello,
					alice,alice,null);
	}

	// MSD Logic:
	protected void  changeActiveMSDCutState(int MSDm, Object sourceObject, Object targetObject,ActiveMSDAspect activeMSD,
		ArrayList args)
	{

		boolean unification=false;
		switch (MSDm)
		{
			case MSDMethods.Env_Person_trigger:
				if(activeMSD.instancesEquals(Person_INST_Alice,targetObject))
				{
					unification=true;
					if(activeMSD.isInCut(0,0))
					{
						activeMSD.setCut(1,1);
						return;
					}
				}
				if(!unification)//No unification...
					return;
				break;

			case MSDMethods.Person_Person_hello:
				if(activeMSD.instancesEquals(Person_INST_Alice,sourceObject)
					&& activeMSD.instancesEquals(Person_INST_Alice,targetObject))
				{
					unification=true;
					if(activeMSD.isInCut(1,1))
					{
						activeMSD.setCut(1,2);
						break;
					}
				}
				if(!unification)//No unification...
					return;
				break;

			}
		if(activeMSD.checkViolation())
			activeMSD.completion();
	}

	@SuppressWarnings("unused")
	private boolean evaluateCondition(int conditionNumber,ActiveMSDAspect activeMSD)
	{
		Env User = (Env)bindObjectByExpression(activeMSD,Env_INST_User,null, true);;
		Person Alice = (Person)bindObjectByExpression(activeMSD,Person_INST_Alice,null, true);;

		switch (conditionNumber)
		{
		}
		return false;
	}


	protected void setCutsExpressions()
	{

	}


	@SuppressWarnings("unused")
	protected void doBindings(ActiveMSDAspect activeMSD)
	{
		Env User = (Env)bindObjectByExpression(activeMSD,Env_INST_User,null, true);;
		Person Alice = (Person)bindObjectByExpression(activeMSD,Person_INST_Alice,null, true);;

		activeMSD.setLineInstance(Env_INST_User,AppObjects.getObject("User","Env"));
		activeMSD.setLineInstance(Person_INST_Alice,AppObjects.getObject("Alice","Person"));

	}


	protected void  evaluateWaitConditions(ActiveMSDAspect activeMSD)
	{
		for(Integer cond:activeMSD.getWaitingConditions()){
			switch(cond)
			{
			}
		}
	}

	protected void getActiveMSDCutState(MSDMethodSet ME,MSDMethodSet EE,
			MSDMethodSet CV,MSDMethodSet HV,ActiveMSDAspect activeMSD)
	{
		Env User = (Env)bindObjectByExpression(activeMSD,Env_INST_User,null, true);;
		Person Alice = (Person)bindObjectByExpression(activeMSD,Person_INST_Alice,null, true);;

		MSDMethod MSDm0 = new MSDMethod(User,Alice,
			MSDMethods.Env_Person_trigger,null,"Env:Person:trigger",this.getNiceName(),activeMSD.getId());// trigger() Monitored
		MSDMethod MSDm1 = new MSDMethod(Alice,Alice,
			MSDMethods.Person_Person_hello,null,"Person:Person:hello",this.getNiceName(),activeMSD.getId());// hello() Execute

		 if(activeMSD.isInCut(0,0))
		{
				ME.add(MSDm0);
				CV.add(MSDm1);
				return;
		}
		 if(activeMSD.isInCut(1,1))
		{
				EE.add(MSDm1);
				CV.add(MSDm0);
				return;
		}
	}
	
	@SuppressWarnings("unused")
	public Object bindObjectByExpression (ActiveMSDAspect activeMSD,int lifelineIndex, Object obj, boolean retrieveOnly){

		Object result = null;
		// in case the object is already bounded returning the bounded object
		result = activeMSD.getLineInstance(lifelineIndex);
		if (result == null){
			Env User = (Env) activeMSD.getLineInstance(Env_INST_User);
			Person Alice = (Person) activeMSD.getLineInstance(Person_INST_Alice);
			switch (lifelineIndex){
			}
		}
		return result;
	}
	
	
	public boolean validateBoundedObject(ActiveMSDAspect activeMSD, int lifelineIndex, Object object){
		boolean result = false;
		try{
			Object bounded = bindObjectByExpression(activeMSD, lifelineIndex, object, true);
			if (bounded != null){
				result = bounded.equals(object);
			}else if (object == null){
				result = true;
			}
		}catch(NullPointerException npe){
			result = false; // parent object not bounded yet
		}
		return result;
	}
	
}
