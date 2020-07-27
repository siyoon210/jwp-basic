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

public class JdbcTemplate<T> {
    private static final Logger log = LoggerFactory.getLogger(JdbcTemplate.class);

    public void update(String sql, PreparedStatementSetter pstmtSetter) {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmtSetter.setValues(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("SQLException!! {}", e.getMessage());
            throw new JdbcTemplateException();
        }
    }

    public void update(String sql, Object... objects) {
        update(sql, createPreparedStatementSetter(objects));
    }

    public PreparedStatementSetter createPreparedStatementSetter(Object... objects) {
        return (PreparedStatement pstmt) -> {
            for (int i = 1; i <= objects.length; i++) {
                pstmt.setObject(i, objects[i - 1]);
            }
        };
    }

    public List<T> query(String sql, RowMapper<T> rowMapper, PreparedStatementSetter pstmtSetter) {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmtSetter.setValues(pstmt);
            final ResultSet rs = pstmt.executeQuery();
            List<T> results = new ArrayList<>();
            while (rs.next()) {
                final T o = rowMapper.mapRow(rs);
                results.add(o);
            }
            return results;
        } catch (SQLException e) {
            log.error("SQLException!! {}", e.getMessage());
            throw new JdbcTemplateException();
        }
    }

    public List<T> query(String sql, RowMapper<T> rowMapper, Object... objects) {
        return query(sql, rowMapper, createPreparedStatementSetter(objects));
    }

    public T queryForObject(String sql, RowMapper<T> rowMapper, PreparedStatementSetter pstmtSetter) {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmtSetter.setValues(pstmt);
            final ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rowMapper.mapRow(rs);
            }
            return null;
        } catch (SQLException e) {
            log.error("SQLException!! {}", e.getMessage());
            throw new JdbcTemplateException();
        }
    }

    public T queryForObject(String sql, RowMapper<T> rowMapper, Object... objects) {
        return queryForObject(sql, rowMapper, createPreparedStatementSetter(objects));
    }
}
