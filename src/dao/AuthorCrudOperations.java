package dao;

import db.DataSource;
import entity.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorCrudOperations implements CrudOperations<Author> {
    private final DataSource dataSource = new DataSource();

    @Override
    public List<Author> getAll(int page, int size) {
        if (page < 1) {
            throw new IllegalArgumentException("page must be greater than 0 but actual is " + page);
        }
        String sql = "select a.id, a.name, a.birth_date from author a order by a.id limit ? offset ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, size);
            preparedStatement.setInt(2, size * (page - 1));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Author> authors = new ArrayList<>();
                while (resultSet.next()) {
                    Author author = new Author();
                    author.setId(resultSet.getString("id"));
                    author.setName(resultSet.getString("name"));
                    author.setBirthDate(resultSet.getDate("birth_date").toLocalDate());
                    authors.add(author);
                }
                return authors;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Author findById(String id) {
        String sql = "select a.id, a.name, a.birth_date, a.sex from author a where id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                Author author = new Author();
                while (resultSet.next()) {
                    author.setId(resultSet.getString("id"));
                    author.setName(resultSet.getString("name"));
                    author.setBirthDate(resultSet.getDate("birth_date").toLocalDate());
                }
                return author;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Author> saveAll(List<Author> entities) {
        List<Author> newAuthors = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            entities.forEach(entityToSave -> {
                try (PreparedStatement statement = connection.prepareStatement("insert into author (id, name, birth_date) values (?, ?, ?)")) {
                    statement.setString(1, entityToSave.getId());
                    statement.setString(2, entityToSave.getName());
                    statement.setDate(3, Date.valueOf(entityToSave.getBirthDate()));

                    statement.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                newAuthors.add(findById(entityToSave.getId()));
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return newAuthors;
    }
}
