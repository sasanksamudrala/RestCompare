package com.scalable.diff.restcompare;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalable.diff.restcompare.model.request.SearchRequest;
import com.scalable.diff.restcompare.rest.ResourceConstants;

import io.restassured.RestAssured;

/**
 * Integration test for the application functionality
 * 
 * @author Sasank Samudrala
 * @version 0.0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestcompareApplication.class, 
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SearchResourceIntegrationTest {
    
	/** The server port. */
    @LocalServerPort
    private int port;

    /**
     * Setting up the port, base path and base URI before running the tests.
     *
     */
    @Before
    public void setUp() throws Exception {
        RestAssured.port = Integer.valueOf(port);
        RestAssured.basePath = ResourceConstants.RESOURCE_V1;
        RestAssured.baseURI = "http://localhost";
    }

    
    /**
     * Test the Comparison result by Providing Equal Base64 encoded binary values for both Left and Right window.
     *
     */
    @Test
    public void testEqualData() throws JSONException, JsonMappingException, JsonParseException, IOException {
        JSONObject jsonObj = new JSONObject().put("encodedValue","MTAxMDEx");
        ObjectMapper mapper = new ObjectMapper();
        SearchRequest searchRequest = mapper.readValue(jsonObj.toString(), SearchRequest.class);  
        
        given()
            .contentType("application/json")
            .body(searchRequest)
        .when()
            .post(1+"/left")
        .then()
            .statusCode(201);
        
        given()
	        .contentType("application/json")
	        .body(searchRequest)
	    .when()
	        .post(1+"/right")
	    .then()
	        .statusCode(201);
        
        given()
        .when()
        	.get("1")
        .then()
	    	.statusCode(200)
	    	.body("result", equalTo("The data are Equal"));
    }
    
    /**
     * Test the Comparison result by Providing Un-Equal Base64 encoded binary values for both Left and Right window.
     *
     */
    @Test
    public void testUnEqualSizedData() throws JSONException, JsonMappingException, JsonParseException, IOException {
    	ObjectMapper mapper = new ObjectMapper();
    	
        JSONObject jsonObjOne = new JSONObject().put("encodedValue","MTExMDA=");
        SearchRequest searchRequestOne = mapper.readValue(jsonObjOne.toString(), SearchRequest.class);  
        
        JSONObject jsonObjTwo = new JSONObject().put("encodedValue","MTExMDAx");
        SearchRequest searchRequestTwo = mapper.readValue(jsonObjTwo.toString(), SearchRequest.class);  
        
        given()
            .contentType("application/json")
            .body(searchRequestOne)
        .when()
            .post(1+"/left")
        .then()
            .statusCode(201);
        
        given()
	        .contentType("application/json")
	        .body(searchRequestTwo)
	    .when()
	        .post(1+"/right")
	    .then()
	        .statusCode(201);
        
        given()
        .when()
        	.get("1")
        .then()
	    	.statusCode(200)
	    	.body("result", equalTo("The data are not of Equal size"));
    }
    
    /**
     * Test the Comparison result by Providing Equal sized but Different Base64 encoded binary values as Input.
     *
     */
    @Test
    public void testEqualSizedDataWithDifferentValues() throws JSONException, JsonMappingException, JsonParseException, IOException {
    	ObjectMapper mapper = new ObjectMapper();
    	
        JSONObject jsonObjOne = new JSONObject().put("encodedValue","MTEwMTAxMDEwMQ==");
        SearchRequest searchRequestOne = mapper.readValue(jsonObjOne.toString(), SearchRequest.class);  
        
        JSONObject jsonObjTwo = new JSONObject().put("encodedValue","MTEwMTAwMDEwMQ==");
        SearchRequest searchRequestTwo = mapper.readValue(jsonObjTwo.toString(), SearchRequest.class);  
        
        given()
            .contentType("application/json")
            .body(searchRequestOne)
        .when()
            .post(1+"/left")
        .then()
            .statusCode(201);
        
        given()
	        .contentType("application/json")
	        .body(searchRequestTwo)
	    .when()
	        .post(1+"/right")
	    .then()
	        .statusCode(201);
        
        given()
        .when()
        	.get("1")
        .then()
	    	.statusCode(200)
	    	.body("result", equalTo("The data are different"));
    }
    
    /**
     * Test the Comparison result when there is no data provided for Left and Right window.
     *
     */
    @Test
    public void testCompareWithoutData() throws JSONException, JsonMappingException, JsonParseException, IOException {
        given()
        .when()
        	.get("2")
        .then()
	    	.statusCode(406)
	    	.body("result", equalTo("Please provide only the Binary data and was encoded in Base64"));
    }
    
    /**
     * Test the Comparison result when data is provided for only one (either Left or Right) window.
     *
     */
    @Test
    public void testCompare_WithOnlyOneWindowDataProvided() throws JSONException, JsonMappingException, JsonParseException, IOException {
    	ObjectMapper mapper = new ObjectMapper();
        JSONObject jsonObjOne = new JSONObject().put("encodedValue","MTEwMTAxMDEwMQ==");
        SearchRequest searchRequestOne = mapper.readValue(jsonObjOne.toString(), SearchRequest.class);  
        
        given()
            .contentType("application/json")
            .body(searchRequestOne)
        .when()
            .post(3+"/left")
        .then()
            .statusCode(201);
        
        given()
        .when()
        	.get("3")
        .then()
	    	.statusCode(406)
	    	.body("result", equalTo("Please provide only the Binary data and was encoded in Base64"));
    }
    
    /**
     * Test the result for receiving all values of a particular ID.
     *
     */
    @Test
    public void testIdAll() {
    	given().when().get("1/all").then()
    	.statusCode(200)
    	.body("content.encodedString", hasItem("MTEwMTAwMDEwMQ=="))
    	;
    }
    
    /**
     * Test whether the Bootstrapped data is saved in the database once the application is started.
     *
     */
    @Test
    public void testAll_BootstrappedData() {
    	given().when().get("/all").then()
    	.statusCode(200)
    	.body("content.encodedString", hasItem("ew0KICAgICJkYXRhIjogIlRoaXMgaXMgYSBTdHJpbmciDQp9"))
    	;
    }
    
    /**
     * Test the Comparison result by Providing Base64 encoded Non-binary value.
     *
     */
    @Test
    public void testCompare_WithNonBinaryValueAsSourceOfEncoding() throws JSONException, JsonMappingException, JsonParseException, IOException {
    	ObjectMapper mapper = new ObjectMapper();
    	
        JSONObject jsonObjOne = new JSONObject().put("encodedValue","U2FzYW5rIFNhbXVkcmFsYQ==");
        SearchRequest searchRequestOne = mapper.readValue(jsonObjOne.toString(), SearchRequest.class);  
        
        given()
            .contentType("application/json")
            .body(searchRequestOne)
        .when()
            .post(4+"/left")
        .then()
            .statusCode(201);
        
    	given().when().get("4").then()
    	.statusCode(406)
    	.body("result", equalTo("Please provide only the Binary data and was encoded in Base64"))
    	;
    }
    
}
