package api;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class Send extends AbstractSend {

    public Send setTranslator(SendTranslator translator) {
        this.translator = translator;
        return this;
    }

    public Response get(String request) {
        String send = translator.translate(request);
        Response respGET = given().get(request);
        log.info(send + "\n");
        log.info(respGET.asString() + "\n");
        return respGET;
    }

    private JsonPath post(String url, String request, ResponseSpecification specification) {
        String send = translator.translate(request);
        log.info(url + "\n");
        log.info(send + "\n");
        Response response = given().contentType(JSON).body(send).when().post(url);
        log.info(response.asString());
        response.then().spec(specification);
        return new JsonPath(response.asString());
    }

    public JsonPath post(String url, String request) {
        return post(url, request, successAnswer);
    }

}
