package api;

import io.restassured.builder.ResponseSpecBuilder;

import io.restassured.specification.ResponseSpecification;

import org.apache.http.HttpStatus;
import settings.Setting;

/**
 * @author abolikov
 * @version 1.0
 */

abstract class AbstractSend extends Setting {
    SendTranslator translator = new SendTranslator();
    ResponseSpecification successAnswer = new ResponseSpecBuilder().expectStatusCode(HttpStatus.SC_OK).build();
}
