package com.xenostar.dwk.Controller;

import com.xenostar.dwk.Entity.Reservation;
import com.xenostar.dwk.Service.ReservationService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/reservations")
public class ReservationController {


    private ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService){
        this.reservationService=reservationService;
    }


    @PostMapping("/add")
    public String saveReservation(@RequestBody Reservation reservation) throws ExecutionException, InterruptedException {
        return reservationService.saveReservation(reservation);
    }

    @GetMapping("/get/{name}")
    public Reservation getReservation(@PathVariable String name) throws ExecutionException,InterruptedException {
        return reservationService.getReservation(name);
    }


}
