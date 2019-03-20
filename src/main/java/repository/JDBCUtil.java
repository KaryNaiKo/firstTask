package repository;

import model.Data;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.util.List;

public class JDBCUtil {
    private String URL = "jdbc:postgresql://localhost:5432/test";
    private String USERNAME = "postgres";
    private String PASSWORD = "password";
    private String DRIVER = "org.postgresql.Driver";

    private SingleConnectionDataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Data> rowMapper;

    private static JDBCUtil instance;

    private JDBCUtil() {
        init();
    }

    private void init() {
        dataSource = new SingleConnectionDataSource();
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        jdbcTemplate = new JdbcTemplate(dataSource);
        rowMapper = BeanPropertyRowMapper.newInstance(Data.class);
    }

    public static JDBCUtil getInstance() {
        if (instance == null) {
            synchronized (JDBCUtil.class) {
                if (instance == null) {
                    instance = new JDBCUtil();
                }
            }
        }

        return instance;
    }

    public List<Data> getData() {
        return jdbcTemplate.query("SELECT * FROM data", rowMapper);
    }
}
