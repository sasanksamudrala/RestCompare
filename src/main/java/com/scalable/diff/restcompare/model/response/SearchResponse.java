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
	
	
	public SearchResponse() {
		super();
	}
	
	
	public SearchResponse(Integer inputId, String encodedString) {
		super();
		this.inputId = inputId;
		this.encodedString = encodedString;
	}


	public Integer getInputId() {
		return inputId;
	}
	public void setInputId(Integer inputId) {
		this.inputId = inputId;
	}
	public String getEncodedString() {
		return encodedString;
	}
	public void setEncodedString(String encodedString) {
		this.encodedString = encodedString;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}


	public Links getLinks() {
		return links;
	}


	public void setLinks(Links links) {
		this.links = links;
	}
	
	
	
	
}
