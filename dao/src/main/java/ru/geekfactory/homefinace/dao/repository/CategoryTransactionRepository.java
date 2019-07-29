package ru.geekfactory.homefinace.dao.repository;

import ru.geekfactory.homefinace.dao.model.CategoryTransactionModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryTransactionRepository implements RepositoryCRUD<Long, CategoryTransactionModel> {
    private static final String INSERT = "insert into category_tbl (name) values (?)";
    private static final String FIND_BY_ID = "select * from category_tbl where id = ?";
    private static final String FIND_ALL = "select * from category_tbl";
    private static final String UPDATE = "update category_tbl set name = ? where id = ?";
    private static final String DELETE = "delete from category_tbl where id = ?";
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public CategoryTransactionRepository() {

    }

    @Override
    public void save(CategoryTransactionModel object) {
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
    public Optional<CategoryTransactionModel> findById(Long id) {
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            CategoryTransactionModel categoryTransaction = null;
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                CategoryTransactionModel categoryTran = new CategoryTransactionModel();
                categoryTran.setId(resultSet.getLong("category_id"));
                categoryTransaction = new CategoryTransactionModel(id, name, categoryTran);
            }
            return Optional.ofNullable(categoryTransaction);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public CategoryTransactionModel update(CategoryTransactionModel object) {
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
    public void remove(CategoryTransactionModel object) {
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
    public List<CategoryTransactionModel> findAll() {
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<CategoryTransactionModel> list = new ArrayList<>();
            while (resultSet.next()) {
//                list.add(new CategoryTransaction (resultSet.getLong("id"), resultSet.getString("name")));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
