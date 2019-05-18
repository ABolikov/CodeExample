package api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

/**
 * @author abolikov
 * @version 1.0
 */

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
        allureOutputInfo(null, send, respGET.asString());
        return respGET;
    }

    private JsonPath post(String url, String request, ResponseSpecification specification) {
        String send = translator.translate(request);
        log.info(url + "\n");
        log.info(send + "\n");
        Response response = given().contentType(JSON).body(send).when().post(url);
        log.info(response.asString());
        response.then().spec(specification);
        allureOutputInfo(url, send, response.asString());
        return new JsonPath(response.asString());
    }

    public JsonPath post(String url, String request) {
        return post(url, request, successAnswer);
    }

    public JsonPath getUnirest(String request) throws UnirestException {
        String send = translator.translate(request);
        HttpResponse<String> s = Unirest.get(send).
                header("content-type", "text/html").
                header("cache-control", "no-cache").asString();
        log.info(send + "\n");
        log.info(s.getBody() + "\n");
        allureOutputInfo(null, send, s.getBody());
        return new JsonPath(s.getBody());
    }

}
