package dao;

import db.DataSource;
import entity.Author;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.mapper.SexMapper;

public class AuthorCrudOperations implements CrudOperations<Author> {
	private final DataSource dataSource;
    private final SexMapper sexMapper;

	// Constructor
	public AuthorCrudOperations(DataSource dataSource, SexMapper sexMapper) {
		this.dataSource = dataSource;
		this.sexMapper = sexMapper;
	}

	// Default constructor
	public AuthorCrudOperations() {
		this.dataSource = new DataSource();
		this.sexMapper = new SexMapper();
	}

	@Override
    public List<Author> getAll(int page, int size) {
        if (page < 1) {
            throw new IllegalArgumentException("page must be greater than 0 but actual is " + page);
        }
        String sql = "select a.id, a.name, a.sex, a.birth_date from author a order by a.id limit ? offset ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, size);
            preparedStatement.setInt(2, size * (page - 1));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Author> authors = new ArrayList<>();
                while (resultSet.next()) {
                    authors.add(mapAuthorFromResultSet(resultSet));
                }
                return authors;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Author findById(String id) {
        String sql = "select a.id, a.name, a.sex, a.birth_date from author a where id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
					return mapAuthorFromResultSet(resultSet);
				} else {
					throw new IllegalArgumentException("Author with id " + id + " not found");
				}
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
				// String sql = "insert into author (id, name, sex, birth_date) values (?, ?, ?, ?)";
				String sql = "insert into author (id, name, sex, birth_date) values (?, ?, ?, ?) " +
						 "on conflict (id) do update set name = excluded.name, sex = excluded.sex, birth_date = excluded.birth_date";
				try (PreparedStatement statement = connection.prepareStatement(sql)) {
					statement.setString(1, entityToSave.getId());
					statement.setString(2, entityToSave.getName());
                    statement.setObject(3, sexMapper.mapToDatabaseColumn(entityToSave.getSex()), java.sql.Types.OTHER);
					statement.setDate(4, Date.valueOf(entityToSave.getBirthDate()));
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

    @Override
    public List<Author> findByCriteria(List<Criteria> criteria, int page, int size) {
        List<Author> authors = new ArrayList<>();
        String sql = "select a.id, a.name, a.sex, a.birth_date from author a where 1=1";
        for (Criteria c : criteria) {
            if ("name".equals(c.getColumn())) {
                sql += " and a." + c.getColumn() + " ilike '%" + c.getValue().toString() + "%'";
            } else if ("birth_date".equals(c.getColumn())) {
                sql += " or a." + c.getColumn() + " = '" + c.getValue().toString() + "'";
            }
        }
		sql += " limit ? offset ?";

		try (Connection connection = dataSource.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, size);
			preparedStatement.setInt(2, size * (page - 1));
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					authors.add(mapAuthorFromResultSet(resultSet));
				}
				return authors;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
    }

	@Override
	public List<Author> orderByCriteria(List<Criteria> criteria, int page, int size) {
		List<Author> authors = new ArrayList<>();

		String sql = "select a.id, a.name, a.sex, a.birth_date from author a order by ";
        for (Criteria c : criteria) {
            sql += "a." + c.getColumn() + " ASC" + ", ";
        }

		sql = sql.substring(0, sql.length() - 2);

		sql += " limit ? offset ?";

		try (Connection connection = dataSource.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, size);
			preparedStatement.setInt(2, size * (page - 1));
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					authors.add(mapAuthorFromResultSet(resultSet));
				}
				return authors;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

    private Author mapAuthorFromResultSet(ResultSet resultSet) throws SQLException {
            Author author = new Author();
            author.setId(resultSet.getString("id"));
            author.setName(resultSet.getString("name"));
			author.setSex(sexMapper.mapFromResultSet(resultSet.getString("sex")));
            author.setBirthDate(resultSet.getDate("birth_date").toLocalDate());
        return author;
    }

}
