package next.dao;

import core.jdbc.ConnectionManager;
import next.exception.JdbcTemplateException;
import next.model.User;
import org.h2.jdbc.JdbcSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class JdbcTemplate {
    public void update(String sql) {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            this.setValues(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new JdbcTemplateException();
        }
    }

    abstract void setValues(PreparedStatement pstmt) throws SQLException;
}
