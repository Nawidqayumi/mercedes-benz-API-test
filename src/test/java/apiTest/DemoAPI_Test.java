package apiTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.given;


public class DemoAPI_Test {

    @BeforeClass
    public void beforeClass(){ RestAssured.baseURI = ConfigurationReader.get("baseUrl");
    }

    /**
     *given acccept type is JSON
     * And Path param is vehicles/{vehicleId}/resources/decklidstatus: WDB111111ZZZ22222
     * Then status code should be 200
     * And content type should be : application/json;charset=utf-8
     * And response body should have following data;
     *
     *   "decklidstatus":
     *     "value": "false",
     *     "timestamp": 1541080800000
     *
     * And headder content-length: 71
     * ANd header host: api.mercedes-benz.com
     */
    String vehicleId =  ConfigurationReader.get("vehicleId");
    String token = ConfigurationReader.get("token");

    @Test
    public void decklidResourcesValidation(){

        Response response = given().accept(ContentType.JSON).and()
                .header("Authorization", token)
                .and().pathParam("vehicleId", vehicleId)
                .when().get("vehicles/{vehicleId}/resources/decklidstatus");

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json;charset=utf-8");
        Assert.assertEquals(Integer.parseInt(response.header("content-length")), 71);
        Assert.assertEquals(response.header("host"), "api.mercedes-benz.com");
        Assert.assertFalse(Boolean.parseBoolean(response.path("decklidstatus.value")));
    }

    /**
     *given acccept type is JSON
     * And Path param is vehicles/{vehicleId}/resources/decklidstatus: WDB111111ZZZ22222
     * Then status code should be 401
     * And content type should be : application/json
     * And response body should have following data;
     *
     *   "exveErrorId": "101",
     *    "exveErrorMsg": "Unauthorized",
     *    "exveErrorRef": "e3511d13-bc2e-4f72-bd02-952bb64c5bf5"
     *
     * And headder content-length: 119
     * ANd header host: api.mercedes-benz.com
     */
    @Test
    public void decklidResourcesUnauthorizedRequest(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("vehicleId", vehicleId)
                .when().get("vehicles/{vehicleId}/resources/decklidstatus");

        response.prettyPrint();

        Assert.assertEquals(response.statusCode(), 401);
        Assert.assertEquals(response.contentType(), "application/json");
        Assert.assertEquals(Integer.parseInt(response.header("content-length")), 119);
        Assert.assertEquals(response.header("host"), "api.mercedes-benz.com");
        Assert.assertEquals(response.path("exveErrorMsg"), "Unauthorized");

    }

    /**
     * given acccept type is JSON
     * And Path param is /vehicles/{vehicleId}/resources/doorstatusfrontleft: WDB111111ZZZ22222
     * Then status code should be 200
     * And content type should be : application/json;charset=utf-8
     * And response body should have following data;
     *
     *   "decklidstatus":
     *     "value": "false",
     *     "timestamp": 1541080800000
     *
     * And headder content-length: 77
     * ANd header host: api.mercedes-benz.com
     */
    @Test
    public void frontLeftDoorValidation(){

        Response response = given().accept(ContentType.JSON).and()
                .header("Authorization", token)
                .and().pathParam("vehicleId", vehicleId)
                .when().get("vehicles/{vehicleId}/resources/doorstatusfrontleft");

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json;charset=utf-8");
        Assert.assertEquals(Integer.parseInt(response.header("content-length")), 77);
        Assert.assertFalse(Boolean.parseBoolean(response.path("decklidstatus.value")));
    }
}
