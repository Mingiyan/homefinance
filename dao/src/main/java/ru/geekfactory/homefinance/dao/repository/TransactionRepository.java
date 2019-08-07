package ru.geekfactory.homefinance.dao.repository;

import ru.geekfactory.homefinance.dao.HomeFinanceDaoException;
import ru.geekfactory.homefinance.dao.model.AccountModel;
import ru.geekfactory.homefinance.dao.model.CategoryTransactionModel;
import ru.geekfactory.homefinance.dao.model.TransactionModel;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class TransactionRepository implements RepositoryCRUD<Long, TransactionModel> {

    private static final String INSERT = "with ins1 as (" +
            " insert into transaction_tbl (name, date_time, account_id) values (?, ?, ?) returning id)" +
            " insert into transaction_category_tbl (transaction_id, category_id)" +
            " select id, unnest(array[?]::integer[]) from ins1";
    private static final String FIND_CATEGORIES = "select c.id, c.name, c.category_id from transaction_tbl t1 join" +
            " transaction_category_tbl t2 on t1.id = t2.transaction_id join category_tbl c on t2.category_id = c.id where t1.id = ?";
    private static final String FIND_BY_ID = "select id, name, date_time, account_id from transaction_tbl where id = ?";
    private static final String FIND_ALL = "select id, name, date_time, account_id from transaction_tbl";
    private static final String UPDATE = "with upd1 as (" +
            " update transaction_tbl set name = ?, date_time = ?, account_id = ? where id = ? returning id) " +
            " insert into transaction_category_tbl (transaction_id, category_id) " +
            " select id, unnest(array[?]::integer[]) from upd1";
    private static final String DELETE = "delete from transaction_tbl where id = ?";
    private static final String DELETE_FROM_CATEGORIES = "delete from transaction_category_tbl tc using transaction_tbl t where tc.transaction_id = t.id and t.id = ?";
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
        try (Connection connection = databaseConnector.getConnection()){
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