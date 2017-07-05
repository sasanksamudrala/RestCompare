package com.scalable.diff.restcompare.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scalable.diff.restcompare.entity.JsonBinaryDataEntity;
import com.scalable.diff.restcompare.model.request.SearchRequest;
import com.scalable.diff.restcompare.model.response.ComparisonResultResponse;
import com.scalable.diff.restcompare.model.response.SearchResponse;
import com.scalable.diff.restcompare.repository.JsonBinaryDataRepository;
import com.scalable.diff.restcompare.repository.PageableJsonBinaryDataRepository;

import converter.JsonBinaryDataEntityToSearchResponseConverter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.transaction.Transactional;


/**
 * Class to intermediate all operations to be performed on the Comparison
 * 
 * @author Sasank Samudrala
 * @version 0.0.1
 */

@RestController
@RequestMapping(ResourceConstants.RESOURCE_V1)
public class SearchResource {
    
    @Autowired
    private JsonBinaryDataRepository jsonBinaryDataRepository;
    
    @Autowired
    private PageableJsonBinaryDataRepository pageableJsonBinaryDataRepository;
    

    /**
     * Consume the Input provided by the user for the Left window and save it in the H2 in-memory database
     * In case the data is already present for that ID, then that data will be overridden.
     * NO other validations related to the Binary data will be done by this method 
     * 
     * @param inputId
     *             the input Id given by the user
     * 
     * @return Http status indicating whether the record is successfully created
     */
    @RequestMapping(path=ResourceConstants.LEFT_RESOURCE_INPUT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional
    public ResponseEntity<HttpStatus> setLeftValue (@PathVariable Integer inputId, @RequestBody SearchRequest searchRequest) {
        if (1 > jsonBinaryDataRepository.findByInputIdAndDirection(inputId, StaticConstants.LEFT).size()) {
            jsonBinaryDataRepository.save(new JsonBinaryDataEntity(inputId, searchRequest.getEncodedValue(), StaticConstants.LEFT));
            
        } else {
            //HttpStatus.CONFLICT
            jsonBinaryDataRepository.deleteByInputIdAndDirection(inputId, StaticConstants.LEFT);
            jsonBinaryDataRepository.save(new JsonBinaryDataEntity(inputId, searchRequest.getEncodedValue(), StaticConstants.LEFT));
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    
    /**
     * Consume the Input provided by the user for the Right window and save it in the H2 in-memory database
     * In case the data is already present for that ID, then that data will be overridden.
     * NO other validations related to the Binary data will be done by this method 
     * 
     * @param inputId
     *             the input Id given by the user
     * 
     * @return Http status indicating whether the record is successfully created
     */
    @RequestMapping(path=ResourceConstants.RIGHT_RESOURCE_INPUT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional
    public ResponseEntity<HttpStatus> setRightValue (@PathVariable Integer inputId, @RequestBody SearchRequest searchRequest) {
        if (1 > jsonBinaryDataRepository.findByInputIdAndDirection(inputId, StaticConstants.RIGHT).size()) {
            jsonBinaryDataRepository.save(new JsonBinaryDataEntity(inputId, searchRequest.getEncodedValue(), StaticConstants.RIGHT));
        } else {
            //HttpStatus.CONFLICT
            jsonBinaryDataRepository.deleteByInputIdAndDirection(inputId, StaticConstants.RIGHT);
            jsonBinaryDataRepository.save(new JsonBinaryDataEntity(inputId, searchRequest.getEncodedValue(), StaticConstants.RIGHT));
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    
    /**
     * Get the ID value from the user and compare the Left & Right window messages
     * In case of any issue with the given input, mis-match of data, etc the respective message will be sent as response
     * In case of invalid data the Http status "Not Acceptible" will be returned
     * 
     * @param inputId
     *             the input Id given by the user
     * 
     * @return Comparison result for the user
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping(path=ResourceConstants.RESOURCE_COMPARE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ComparisonResultResponse> compareResource (@PathVariable Integer inputId) throws UnsupportedEncodingException {
        if (!jsonBinaryDataRepository.findByInputIdAndDirection(inputId, StaticConstants.RIGHT).isEmpty() && !jsonBinaryDataRepository.findByInputIdAndDirection(inputId, StaticConstants.LEFT).isEmpty()) {
            JsonBinaryDataEntity jsonBinaryDataEntityRight = jsonBinaryDataRepository.findByInputIdAndDirection(inputId, StaticConstants.RIGHT).get(0);
            JsonBinaryDataEntity jsonBinaryDataEntityLeft = jsonBinaryDataRepository.findByInputIdAndDirection(inputId, StaticConstants.LEFT).get(0);
            
            if (checkForBinaryDataAfterDecode(jsonBinaryDataEntityRight.getEncodedValue()) && checkForBinaryDataAfterDecode(jsonBinaryDataEntityLeft.getEncodedValue())) {
            	String rightText = binaryToText(decodeBase64EncodedString(jsonBinaryDataEntityRight.getEncodedValue()));            
                String leftText = binaryToText(decodeBase64EncodedString(jsonBinaryDataEntityLeft.getEncodedValue()));
                ComparisonResultResponse comparisonResultResponse = new ComparisonResultResponse();
                comparisonResultResponse = compareString(rightText, leftText, comparisonResultResponse);
                return new ResponseEntity<>(comparisonResultResponse, HttpStatus.OK);
            }
        }
        ComparisonResultResponse comparisonResultResponse = new ComparisonResultResponse();
        comparisonResultResponse.setResult("Please provide only the Binary data and was encoded in Base64");
        return new ResponseEntity<>(comparisonResultResponse, HttpStatus.NOT_ACCEPTABLE);
    }
    
    
    /**
     * Additional functionality to Get the ID value from the user and List all the data present for that particular ID
     * In case of any issue with the given input, mis-match of data, etc the respective message will be sent as response
     * In case of invalid data the Http status "Not Acceptible" will be returned
     * 
     * @param inputId
     *             the input Id given by the user
     * 
     * @return Comparison result for the user
     */
    @RequestMapping(path=ResourceConstants.ID_ALL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<SearchResponse> getAllValuesForGivenId (@PathVariable Integer inputId, Pageable pageable) {
        Page<JsonBinaryDataEntity> jsonBinaryDataEntityList = pageableJsonBinaryDataRepository.findByInputId(inputId, pageable);
        return jsonBinaryDataEntityList.map(new JsonBinaryDataEntityToSearchResponseConverter());
    }
    
    
    /**
     * Additional functionality to List all the data present in the system
     * The Content tag will be empty in case no data is present
     * 
     * @param inputId
     *             the input Id given by the user
     * 
     * @return Comparison result for the user
     */
    @RequestMapping(path=ResourceConstants.FIND_ALL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<SearchResponse> getAllValues (Pageable pageable) {
        Page<JsonBinaryDataEntity> jsonBinaryDataEntityList = pageableJsonBinaryDataRepository.findAll(pageable);
        return jsonBinaryDataEntityList.map(new JsonBinaryDataEntityToSearchResponseConverter());
    }
    
    
    
    /**
     * Decodes a base64 encoded String.
     *
     * @param encodedString
     * @return the decoded string
     * @throws UnsupportedEncodingException 
     */
    private static String decodeBase64EncodedString(final String encodedString) throws UnsupportedEncodingException {
    	return new String(Base64.getUrlDecoder().decode(encodedString), "utf-8");
    }
    
    /**
     * Compares the Binary data to Text String.
     *
     * @param info
                the binary value
     *        
     * @return the text value string
     */
    private static String binaryToText(String info)throws UnsupportedEncodingException{
		String input = info;
		StringBuilder output = new StringBuilder();
		for(int i = 0; i <= input.length() - 8; i+=8)
		{
		    int characterValue = Integer.parseInt(input.substring(i, i+8), 2);
		    output.append((char) characterValue);
		}   
		return output.toString();
    }
    
    /**
     * Compares the left and right window text.
     *
     * @param leftText
                the Left window data
     * @param rightText
                the Right window data
     * @param comparisonResultResponse
                the ComparisonResultResponse object which needs to be populated
     *        
     * @return comparisonResultResponse
     *            the comparisonResultResponse object
     */
    private static ComparisonResultResponse compareString(String rightText, String leftText, ComparisonResultResponse comparisonResultResponse) {
    	if (rightText.length() != leftText.length()) {
            comparisonResultResponse.setResult("The data are not of Equal size");
        } else {
            List<Integer> differentPositionList = new ArrayList<>();
            for (int i=0; i < rightText.length(); i++) {
            	char chA = rightText.charAt(i);
        	    char chB = leftText.charAt(i);
        	    if (chA != chB) {
        	    	differentPositionList.add(i+1);
        	    }
            }
            if (differentPositionList.isEmpty()) {
                comparisonResultResponse.setResult("The data are Equal");
            } else {
                comparisonResultResponse.setPosition(differentPositionList);
                comparisonResultResponse.setResult("The data are different");
            }
        }
        return comparisonResultResponse;
    	
    }

    
    /**
     * Checks whether the decoded base64 value is binary value or not.
     *
     * @param encodedString
     * @return boolean value indicating the result
     */
    private static boolean checkForBinaryDataAfterDecode(final String encodedString) {
        try {
            String decodedString = new String(Base64.getUrlDecoder().decode(encodedString), "utf-8");
            return decodedString.matches("^[0-1]*$");
            
        } catch (final UnsupportedEncodingException e) {
            return false;
        }
    }
    


}
