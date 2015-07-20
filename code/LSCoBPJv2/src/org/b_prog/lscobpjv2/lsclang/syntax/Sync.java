package org.b_prog.lscobpjv2.lsclang.syntax;

/**
 * An LSC Sync construct.
 * @author michael
 */
public class Sync extends LscConstruct {

    public Sync() {
    }

    public Sync(String aName) {
        super(aName);
    }
    
    @Override
    public String toString() {
        return "[Sync " + getName() + "]";
    }
}
