package gr.val.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class ConfigProperties {

    @Value( "${postgres.host}" )
    private String host;

    @Value( "${postgres.port}" )
    private String port;

    @Value( "${postgres.database}" )
    private String database;

    @Value( "${postgres.user}" )
    private String user;

    @Value( "${postgres.password}" )
    private String password;

}
