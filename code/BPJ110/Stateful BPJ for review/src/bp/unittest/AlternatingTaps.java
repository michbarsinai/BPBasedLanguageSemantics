package bp.unittest;

import static bp.eventSets.EventSetConstants.all;
import static bp.eventSets.EventSetConstants.none;
import bApplication.BApplication;
import bp.BProgram;
import bp.BThread;
import bp.Event;
import bp.exceptions.BPJException;
import static bp.BProgram.bp;

public class AlternatingTaps implements BApplication {
	@SuppressWarnings("serial")
	static class TapEvent extends Event {

		public TapEvent(String name) {
			this.setName(name);
		}

	}

	static TapEvent addHot = new TapEvent("AddHot");
	static TapEvent addCold = new TapEvent("AddCold");

	@SuppressWarnings("serial")
	public class AddHotThreeTimes extends BThread {
		public void runBThread() throws BPJException {
			for (int i = 1; i <= 3; i++) {
				bp.bSync(addHot, none, none);
			}
		}
	}

	@SuppressWarnings("serial")
	public class AddColdThreeTimes extends BThread {
		public void runBThread() throws BPJException {
			for (int i = 1; i <= 3; i++) {
				bp.bSync(addCold, none, none);
			}
		}
	}

	@SuppressWarnings("serial")
	public class Interleave extends BThread {
		public void runBThread() throws BPJException {
			while (true) {
				bp.bSync(none, addHot, addCold);
				bp.bSync(none, addCold, addHot);
			}
		}
	}

	@SuppressWarnings("serial")
	public class DisplayEvents extends BThread {
		public void runBThread() throws BPJException {
			while (true) {
				bp.bSync(none, all, none);
				System.out.println("Physically turned water tap per event: " + bp.lastEvent);
			}
		}
	}

	public void runBApplication() {
		System.out.println("runBApplication () at " + this);
		bp.add(new AddHotThreeTimes(), 1.0);
	//	bp.add(new DisplayEvents(), 2.0);
		bp.add(new AddColdThreeTimes(), 3.0);
		bp.add(new Interleave(), 4.0);

		bp.startAll();
	}

	static public void main(String arg[]) {
		try {

			BProgram.startBApplication(AlternatingTaps.class, "bp.unittest");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
