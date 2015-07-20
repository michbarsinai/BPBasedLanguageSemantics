/**
 * <p>
 * The eventhistory package allows querying the history of the events fired during a BPJ run. This package
 * is designed in an API/SPI<sup>*</sup> manner: the client code sees only interfaces (with the exception of a 
 * "bootstrap" class). The implementation is (normally) determined by the {@link EventHistory} class, which
 * instantiates the needed objects.    
 * </p><p>
 * The package is composed of the following layers (from the outside in):
 * <ol>
 * 	<li>{@link EventHistorian} - the API client code uses to query the event history.</li>
 * 	<li>{@link EventHistory} - the "bootstrap" class. Client code uses it to obtain an instance of
 * 							   the event historian. Instantiates the service provider SPI implementation.</li>
 * 	<li>{@link EventHistoryWriter} - the SPI the service providers implements in order to provide
 * 									 event history recording service.
 * </ol>
 * </p><p>
 * The package comes with a default implementation ({@link DefaultEventHistoryWriter}
 * which records the events in memory.
 *  
 * </p>
 * 
 * <sup>*</sup> Service Provider Interface
 * 
 * @author michaelbar-sinai
 */
package bp.contrib.eventhistory;

