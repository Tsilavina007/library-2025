import dao.AuthorCrudOperations;
import db.DataSource;
import entity.Author;
import entity.Sex;

import static entity.Sex.MALE;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class LibraryApplication {
    static Logger logger = Logger.getLogger(LibraryApplication.class.getName());

    public static void main(String[] args) throws SQLException {
        // Check database connection works properly !
        DataSource dataSource = new DataSource();
        Connection connection = dataSource.getConnection();
        logger.info(connection.toString());
        connection.close();

        // Check author getAll() works
        AuthorCrudOperations authorCrudOperations = new AuthorCrudOperations();

		Author authorRado = new Author("author2_idRado", "Rado", Sex.MALE, LocalDate.of(1990, 1, 1));

		Author authorJJR = new Author("author1_id", "JJR", MALE, LocalDate.of(2000, 1, 1));



		logger.info(authorCrudOperations.saveAll(List.of(authorJJR)).toString());
		logger.info(authorCrudOperations.saveAll(List.of(authorRado)).toString());

        // List<Author> authors = authorCrudOperations.getAll(1, 10);

        // logger.info(authors.toString());
    }
}
