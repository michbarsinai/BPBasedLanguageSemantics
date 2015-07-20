//  The file MSDCoordinatorAspect.aj was automatically generated using S2A 2.0.0.201501061329.
//	 Usage of the generated code is permitted for non-commercial research and evaluation purposes..
//	 Removing or changing the above comment is prohibited by S2A 2.0.0.201501061329 license agreement.
//	 For information see http://www.wisdom.weizmann.ac.il/~maozs/s2a/

package aspects;

import playgo.systemmodel.classes.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Stack;
import il.ac.wis.cs.s2a.runtime.Utils;
import il.ac.wis.cs.s2a.runtime.lib.*;


public aspect MSDCoordinatorAspect extends MSDCoordinator
{
	declare precedence: MSDCoordinatorAspect, *;
	ICoordinatorStrategy strategy = null;

	TraceVisHandler traceVisHandler = null;

	private static final int ENV_MESSAGE_ID = 777;
	private Vector<MSDMethod> history;

	private boolean lock = false;
	private static Stack<MSDMethod> coordinateStack = new Stack<MSDMethod>();
	private static boolean firstTime = true;
	private static MSDCoordinatorAspect instance = null;

		public static MSDCoordinatorAspect getInstance(){
		return instance;
	}

	pointcut strategyInit()
		: (execution(* *.*(..)) && !within(MSDCoordinatorAspect) && if(firstTime) );

	before() : strategyInit()
	{
		instance=this;

		firstTime=false;

		new AdditionalInfo(Utils.getModelFilesPaths(),
				MSDSPOStaticInfo.getInteractionLifelines(),
				MSDSPOStaticInfo.getInteractionVariables(),
				Utils.getRuntimePropertiesPath(),
				Utils.isConcreteLSc());

		traceVisHandler = TraceVisHandler.getSingleton();

		if(strategy == null){
			strategy = StrategyFactory.createStrategy(
				Utils.getRuntimePropertiesPath());
			history = new Vector<MSDMethod>();

		}

		boolean initSucceed = strategy.init();
		if (!initSucceed)
		{
			MSDPlayoutView.setStrategyInitFailed();
		}
	}

	pointcut EnvNoop():
		execution(void il.ac.wis.cs.systemmodel.SMEnvBaseClass+.noop());

	after (): EnvNoop(){

		MSDMethod envMeth = new MSDMethod(
			null,	//src (env)
			null,	//tgt (env)
			-1,		//messageId (not in use here)
			null);	//args

		envMeth.messageIdStr="env:env:noop";
		history.add(envMeth);

		coordinate(envMeth);
	}

	public void Env.Wrappertrigger(Person person)
	{
		person.trigger();
	}

	public void Person.Wrapperhello(Person person)
	{
		person.hello();
	}

	pointcut MSDMessage():
	call(void trigger(..))
	|| call(void hello(..));


	after (): MSDMessage()
	{
		ArrayList<Object> args = null;
		if(thisJoinPoint.getArgs()!=null){
			args = new ArrayList<Object>();
			for(int i=0;i<thisJoinPoint.getArgs().length;i++){
				args.add(thisJoinPoint.getArgs()[i]);
			}	
		}
		MSDMethod envMeth = new MSDMethod(
				null,	//src (env)
				thisJoinPoint.getTarget(),
				ENV_MESSAGE_ID, //messageId (not in use here)
				args);

		envMeth.messageIdStr="env:"+
			thisJoinPoint.getTarget().getClass().getSimpleName()+":"+
			thisJoinPoint.getSignature().getName();

		history.add(envMeth);

		MSDAspect.handlingEvent(thisJoinPoint);
		coordinate(envMeth);
	}

	public void coordinate(MSDMethod envMeth)
	{
		if (lock)
		{
			coordinateStack.push(envMeth);
			return;
		}
		else
		{
			lock = true;
			afterImpl(envMeth);
			lock = false;
		}
		if(coordinateStack.size() > 0)
		{
			coordinate(coordinateStack.pop());
		}
	}

	private void afterImpl(MSDMethod envMeth) 
	{
		ArrayList<MSDAspect> aspects = new ArrayList<MSDAspect>();

		MSDMethodSet monitoringEnabled = new MSDMethodSet();
		MSDMethodSet executingEnabled = new MSDMethodSet();
		MSDMethodSet coldViolation = new MSDMethodSet();
		MSDMethodSet hotViolation = new MSDMethodSet();

		MSDAspectmain.aspectOf().getCutState(monitoringEnabled, executingEnabled, coldViolation, hotViolation);
		aspects.add(MSDAspectmain.aspectOf());

		MSDMethod MSDm = strategy.chooseMethod(monitoringEnabled, executingEnabled, coldViolation, hotViolation
				,history,aspects);

		history.add(MSDm);
		dealWithTraceVis(MSDm, envMeth);
		wrapperCallPoint(MSDm);
	}

	private void dealWithTraceVis(MSDMethod MSDm, MSDMethod envMeth) {
		TraceVisMSDMethodSet monitoringEnabled = new TraceVisMSDMethodSet();
		TraceVisMSDMethodSet executingEnabled = new TraceVisMSDMethodSet();
		TraceVisMSDMethodSet coldViolation = new TraceVisMSDMethodSet();
		TraceVisMSDMethodSet hotViolation = new TraceVisMSDMethodSet();

		MSDAspectmain.aspectOf().getCutState(monitoringEnabled, executingEnabled, coldViolation, hotViolation);

	if (envMeth != null && MSDm != null && envMeth.messageID == ENV_MESSAGE_ID) {
		envMeth = null;
	}

		traceVisHandler.notify(monitoringEnabled, executingEnabled, coldViolation, hotViolation, MSDm, envMeth);
	}


	protected void wrapperCall(MSDMethod MSDm)
	{
		if(MSDm==null){
			return;
		}
		switch (MSDm.messageID)
		{
			case MSDMethods.Person_Person_hello:
			 ((Person)MSDm.sourceInstance).Wrapperhello(
				(Person)MSDm.targetInstance);
			break;
		}
	}

}

