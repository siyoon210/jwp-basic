package next.service;

import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;

public class QuestionService {
    private final QuestionDao questionDao;

    public QuestionService(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public Question deleteQuestion(long questionId, User loginUser) {
        final Question question = questionDao.findById(questionId);

        if (!question.getWriter().equals(loginUser.getName())) {
            throw new RuntimeException();
        }

        return question;
    }
}
