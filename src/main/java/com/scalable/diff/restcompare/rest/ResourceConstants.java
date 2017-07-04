package com.scalable.diff.restcompare.rest;

/**
 * Resource constants used in rest controller
 * 
 * @author Sasank Samudrala
 * @version 0.0.1
 */
public class ResourceConstants {
    /** The base URI */
    public static final String RESOURCE_V1 = "/v1/diff";
    
    /** The URI for the Left window input */
    public static final String LEFT_RESOURCE_INPUT = "{inputId}/left";
    
    /** The URI for the Right window input */
    public static final String RIGHT_RESOURCE_INPUT = "{inputId}/right";
    
    /** The URI to get the comparison result */
    public static final String RESOURCE_COMPARE = "{inputId}";
    
    /** The additional URI to check the records per each ID */
    public static final String ID_ALL = "{inputId}/all";
    
    /** The additional URI to check the records in system */
    public static final String FIND_ALL = "/all";
    
    /**
     * Constructor for the class.
     * 
     */
    private ResourceConstants() {
        //Private constructor
    }
    
}
