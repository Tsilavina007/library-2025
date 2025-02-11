import dao.AuthorCrudOperations;
import db.DataSource;
import entity.Author;
import entity.Sex;

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

		Author newAuthor = new Author("authoridsadj_8", "Tsong", Sex.FEMALE, LocalDate.of(2001, 1, 1));


		// logger.info(authorCrudOperations.saveAll(List.of(newAuthor)).toString());

        List<Author> authors = authorCrudOperations.getAll(1, 10);

        logger.info(authors.toString());
    }
}
