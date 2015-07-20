package bp.contrib.eventhistory;


/**
 * Creates instances of event history writers. {@link EvenhHistory} class uses it to create event historians.
 * 
 * @author michaelbar-sinai
 */
public interface EventHistoryWriterFactory {
	public EventHistoryWriter buildWriter();
}
