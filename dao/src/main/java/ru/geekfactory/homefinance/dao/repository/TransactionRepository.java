package ru.geekfactory.homefinance.dao.repository;

import ru.geekfactory.homefinance.dao.HomeFinanceDaoException;
import ru.geekfactory.homefinance.dao.model.AccountModel;
import ru.geekfactory.homefinance.dao.model.CategoryTransactionModel;
import ru.geekfactory.homefinance.dao.model.TransactionModel;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class TransactionRepository implements RepositoryCRUD<Long, TransactionModel> {

    private static final String INSERT = "WITH ins1 AS (" +
            " INSERT INTO transaction_tbl (name, date_time, account_id) VALUES (?, ?, ?) RETURNING id)" +
            " INSERT INTO transaction_category_tbl (transaction_id, category_id)" +
            " SELECT id, unnest(ARRAY[?]::INTEGER[]) FROM ins1";
    private static final String FIND_CATEGORIES = "SELECT c.id, c.name, c.category_id FROM transaction_tbl t1 JOIN" +
            " transaction_category_tbl t2 ON t1.id = t2.transaction_id JOIN category_tbl c ON t2.category_id = c.id WHERE t1.id = ?";
    private static final String FIND_BY_ID = "SELECT id, name, date_time, account_id FROM transaction_tbl WHERE id = ?";
    private static final String FIND_ALL = "SELECT id, name, date_time, account_id FROM transaction_tbl";
    private static final String UPDATE = "WITH upd1 AS (" +
            " UPDATE transaction_tbl SET name = ?, date_time = ?, account_id = ? WHERE id = ? RETURNING id) " +
            " INSERT INTO transaction_category_tbl (transaction_id, category_id) " +
            " SELECT id, unnest(ARRAY[?]::INTEGER[]) FROM upd1";
    private static final String DELETE = "DELETE FROM transaction_tbl WHERE id = ?";
    private static final String DELETE_FROM_CATEGORIES = "DELETE FROM transaction_category_tbl tc USING transaction_tbl t WHERE tc.transaction_id = t.id AND t.id = ?";
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
            preparedStatement.setObject(4, convertToArray(collection));
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new HomeFinanceDaoException("error while save TransactionModel " + object, e);
        }
    }

    @Override
    public Optional<TransactionModel> findById(Long id) {
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement psFindById = connection.prepareStatement(FIND_BY_ID);
            PreparedStatement psFindCategories = connection.prepareStatement(FIND_CATEGORIES);
            psFindById.setLong(1, id);
            psFindCategories.setLong(1, id);
            ResultSet rsById = psFindById.executeQuery();
            ResultSet rsCategories = psFindCategories.executeQuery();
            TransactionModel transaction = null;
            if (rsById.next()) {
                String name = rsById.getString("name");
                LocalDateTime dateTime = rsById.getTimestamp("date_time").toLocalDateTime();
                AccountModel accountOptional = accountRepository.findById(rsById.getLong("account_id")).orElse(null);
                Collection<CategoryTransactionModel> collection = new HashSet<>();
                while (rsCategories.next()) {
                    Long categoryId = rsCategories.getLong("id");
                    String categoryName = rsCategories.getString("name");
                    CategoryTransactionModel parentCategory = categoryTransactionRepository.findById(rsCategories.getLong("category_id")).orElse(null);
                    collection.add(new CategoryTransactionModel(categoryId, categoryName, parentCategory));
                }
                transaction = new TransactionModel(id, name, dateTime, collection, accountOptional);
            }
            return Optional.ofNullable(transaction);
        } catch (SQLException e) {
            throw new HomeFinanceDaoException("error while find TransactionModel by id", e);
        }
    }

    @Override
    public TransactionModel update(TransactionModel object) {
        try (Connection connection = databaseConnector.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
                PreparedStatement psDeleteCategoryes = connection.prepareStatement(DELETE_FROM_CATEGORIES);
                psDeleteCategoryes.setLong(1, object.getId());
                psDeleteCategoryes.executeUpdate();
                preparedStatement.setString(1, object.getName());
                preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setLong(3, object.getAccount().getId());
                preparedStatement.setLong(4, object.getId());
                Collection<CategoryTransactionModel> collection = object.getCategoryTransaction();
                preparedStatement.setObject(5, convertToArray(collection));
                preparedStatement.executeUpdate();
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
            PreparedStatement psDeleteCategoryes = connection.prepareStatement(DELETE_FROM_CATEGORIES);
            psDeleteCategoryes.setLong(1, object.getId());
            psDeleteCategoryes.executeUpdate();
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
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TransactionModel> list = new ArrayList<>();
            while (resultSet.next()) {
                Collection<CategoryTransactionModel> collection = new HashSet<>();
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                LocalDateTime dateTime = resultSet.getTimestamp("date_time").toLocalDateTime();
                AccountModel accountOptional = accountRepository.findById(resultSet.getLong("account_id")).orElse(null);
                PreparedStatement psCategory = connection.prepareStatement(FIND_CATEGORIES);
                psCategory.setLong(1, id);
                ResultSet rsCategories = psCategory.executeQuery();
                while (rsCategories.next()) {
                    Long categoryId = rsCategories.getLong("id");
                    String categoryName = rsCategories.getString("name");
                    CategoryTransactionModel parentCategory = categoryTransactionRepository.findById(rsCategories.getLong("category_id")).orElse(null);
                    collection.add(new CategoryTransactionModel(categoryId, categoryName, parentCategory));
                }
                list.add(new TransactionModel(id, name, dateTime, collection, accountOptional));
            }
            return list;
        } catch (SQLException e) {
            throw new HomeFinanceDaoException("error while find all TransactionModel", e);
        }
    }

    private int[] convertToArray(Collection<CategoryTransactionModel> collection) {
        int[] array = {};
        if (collection != null) {
            array = new int[collection.size()];
            int i = 0;
            for (CategoryTransactionModel category : collection) {
                array[i++] = Math.toIntExact(category.getId());
            }
        }
        return array;
    }
}
