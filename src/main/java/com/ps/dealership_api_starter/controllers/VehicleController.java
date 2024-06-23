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

    private final VehicleDao vehicleDao;

    @Autowired
    public VehicleController(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @GetMapping("/vehicles")
    public List<Vehicle> searchVehicles(
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice,
            @RequestParam(name = "make", required = false) String make,
            @RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "minYear", required = false) Integer minYear,
            @RequestParam(name = "maxYear", required = false) Integer maxYear,
            @RequestParam(name = "color", required = false) String color,
            @RequestParam(name = "minMiles", required = false) Integer minMiles,
            @RequestParam(name = "maxMiles", required = false) Integer maxMiles,
            @RequestParam(name = "type", required = false) String type
    ) {
        try {
            return vehicleDao.search(minPrice, maxPrice, make, model, minYear, maxYear, color, minMiles, maxMiles, type);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while searching for vehicles");
        }
    }

    @GetMapping("/vehicles/{vin}")
    public Vehicle getVehicleByVin(@PathVariable String vin) {
        try {
            Vehicle vehicle = (Vehicle) vehicleDao.getVehicleByVin(vin);
            if (vehicle == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            return vehicle;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }


    @GetMapping("/vehicles/{make}/{model}")
    public Vehicle getVehicleByMakeModel(@PathVariable String make, @PathVariable String model) {
        try {
            Vehicle vehicle = (Vehicle) vehicleDao.getVehicleByMakeModel(make, model);
            if (vehicle == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            return vehicle;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }


    @GetMapping("/vehicles/year/{minYear}/{maxYear}")
    public List<Vehicle> getVehiclesByYearRange(@PathVariable int minYear, @PathVariable int maxYear) {
        try {
            List<Vehicle> vehicles = vehicleDao.getVehiclesByYearRange(minYear, maxYear);
            if (vehicles == null || vehicles.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            return vehicles;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    @GetMapping("/vehicles/color/{color}")
    public List<Vehicle> getVehiclesByColor(@PathVariable String color) {
        try {
            List<Vehicle> vehicles = vehicleDao.getVehiclesByColor(color);
            if (vehicles == null || vehicles.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            return vehicles;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
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
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while adding vehicle");
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
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while updating vehicle");
        }
    }

    @DeleteMapping("{id}")
    public void deleteVehicle(@PathVariable int id) {
        try {
            Vehicle vehicle = vehicleDao.deleteVehicle(id);
            if (vehicle == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
}
