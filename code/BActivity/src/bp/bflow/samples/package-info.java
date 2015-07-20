/**
 * We have implementations of the following 2 BActivities:
 *  SanityCheckBActivity
 *   A-->B-->C
 *
 *  ForkJoinBActivity
 *         +->C1-->C1a-+
 *         |           |
 *  A-->B--+->C2-------+-->D
 *         |           |
 *         +->C3-------+
 *
 *	
 *	For both, we first do control-flow, and then we add data passing.
 *
 */
package bp.bflow.samples;

