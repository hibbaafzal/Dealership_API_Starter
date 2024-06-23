package com.ps.dealership_api_starter.data.mysql;

import com.ps.dealership_api_starter.data.LeaseContractDao;
import com.ps.dealership_api_starter.models.LeaseContract;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlLeaseContractsDao extends MySqlDaoBase implements LeaseContractDao {

    public MySqlLeaseContractsDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<LeaseContract> getLeaseContractById(Long id) {
        List<LeaseContract> leaseContracts = new ArrayList<>();
        String query = "SELECT * FROM lease_contracts WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    LeaseContract leaseContract = new LeaseContract();
                    leaseContract.setId(resultSet.getLong("id"));
                    leaseContract.setVin(resultSet.getString("vin"));
                    leaseContract.setDetails(resultSet.getString("details"));


                    leaseContracts.add(leaseContract);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return leaseContracts;
    }

    @Override
    public LeaseContract createLeaseContract(LeaseContract leaseContract) {
        String query = "INSERT INTO lease_contracts (vin) VALUES (?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, leaseContract.getVin());

            preparedStatement.executeUpdate();

            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                while (keys.next()) {
                    leaseContract.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return leaseContract;
    }
}
