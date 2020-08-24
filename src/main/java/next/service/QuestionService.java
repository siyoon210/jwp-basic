package next.service;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;

import java.util.List;
import java.util.Objects;

public class QuestionService {
    private final QuestionDao questionDao;
    private final AnswerDao answerDao;

    public QuestionService(QuestionDao questionDao, AnswerDao answerDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    public Question deleteQuestion(long questionId, User loginUser) {
        if (Objects.isNull(loginUser)) {
            throw  new RuntimeException();
        }

        final Question question = questionDao.findById(questionId);

        if (!question.getWriter().equals(loginUser.getName())) {
            throw new RuntimeException();
        }

        final List<Answer> allByQuestionId = answerDao.findAllByQuestionId(questionId);

        if (allByQuestionId.stream()
                .anyMatch(a -> !question.getWriter().equals(a.getWriter()))) {
            throw new RuntimeException();
        }

        questionDao.delete(question.getQuestionId());
        return question;
    }
}
