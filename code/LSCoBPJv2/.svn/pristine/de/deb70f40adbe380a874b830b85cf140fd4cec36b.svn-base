package org.b_prog.lscobpjv2.events.lsc;

/**
 * An BP event about an LSC event, such as {@code enabled(message)}, {@code completed(lsc)} etc.
 * @author michael
 * @param <T> The type of the LSC event.
 */
public class LscMetaEvent<T extends LscRuntimeEvent> extends LscRuntimeEvent {
    
    private final T lscEvent;
    
    /**
     * Convenience construction method, to allow type inference.
     * @param <Q> the type of LscEvent
     * @param name name of the meta event
     * @param event the event we're meta about
     * @return the meta event.
     */
    public static  <Q extends LscRuntimeEvent> LscMetaEvent<Q> forEvent( Q event, String name ) {
        return new LscMetaEvent<>(name, event);
    }

    public LscMetaEvent( String name, T lscEvent ) {
        this.lscEvent = lscEvent;
        setName( name + "(" + lscEvent.getName() + ")" );
    }
    
    public LscMetaEvent(T lscEvent) {
        this( "meta", lscEvent );
    }

    public T getLscEvent() {
        return lscEvent;
    }

    @Override
    public boolean contains(Object o) {
        return (o instanceof LscMetaEvent)
                   && ((LscMetaEvent<?>)o).getLscEvent().contains(getLscEvent());
    }
    
}
