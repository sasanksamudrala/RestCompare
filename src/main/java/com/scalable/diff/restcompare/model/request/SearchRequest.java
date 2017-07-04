package com.scalable.diff.restcompare.model.request;

/**
 * Domain class for Search request
 * 
 * @author Sasank Samudrala
 * @version 0.0.1
 */
public class SearchRequest {

    private String encodedValue;

    /**
     * Getter method to fetch the Base64 encoded Binary value provided by the user.
     * 
     * @return the encoded value
     */
    public String getEncodedValue() {
        return encodedValue;
    }

    /**
     * Setter method to set the encoded Value in the Domain class.
     * 
     * @param encodedValue
     *            the Base64 encoded value from the user
     */
    public void setEncodedValue(String encodedValue) {
        this.encodedValue = encodedValue;
    }
    
    
}
