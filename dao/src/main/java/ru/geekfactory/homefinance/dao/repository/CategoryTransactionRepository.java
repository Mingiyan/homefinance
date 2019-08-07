package ru.geekfactory.homefinance.dao.repository;

import ru.geekfactory.homefinance.dao.HomeFinanceDaoException;
import ru.geekfactory.homefinance.dao.model.CategoryTransactionModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryTransactionRepository implements RepositoryCRUD<Long, CategoryTransactionModel> {
    private static final String INSERT = "INSERT INTO category_tbl (name, category_id) VALUES (?, ?)";
    private static final String FIND_BY_ID = "SELECT id, name, category_id FROM category_tbl WHERE id = ?";
    private static final String FIND_ALL = "SELECT id, name, category_id FROM category_tbl";
    private static final String UPDATE = "UPDATE category_tbl SET name = ?, category_id = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM category_tbl WHERE id = ?";
    private DatabaseConnector databaseConnector = new DatabaseConnector();
    public CategoryTransactionRepository() {

    }

    @Override
    public void save(CategoryTransactionModel object) {
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, object.getName());
            if (object.getParentCategory() != null) {
                preparedStatement.setLong(2, object.getParentCategory().getId());
            } else {
                preparedStatement.setObject(2, null);
            }
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new HomeFinanceDaoException("error while save CategoryTransactionModel " + object, e);
        }
    }

    @Override
    public Optional<CategoryTransactionModel> findById(Long id) {
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            CategoryTransactionModel categoryTransaction = null;
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                CategoryTransactionModel parentCategory = findById(resultSet.getLong("category_id")).orElse(null);
                categoryTransaction = new CategoryTransactionModel(id, name, parentCategory);
            }
            return Optional.ofNullable(categoryTransaction);
        } catch (SQLException e) {
            throw new HomeFinanceDaoException("error while find CategoryTransactionModel by id", e);
        }
    }

    @Override
    public CategoryTransactionModel update(CategoryTransactionModel object) {
        try (Connection connection = databaseConnector.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)){
                preparedStatement.setString(1, object.getName());
                preparedStatement.setLong(2, object.getParentCategory().getId());
                preparedStatement.setLong(3, object.getId());
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    object.setId(resultSet.getLong(3));
                }
                connection.commit();
                return object;
            } catch (SQLException e) {
                connection.rollback();
                throw new HomeFinanceDaoException("error while update CategoryTransactionModel " + object, e);
            }
        } catch (SQLException e) {
            throw new HomeFinanceDaoException("error while update CategoryTransactionModel " + object, e);
        }
    }

    @Override
    public void remove(CategoryTransactionModel object) {
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, object.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new HomeFinanceDaoException("error while remove CategoryTransactionModel " + object, e);
        }
    }

    @Override
    public List<CategoryTransactionModel> findAll() {
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<CategoryTransactionModel> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(new CategoryTransactionModel(resultSet.getLong("id"), resultSet.getString("name"), findById(resultSet.getLong("category_id")).orElse(null)));
            }
            return list;
        } catch (SQLException e) {
            throw new HomeFinanceDaoException("error while find all CategoryTransactionModel ", e);
        }
    }
}
