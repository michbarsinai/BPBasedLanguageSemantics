/*
 *  (C) Michael Bar-Sinai
 */
package org.b_prog.lscobpjv2.events;

import bp.Event;
import static junit.framework.Assert.assertTrue;
import org.b_prog.lscobpjv2.bplscruntime.LiveLscCopy;
import org.b_prog.lscobpjv2.events.lsc.LscMetaEvent;
import org.b_prog.lscobpjv2.events.lsc.LscRuntimeEvent;
import org.b_prog.lscobpjv2.events.lsc.SyncEvent;
import org.b_prog.lscobpjv2.lsclang.syntax.LSC;
import org.b_prog.lscobpjv2.lsclang.syntax.Sync;
import org.junit.Test;

/**
 *
 * @author michael
 */
public class UnificationTests {
    
    @Test
    public void testSyncEventUnification() {
        
        LSC testLsc = new LSC("Test");
        Sync theSync = new Sync("Test sync");
        
        LiveLscCopy llc = new LiveLscCopy(testLsc);
        SyncEvent se1 = new SyncEvent(llc, theSync);
        SyncEvent se2 = new SyncEvent(llc, theSync);
        
        assertTrue( se1.contains(se1) );
        assertTrue( se1.contains(se2) );
        assertTrue( se2.contains(se1) );
        
        LscMetaEvent<LscRuntimeEvent> se1Enabled = se1.getEnabledEvent();
        LscMetaEvent<LscRuntimeEvent> se2Enabled = se2.getEnabledEvent();
        
        assertTrue( se1Enabled.contains(se1Enabled) );
        assertTrue( se1Enabled.contains(se2Enabled) );
        assertTrue( se2Enabled.contains(se1Enabled) );
        
    }
}
