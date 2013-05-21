
package net.flinkgutt.samples.nodes.domain.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author Christian
 */
public abstract class SuperDAO {

    public static NamedParameterJdbcTemplate jdbcTemplate;

    public enum DBServer {

        MySQL, PostgreSQL
    }
    public DBServer selected = DBServer.MySQL;

    public SuperDAO() {
        if (jdbcTemplate == null) {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();

            if (selected == DBServer.PostgreSQL) {
                dataSource.setDriverClassName("org.postgresql.Driver");
                dataSource.setUrl("jdbc:postgresql://localhost:5432/netbeans-samples");
                dataSource.setUsername("netbeans-samples");
                dataSource.setPassword("secretpassword123");
            } else if (selected == DBServer.MySQL) {
                dataSource.setDriverClassName("org.gjt.mm.mysql.Driver");
                dataSource.setUrl("jdbc:mysql://localhost:3306/netbeans-samples");
                dataSource.setUsername("netbeans-samples");
                dataSource.setPassword("secretpassword123");
            }
            jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        }
    }
}
