package com.contsol.pax.rest;

import com.contsol.pax.dto.PassengerDTO;
import com.contsol.pax.service.PassengerService;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/people")
@RequestScoped
@RegisterForReflection
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class PassengerResource {

    private final Logger log = Logger.getLogger(PassengerResource.class);

    final PassengerService passengerService;

    public PassengerResource(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GET
    @Timed(description = "Monitor the time getPassengers method takes")
    @Counted(description = "Monitor how many times getPassengers method was called")
    public Response getPassengers() {
        log.debug("Inside PeopleResource::getPeople");
        return Response
                .ok(passengerService.getPassengers())
                .build();
    }

    @POST
    public Response addPassenger(PassengerDTO.Request.Create person) {
        log.debug("Inside PeopleResource::addPerson");
        return Response.ok(passengerService.addPassenger(person))
                .build();
    }

    @GET
    @Path("/{uuid}")
    public Response getPassenger(@PathParam("uuid") String uuid) {
        log.debug("Inside PeopleResource::getPerson");
        return Response.ok(passengerService.getPassenger(uuid))
                .build();
    }

    @PUT
    @Path("/{uuid}")
    public Response updatePassenger(@PathParam("uuid") String uuid, PassengerDTO.Request.Create person) {
        log.debug("Inside PeopleResource::updatePerson {}");
        return Response.accepted(passengerService.updatePassenger(uuid, person))
                .build();
    }

    @DELETE
    @Path("/{uuid}")
    public Response deletePassenger(@PathParam("uuid") String uuid) {
        log.debug("Inside PeopleResource::deletePerson");
        passengerService.deletePassenger(uuid);
        return Response.ok().build();
    }
}