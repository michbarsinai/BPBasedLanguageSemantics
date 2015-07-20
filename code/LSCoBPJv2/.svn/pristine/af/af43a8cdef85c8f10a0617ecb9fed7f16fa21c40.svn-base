package org.b_prog.lscobpjv2.lsclang.syntax;

/**
 * Base class for anything that a {@link Lifeline} can meet on an {@link LSC}.
 * @author michael
 */
public abstract class LscConstruct {
    
    private String name;
    
    public LscConstruct(){}
    
    public LscConstruct( String aName ) {
        name = aName;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        String[] className = getClass().getName().split("\\.");
        return "[" + className[ className.length-1] + " " + name +"]";
    }
    
}
