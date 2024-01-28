package gr.val.postgres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class PostgresManager {

    @Autowired
    private Environment env;

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(
                "jdbc:postgresql://" +
                        env.getProperty("postgres.host") + ":" +
                        env.getProperty("postgres.port") + "/" +
                        env.getProperty("postgres.database"),
                env.getProperty("postgres.user"), env.getProperty("postgres.password"));
    }
}
