package com.contsol.pax.domain;

import io.quarkus.mongodb.panache.MongoEntity;
import org.bson.types.ObjectId;

@MongoEntity(collection = "passengers")
public class Passenger {
    public ObjectId id;
    public String uuid;
    public boolean survived;
    public int paxClass;
    public String name;
    public String sex;
    public int age;
    public int siblingsOrSpousesAboard;
    public int parentsOrChildrenAboard;
    public double fare;
}