package repository;

import model.Data;
import model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.util.HashMap;
import java.util.List;

public class JDBCUtil {
    private String URL = "jdbc:postgresql://localhost:5432/test";
    private String USERNAME = "postgres";
    private String PASSWORD = "password";
    private String DRIVER = "org.postgresql.Driver";

    private SingleConnectionDataSource dataSource;
    private NamedParameterJdbcTemplate jdbcTemplate;
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

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
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

    public synchronized List<Data> getData() {
        return jdbcTemplate.query("SELECT * FROM data", rowMapper);
    }

    public synchronized User authUser(String login, String password) {
        try {
            HashMap<String, Object> pp = new HashMap<>();
            pp.put("login", login);
            pp.put("password", password);
            User user = jdbcTemplate.queryForObject("select * from users where login =:login and password =:password", pp, new BeanPropertyRowMapper<>(User.class));
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
