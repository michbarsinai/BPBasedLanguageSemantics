package bp.unittest;

import static bp.eventSets.EventSetConstants.all;
import static bp.eventSets.EventSetConstants.none;
import bApplication.BApplication;
import bp.BProgram;
import bp.BThread;
import bp.Event;
import bp.exceptions.BPJException;
import static bp.BProgram.bp;

public class HelloWorld implements BApplication {
	@SuppressWarnings("serial")
	static class GreetingEvent extends Event {

		public GreetingEvent(String name) {
			this.setName(name);
		}

	}

	static GreetingEvent goodMorning = new GreetingEvent("Good Morning!");
	static GreetingEvent goodEvening = new GreetingEvent("Good Evening!");

	@SuppressWarnings("serial")
	public class SayGoodMorning extends BThread {
		public void runBThread() throws BPJException {
			for (int i = 1; i <= 3; i++) {
				bp.bSync(goodMorning, none, none);
			}
		}
	}

	@SuppressWarnings("serial")
	public class SayGoodEvening extends BThread {
		public void runBThread() throws BPJException {
			for (int i = 1; i <= 3; i++) {
				bp.bSync(goodEvening, none, none);
			}
		}
	}

	@SuppressWarnings("serial")
	public class Interleave extends BThread {
		public void runBThread() throws BPJException {
			while (true) {
				bp.bSync(none, goodMorning, goodEvening);
				bp.bSync(none, goodEvening, goodMorning);
			}
		}
	}

	@SuppressWarnings("serial")
	public class DisplayEvents extends BThread {
		public void runBThread() throws BPJException {
			while (true) {
				bp.bSync(none, all, none);
				System.out.println("Processed:" + bp.lastEvent);
			}
		}
	}

	public void runBApplication() {
		System.out.println("runBApplication () at " + this);
		bp.add(new SayGoodMorning(), 1.0);
		bp.add(new DisplayEvents(), 2.0);
		bp.add(new SayGoodEvening(), 3.0);
		bp.add(new Interleave(), 4.0);

		bp.startAll();
	}

	static public void main(String arg[]) {
		try {

			BProgram.startBApplication(HelloWorld.class, "bp.unittest");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
