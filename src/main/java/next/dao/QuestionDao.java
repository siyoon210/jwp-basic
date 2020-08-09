package next.dao;

import core.jdbc.*;
import next.model.Question;

import java.sql.*;
import java.util.List;

public class QuestionDao {
    public Question insert(Question question) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO QUESTIONS " + 
                "(writer, title, contents, createdDate) " + 
                " VALUES (?, ?, ?, ?)";
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, question.getWriter());
                pstmt.setString(2, question.getTitle());
                pstmt.setString(3, question.getContents());
                pstmt.setTimestamp(4, new Timestamp(question.getTimeFromCreateDate()));
                return pstmt;
            }
        };

        KeyHolder keyHolder = new KeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        return findById(keyHolder.getId());
    }
    
    public List<Question> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT questionId, writer, title, createdDate, countOfAnswer FROM QUESTIONS "
                + "order by questionId desc";

        RowMapper<Question> rm = new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet rs) throws SQLException {
                return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"), null,
                        rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));
            }

        };

        return jdbcTemplate.query(sql, rm);
    }

    public Question findById(long questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS "
                + "WHERE questionId = ?";

        RowMapper<Question> rm = new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet rs) throws SQLException {
                return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"),
                        rs.getString("contents"), rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));
            }
        };

        return jdbcTemplate.queryForObject(sql, rm, questionId);
    }

    public void increaseCountOfAnswer(long questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE QUESTIONS SET countOfAnswer = countOfAnswer + 1 WHERE questionId = ?1";

        PreparedStatementSetter ps = pstmt -> {
            pstmt.setObject(1, questionId);
        };

        jdbcTemplate.update(sql, ps);
    }

    public void decreaseCountOfAnswer(long questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE QUESTIONS SET countOfAnswer = countOfAnswer - 1 WHERE questionId = ?1";

        PreparedStatementSetter ps = pstmt -> {
            pstmt.setObject(1, questionId);
        };

        jdbcTemplate.update(sql, ps);
    }
}
