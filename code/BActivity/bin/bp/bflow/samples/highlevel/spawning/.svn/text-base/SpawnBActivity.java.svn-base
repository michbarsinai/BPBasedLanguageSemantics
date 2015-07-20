package bp.bflow.samples.highlevel.spawning;

import bp.bflow.core.BFlow;
import bp.bflow.core.BStep;
import bp.contrib.NamedEvent;

public class SpawnBActivity extends BFlow {
	
	@Override
	public void runBFlow() throws InterruptedException {
		System.out.println("Main Started");
		bpExec( 
			spawn( sequence(bstep("a"), bstep("b"), waitFor( new NamedEvent("E") ), bstep("c")),
				   sequence(bstep("A"), bstep("B"), waitFor( new NamedEvent("E") ), bstep("C")),
				   sequence(bstep("1"), bstep("2"), waitFor( new NamedEvent("E") ), bstep("3")) ),
			waitFor( new BStep("2").getSuccessEvent() ),
			broadcast( new NamedEvent("E") )
		);
	}

}
