package com.contsol.pax.init;

import com.contsol.pax.EmbeddedMongoQuarkusTestResource;
import com.contsol.pax.dto.PassengerDTO;
import com.contsol.pax.repository.PassengerRepository;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

@QuarkusTest
@QuarkusTestResource(EmbeddedMongoQuarkusTestResource.class)
class PassengerResourceTest {
    @Inject
    AppLifecycleBean appLifecycleBean;

    @Inject
    PassengerRepository passengerRepository;

    @BeforeAll
    public static void init() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @BeforeEach
    public void setup() throws Exception {
        passengerRepository.deleteAll();
        appLifecycleBean.onStart(Mockito.mock(StartupEvent.class));
    }

    @Test
    @DisplayName("Test - When calling POST - /people should create a passenger resource - 200")
    void addPassenger() {
        PassengerDTO.Response.Public aPublic = new PassengerDTO.Response.Public(UUID.randomUUID(),
                true,
                "Jeff Barr",
                "male",
                55,
                0,
                0,
                500.00);
        given()
                .when()
                .body(aPublic)
                .contentType(ContentType.JSON)
                .post("/people")
                .then()
                .statusCode(200)
                .body("name", is("Jeff Barr"))
                .body("sex", is("male"))
                .body("id", is(notNullValue()))
                .body("age", is(55));
    }

    @Test
    @DisplayName("Test - When Calling GET - /people should return all passengers - 200 - OK")
    void getPassengers() throws Exception {
        given()
                .when()
                .get("/people")
                .then()
                .statusCode(200)
                .body("$.size", greaterThan(100));
    }
}
