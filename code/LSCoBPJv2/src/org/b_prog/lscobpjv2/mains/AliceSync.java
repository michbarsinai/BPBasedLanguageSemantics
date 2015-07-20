package org.b_prog.lscobpjv2.mains;

import org.b_prog.lscobpjv2.bplscruntime.LiveLscCopy;
import org.b_prog.lscobpjv2.bplscruntime.loggers.LiveLscCopyLogger;
import org.b_prog.lscobpjv2.lsclang.syntax.LSC;
import org.b_prog.lscobpjv2.lsclang.syntax.Lifeline;
import org.b_prog.lscobpjv2.lsclang.syntax.LscClass;

import bp.BProgram;
import bp.BThread;

public class AliceSync {
	public static void main(String[] args ) {
		// Build the chart
		LscClass personClass = new LscClass("Person");
		Lifeline alice = new Lifeline("Alice", personClass);
		LscBuilder bld = new LscBuilder(alice).name("AliceSync");
		
		bld.sync( alice );
		
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
