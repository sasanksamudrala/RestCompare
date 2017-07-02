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
	
	
	public List<Integer> getPosition() {
		return position;
	}
	public void setPosition(List<Integer> position) {
		this.position = position;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
	
}
