package next.dao;

import next.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class UserDao {
    public void insert(User user) {
        final JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<>();

        PreparedStatementSetter pstmtSetter = (PreparedStatement pstmt) -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
        };

        jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", pstmtSetter);
    }

    public void update(User user) {
        final JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<>();

        PreparedStatementSetter pstmtSetter = (PreparedStatement pstmt) -> {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
        };

        jdbcTemplate.update("UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?", pstmtSetter);
    }

    public List<User> findAll() {
        final JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<>();

        PreparedStatementSetter pstmtSetter = (PreparedStatement pstmt) -> {};

        RowMapper<User> rowMapper = (ResultSet rs) -> new User(rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email"));

        return jdbcTemplate.query("SELECT userId, password, name, email FROM USERS", pstmtSetter, rowMapper);
    }

    public User findByUserId(String userId) {
        final JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<>();

        PreparedStatementSetter pstmtSetter = (PreparedStatement pstmt) -> pstmt.setString(1, userId);

        RowMapper<User> rowMapper = (ResultSet rs) -> new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                rs.getString("email"));

        return jdbcTemplate.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?", pstmtSetter, rowMapper);
    }
}
