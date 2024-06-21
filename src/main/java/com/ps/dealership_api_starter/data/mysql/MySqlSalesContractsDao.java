package com.ps.dealership_api_starter.data.mysql;

import com.ps.dealership_api_starter.data.SalesContractsDao;
import com.ps.dealership_api_starter.models.SalesContract;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

public class MySqlSalesContractsDao implements SalesContractsDao {



        @Autowired
        private DataSource dataSource;

        public <SalesContract> Object getSalesContractById(Long id) {
            try (
                    Connection connection = dataSource.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM sales_contracts WHERE id = ?")
            ) {
                preparedStatement.setLong(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        SalesContract salesContract = new SalesContract();
                        salesContract.setId(resultSet.getLong("id"));
                        salesContract.setDetails(resultSet.getString("details"));
                        return Optional.of(salesContract);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Optional.empty();
        }

        public SalesContract createSalesContract(SalesContract salesContract) {
            try (
                    Connection connection = dataSource.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "INSERT INTO sales_contracts(vin) VALUES(?)",
                            Statement.RETURN_GENERATED_KEYS)
            ) {
                preparedStatement.setString(1, salesContract.getVin());
                preparedStatement.executeUpdate();

                try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                    if (keys.next()) {
                        salesContract.setId(keys.getLong(1));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return salesContract;
        }
    }

}
