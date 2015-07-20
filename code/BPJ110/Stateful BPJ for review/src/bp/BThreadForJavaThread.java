package bp;

import bp.exceptions.BPJException;
import bp.exceptions.BPJJavaThreadStartException;

public class BThreadForJavaThread extends BThread {
	public BThreadForJavaThread(Thread th) {
		setThread(th);
	}

	public void runBThread() throws BPJException {
		throw new BPJJavaThreadStartException();
	}
}
