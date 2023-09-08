package tests;

import DAL.DaoQuestion;
import domain.Question;
import domain.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionTests {


    private DaoQuestion dao = new DaoQuestion("jdbc:hsqldb:file:db/mydb");

    @Test
    public void saveQuestion() {
        // Arrange
        Question question = new Question("Math", "What is 2+2", 1);
        List<Response> responses = new ArrayList<>();
        responses.add(new Response("4", true));
        responses.add(new Response("5", false));
        question.setResponses(responses);

        // Act
        Question returnedQuestion = dao.insertQuestion(question, 10);
        List<Response> returnedResponses = returnedQuestion.getResponses();

        // Assert
        assertEquals(1, returnedQuestion.getDifficulty());
        assertEquals(true, returnedResponses.get(0).getCorrect());
        assertEquals("5", returnedResponses.get(1).getText());
    }

    @Test
    public void updateQuestion() {
        // Arrange
        Question updatedQuestion = new Question("physics", "mass–energy equivalence formula", 4);
        List<Response> updatedResponses = new ArrayList<>();
        updatedResponses.add(new Response("E=mc^2", true));
        updatedResponses.add(new Response("E=hf", false));
        updatedQuestion.setResponses(updatedResponses);

        // Act
        dao.updateQuestion(updatedQuestion, 20);
        Question returnedQuestion = dao.findQuestionById(20);
        List<Response> returnedResponses = returnedQuestion.getResponses();

        // Assert
        assertEquals(4, returnedQuestion.getDifficulty());
        assertEquals(2, returnedResponses.size());
        assertEquals(true, returnedResponses.get(0).getCorrect());
        assertEquals("E=hf", returnedResponses.get(1).getText());

    }

    @Test
    public void deleteQuestion() {
        // Arrange
        Question question = new Question("test", "content", 5);
        int questionId = dao.insertQuestion(question, 10).getId();

        // Act
        dao.deleteQuestion(questionId);
        Question emptyQuestion = dao.findQuestionById(questionId);

        // Assert
        assertEquals(null, emptyQuestion);
    }

    @Test
    public void findQuestionByTopic() {
        // Arrange
        dao.insertQuestion(new Question("Topic", "question", 1), 10);
        dao.insertQuestion(new Question("ToPiC", "question", 2), 10);
        dao.insertQuestion(new Question("NotTopic", "question", 3), 10);


        // Act
        List<Question> questions = dao.findByTopic("topic");

        // Assert
        assertEquals(2, questions.size());
        assertEquals(2, questions.get(1).getDifficulty());

    }

}
