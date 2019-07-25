package dordzhiev.repository;

import dordzhiev.model.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyRepository implements RepositoryCRUD<Long, Currency> {
    private static final String INSERT = "insert into currency_tbl (name) values (?)";
    private static final String FIND_BY_ID = "select * from currency_tbl where id = ?";
    private static final String FIND_BY_NAME = "select * from currency_tbl where name = ?";
    private static final String FIND_ALL = "select * from currency_tbl";
    private static final String UPDATE = "update currency_tbl set name = ? where id = ?";
    private static final String DELETE = "delete from currency_tbl where id = ?";
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public CurrencyRepository() {
    }

    @Override
    public void save(Currency object) {
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, object.getName());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Currency> findById(Long id) {
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Currency currency = null;
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                currency = new Currency(id, name);
            }
            return Optional.ofNullable(currency);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
        @Override
    public Currency update(Currency object) {
        try (Connection connection = databaseConnector.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)){
                preparedStatement.setString(1, object.getName());
                preparedStatement.setLong(2, object.getId());
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    object.setId(resultSet.getLong(2));
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(Currency object) {
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
    public List<Currency> findAll() {
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Currency> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(new Currency(resultSet.getLong("id"), resultSet.getString("name")));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Optional<Currency> findByName(String name) {
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            Currency currency = null;
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                currency = new Currency(id, name);
            }
            return Optional.ofNullable(currency);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
