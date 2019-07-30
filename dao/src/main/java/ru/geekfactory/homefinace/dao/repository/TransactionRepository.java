package ru.geekfactory.homefinace.dao.repository;

import ru.geekfactory.homefinace.dao.HomeFinanceDaoException;
import ru.geekfactory.homefinace.dao.model.AccountModel;
import ru.geekfactory.homefinace.dao.model.CategoryTransactionModel;
import ru.geekfactory.homefinace.dao.model.TransactionModel;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class TransactionRepository implements RepositoryCRUD<Long, TransactionModel> {

    private static final String INSERT = "with ins1 as (" +
            "  insert into transaction_tbl (name, date_time, account_id) values (?, ?, ?) returning id)" +
            "  insert into transaction_category_tbl (transaction_id, category_id)" +
            "  select id, unnest(array[?]::integer[]) from ins1;";
    private static final String INSERT_TO_TC = "insert into transaction_category_tbl (transaction_id, category_id) VALUES (?, ?)";
    private static final String FIND_BY_ID = "select id, name, date_time, account_id from transaction_tbl where id = ?";
    private static final String FIND_ALL = "select id, name, date_time, account_id from transaction_tbl";
    private static final String UPDATE = "update transaction_tbl set name = ?, date_time = ?, category_id = ?, ammount_id = ? where id = ?";
    private static final String DELETE = "delete from transaction_tbl where id = ?";
    private CategoryTransactionRepository categoryTransactionRepository = new CategoryTransactionRepository();
    private AccountRepository accountRepository = new AccountRepository();
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    @Override
    public void save(TransactionModel object) {
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, object.getName());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setLong(3, object.getAccount().getId());
            Collection<CategoryTransactionModel> collection = object.getCategoryTransaction();
            int[] array = {};
            if (collection != null) {
                array = new int[collection.size()];
                int i = 0;
                for (CategoryTransactionModel category : collection) {
                    array[i++] = Math.toIntExact(category.getId());
                }
            }
            preparedStatement.setObject(4, array);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new HomeFinanceDaoException("error while save TransactionModel " + object, e);
        }
    }

    @Override
    public Optional<TransactionModel> findById(Long id) {
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            TransactionModel transaction = null;
            while (resultSet.next()) { // if
                String name = resultSet.getString("name");
                LocalDateTime dateTime = resultSet.getTimestamp("date_time").toLocalDateTime();
                Optional<CategoryTransactionModel> categoryTransactionOptional = categoryTransactionRepository.findById(resultSet.getLong("category_id"));
                Optional<AccountModel> accountOptional = accountRepository.findById(resultSet.getLong("account_id"));
//                transaction = new TransactionModel(id, name, dateTime, categoryTransactionOptional.get(), accountOptional.get());
            }
            return Optional.ofNullable(transaction);
        } catch (SQLException e) {
            throw new HomeFinanceDaoException("error while find TransactionModel by id", e);
        }
//        return Optional.empty();
    }

    @Override
    public TransactionModel update(TransactionModel object) {
        try (Connection connection = databaseConnector.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
                preparedStatement.setString(1, object.getName());
                preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
//                preparedStatement.setLong(3, object.getCategoryTransaction());
                preparedStatement.setLong(4, object.getAccount().getId());
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    object.setId(resultSet.getLong(1));
                }
                connection.commit();
                return object;
            } catch (SQLException e) {
                connection.rollback();
                throw new HomeFinanceDaoException("error while update TransactionModel " + object, e);
            }
        } catch (SQLException e) {
            throw new HomeFinanceDaoException("error while update TransactionModel " + object, e);
        }
    }

    @Override
    public void remove(TransactionModel object) {
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, object.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new HomeFinanceDaoException("error while update TransactionModel " + object, e);
        }
    }

    @Override
    public List<TransactionModel> findAll() {
        try (Connection connection = databaseConnector.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TransactionModel> list = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                LocalDateTime dateTime = resultSet.getTimestamp("date_time").toLocalDateTime();
                Optional<CategoryTransactionModel> categoryTransactionOptional = categoryTransactionRepository.findById(resultSet.getLong("category_id"));
                Optional<AccountModel> accountOptional = accountRepository.findById(resultSet.getLong("account_id"));
//                list.add(new TransactionModel(id, name, dateTime, accountOptional.get().getId(), accountOptional.get()));
            }
            return list;
        } catch (SQLException e) {
            throw new HomeFinanceDaoException("error while find all TransactionModel", e);
        }
    }
}
