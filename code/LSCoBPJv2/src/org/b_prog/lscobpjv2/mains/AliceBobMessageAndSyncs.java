package org.b_prog.lscobpjv2.mains;

import java.util.Arrays;

import bp.BProgram;
import bp.BThread;
import org.b_prog.lscobpjv2.bplscruntime.LiveLscCopy;
import org.b_prog.lscobpjv2.bplscruntime.loggers.LiveLscCopyLogger;
import static org.b_prog.lscobpjv2.lsclang.Modality.Temperature.*;
import static org.b_prog.lscobpjv2.lsclang.Modality.Trigger.*;
import org.b_prog.lscobpjv2.lsclang.syntax.Lifeline;
import org.b_prog.lscobpjv2.lsclang.syntax.LscClass;
import org.b_prog.lscobpjv2.lsclang.syntax.Message;

/**
 * A simple LSC, featuring Alice and Bob. Alice sends bob a message, 
 * Bob syncs with himself, and then they both sync.
 * 
 * @author michael
 */
public class AliceBobMessageAndSyncs {
    
    public static void main(String[] args) {
        LscClass personClass = new LscClass("Person");
        
        Lifeline user = new Lifeline("User", personClass);
		Lifeline alice = new Lifeline("Alice", personClass);
		Lifeline bob = new Lifeline("Bob", personClass);
		LscBuilder bld = new LscBuilder(user, alice, bob).name("AliceBobMessageAndSyncs");
        
		bld.send(new Message("Trigger", Hot, Execution)).from(user).to(alice);
		
		bld.send(new Message("Hello", Hot, Execution)).from(alice).to(bob)
		   .sync(bob)
		   .sync(alice,bob);
                
        LiveLscCopy liveCopy = new LiveLscCopy(bld.get());
        
        BProgram bp = new BProgram();
        double p=1;
        for ( BThread bt : liveCopy.interpret() ) {
            bp.add(bt, p);
            p += 1.0;
        }
        
        LiveLscCopyLogger logger = new LiveLscCopyLogger(liveCopy, true);
        logger.setLifelineIdOrder(Arrays.asList("User","Alice","Bob"));
        bp.add(logger, ++p);
        
        bp.startAll();
    }
    
}
