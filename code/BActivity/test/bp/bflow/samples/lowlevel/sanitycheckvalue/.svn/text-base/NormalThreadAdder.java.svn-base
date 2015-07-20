package bp.bflow.samples.lowlevel.sanitycheckvalue;

import org.junit.Test;

import bp.BProgram;
import bp.Event;
import static bp.eventSets.EventSetConstants.*;
import static org.junit.Assert.assertTrue;
public class NormalThreadAdder {
	
	@Test
	public void tryAdding() {
		BProgram bp = new BProgram();
		bp.add(2d);
		bp.bSync(new Event(), none, none);
		bp.remove();
		
		assertTrue( true );
	}
	
}
