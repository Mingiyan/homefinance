package ru.dordzhiev.repository;

import ru.dordzhiev.model.Account;
import ru.dordzhiev.model.AccountType;
import ru.dordzhiev.model.Currency;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AccountRepository implements RepositoryCRUD<Long, Account>{
    private static final String INSERT = "insert into account_tbl (name, amount, type, currency_id) values (?, ?, ?, ?)";
    private static final String FIND_BY_ID = "select * from account_tbl where id = ?";
    private static final String FIND_ALL = "select* from account_tbl";
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public AccountRepository() {

    }

    @Override
    public void save(Account object) {
        try (Connection connection = databaseConnector.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, object.getName());
            preparedStatement.setBigDecimal(2, object.getAmount());
            preparedStatement.setString(3, String.valueOf(object.getAccountType()));
            preparedStatement.setLong(4, object.getCurrency().get().getId());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                object.setId(resultSet.getLong(1));
            }
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Account> findById(Long id) {
        try(Connection connection = databaseConnector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Account account = null;
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String amount = resultSet.getString("amount");
                String type = resultSet.getString("type");
//                account = new Account(name, amount, AccountType.valueOf(type),
            }
            return Optional.ofNullable(account);

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
