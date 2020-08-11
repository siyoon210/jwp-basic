package next.service;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class QuestionServiceTest {
    @Mock
    private final QuestionDao questionDao = new QuestionDao();
    @Mock
    private final AnswerDao answerDao = new AnswerDao();
    @InjectMocks
    private QuestionService questionService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("글쓴이가 로그인한 유저의 name과 같은 경우 삭제가 가능하다.")
    public void deleteQuestionTest_SameWriterAndLoginUserName() {
        //given
        User loginUser = new User("siyoon", "1234", "푸루", "beko@naver.com");
        final int questionId = 1;
        when(questionDao.findById(anyLong()))
                .thenReturn(new Question(questionId, "푸루", "답변이 안달려서 슬픈 질문", "제발 답변좀 해주세요", new Date(), 0));

        //when
        final Question question = questionService.deleteQuestion(questionId, loginUser);

        //then
        assertThat(question.getQuestionId()).isEqualTo(questionId);
        verify(questionDao, times(1)).delete(questionId);
    }

    @Test
    @DisplayName("글쓴이가 로그인한 유저의 name과 다른 경우 삭제가 불가능하다.")
    public void deleteQuestionTest_DifferentWriterAndLoginUserName() {
        //given
        User loginUser = new User("siyoon", "1234", "배코", "beko@naver.com");
        when(questionDao.findById(anyLong()))
                .thenReturn(new Question(1, "푸루", "답변이 안달려서 슬픈 질문", "제발 답변좀 해주세요", new Date(), 0));

        //when
        //then
        assertThrows(RuntimeException.class, () -> questionService.deleteQuestion(1, loginUser));
    }

    @Test
    @DisplayName("답변이 없는 경우엔 삭제가 가능하다.")
    public void deleteQuestionTest_DoesNotHaveAnyAnswer() {
        //given
        User loginUser = new User("siyoon", "1234", "푸루", "beko@naver.com");
        final int questionId = 1;
        when(questionDao.findById(questionId))
                .thenReturn(new Question(questionId, "푸루", "답변이 안달려서 슬픈 질문", "제발 답변좀 해주세요", new Date(), 0));
        when(answerDao.findAllByQuestionId(questionId))
                .thenReturn(Lists.emptyList());

        //when
        final Question question = questionService.deleteQuestion(questionId, loginUser);

        //then
        assertThat(question.getQuestionId()).isEqualTo(questionId);
        verify(questionDao, times(1)).delete(questionId);
    }

    @Test
    @DisplayName("답변이 있는 경우엔 답변자가 모두 질문자와 동일한 경우에 삭제 가능하다.")
    public void deleteQuestionTest_questionWriterAndAnswerWriterAreAllSame() {
        //given
        User loginUser = new User("siyoon", "1234", "푸루", "beko@naver.com");
        final int questionId = 1;
        when(questionDao.findById(questionId))
                .thenReturn(new Question(questionId, "푸루", "답변이 안달려서 슬픈 질문", "제발 답변좀 해주세요", new Date(), 0));

        when(answerDao.findAllByQuestionId(questionId))
                .thenReturn(getNoOneAnswers());

        //when
        final Question question = questionService.deleteQuestion(questionId, loginUser);

        //then
        assertThat(question.getQuestionId()).isEqualTo(questionId);
        verify(questionDao, times(1)).delete(questionId);
    }

    @Test
    @DisplayName("답변이 있는 경우엔 질문자와 답변자가 다른 답변이 하나라도 있는 경우 삭제 불가")
    public void deleteQuestionTest_questionWriterAndAnswerWriterAreDifferent() {
        //given
        User loginUser = new User("siyoon", "1234", "푸루", "beko@naver.com");
        final int questionId = 1;
        when(questionDao.findById(questionId))
                .thenReturn(new Question(questionId, "푸루", "답변이 안달려서 슬픈 질문", "제발 답변좀 해주세요", new Date(), 0));

        when(answerDao.findAllByQuestionId(questionId))
                .thenReturn(getAnsweredAnswers());

        //when
        //then
        assertThrows(RuntimeException.class, () -> questionService.deleteQuestion(1, loginUser));
    }

    private List<Answer> getAnsweredAnswers() {
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer(1, "배코", "푸루야 잘좀해", new Date(), 1));
        answers.add(new Answer(2, "푸루", "너무해", new Date(), 1));
        return answers;
    }

    private List<Answer> getNoOneAnswers() {
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer(1, "푸루", "여기 아무도 없나요?", new Date(), 1));
        answers.add(new Answer(2, "푸루", "너무해", new Date(), 1));
        return answers;
    }
}