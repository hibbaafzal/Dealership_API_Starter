
package com.ps.dealership_api_starter.data.mysql;

import com.ps.dealership_api_starter.controllers.VehicleController;
import com.ps.dealership_api_starter.data.VehicleDao;
import com.ps.dealership_api_starter.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MySqlVehicleDao extends VehicleController implements VehicleDao {

    private final DataSource dataSource;

    @Autowired
    public MySqlVehicleDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
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
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles WHERE make = ? AND model = ?";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
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
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> getVehiclesByYearRange(int startYear, int endYear) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles WHERE year BETWEEN ? AND ?";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
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
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> getVehiclesByColor(String color) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles WHERE color = ?";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
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
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return vehicles;
    }

    @Override
    public int createVehicle(Vehicle vehicle) {
        int generatedId = -1;
        String query = "INSERT INTO vehicles (vin, make, model, year, color) VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        query, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, vehicle.getVin());
            preparedStatement.setString(2, vehicle.getMake());
            preparedStatement.setString(3, vehicle.getModel());
            preparedStatement.setInt(4, vehicle.getYear());
            preparedStatement.setString(5, vehicle.getColor());

            preparedStatement.executeUpdate();

            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                while (keys.next()) {
                    generatedId = keys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return generatedId;
    }

    @Override
    public void deleteVehicle(String vin) {
        String query = "DELETE FROM vehicles WHERE vin = ?";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, vin);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Vehicle> getVehicleByVin(String vin) {
        String query = "SELECT * FROM vehicles WHERE vin = ?";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, vin);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Vehicle vehicle = new Vehicle(
                            resultSet.getString("vin"),
                            resultSet.getString("make"),
                            resultSet.getString("model"),
                            resultSet.getInt("year"),
                            resultSet.getString("color")
                    );
                    return Optional.of(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void updateVehicle(String vin, Vehicle vehicle) {
        String query = "UPDATE vehicles SET make = ?, model = ?, year = ?, color = ? WHERE vin = ?";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, vehicle.getMake());
            preparedStatement.setString(2, vehicle.getModel());
            preparedStatement.setInt(3, vehicle.getYear());
            preparedStatement.setString(4, vehicle.getColor());
            preparedStatement.setString(5, vin);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                return vehicle;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }
}
