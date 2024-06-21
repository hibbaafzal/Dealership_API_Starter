package com.ps.dealership_api_starter.data.mysql;


import com.ps.dealership_api_starter.data.VehicleDao;
import com.ps.dealership_api_starter.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MySqlVehicleDao implements VehicleDao {

    public MySqlVehicleDao(DataSource dataSource) {

    }


    @Repository
    public class VehicleDao {

        @Autowired
        private DataSource dataSource;

        public List<Vehicle> getAllVehicles() {
            List<Vehicle> vehicles = new ArrayList<>();
            try (
                    Connection connection = dataSource.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM vehicles");
                    ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                while (resultSet.next()) {
                    String vin = resultSet.getString("vin");
                    String make = resultSet.getString("make");
                    String model = resultSet.getString("model");
                    int year = resultSet.getInt("year");
                    String color = resultSet.getString("color");

                    Vehicle vehicle = new Vehicle(vin, make, model, year, color);
                    vehicles.add(vehicle);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return vehicles;
        }

        public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
            List<Vehicle> vehicles = new ArrayList<>();
            try (
                    Connection connection = dataSource.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "SELECT * FROM vehicles WHERE make = ? AND model = ?")
            ) {
                preparedStatement.setString(1, make);
                preparedStatement.setString(2, model);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Vehicle vehicle = new Vehicle(
                                resultSet.getString("vin"),
                                resultSet.getString("make"),
                                resultSet.getString("model"),
                                resultSet.getInt("year"),
                                resultSet.getString("color")
                        );
                        vehicles.add(vehicle);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return vehicles;
        }

        public List<Vehicle> getVehiclesByYearRange(int startYear, int endYear) {
            List<Vehicle> vehicles = new ArrayList<>();
            try (
                    Connection connection = dataSource.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "SELECT * FROM vehicles WHERE year BETWEEN ? AND ?")
            ) {
                preparedStatement.setInt(1, startYear);
                preparedStatement.setInt(2, endYear);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Vehicle vehicle = new Vehicle(
                                resultSet.getString("vin"),
                                resultSet.getString("make"),
                                resultSet.getString("model"),
                                resultSet.getInt("year"),
                                resultSet.getString("color")
                        );
                        vehicles.add(vehicle);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return vehicles;
        }

        public List<Vehicle> getVehiclesByColor(String color) {
            List<Vehicle> vehicles = new ArrayList<>();
            try (
                    Connection connection = dataSource.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "SELECT * FROM vehicles WHERE color = ?")
            ) {
                preparedStatement.setString(1, color);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Vehicle vehicle = new Vehicle(
                                resultSet.getString("vin"),
                                resultSet.getString("make"),
                                resultSet.getString("model"),
                                resultSet.getInt("year"),
                                resultSet.getString("color")
                        );
                        vehicles.add(vehicle);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return vehicles;
        }

        public int createVehicle(Vehicle vehicle) {
            int generatedId = -1;
            try (
                    Connection connection = dataSource.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "INSERT INTO vehicles(vin, make, model, year, color) VALUES(?,?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS)
            ) {
                preparedStatement.setString(1, vehicle.getVin());
                preparedStatement.setString(2, vehicle.getMake());
                preparedStatement.setString(3, vehicle.getModel());
                preparedStatement.setInt(4, vehicle.getYear());
                preparedStatement.setString(5, vehicle.getColor());

                preparedStatement.executeUpdate();

                try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                    if (keys.next()) {
                        generatedId = keys.getInt(1);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return generatedId;
        }

        public void deleteVehicle(String vin) {
            try (
                    Connection connection = dataSource.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "DELETE FROM vehicles WHERE vin = ?")
            ) {
                preparedStatement.setString(1, vin);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public List <Vehicle> getVehicleByVin(String vin) {
            try (
                    Connection connection = dataSource.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "SELECT * FROM vehicles WHERE vin = ?")
            ) {
                preparedStatement.setString(1, vin);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Vehicle vehicle = new Vehicle(
                                resultSet.getString("vin"),
                                resultSet.getString("make"),
                                resultSet.getString("model"),
                                resultSet.getInt("year"),
                                resultSet.getString("color")
                        );
                        return (vehicle);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Optional.empty();
        }

        @Component
        public Vehicle updateVehicle(String vin, Vehicle vehicle) {
            Vehicle existingVehicle = getVehicleByVin(vin).orElse(null);
            if (existingVehicle != null) {
                try (
                        Connection connection = dataSource.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(
                                "UPDATE vehicles SET make = ?, model = ?, year = ?, color = ? WHERE vin = ?")
                ) {
                    preparedStatement.setString(1, vehicle.getMake());
                    preparedStatement.setString(2, vehicle.getModel());
                    preparedStatement.setInt(3, vehicle.getYear());
                    preparedStatement.setString(4, vehicle.getColor());
                    preparedStatement.setString(5, vin);

                    preparedStatement.executeUpdate();
                    return vehicle;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}

