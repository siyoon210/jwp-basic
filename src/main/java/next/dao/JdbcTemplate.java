package next.dao;

import core.jdbc.ConnectionManager;
import next.exception.JdbcTemplateException;
import next.model.User;
import org.h2.jdbc.JdbcSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class JdbcTemplate {
    private static final Logger log = LoggerFactory.getLogger(JdbcTemplate.class);

    public void update(String sql) {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            this.setValues(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("SQLException!! {}", e.getMessage());
            throw new JdbcTemplateException();
        }
    }

    abstract void setValues(PreparedStatement pstmt) throws SQLException;
}
