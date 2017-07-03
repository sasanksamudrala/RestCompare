package com.scalable.diff.restcompare.rest;

/**
 * Resource constants used in rest controller
 * 
 * @author Sasank Samudrala
 * @version 0.0.1
 */
public class ResourceConstants {
    public static final String RESOURCE_V1 = "/v1/diff";
    public static final String LEFT_RESOURCE_INPUT = "{inputId}/left";
    public static final String RIGHT_RESOURCE_INPUT = "{inputId}/right";
    public static final String RESOURCE_COMPARE = "{inputId}";
    public static final String ID_ALL = "{inputId}/all";
    public static final String FIND_ALL = "/all";
    
    private ResourceConstants() {
        //Private constructor
    }
    
}
