package ru.geekfactory.homefinance.dao.repository;

import ru.geekfactory.homefinance.dao.HomeFinanceDaoException;
import ru.geekfactory.homefinance.dao.model.AccountModel;
import ru.geekfactory.homefinance.dao.model.AccountType;
import ru.geekfactory.homefinance.dao.model.CurrencyModel;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountRepository implements RepositoryCRUD<Long, AccountModel>{
    private static final String INSERT = "INSERT INTO account_tbl (name, amount, type, currency_id) VALUES (?, ?, ?, ?)";
    private static final String FIND_BY_ID = "SELECT id, name, amount, type, currency_id FROM account_tbl WHERE id = ?";
    private static final String UPDATE = "UPDATE account_tbl SET name = ?, amount = ?, type = ?, currency_id = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM account_tbl WHERE id = ?";
    private static final String FIND_ALL = "SELECT id, name, amount, type, currency_id FROM account_tbl";
    private DatabaseConnector databaseConnector = new DatabaseConnector();
    private CurrencyRepository currencyRepository = new CurrencyRepository();
    public AccountRepository() {

    }

    @Override
    public void save(AccountModel object) {
        try (Connection connection = databaseConnector.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, object.getName());
            preparedStatement.setBigDecimal(2, object.getAmount());
            preparedStatement.setString(3, String.valueOf(object.getAccountType()));
            if (object.getCurrency() != null) {
                preparedStatement.setLong(4, object.getCurrency().getId());
            } else {
                preparedStatement.setObject(4, null);
            }
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new HomeFinanceDaoException("error while save Account model " + object, e);
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
                CurrencyModel currency = currencyRepository.findById(resultSet.getLong("currency_id")).orElse(null);
                account = new AccountModel(id ,name, amount, AccountType.valueOf(type), currency);
            }
            return Optional.ofNullable(account);

        } catch (SQLException e) {
            throw new HomeFinanceDaoException("error while find Account model by id", e);
        }
    }

    @Override
    public AccountModel update(AccountModel object) {
        try (Connection connection = databaseConnector.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
                preparedStatement.setString(1, object.getName());
                preparedStatement.setBigDecimal(2, object.getAmount());
                preparedStatement.setString(3, String.valueOf(object.getAccountType()));
                preparedStatement.setLong(4, object.getCurrency().getId());
                preparedStatement.setLong(5, object.getId());
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
            throw new HomeFinanceDaoException("error while update Account model " + object, e);
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
            throw new HomeFinanceDaoException("error while remove Account model " + object, e);
        }
    }

    @Override
    public List<AccountModel> findAll() {
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<AccountModel> list = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                BigDecimal amount = resultSet.getBigDecimal("amount");
                String type = resultSet.getString("type");
                Optional<CurrencyModel> currency = currencyRepository.findById(resultSet.getLong("currency_id"));
                list.add(new AccountModel (id, name, amount, AccountType.valueOf(type), currency.get()));
            }
            return list;
        } catch (SQLException e) {
            throw new HomeFinanceDaoException("error while find all Account model", e);
        }
    }
}
