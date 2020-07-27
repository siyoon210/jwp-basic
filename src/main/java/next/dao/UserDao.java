package next.dao;

import next.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class UserDao {
    public void insert(User user) {
        final JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<>();

        jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)",
                user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public void update(User user) {
        final JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<>();

        jdbcTemplate.update("UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?",
                user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }

    public List<User> findAll() {
        final JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<>();

        RowMapper<User> rowMapper = (ResultSet rs) -> new User(rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email"));

        return jdbcTemplate.query("SELECT userId, password, name, email FROM USERS", rowMapper);
    }

    public User findByUserId(String userId) {
        final JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<>();

        RowMapper<User> rowMapper = (ResultSet rs) -> new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                rs.getString("email"));

        return jdbcTemplate.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?", rowMapper, userId);
    }
}
