package com.scalable.diff.restcompare.model;

/**
 * Domain class to maintain Links
 * 
 * @author Sasank Samudrala
 * @version 0.0.1
 */
public class Links {

    private Self self;

    /**
     * Gets the self reference link value object.
     * 
     * @return the self reference link object
     */
    public Self getSelf() {
        return self;
    }

    /**
     * Sets the self reference link value object.
     * 
     * @param self
     *            the self reference object
     */
    public void setSelf(Self self) {
        this.self = self;
    }
    
    
    
}
