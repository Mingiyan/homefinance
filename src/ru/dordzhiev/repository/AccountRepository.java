package ru.dordzhiev.repository;

import ru.dordzhiev.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AccountRepository implements RepositoryCRUD<Long, Account>{
    private static final String INSERT = "insert into account_tbl (name, amount, type, currency) values (?, ?, ?, ?)";
    private DatabaseConnector databaseConnector;

    public AccountRepository(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    @Override
    public void save(Account object) {
        try (Connection connection = databaseConnector.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
                preparedStatement.setString(1, object.getName());
                preparedStatement.setString(2, String.valueOf(object.getAmount()));
                preparedStatement.setString(3, String.valueOf(object.getAccountType()));
                preparedStatement.setString(4, String.valueOf(object.getCurrency()));
                preparedStatement.executeUpdate();

                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    object.setId(resultSet.getLong(1));
                }
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Account> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Account update(Account object) {
        return null;
    }

    @Override
    public void remove(Account object) {

    }

    @Override
    public List<Account> findAll() {
        return null;
    }
}
