package ru.geekfactory.homefinace.dao.repository;

import ru.geekfactory.homefinace.dao.model.AccountModel;
import ru.geekfactory.homefinace.dao.model.AccountType;
import ru.geekfactory.homefinace.dao.model.CurrencyModel;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountRepository implements RepositoryCRUD<Long, AccountModel>{
    private static final String INSERT = "insert into account_tbl (name, amount, type, currency_id) values (?, ?, ?, ?)";
    private static final String FIND_BY_ID = "select * from account_tbl where id = ?";
    private static final String UPDATE = "update account_tbl set name = ?, amount = ?, type = ?, currency_id = ? where id = ?";
    private static final String DELETE = "delete from account_tbl where id = ?";
    private static final String FIND_ALL = "select * from account_tbl";
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public AccountRepository() {

    }

    @Override
    public void save(AccountModel object) {
        try (Connection connection = databaseConnector.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, object.getName());
            preparedStatement.setBigDecimal(2, object.getAmount());
            preparedStatement.setString(3, String.valueOf(object.getAccountType()));
            preparedStatement.setLong(4, object.getCurrency().getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<AccountModel> findById(Long id) {
        try(Connection connection = databaseConnector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            AccountModel account = null;
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                BigDecimal amount = resultSet.getBigDecimal("amount");
                String type = resultSet.getString("type");
                CurrencyRepository currencyRepository = new CurrencyRepository();
                Optional<CurrencyModel> currency = currencyRepository.findById(resultSet.getLong("currency_id"));
                account = new AccountModel(id ,name, amount, AccountType.valueOf(type), currency.get());
            }
            return Optional.ofNullable(account);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public AccountModel update(AccountModel object) {
        try (Connection connection = databaseConnector.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
                preparedStatement.setString(1, object.getName());
                preparedStatement.setBigDecimal(2, object.getAmount());
                preparedStatement.setString(3, String.valueOf(object.getAccountType()));
                preparedStatement.setLong(4, object.getCurrency().getId());
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    object.setId(resultSet.getLong(1));
                }
                connection.commit();
                return object;
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void remove(AccountModel object) {
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, object.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<AccountModel> findAll() {
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<AccountModel> list = new ArrayList<>();
            while (resultSet.next()) {
                CurrencyRepository currencyRepository = new CurrencyRepository();
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                BigDecimal amount = resultSet.getBigDecimal("amount");
                String type = resultSet.getString("type");
                Optional<CurrencyModel> currency = currencyRepository.findById(resultSet.getLong("currency_id"));
                list.add(new AccountModel (id, name, amount, AccountType.valueOf(type), currency.get()));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }
}
