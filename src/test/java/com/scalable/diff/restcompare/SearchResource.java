package com.scalable.diff.restcompare;

import static io.restassured.RestAssured.given;

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
 * Unit test for the application functionality
 * 
 * @author Sasank Samudrala
 * @version 0.0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestcompareApplication.class, 
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SearchResource {
    
    @LocalServerPort
    private int port;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = Integer.valueOf(port);
        RestAssured.basePath = ResourceConstants.RESOURCE_V1;
        RestAssured.baseURI = "http://localhost";
    }

    
    @Test
    public void testAll() {
        given().when().get("/all").then().statusCode(200);
    }
    
    @Test
    public void testIdAll() {
        given().when().get(0+"/all").then().statusCode(200);
    }
    
    @Test
    public void testIdLeft() throws JSONException, JsonMappingException, JsonParseException, IOException {
        JSONObject jsonObj = new JSONObject().put("encodedValue","MTAxMDEx");
        ObjectMapper mapper = new ObjectMapper();
        SearchRequest searchRequest = mapper.readValue(jsonObj.toString(), SearchRequest.class);  
        
        given()
            .contentType("application/json")
            .body(searchRequest)
        .when()
            .post(0+"/left")
        .then()
            .statusCode(201);
    }
    
    @Test
    public void testIdRight() throws JSONException, JsonMappingException, JsonParseException, IOException {
        JSONObject jsonObj = new JSONObject().put("encodedValue","MTAxMDEx");
        ObjectMapper mapper = new ObjectMapper();
        SearchRequest searchRequest = mapper.readValue(jsonObj.toString(), SearchRequest.class);  
        
        given()
            .contentType("application/json")
            .body(searchRequest)
        .when()
            .post(0+"/right")
        .then()
            .statusCode(201);
    }
    
    @Test
    public void testIdCompareResource() throws JSONException, JsonMappingException, JsonParseException, IOException {
        given()
            .when()
                .get("/"+0)
            .then()
                .statusCode(406);
    }
    

}
