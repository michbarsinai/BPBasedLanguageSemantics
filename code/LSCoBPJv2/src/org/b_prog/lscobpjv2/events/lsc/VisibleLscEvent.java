package org.b_prog.lscobpjv2.events.lsc;

import bp.eventSets.EventSetInterface;
import bp.eventSets.EventSets;

/**
 * An LSC event that can affect multiple live LSC copies.
 * @author michael
 */
public abstract class VisibleLscEvent extends LscRuntimeEvent {
    
    public static final EventSetInterface ALL = EventSets.ofClass("LSC_VisibleEvents", VisibleLscEvent.class);
    
    
}
