package com.scalable.diff.restcompare.model.response;

import com.scalable.diff.restcompare.model.Links;

/**
 * Domain class for Search response
 * 
 * @author Sasank Samudrala
 * @version 0.0.1
 */
public class SearchResponse {
    
    private Integer inputId;
    private String encodedString;
    private String direction;
    
    private Links links;
    
    
    /**
     * Constructor for the class without arguments.
     * 
     */
    public SearchResponse() {
        super();
    }
    
    
    /**
     * Constructor for the class.
     * 
     * @param the ID value
     * @param the encoded string value
     */
    public SearchResponse(Integer inputId, String encodedString) {
        super();
        this.inputId = inputId;
        this.encodedString = encodedString;
    }


    /**
     * Fetches the input ID provided by the user.
     * 
     * @return the ID value
     */
    public Integer getInputId() {
        return inputId;
    }
    
    /**
     * Sets the input ID provided by the user.
     * 
     * @param inputId
     *            the ID value
     */
    public void setInputId(Integer inputId) {
        this.inputId = inputId;
    }
    
    /**
     * Gets the Base64 encoded string given by user.
     * 
     * @return the encoded String value
     */
    public String getEncodedString() {
        return encodedString;
    }
    
    /**
     * Sets the Base 64 encoded string.
     * 
     * @param encodedString
     *            the encoded String value
     */
    public void setEncodedString(String encodedString) {
        this.encodedString = encodedString;
    }
    
    /**
     * Gets the Direction indicating whether the given data is for Left or Right window.
     * 
     * @return the left or right direction
     */
    public String getDirection() {
        return direction;
    }
    
    /**
     * Sets the Direction indicating whether the given data is for Left or Right window.
     * 
     * @param direction
     *            the left or right direction
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Gets the links value object.
     * 
     * @return the Links object
     */
    public Links getLinks() {
        return links;
    }

    /**
     * Sets the link value object.
     * 
     * @param links
     *            the Links object
     */
    public void setLinks(Links links) {
        this.links = links;
    }
    
    
    
}
