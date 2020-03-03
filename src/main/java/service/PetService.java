package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Pet;
import org.apache.http.HttpStatus;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class PetService {

    private final static String BASE_URL = "https://petstore.swagger.io";
    private final static String V2_PET_ENDPOINT = "v2/pet";
    private static ObjectMapper mapper = new ObjectMapper();

    public static void createPet(Pet pet) throws JsonProcessingException {
        RequestSpecification request = buildRestSpec(pet);
        Response response = request.post(V2_PET_ENDPOINT);
        verifyPet(pet, response);
    }

    private static void verifyPet(Pet pet, Response response) throws JsonProcessingException {
        assert response.statusCode() == HttpStatus.SC_OK;
        assertThat(response.getBody().asString(), equalTo(mapper.writeValueAsString(pet)));
    }

    public static void updatePet(Pet pet) throws JsonProcessingException {
        RequestSpecification request = buildRestSpec(pet);
        Response response = request.put(V2_PET_ENDPOINT);
        verifyPet(pet, response);
    }

    public static void deletePet(int petId){
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given().
                header("accept", "application/json");
        Response response = request.delete(V2_PET_ENDPOINT+"/"+petId);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.SC_OK));
    }


    public static void getPet(Pet pet, boolean deleted) throws JsonProcessingException {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given().
                header("accept", "application/json");
        Response response = request.get(V2_PET_ENDPOINT+"/"+pet.getID());
        if(!deleted){
            verifyPet(pet, response);
        }
        else{
            assertThat(response.getStatusCode(), equalTo(HttpStatus.SC_NOT_FOUND));
        }
    }


    public static void updatePet(int petId, String newName, String newStatus) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given().
                header("Content-Type", "application/x-www-form-urlencoded")
                .param("name",newName)
                .param("status",newStatus);
        Response response = request.post(V2_PET_ENDPOINT+"/"+petId);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.SC_OK));
    }

    private static RequestSpecification buildRestSpec(Pet p) throws JsonProcessingException {
        RestAssured.baseURI = BASE_URL;
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .body(mapper.writeValueAsString(p));
    }
}
