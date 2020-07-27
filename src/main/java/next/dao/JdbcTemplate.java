package next.dao;

import core.jdbc.ConnectionManager;
import next.exception.JdbcTemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class JdbcTemplate<T> {
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

    public List<T> query(String sql) {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            this.setValues(pstmt);
            final ResultSet rs = pstmt.executeQuery();
            List<T> results = new ArrayList<>();
            while (rs.next()) {
                final T o = mapRow(rs);
                results.add(o);
            }
            return results;
        } catch (SQLException e) {
            log.error("SQLException!! {}", e.getMessage());
            throw new JdbcTemplateException();
        }
    }

    public T queryForObject(String sql) {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            this.setValues(pstmt);
            final ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
            return null;
        } catch (SQLException e) {
            log.error("SQLException!! {}", e.getMessage());
            throw new JdbcTemplateException();
        }
    }

    abstract void setValues(PreparedStatement pstmt) throws SQLException;
    abstract T mapRow(ResultSet rs) throws SQLException;
}
