package com.mini.backend.service;

import com.mini.backend.model.Ride;
import com.mini.backend.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    public Ride createRide(Ride ride) {
        ride.setStatus("searching");
        // Generate random 4-digit OTP
        int otp = (int)(Math.random() * 9000) + 1000;
        ride.setOtp(String.valueOf(otp));
        return rideRepository.save(ride);
    }

    public Ride acceptRide(String rideId, String driverEmail) {
        Optional<Ride> rideOpt = rideRepository.findById(rideId);
        if (rideOpt.isPresent()) {
            Ride ride = rideOpt.get();
            ride.setDriverEmail(driverEmail);
            ride.setStatus("accepted");
            return rideRepository.save(ride);
        }
        throw new RuntimeException("Ride not found");
    }

    public List<Ride> getAvailableRides() {
        return rideRepository.findByStatus("searching");
    }

    public Optional<Ride> getRideById(String id) {
        return rideRepository.findById(id);
    }
}
