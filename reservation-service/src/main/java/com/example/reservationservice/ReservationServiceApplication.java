package com.example.reservationservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;
import java.util.stream.Stream;

@SpringBootApplication
public class ReservationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationServiceApplication.class, args);
    }

}

@Entity
class Reservation {
    @Id
    @GeneratedValue
    private Long id;
    private String reservationName;

    Reservation() {
    }

    public Reservation(String reservationName) {
        this.reservationName = reservationName;
    }
}

interface RservationRepo extends JpaRepository<Reservation, Long> {
    Collection<Reservation> findByReservationName(String rn);

}

@Component
class SampleDataCLR implements CommandLineRunner {
    private final RservationRepo reservationRepo;

    public SampleDataCLR(RservationRepo reservationRepo) {
        this.reservationRepo = reservationRepo;
    }


    @Override
    public void run(String... args) throws Exception {
        Stream.of("shaaban", "ahmed", "ibrahim").forEach(name ->
        {
            this.reservationRepo.save(new Reservation(name));
        });
        this.reservationRepo.findByReservationName("shaaban").forEach(System.out::println);
    }
}

@RestController
@RefreshScope
class TestController {

    @Value("${message}")
    private String message;

    @GetMapping("/message")
    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }
}
