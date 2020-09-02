package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.User;

import java.util.List;

public class JdbcUserDao implements UserDao {
    private final JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();

    public JdbcUserDao() {
    }

    public void insert(User user) {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public User findByUserId(String userId) {
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";

        RowMapper<User> rm = rs -> new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                rs.getString("email"));

        return jdbcTemplate.queryForObject(sql, rm, userId);
    }

    public List<User> findAll() {
        String sql = "SELECT userId, password, name, email FROM USERS";

        RowMapper<User> rm = rs -> new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                rs.getString("email"));

        return jdbcTemplate.query(sql, rm);
    }

    public void update(User user) {
        String sql = "UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?";
        jdbcTemplate.update(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }
}
