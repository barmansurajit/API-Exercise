package com.contsol.pax.repository;

import com.contsol.pax.domain.Passenger;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PassengerRepository implements PanacheMongoRepository<Passenger> {
    public Passenger findByUUID(String uuid){
        return find("uuid", uuid).firstResult();
    }
    public long deleteByUUID(String uuid){
        return delete("uuid", uuid);
    }
}