package com.ps.dealership_api_starter.controllers;

import com.ps.dealership_api_starter.data.VehicleDao;
import com.ps.dealership_api_starter.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("vehicles")
@CrossOrigin
public class VehicleController {

    private VehicleDao vehicleDao;

    @Autowired
    public VehicleController(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @GetMapping("/vehicles")
    public List<Vehicle> searchVehicles(@RequestParam(name = "minPrice", required = false) Double minPrice,
                                        @RequestParam(name = "maxPrice", required = false) Double maxPrice,
                                        @RequestParam(name = "make", required = false) String make,
                                        @RequestParam(name = "model", required = false) String model,
                                        @RequestParam(name = "minYear", required = false) Integer minYear,
                                        @RequestParam(name = "maxYear", required = false) Integer maxYear,
                                        @RequestParam(name = "color", required = false) String color,
                                        @RequestParam(name = "minMiles", required = false) Integer minMiles,
                                        @RequestParam(name = "maxMiles", required = false) Integer maxMiles,
                                        @RequestParam(name = "type", required = false) String type) {
        try {
            return vehicleDao.getFilteredVehicles(minPrice, maxPrice, make, model, minYear, maxYear, color, minMiles, maxMiles, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/vehicles/{vin}")
    public Vehicle getVehicleByVin(@PathVariable String vin) {
        try {
            return vehicleDao.getVehicleByVin(vin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/vehicles")
    public Vehicle addVehicle(@RequestBody Vehicle vehicle) {
        try {
            int generatedId = vehicleDao.createVehicle(vehicle);
            if (generatedId == -1) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create vehicle");
            }
            return vehicle;
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @PutMapping("{vin}")
    public void updateVehicle(@PathVariable String vin, @RequestBody Vehicle vehicle) {
        try {
            Vehicle updatedVehicle = vehicleDao.updateVehicle(vin, vehicle);
            if (updatedVehicle == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DeleteMapping("{vin}")
    public void deleteVehicle(@PathVariable String vin) {
        try {
            boolean deleted = vehicleDao.deleteVehicle(vin);
            if (!deleted) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
