package bp.contrib;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BThreadPriorityManagerTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNextPriorityPriority() {
		
		double low = BThreadPriorityManager.nextPriority( BThreadPriorityManager.Range.LOW );
		double normal = BThreadPriorityManager.nextPriority( BThreadPriorityManager.Range.NORMAL );
		double high = BThreadPriorityManager.nextPriority( BThreadPriorityManager.Range.HIGH );
		
		assertTrue( low < normal );
		assertTrue( normal < high );
		
		double normal2 = BThreadPriorityManager.nextPriority( BThreadPriorityManager.Range.NORMAL );
		assertTrue( normal != normal2 );
		
		double normalDefault = BThreadPriorityManager.nextPriority();
		assertTrue( normalDefault != normal );
		assertTrue( normalDefault != normal2 );
	}

}
