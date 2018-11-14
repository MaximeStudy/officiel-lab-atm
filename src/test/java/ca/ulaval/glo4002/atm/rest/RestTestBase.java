package ca.ulaval.glo4002.atm.rest;
import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ca.ulaval.glo4002.atm.suites.ApiITestSuite;

public class RestTestBase {
    protected static final int ACCOUNT_NUMBER_WITH_MONEY = 123;
    protected static final int ACCOUNT_NUMBER_WITHOUT_MONEY = 456;

    protected RequestSpecification givenBaseRequest() {
        return given().accept(ContentType.JSON).port(ApiITestSuite.TEST_SERVER_PORT).contentType(ContentType.JSON);
    }

    protected Map<String, Object> buildPathParams(String key, Object value) {
        Map<String, Object> params = new HashMap<>();
        params.put(key, value);
        return params;
    }
}
