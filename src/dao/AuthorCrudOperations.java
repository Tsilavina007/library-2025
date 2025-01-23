package dao;

import db.DatabaseConf;
import entity.Author;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AuthorCrudOperations implements CrudOperations<Author> {
    private final DatabaseConf databaseConf = new DatabaseConf();

    @Override
    public List<Author> getAll() {
        try (Connection connection = databaseConf.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("select a.id, a.name, a.birth_date from author a")) {
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
    public Author findById(int id) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public List<Author> saveAll(List<Author> entities) {
        throw new RuntimeException("Not implemented yet");
    }
}
