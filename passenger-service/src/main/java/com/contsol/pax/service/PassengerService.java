package com.contsol.pax.service;

import com.contsol.pax.domain.Passenger;
import com.contsol.pax.dto.PassengerDTO;
import com.contsol.pax.repository.PassengerRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class PassengerService {
    private final Logger log = Logger.getLogger(PassengerService.class);

    final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public List<PassengerDTO.Response.Public> getPassengers() {
        var passengers = passengerRepository.findAll().list();
        return passengers.stream()
                .map(pax ->
                        new PassengerDTO.Response.Public(
                                UUID.fromString(pax.uuid),
                                pax.survived,
                                pax.name,
                                pax.sex,
                                pax.age,
                                pax.parentsOrChildrenAboard,
                                pax.siblingsOrSpousesAboard,
                                pax.fare
                        ))
                .collect(Collectors.toList());
    }

    @Transactional
    public PassengerDTO.Response.Public addPassenger(PassengerDTO.Request.Create person) {
        Passenger passenger = new Passenger();
        passenger.uuid = UUID.randomUUID().toString();
        passenger.survived = person.hasSurvived();
        passenger.name = person.getName();
        passenger.sex = person.getSex();
        passenger.age = person.getAge();
        passenger.parentsOrChildrenAboard = person.getParentsOrChildrenAboard();
        passenger.siblingsOrSpousesAboard = person.getSiblingsOrSpousesAboard();
        passenger.fare = person.getFare();

        passengerRepository.persist(passenger);
        Passenger pax = passengerRepository.findByUUID(passenger.uuid);
        return new PassengerDTO.Response.Public(
                UUID.fromString(pax.uuid),
                pax.survived,
                pax.name,
                pax.sex,
                pax.age,
                pax.parentsOrChildrenAboard,
                pax.siblingsOrSpousesAboard,
                pax.fare
        );
    }

    public PassengerDTO.Response.Public getPassenger(String uuid) {
        Passenger pax = passengerRepository.findByUUID(uuid);
        return new PassengerDTO.Response.Public(
                UUID.fromString(pax.uuid),
                pax.survived,
                pax.name,
                pax.sex,
                pax.age,
                pax.parentsOrChildrenAboard,
                pax.siblingsOrSpousesAboard,
                pax.fare
        );
    }

    @Transactional
    public PassengerDTO.Response.Public updatePassenger(String uuid, PassengerDTO.Request.Create person) {

        Passenger pax = passengerRepository.findByUUID(uuid);

        pax.uuid = uuid;
        pax.survived = person.hasSurvived();
        pax.name = person.getName();
        pax.sex = person.getSex();
        pax.age = person.getAge();
        pax.parentsOrChildrenAboard = person.getParentsOrChildrenAboard();
        pax.siblingsOrSpousesAboard = person.getSiblingsOrSpousesAboard();
        pax.fare = person.getFare();
        passengerRepository.update(pax);
        return new PassengerDTO.Response.Public(
                UUID.fromString(pax.uuid),
                pax.survived,
                pax.name,
                pax.sex,
                pax.age,
                pax.parentsOrChildrenAboard,
                pax.siblingsOrSpousesAboard,
                pax.fare
        );
    }

    @Transactional
    public void deletePassenger(String uuid) {
        long status = passengerRepository.deleteByUUID(uuid);
        log.debug("Delete status:" + status);
    }
}