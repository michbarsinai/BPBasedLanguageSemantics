package org.b_prog.lscobpjv2.mains;

import java.util.Arrays;

import org.b_prog.lscobpjv2.bplscruntime.LiveLscCopy;
import org.b_prog.lscobpjv2.bplscruntime.loggers.LiveLscCopyLogger;
import static org.b_prog.lscobpjv2.lsclang.Modality.Temperature.*;
import static org.b_prog.lscobpjv2.lsclang.Modality.Trigger.*;
import org.b_prog.lscobpjv2.lsclang.syntax.LSC;
import org.b_prog.lscobpjv2.lsclang.syntax.Lifeline;
import org.b_prog.lscobpjv2.lsclang.syntax.LscClass;
import org.b_prog.lscobpjv2.lsclang.syntax.Message;

import bp.BProgram;
import bp.BThread;

public class AliceBobMessage {

	public static void main( String[] args ) {
		
		// Build the chart
		LscClass personClass = new LscClass("Person");
		Lifeline alice = new Lifeline("Alice", personClass);
		Lifeline bob = new Lifeline("Bob", personClass);
		LscBuilder bld = new LscBuilder(alice, bob).name("AliceBobMessage");
		
		bld.send( new Message("hello", Cold, Execution) )
			.from(alice).to(bob);
	
		bld.send( new Message("hello", Cold, Execution) )
		.from(bob).to(alice);
		
		// Run the chart
		
		LSC chart = bld.get();
		
		LiveLscCopy lc = new LiveLscCopy("test-run", chart);
		LiveLscCopyLogger llcl = new LiveLscCopyLogger(lc, true);
		llcl.setLifelineIdOrder(Arrays.asList(alice.getId(), bob.getId()));
		
		BProgram bp = new BProgram();
        double p=1;
        for ( BThread bt : lc.interpret() ) {
            bp.add(bt, p++);
        }
		bp.add(llcl, p);
        
        bp.startAll();
	}
	
}
