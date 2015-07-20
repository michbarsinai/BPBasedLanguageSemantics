package org.b_prog.lscobpjv2.lsclang.syntax;

import java.util.Objects;

/**
 * A class of LSC objects.
 * @author michael
 */
public class LscClass {
    
    private final String name;

    public LscClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if ( !(obj instanceof LscClass)) {
            return false;
        }
        final LscClass other = (LscClass) obj;
    
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return "[LscClass name:" + getName() + "]";
    }
    
}
