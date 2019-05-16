package api;

import io.restassured.builder.ResponseSpecBuilder;

import io.restassured.specification.ResponseSpecification;

import org.apache.http.HttpStatus;
import settings.Setting;

abstract class AbstractSend extends Setting {
    SendTranslator translator = new SendTranslator();
    ResponseSpecification successAnswer = new ResponseSpecBuilder().expectStatusCode(HttpStatus.SC_OK).build();
}
