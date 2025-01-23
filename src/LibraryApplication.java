import db.DatabaseConf;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class LibraryApplication {
    static Logger logger = Logger.getLogger(LibraryApplication.class.getName());

    public static void main(String[] args) throws SQLException {
        // Check database connection works properly !
        DatabaseConf databaseConf = new DatabaseConf();
        Connection connection = databaseConf.getConnection();
        logger.info(connection.toString());
        connection.close();
    }
}
