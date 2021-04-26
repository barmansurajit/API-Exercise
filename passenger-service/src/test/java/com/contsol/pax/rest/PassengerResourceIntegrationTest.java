package com.contsol.pax.rest;

import com.contsol.pax.dto.PassengerDTO;
import com.contsol.pax.dto.PassengerDTO.Response.Public;
import com.contsol.pax.service.PassengerService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestHTTPEndpoint(PassengerResource.class)
class PassengerResourceIntegrationTest {

    @InjectMock
    PassengerService passengerService;

    @Inject
    PassengerResource passengerResource;

    @Test
    void people_resource_returns_200_with_expected_id_persons() {
        // arrange
        when(passengerService.getPassengers())
                .thenReturn(Collections.singletonList(new Public(UUID.randomUUID(),
                        true,
                        "Jeff Barr",
                        "male",
                        55,
                        0,
                        0,
                        500.00)));

        // act
        List<Public> people = given()
                .when()
                .get()
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getList(".", PassengerDTO.Response.Public.class);

        // assert
        assertThat(people)
                .isNotEmpty()
                .hasSize(1)
                .extracting(PassengerDTO.Response.Public::getName)
                .containsExactly("Jeff Barr");
        Mockito.verify(passengerService, Mockito.times(1)).getPassengers();
    }

    @Test
    void people_resource_returns_201_with_expected_id_of_person() {
//        // arrange
//        Person person = new Person(UUID.randomUUID(),
//                true,
//                "Jeff Barr",
//                "male",
//                55,
//                0,
//                0,
//                500.00);
//        when(peopleService.addPerson(person))
//                .thenReturn(person);
//
//        // act
//        given()
//                .contentType(ContentType.JSON)
//                .body(person)
//                .when()
//                .post()
//                .then()
//                .assertThat()
//                .statusCode(200)
//                .log()
//                .all();

//        Mockito.verify(peopleService, Mockito.times(1)).addPerson(person);
    }
}