import dao.AuthorCrudOperations;
import db.DatabaseConf;
import entity.Author;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class LibraryApplication {
    static Logger logger = Logger.getLogger(LibraryApplication.class.getName());

    public static void main(String[] args) throws SQLException {
        // Check database connection works properly !
        DatabaseConf databaseConf = new DatabaseConf();
        Connection connection = databaseConf.getConnection();
        logger.info(connection.toString());
        connection.close();

        // Check author getAll() works
        AuthorCrudOperations authorCrudOperations = new AuthorCrudOperations();
        List<Author> authors = authorCrudOperations.getAll();
        logger.info(authors.toString());
    }
}
