package com.contsol.pax.init;

import com.contsol.pax.domain.Passenger;
import com.contsol.pax.repository.PassengerRepository;
import com.mongodb.client.MongoClient;
import com.opencsv.CSVReader;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class AppLifecycleBean {
    private static final Logger log = Logger.getLogger(AppLifecycleBean.class);

    @ConfigProperty(name = "configuration.initialLoad", defaultValue = "true")
    boolean initialLoad;

    @Inject
    MongoClient mongoClient;

    @Inject
    PassengerRepository passengerRepository;

    void onStart(@Observes StartupEvent ev) throws Exception {
        if (initialLoad) {
            log.info("MongoDB settings: " + mongoClient.getClusterDescription());
            if (passengerRepository.count() == 0) {
                var classLoader = Thread.currentThread().getContextClassLoader();
                Reader reader = new InputStreamReader(Objects.requireNonNull(classLoader.getResourceAsStream("titanic.csv")));
                List<String[]> data = readAll(reader);
                List<Passenger> passengers = data.stream().skip(1).map(str -> {
                    var passenger = new Passenger();
                    passenger.uuid = UUID.randomUUID().toString();
                    passenger.survived = !str[0].equals("0");
                    passenger.paxClass = Integer.parseInt(str[1]);
                    passenger.name = str[2];
                    passenger.sex = str[3];
                    passenger.age = (int) Double.parseDouble(str[4]);
                    passenger.siblingsOrSpousesAboard = Integer.parseInt(str[5]);
                    passenger.parentsOrChildrenAboard = Integer.parseInt(str[6]);
                    passenger.fare = Double.parseDouble(str[7]);
                    return passenger;
                }).collect(Collectors.toList());
                passengerRepository.persist(passengers);
            }
        }
    }

    private List<String[]> readAll(Reader reader) throws Exception {
        var csvReader = new CSVReader(reader);
        List<String[]> list = csvReader.readAll();
        reader.close();
        csvReader.close();
        return list;
    }
}