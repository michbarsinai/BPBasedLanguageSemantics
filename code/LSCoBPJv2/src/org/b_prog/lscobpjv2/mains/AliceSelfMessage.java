package org.b_prog.lscobpjv2.mains;

import static org.b_prog.lscobpjv2.lsclang.Modality.Temperature.Cold;
import static org.b_prog.lscobpjv2.lsclang.Modality.Trigger.Execution;

import org.b_prog.lscobpjv2.bplscruntime.LiveLscCopy;
import org.b_prog.lscobpjv2.bplscruntime.loggers.LiveLscCopyLogger;
import org.b_prog.lscobpjv2.lsclang.syntax.LSC;
import org.b_prog.lscobpjv2.lsclang.syntax.Lifeline;
import org.b_prog.lscobpjv2.lsclang.syntax.LscClass;
import org.b_prog.lscobpjv2.lsclang.syntax.Message;

import bp.BProgram;
import bp.BThread;

public class AliceSelfMessage {
	public static void main(String[] args ) {
		// Build the chart
		LscClass personClass = new LscClass("Person");
		Lifeline alice = new Lifeline("Alice", personClass);
		LscBuilder bld = new LscBuilder(alice).name("AliceSync");
		
		bld.send( new Message("hello", Cold, Execution) )
		.from(alice).to(alice);

		// Run the chart
		LSC chart = bld.get();
		
		LiveLscCopy lc = new LiveLscCopy("test-run", chart);
		LiveLscCopyLogger llcl = new LiveLscCopyLogger(lc, true);
		
		BProgram bp = new BProgram();
        double p=1;
        for ( BThread bt : lc.interpret() ) {
            bp.add(bt, p++);
        }
		bp.add(llcl, p);
        
        bp.startAll();
	}
}
