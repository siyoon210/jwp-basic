package next.dao;

import next.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class UserDao {
    private final JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<>();

    public void insert(User user) {
        jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)",
                user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public void update(User user) {
        jdbcTemplate.update("UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?",
                user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }

    public List<User> findAll() {
        return jdbcTemplate.query("SELECT userId, password, name, email FROM USERS",
                User::new);
    }

    public User findByUserId(String userId) {
        return jdbcTemplate.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?",
                User::new, userId);
    }
}
