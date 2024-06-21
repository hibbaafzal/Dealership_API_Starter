package com.ps.dealership_api_starter.data;

import com.ps.dealership_api_starter.models.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleDao {

    List<Vehicle> getAllVehicles();

    List<Vehicle> getVehiclesByMakeModel(String make, String model);

    List<Vehicle> getVehiclesByYearRange(int startYear, int endYear);

    List<Vehicle> getVehiclesByColor(String color);


    int createVehicle(Vehicle vehicle);


    void deleteVehicle(String vin);


    List<Vehicle> getVehicleByVin(String vin);

}







