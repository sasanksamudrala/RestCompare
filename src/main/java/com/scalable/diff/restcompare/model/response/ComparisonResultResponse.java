package com.scalable.diff.restcompare.model.response;

import java.util.List;

/**
 * Domain class for Result of comparison
 * 
 * @author Sasank Samudrala
 * @version 0.0.1
 */
public class ComparisonResultResponse {

    private List<Integer> position;
    private String result;
    
    /**
     * Getter method to fetch the List of positions(offset+length) that are different between the binary values.
     * 
     * @return the positions that are different
     */
    public List<Integer> getPosition() {
        return position;
    }
    
    /**
     * Setter method to set the List of different positions at which the binary values are different.
     * 
     * @param position
     *         the list of positions where the binary value is different
     */
    public void setPosition(List<Integer> position) {
        this.position = position;
    }
    
    /**
     * Getter method to fetch the result of the comparison.
     * 
     * @return result
     *          the result indicating the output of the data comparison
     */
    public String getResult() {
        return result;
    }
    
    /**
     * Setter method to set the result of the comparison.
     * 
     * @param result
     *            the result indicating the output of the data comparison
     */
    public void setResult(String result) {
        this.result = result;
    }
    
    
    
}
