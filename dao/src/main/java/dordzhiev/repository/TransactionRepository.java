package dordzhiev.repository;

import dordzhiev.model.Account;
import dordzhiev.model.CategoryTransaction;
import dordzhiev.model.Transaction;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionRepository implements RepositoryCRUD<Long, Transaction> {

    private static final String INSERT = "insert into transaction_tbl (name, date_time, category_id, ammount_id) values (?, ?, ?, ?)";
    private static final String FIND_BY_ID = "select * from transaction_tbl where id = ?";
    private static final String FIND_ALL = "select * from transaction_tbl";
    private static final String UPDATE = "update transaction_tbl set name = ?, date_time = ?, category_id = ?, ammount_id = ? where id = ?";
    private static final String DELETE = "delete from transaction_tbl where id = ?";
    CategoryTransactionRepository categoryTransactionRepository = new CategoryTransactionRepository();
    private AccountRepository accountRepository = new AccountRepository();
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    @Override
    public void save(Transaction object) {
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, object.getName());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setLong(3, object.getCategoryTransaction().getId());
            preparedStatement.setLong(4, object.getAccount().getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Transaction transaction = null;
            while (resultSet.next()) { // if

                String name = resultSet.getString("name");
                LocalDateTime dateTime = resultSet.getTimestamp("date_time").toLocalDateTime();
                Optional<CategoryTransaction> categoryTransactionOptional = categoryTransactionRepository.findById(resultSet.getLong("category_id"));
                Optional<Account> accountOptional = accountRepository.findById(resultSet.getLong("account_id"));
                transaction = new Transaction(id, name, dateTime, categoryTransactionOptional.get(), accountOptional.get());
            }
            return Optional.ofNullable(transaction);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Transaction update(Transaction object) {
        try (Connection connection = databaseConnector.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
                preparedStatement.setString(1, object.getName());
                preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setLong(3, object.getCategoryTransaction().getId());
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
                e.printStackTrace();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void remove(Transaction object) {
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
    public List<Transaction> findAll() {
        try (Connection connection = databaseConnector.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Transaction> list = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                LocalDateTime dateTime = resultSet.getTimestamp("date_time").toLocalDateTime();
                Optional<CategoryTransaction> categoryTransactionOptional = categoryTransactionRepository.findById(resultSet.getLong("category_id"));
                Optional<Account> accountOptional = accountRepository.findById(resultSet.getLong("account_id"));
                list.add(new Transaction(id, name, dateTime, categoryTransactionOptional.get(), accountOptional.get()));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
