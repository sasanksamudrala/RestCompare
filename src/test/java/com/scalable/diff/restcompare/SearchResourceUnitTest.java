package com.scalable.diff.restcompare;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.scalable.diff.restcompare.entity.JsonBinaryDataEntity;
import com.scalable.diff.restcompare.model.request.SearchRequest;
import com.scalable.diff.restcompare.model.response.ComparisonResultResponse;
import com.scalable.diff.restcompare.repository.JsonBinaryDataRepository;
import com.scalable.diff.restcompare.repository.PageableJsonBinaryDataRepository;
import com.scalable.diff.restcompare.rest.SearchResource;

/**
 * Unit test for the application functionality
 * 
 * @author Sasank Samudrala
 * @version 0.0.1
 */
@RunWith(MockitoJUnitRunner.class)
public class SearchResourceUnitTest {
    
	@InjectMocks
    private SearchResource mockSearchResource;
	
	@Mock
    private JsonBinaryDataRepository jsonBinaryDataRepository;
    
    @Mock
    private PageableJsonBinaryDataRepository pageableJsonBinaryDataRepository;
    
    

    @Before
    public void setUp() throws Exception {
    }

    
    /**
     * Test the result when the Data is provided by the user for an ID.
     *
     */
    @Test
    public void testSetLeftValue() throws Exception {
    	SearchRequest searchRequest = new SearchRequest();
    	searchRequest.setEncodedValue("MTAxMDAxMTE=");
    	
    	ResponseEntity<HttpStatus> httpStatusResponse = mockSearchResource.setLeftValue(1, searchRequest);
    	
    	assertEquals(HttpStatus.CREATED, httpStatusResponse.getStatusCode());
    }
    
    /**
     * Test the result when the Data is provided by the user for an ID which already has an existing record in the system.
     *
     */
    @Test
    public void testSetLeftValue_SecondTime() throws Exception {
    	SearchRequest searchRequest = new SearchRequest();
    	searchRequest.setEncodedValue("MTAxMDAxMTE=");
    	
    	when(jsonBinaryDataRepository.findByInputIdAndDirection(any(Integer.class), any(String.class))).thenReturn(createMockJsonBinaryDataEntityListWithLeftDirectionForSameData());
    	
    	ResponseEntity<HttpStatus> httpStatusResponse = mockSearchResource.setLeftValue(1, searchRequest);
    	
    	assertEquals(HttpStatus.CREATED, httpStatusResponse.getStatusCode());
    }
    
    /**
     * Test the result when the Data is provided by the user for an ID.
     *
     */
    @Test
    public void testSetRightValue() throws Exception {
    	SearchRequest searchRequest = new SearchRequest();
    	searchRequest.setEncodedValue("MTAxMDAxMTE=");
    	
    	ResponseEntity<HttpStatus> httpStatusResponse = mockSearchResource.setRightValue(1, searchRequest);
    	
    	assertEquals(HttpStatus.CREATED, httpStatusResponse.getStatusCode());
    }
    
    /**
     * Test the result when the Data is provided by the user for an ID which already has an existing record in the system.
     *
     */
    @Test
    public void testSetRightValue_SecondTime() throws Exception {
    	SearchRequest searchRequest = new SearchRequest();
    	searchRequest.setEncodedValue("MTAxMDAxMTE=");
    	
    	when(jsonBinaryDataRepository.findByInputIdAndDirection(any(Integer.class), any(String.class))).thenReturn(createMockJsonBinaryDataEntityListWithRightDirectionForSameData());
    	
    	ResponseEntity<HttpStatus> httpStatusResponse = mockSearchResource.setRightValue(1, searchRequest);
    	
    	assertEquals(HttpStatus.CREATED, httpStatusResponse.getStatusCode());
    }
    
    /**
     * Test the Comparison result by Providing Equal Base64 encoded binary values for both Left and Right window.
     *
     */
    @Test
    public void testCompareResource_SameData() throws Exception {
	  doAnswer(new Answer<List<JsonBinaryDataEntity>>(){
	        @Override
	        public List<JsonBinaryDataEntity> answer(InvocationOnMock invocation){
	            if ("left".equals(invocation.getArguments()[1])){
	                return createMockJsonBinaryDataEntityListWithLeftDirectionForSameData();
	            }
	            else if ("right".equals(invocation.getArguments()[1])){
	                return createMockJsonBinaryDataEntityListWithRightDirectionForSameData();
	            }
	            return createMockJsonBinaryDataEntityListWithLeftDirectionForSameData();
	        }}).when(jsonBinaryDataRepository).findByInputIdAndDirection(any(Integer.class), any(String.class));
    	
    	ResponseEntity<ComparisonResultResponse> comparisonResultResponse = mockSearchResource.compareResource(1);
    	
    	assertEquals(HttpStatus.OK, comparisonResultResponse.getStatusCode());
    	assertEquals("The data are Equal", comparisonResultResponse.getBody().getResult());
        
    }
    
    /**
     * Test the Comparison result by Providing Different sized Base64 encoded binary values for both Left and Right window.
     *
     */
    @Test
    public void testCompareResource_DifferentSizedData() throws Exception {
	  doAnswer(new Answer<List<JsonBinaryDataEntity>>(){
	        @Override
	        public List<JsonBinaryDataEntity> answer(InvocationOnMock invocation){
	            if ("left".equals(invocation.getArguments()[1])){
	                return createMockJsonBinaryDataEntityListWithLeftDirectionForDifferentSizedData();
	            }
	            else if ("right".equals(invocation.getArguments()[1])){
	                return createMockJsonBinaryDataEntityListWithRightDirectionForDifferentSizedData();
	            }
	            return createMockJsonBinaryDataEntityListWithLeftDirectionForDifferentSizedData();
	        }}).when(jsonBinaryDataRepository).findByInputIdAndDirection(any(Integer.class), any(String.class));
    	
    	ResponseEntity<ComparisonResultResponse> comparisonResultResponse = mockSearchResource.compareResource(1);
    	
    	assertEquals(HttpStatus.OK, comparisonResultResponse.getStatusCode());
    	assertEquals("The data are not of Equal size", comparisonResultResponse.getBody().getResult());
        
    }
    
    /**
     * Test the Comparison result by Providing Different Base64 encoded binary values for both Left and Right window.
     *
     */
    @Test
    public void testCompareResource_DifferentData() throws Exception {
	  doAnswer(new Answer<List<JsonBinaryDataEntity>>(){
	        @Override
	        public List<JsonBinaryDataEntity> answer(InvocationOnMock invocation){
	            if ("left".equals(invocation.getArguments()[1])){
	                return createMockJsonBinaryDataEntityListWithLeftDirectionForDifferentData();
	            }
	            else if ("right".equals(invocation.getArguments()[1])){
	                return createMockJsonBinaryDataEntityListWithRightDirectionForDifferentData();
	            }
	            return createMockJsonBinaryDataEntityListWithLeftDirectionForDifferentData();
	        }}).when(jsonBinaryDataRepository).findByInputIdAndDirection(any(Integer.class), any(String.class));
    	
    	ResponseEntity<ComparisonResultResponse> comparisonResultResponse = mockSearchResource.compareResource(1);
    	
    	assertEquals(HttpStatus.OK, comparisonResultResponse.getStatusCode());
    	assertEquals("The data are different", comparisonResultResponse.getBody().getResult());
        
    }
    
    /**
     * Test the Comparison result by Providing Base64 encoded Non-binary values.
     *
     */
    @Test
    public void testCompareResource_NonBinaryData() throws Exception {
	  doAnswer(new Answer<List<JsonBinaryDataEntity>>(){
	        @Override
	        public List<JsonBinaryDataEntity> answer(InvocationOnMock invocation){
	            if ("left".equals(invocation.getArguments()[1])){
	                return createMockJsonBinaryDataEntityListWithLeftDirectionForNonBinaryData();
	            }
	            else if ("right".equals(invocation.getArguments()[1])){
	                return createMockJsonBinaryDataEntityListWithRightDirectionForNonBinaryData();
	            }
	            return createMockJsonBinaryDataEntityListWithLeftDirectionForNonBinaryData();
	        }}).when(jsonBinaryDataRepository).findByInputIdAndDirection(any(Integer.class), any(String.class));
    	
    	ResponseEntity<ComparisonResultResponse> comparisonResultResponse = mockSearchResource.compareResource(1);
    	
    	assertEquals(HttpStatus.NOT_ACCEPTABLE, comparisonResultResponse.getStatusCode());
    	assertEquals("Please provide only the Binary data and was encoded in Base64", comparisonResultResponse.getBody().getResult());
        
    }
    
    
    
    /**
     * Creates Mock JsonBinaryDataEntityList object.
     *
     */
    private List<JsonBinaryDataEntity> createMockJsonBinaryDataEntityListWithLeftDirectionForSameData() {
    	List<JsonBinaryDataEntity> jsonBinaryDataEntityMockList = new ArrayList<>();
    	
    	JsonBinaryDataEntity jsonBinaryDataEntityMock = new JsonBinaryDataEntity();
    	jsonBinaryDataEntityMock.setDirection("left");
    	jsonBinaryDataEntityMock.setEncodedValue("MTAxMDAxMTE=");
    	jsonBinaryDataEntityMock.setInputId(1);
    	jsonBinaryDataEntityMockList.add(jsonBinaryDataEntityMock);
    	
    	return jsonBinaryDataEntityMockList;
    }
    
    /**
     * Creates Mock JsonBinaryDataEntityList object.
     *
     */
    private List<JsonBinaryDataEntity> createMockJsonBinaryDataEntityListWithRightDirectionForSameData() {
    	List<JsonBinaryDataEntity> jsonBinaryDataEntityMockList = new ArrayList<>();
    	
    	JsonBinaryDataEntity jsonBinaryDataEntityMock = new JsonBinaryDataEntity();
    	jsonBinaryDataEntityMock.setDirection("right");
    	jsonBinaryDataEntityMock.setEncodedValue("MTAxMDAxMTE=");
    	jsonBinaryDataEntityMock.setInputId(1);
    	jsonBinaryDataEntityMockList.add(jsonBinaryDataEntityMock);
    	
    	return jsonBinaryDataEntityMockList;
    }
    
    /**
     * Creates Mock JsonBinaryDataEntityList object.
     *
     */
    private List<JsonBinaryDataEntity> createMockJsonBinaryDataEntityListWithLeftDirectionForDifferentSizedData() {
    	List<JsonBinaryDataEntity> jsonBinaryDataEntityMockList = new ArrayList<>();
    	
    	JsonBinaryDataEntity jsonBinaryDataEntityMock = new JsonBinaryDataEntity();
    	jsonBinaryDataEntityMock.setDirection("left");
    	jsonBinaryDataEntityMock.setEncodedValue("MDExMDAwMDEwMTEwMTExMA==");
    	jsonBinaryDataEntityMock.setInputId(1);
    	jsonBinaryDataEntityMockList.add(jsonBinaryDataEntityMock);
    	
    	return jsonBinaryDataEntityMockList;
    }
    
    /**
     * Creates Mock JsonBinaryDataEntityList object.
     *
     */
    private List<JsonBinaryDataEntity> createMockJsonBinaryDataEntityListWithRightDirectionForDifferentSizedData() {
    	List<JsonBinaryDataEntity> jsonBinaryDataEntityMockList = new ArrayList<>();
    	
    	JsonBinaryDataEntity jsonBinaryDataEntityMock = new JsonBinaryDataEntity();
    	jsonBinaryDataEntityMock.setDirection("right");
    	jsonBinaryDataEntityMock.setEncodedValue("MDExMTAxMTAwMTEwMDEwMTAxMTEwMDEwMDExMTEwMDE=");
    	jsonBinaryDataEntityMock.setInputId(1);
    	jsonBinaryDataEntityMockList.add(jsonBinaryDataEntityMock);
    	
    	return jsonBinaryDataEntityMockList;
    }
    
    /**
     * Creates Mock JsonBinaryDataEntityList object.
     *
     */
    private List<JsonBinaryDataEntity> createMockJsonBinaryDataEntityListWithLeftDirectionForDifferentData() {
    	List<JsonBinaryDataEntity> jsonBinaryDataEntityMockList = new ArrayList<>();
    	
    	JsonBinaryDataEntity jsonBinaryDataEntityMock = new JsonBinaryDataEntity();
    	jsonBinaryDataEntityMock.setDirection("left");
    	jsonBinaryDataEntityMock.setEncodedValue("MDExMDAwMDEwMTExMDAxMQ==");
    	jsonBinaryDataEntityMock.setInputId(1);
    	jsonBinaryDataEntityMockList.add(jsonBinaryDataEntityMock);
    	
    	return jsonBinaryDataEntityMockList;
    }
    
    /**
     * Creates Mock JsonBinaryDataEntityList object.
     *
     */
    private List<JsonBinaryDataEntity> createMockJsonBinaryDataEntityListWithRightDirectionForDifferentData() {
    	List<JsonBinaryDataEntity> jsonBinaryDataEntityMockList = new ArrayList<>();
    	
    	JsonBinaryDataEntity jsonBinaryDataEntityMock = new JsonBinaryDataEntity();
    	jsonBinaryDataEntityMock.setDirection("right");
    	jsonBinaryDataEntityMock.setEncodedValue("MDExMDAwMDEwMTEwMTExMA==");
    	jsonBinaryDataEntityMock.setInputId(1);
    	jsonBinaryDataEntityMockList.add(jsonBinaryDataEntityMock);
    	
    	return jsonBinaryDataEntityMockList;
    }
    
    /**
     * Creates Mock JsonBinaryDataEntityList object.
     *
     */
    private List<JsonBinaryDataEntity> createMockJsonBinaryDataEntityListWithRightDirectionForNonBinaryData() {
    	List<JsonBinaryDataEntity> jsonBinaryDataEntityMockList = new ArrayList<>();
    	
    	JsonBinaryDataEntity jsonBinaryDataEntityMock = new JsonBinaryDataEntity();
    	jsonBinaryDataEntityMock.setDirection("right");
    	jsonBinaryDataEntityMock.setEncodedValue("U2FzYW5rIFNhbXVkcmFsYQ==");
    	jsonBinaryDataEntityMock.setInputId(1);
    	jsonBinaryDataEntityMockList.add(jsonBinaryDataEntityMock);
    	
    	return jsonBinaryDataEntityMockList;
    }
    
    /**
     * Creates Mock JsonBinaryDataEntityList object.
     *
     */
    private List<JsonBinaryDataEntity> createMockJsonBinaryDataEntityListWithLeftDirectionForNonBinaryData() {
    	List<JsonBinaryDataEntity> jsonBinaryDataEntityMockList = new ArrayList<>();
    	
    	JsonBinaryDataEntity jsonBinaryDataEntityMock = new JsonBinaryDataEntity();
    	jsonBinaryDataEntityMock.setDirection("left");
    	jsonBinaryDataEntityMock.setEncodedValue("U2FzYW5rIFNhbXVkcmFsYQ==");
    	jsonBinaryDataEntityMock.setInputId(1);
    	jsonBinaryDataEntityMockList.add(jsonBinaryDataEntityMock);
    	
    	return jsonBinaryDataEntityMockList;
    }
}
